package com.dotori.golababdiscord.domain.vote.service;

import com.dotori.golababdiscord.domain.api.dto.RequestDayVoteResultDto;
import com.dotori.golababdiscord.domain.api.dto.RequestMealVoteResultDto;
import com.dotori.golababdiscord.domain.api.dto.ResponseMealMenuDto;
import com.dotori.golababdiscord.domain.api.service.LunchApiService;
import com.dotori.golababdiscord.domain.api.service.VoteApiService;
import com.dotori.golababdiscord.domain.discord.SogoBot;
import com.dotori.golababdiscord.domain.discord.dto.MessageDto;
import com.dotori.golababdiscord.domain.discord.dto.ReceiverDto;
import com.dotori.golababdiscord.domain.discord.service.MessageSenderService;
import com.dotori.golababdiscord.domain.discord.view.MessageViews;
import com.dotori.golababdiscord.domain.vote.dto.*;
import com.dotori.golababdiscord.domain.vote.entity.InProgressVote;
import com.dotori.golababdiscord.domain.vote.entity.Menu;
import com.dotori.golababdiscord.domain.vote.enum_type.MealType;
import com.dotori.golababdiscord.domain.vote.enum_type.VoteEmoji;
import com.dotori.golababdiscord.domain.vote.repository.InProgressVoteRepository;
import com.dotori.golababdiscord.domain.vote.repository.MenuRepository;
import com.dotori.golababdiscord.global.exception.DateParseFailureException;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoteServiceImpl implements VoteService{
    private final LunchApiService lunchApiService;
    private final VoteApiService voteApiService;
    private final MessageSenderService messageSenderService;
    private final MessageViews messageViews;
    private final InProgressVoteRepository inProgressVoteRepository;
    private final MenuRepository menuRepository;
    private final SogoBot sogoBot;

    @Lazy
    public VoteServiceImpl(LunchApiService lunchApiService, VoteApiService voteApiService, MessageSenderService messageSenderService, MessageViews messageViews, InProgressVoteRepository inProgressVoteRepository, MenuRepository menuRepository, SogoBot sogoBot) {
        this.lunchApiService = lunchApiService;
        this.voteApiService = voteApiService;
        this.messageSenderService = messageSenderService;
        this.messageViews = messageViews;
        this.inProgressVoteRepository = inProgressVoteRepository;
        this.menuRepository = menuRepository;
        this.sogoBot = sogoBot;
    }

    @Override
    public VoteDto createNewVote(MealType meal) {
        ResponseMealMenuDto menus = lunchApiService.getMealToday(meal);
        return new VoteDto(now(), meal, menus);
    }
    private Date now() {
        Date from = new Date();
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = transFormat.format(from);
        try {
            return transFormat.parse(date);
        } catch (ParseException e) {
            throw new DateParseFailureException();
        }
    }

    @Override
    public InProgressVoteDto openVote(VoteDto vote) {
        Long messageId = sendVoteMessageAndGetId(vote);
        return vote.inProgress(messageId);
    }
    private Long sendVoteMessageAndGetId(VoteDto vote) {
        TextChannel channel = sogoBot.getVoteChannel();

        MessageDto message = messageViews.generateVoteOpenedMessage(vote);
        ReceiverDto receiver = new ReceiverDto(channel);

        return messageSenderService.sendMessage(receiver, message);
    }

    @Override
    public VoteResultGroupDto calculateVoteResult(InProgressVoteGroupDto dto) {
        VoteResultDto breakfast = new VoteResultDto();
        VoteResultDto lunch = new VoteResultDto();
        VoteResultDto dinner = new VoteResultDto();

        calculateMealVoteResult(dto.getBreakfast(), breakfast);
        calculateMealVoteResult(dto.getLunch(), lunch);
        calculateMealVoteResult(dto.getDinner(), dinner);

        return new VoteResultGroupDto(breakfast, lunch, dinner);
    }
    private void calculateMealVoteResult(InProgressVoteMemberDto dto, VoteResultDto result) {
        Message message = sogoBot.getMessageById(dto.getVoteMessageId());
            message.getReactions().forEach(reaction -> { //모든 종류의 반응을 불러와 reaction 에 하나씩 담는다
                for (VoteEmoji emoji : VoteEmoji.values()) {  //모든 종류의 VoteEmoji 를 불러와 emoji 에 하나씩 담는다
                    if(reaction.getReactionEmote().getName().equals(emoji.getEmoji())) {  //reaction 이 vote emoji 인지 검사한다
                        String menuName = dto.getMenus().get(emoji.getId()); //메뉴 이름을 구한다
                        Integer numOfVote = reaction.getCount() - 1; //bot 이 단 이모지 제외

                        result.put(menuName, numOfVote);
                    }
                }
            });
    }

    @Override
    public void closeVote(InProgressVoteGroupDto vote) {
        close(vote.getBreakfast());
        close(vote.getLunch());
        close(vote.getDinner());
    }
    private void close(InProgressVoteMemberDto dto) {
        Message message = sogoBot.getMessageById(dto.getVoteMessageId());
        messageSenderService.clearReactions(message);
        messageSenderService.editMessage(message, messageViews.generateVoteClosedMessage());
    }

    @Override
    public void sendVoteResult(VoteResultGroupDto result) {
        RequestMealVoteResultDto breakfast = new RequestMealVoteResultDto(result.getBreakfast());
        RequestMealVoteResultDto lunch = new RequestMealVoteResultDto(result.getLunch());
        RequestMealVoteResultDto dinner = new RequestMealVoteResultDto(result.getDinner());

        RequestDayVoteResultDto requestDto = new RequestDayVoteResultDto(breakfast, lunch, dinner);
        voteApiService.collectTotalVoteAtDay(requestDto);
    }

    @Override @Transactional
    public void save(InProgressVoteDto inProgressVote) {
        inProgressVoteRepository.save(new InProgressVote(
                inProgressVote.getVoteMessageId(),
                inProgressVote.getVoteDate(),
                inProgressVote.getMeal()));
        inProgressVote.getMenus().forEach(
                menuStr -> {
                    Menu menu = new Menu();
                    menu.setMenuName(menuStr);
                    menu.setVoteMessageId(inProgressVote.getVoteMessageId());
                    menuRepository.save(menu);
                }
        );
    }

    @Override
    public InProgressVoteDto getInProgressVote(Date to, MealType meal) {
        InProgressVote vote = inProgressVoteRepository.getByVoteDateAndMeal(to, meal);
        List<String> menus = getMenusByEntity(vote);

        return new InProgressVoteDto(vote.getVoteDate(), vote.getMeal(), menus, vote.getVoteMessageId());
    }

    private List<String> getMenusByEntity(InProgressVote vote) {
        List<Menu> menus = menuRepository.getAllByVoteMessageId(vote.getVoteMessageId());
        return menus.stream()
                .map(Menu::getMenuName)
                .collect(Collectors.toList());
    }
}
