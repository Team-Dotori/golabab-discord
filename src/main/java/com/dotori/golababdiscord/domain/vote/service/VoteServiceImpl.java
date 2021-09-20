package com.dotori.golababdiscord.domain.vote.service;

import com.dotori.golababdiscord.domain.api.dto.RequestDayVoteResultDto;
import com.dotori.golababdiscord.domain.api.dto.ResponseMealMenuDto;
import com.dotori.golababdiscord.domain.api.service.LunchApiService;
import com.dotori.golababdiscord.domain.api.service.VoteApiService;
import com.dotori.golababdiscord.domain.discord.SogoBot;
import com.dotori.golababdiscord.domain.discord.service.MessageSenderService;
import com.dotori.golababdiscord.domain.discord.view.MessageViews;
import com.dotori.golababdiscord.domain.vote.dto.*;
import com.dotori.golababdiscord.domain.vote.entity.InProgressVote;
import com.dotori.golababdiscord.domain.vote.entity.Menu;
import com.dotori.golababdiscord.domain.vote.enum_type.MealType;
import com.dotori.golababdiscord.domain.vote.enum_type.VoteEmoji;
import com.dotori.golababdiscord.domain.vote.repository.InProgressVoteRepository;
import com.dotori.golababdiscord.domain.vote.repository.MenuRepository;
import com.dotori.golababdiscord.global.utils.DateUtils;
import net.dv8tion.jda.api.entities.Message;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public abstract class VoteServiceImpl implements VoteService{
    private final LunchApiService lunchApiService;
    private final VoteApiService voteApiService;
    protected final MessageSenderService messageSenderService;
    protected final MessageViews messageViews;
    private final InProgressVoteRepository inProgressVoteRepository;
    private final MenuRepository menuRepository;
    private final DateUtils dateUtils;
    protected final SogoBot sogoBot;

    public VoteServiceImpl(LunchApiService lunchApiService,
                           VoteApiService voteApiService,
                           MessageSenderService messageSenderService,
                           MessageViews messageViews,
                           InProgressVoteRepository inProgressVoteRepository,
                           MenuRepository menuRepository,
                           DateUtils dateUtils, SogoBot sogoBot) {
        this.lunchApiService = lunchApiService;
        this.voteApiService = voteApiService;
        this.messageSenderService = messageSenderService;
        this.messageViews = messageViews;
        this.inProgressVoteRepository = inProgressVoteRepository;
        this.menuRepository = menuRepository;
        this.dateUtils = dateUtils;
        this.sogoBot = sogoBot;
    }

    @Override
    public VoteDto createNewVote(MealType meal) {
        Date today = dateUtils.getToday();
        ResponseMealMenuDto menus = lunchApiService.getMealToday(meal);
        return new VoteDto(today, meal, menus);
    }

    @Override
    public InProgressVoteDto openVote(VoteDto vote) {
        Long messageId = sendVoteMessageAndGetId(vote);
        return vote.inProgress(messageId);
    }
    protected abstract Long sendVoteMessageAndGetId(VoteDto vote);

    @Override
    public VoteResultGroupDto calculateVoteResult(InProgressVoteGroupDto dto) {
        VoteResultGroupDto result = new VoteResultGroupDto();
        dto.getVotes().forEach(vote -> result.put(vote.getMeal(), calculateMealVoteResult(vote)));
        return result;
    }

    private VoteResultDto calculateMealVoteResult(InProgressVoteDto dto) {
        VoteResultDto result = new VoteResultDto();
        Message message = sogoBot.getMessageById(dto.getVoteMessageId());

        message.getReactions().forEach(reaction -> { //모든 종류의 반응을 불러와 reaction 에 하나씩 담는다
            Arrays.stream(VoteEmoji.values())
                    .filter(emoji -> reaction.getReactionEmote().getName().equals(emoji.getEmoji()))//reaction 중 vote emoji 만 가져온다
                    .forEach(emoji -> {
                        String menuName = dto.getMenus().get(emoji.getId()); //메뉴 이름을 구한다
                        Integer numOfVote = reaction.getCount() - 1; //bot 이 단 이모지 제외
                        result.put(menuName, numOfVote);
                    });
        });
        return result;
    }

    @Override
    public void closeVote(InProgressVoteGroupDto vote) {
        vote.getVotes().forEach(this::close);
    }
    private void close(InProgressVoteDto dto) {
        Long messageId = dto.getVoteMessageId();
        Message message = sogoBot.getMessageById(messageId);

        messageSenderService.clearReactions(message);
        messageSenderService.editMessageToClose(message, messageViews.generateVoteClosedMessage());
    }

    @Override
    public void sendVoteResult(VoteResultGroupDto result) {
        RequestDayVoteResultDto requestDto = new RequestDayVoteResultDto(result);
        voteApiService.collectTotalVoteAtDay(requestDto);
    }

    @Override @Transactional
    public void save(InProgressVoteDto dto) {
        //convert dto to entity
        InProgressVote entity = new InProgressVote(
                dto.getVoteMessageId(),
                dto.getVoteDate(),
                dto.getMeal());

        //save InProgressVote in in_progress_vote schema
        inProgressVoteRepository.save(entity);
        //save menus in menu schema
        dto.getMenus().forEach(
                menuStr -> {
                    Menu menu = new Menu(menuStr, dto.getVoteMessageId());
                    menuRepository.save(menu);
                }
        );
    }

    @Override
    public InProgressVoteDto getInProgressVote(Date today, MealType meal) {
        //date 와 meal 로 InProgressVote entity 를 받는다
        InProgressVote vote = inProgressVoteRepository.getByVoteDateAndMeal(today, meal);
        if(vote == null) return null; //TODO throw new InProgressVoteNotFoundException();
        List<String> menus = getMenusByEntity(vote); //TODO 양방향 매핑 사용해보기

        return new InProgressVoteDto(vote.getVoteDate(), vote.getMeal(), menus, vote.getVoteMessageId());
    }
    private List<String> getMenusByEntity(InProgressVote vote) {
        //vote message id 를 통해 해당 투표에 명시된 모든 메뉴들을 불러온다 (양방향 매핑을 통해 repository
        List<Menu> menus = menuRepository.getAllByVoteMessageId(vote.getVoteMessageId());
        return menus.stream()
                .map(Menu::getMenuName)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isVoteMessage(long messageIdLong) {
        //받은 message id 가 in_progress_vote 스키마에 명시되어있는지 확인한다
        return inProgressVoteRepository.existsByVoteMessageId(messageIdLong);
    }

    @Override
    public List<InProgressVoteDto> getInProgressVotes(Date today) {
        return Arrays.stream(MealType.values())
                .filter(meal -> getInProgressVote(today, meal) != null)
                .map(meal -> getInProgressVote(today, meal))
                .collect(Collectors.toList());

    }
}
