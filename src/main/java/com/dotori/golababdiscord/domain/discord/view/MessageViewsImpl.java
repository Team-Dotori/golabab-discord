package com.dotori.golababdiscord.domain.discord.view;

import com.dotori.golababdiscord.domain.authorize.dto.DomainValidatedUserDto;
import com.dotori.golababdiscord.domain.authorize.enum_type.FailureReason;
import com.dotori.golababdiscord.domain.discord.dto.*;
import com.dotori.golababdiscord.domain.discord.enum_type.WrongCommandUsageType;
import com.dotori.golababdiscord.domain.discord.exception.UnknownFailureReasonException;
import com.dotori.golababdiscord.domain.permission.enum_type.Feature;
import com.dotori.golababdiscord.domain.permission.enum_type.SogoPermission;
import com.dotori.golababdiscord.domain.ranking.dto.RankingDto;
import com.dotori.golababdiscord.domain.ranking.dto.RequestRankingDto;
import com.dotori.golababdiscord.domain.vote.dto.VoteDto;
import com.dotori.golababdiscord.domain.vote.enum_type.VoteEmoji;
import com.dotori.golababdiscord.global.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.utils.MarkdownUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.List;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@Component
@RequiredArgsConstructor
public class MessageViewsImpl implements MessageViews{
    @Value("${bot.command-prefix}")
    private String rootPrefix;

    @Override
    public MessageDto generateWrongCommandUsageMessage(WrongCommandUsageType usageType, String args, String usage) {
        TitleDto title = new TitleDto("올바른 명령어 사용법이 아닙니다!", "");
        Color color = new Color(32, 205, 55);
        AuthorDto author = new AuthorDto("Dotori 전공동아리");
        FooterDto footer = new FooterDto("");

        MessageDto dto = new MessageDto(title, args, color, author, footer);
        dto.addSection(new SectionDto("어떻게 사용해야할까요?", MarkdownUtil.codeblock(usage), false));
        return dto;
    }

    @Override
    public MessageDto generateArgumentNotFoundMessage() {
        TitleDto title = new TitleDto("명령어를 찾을 수 없습니다!", "");
        String description = "올바른 명령어를 입력해주세요!";
        Color color = new Color(32, 205, 55);
        AuthorDto author = new AuthorDto("Dotori 전공동아리");
        FooterDto footer = new FooterDto("");

        return new MessageDto(title, description, color, author, footer);
    }

    @Override
    public MessageDto generateRequestAuthorizeMessage() {
        TitleDto title = new TitleDto("SW마이스터 고등학교 재학생이신가요?");
        String description = rootPrefix + " 인증 <이름> <이메일> 명령어를 통해 여러분이 SW마이스터고 재학생임을 인증해주세요!";
        Color color = new Color(32, 205, 55);
        AuthorDto author = new AuthorDto("Dotori 전공동아리");
        FooterDto footer = new FooterDto("");

        MessageDto dto = new MessageDto(title, description, color, author, footer);
        dto.addSection(new SectionDto("", "인증을 마치셔야 골라밥 투표 및 그외 다양한 서비스들을 이용하실 수 있습니다!", false));
        return dto;
    }

    @Override
    public MessageDto generateMailSentMessage(DomainValidatedUserDto domainValidatedUser) {
        TitleDto title = new TitleDto("인증메일이 송신되었습니다!", "https://mail.google.com/mail");
        String description = "학교 이메일을 확인해주세요";
        Color color = new Color(32, 205, 55);
        AuthorDto author = new AuthorDto("Dotori 전공동아리");
        FooterDto footer = new FooterDto("");

        MessageDto dto = new MessageDto(title, description, color, author, footer);
        dto.addSection(new SectionDto("이메일이 오지 않으셨나요?", "스팸함과 전체보관함을 확인해주세요!", false));
        return dto;
    }

    @Override
    public MessageDto generateAuthorizeFailureMessage() {
        TitleDto title = new TitleDto("인증메일이 전송중 문제가 발생하였습니다!");
        String description = "관리자에게 문의해주세요!";
        Color color = new Color(32, 205, 55);
        AuthorDto author = new AuthorDto("Dotori 전공동아리");
        FooterDto footer = new FooterDto("");

        return new MessageDto(title, description, color, author, footer);
    }

    @Override
    public MessageDto generateAuthorizeFailureMessage(FailureReason reason) {
        MessageDto dto;
        Color color = new Color(217, 17, 62);
        switch (reason) {
            case ALREADY_ENROLLED:
                TitleDto title = new TitleDto("이미 인증된 이메일입니다!");
                String description = "이미 해당 계정으로 인증된 유저가 있습니다";
                AuthorDto author = new AuthorDto("Dotori 전공동아리");
                FooterDto footer = new FooterDto("");
                dto = new MessageDto(title, description, color, author, footer);
                String checklist = getChecklist();
                dto.addSection(new SectionDto("다음중 하나를 확인해주세요", checklist, false));
                break;
            case DOMAIN_IS_NOT_SCHOOL_DOMAIN:
                title = new TitleDto("학교 이메일이 아닙니다!");
                description = "학교에서 발급한 이메일로 다시시도해주세요!";
                author = new AuthorDto("Dotori 전공동아리");
                footer = new FooterDto("");
                dto = new MessageDto(title, description, color, author, footer);
                break;
            default:
                throw new UnknownFailureReasonException(reason);
        }
        return dto;
    }

    private String getChecklist() {
        return MarkdownUtil.codeblock(
                String.format(
                "- %s\n- %s\n- %s",
                "이미 이전에 인증하셧나요?",
                "이메일을 잘못입력하셧나요?",
                "부계정이신가요?"
                ));
    }

    @Override
    public MessageDto generateAuthorizedMessage() {
        TitleDto title = new TitleDto("인증이 완료되었습니다!");
        String description = "소고봇에 가입하신것을 환영합니다!";
        Color color = new Color(32, 205, 55);
        AuthorDto author = new AuthorDto("Dotori 전공동아리");
        FooterDto footer = new FooterDto("", "");

        return new MessageDto(title, description, color, author, footer);
    }

    @Override
    public MessageDto generateVoteOpenedMessage(VoteDto vote) {
        TitleDto title = new TitleDto(String.format("오늘%s 어떠셧나요?", vote.getMeal().getKorean()));
        String description = "더욱 퀄리티높은 급식을 위해 가장 맛있었던 메뉴에 투표해주세요!";
        Color color = new Color(32, 205, 55);
        AuthorDto author = new AuthorDto("Dotori 전공동아리");
        FooterDto footer = new FooterDto("", "");

        MessageDto dto = new MessageDto(title, description, color, author, footer);
        for (int i = 0; i < vote.getMenus().size(); i++) {
            String emoji = VoteEmoji.of(i).getEmoji();
            dto.addSection(new SectionDto("", emoji + " " + vote.getMenus().get(i), false));
            dto.addEmoji(emoji);
        }
        return dto;
    }

    @Override
    public MessageDto generateVoteClosedMessage() {
        TitleDto title = new TitleDto("이미 투표가 종료되었습니다!");
        String description = "다음 투표에 참여해주세요!";
        Color color = new Color(217, 17, 62);
        AuthorDto author = new AuthorDto("Dotori 전공동아리");
        FooterDto footer = new FooterDto("", "");

        return new MessageDto(title, description, color, author, footer);
    }

    @Override
    public MessageDto generateAlreadyVoteMessage() {
        TitleDto title = new TitleDto("이미 해당 투표에 참여하셧습니다!");
        String description = "기존 투표이모지를 취소하고 시도해주세요!";
        Color color = new Color(217, 17, 62);
        AuthorDto author = new AuthorDto("Dotori 전공동아리");
        FooterDto footer = new FooterDto("", "");

        return new MessageDto(title, description, color, author, footer);
    }

    @Override
    public MessageDto generateTipticMessage(String message) {
        TitleDto title = new TitleDto("이거 아셧나요?");
        Color color = new Color(217, 17, 62);
        AuthorDto author = new AuthorDto("Dotori 전공동아리");
        FooterDto footer = new FooterDto("", "");

        return new MessageDto(title, message, color, author, footer);
    }

    @Override
    public MessageDto generatePermissionDeniedMessage(SogoPermission permission, Feature feature) {
        TitleDto title = new TitleDto("권한이 없습니다!");
        String description = feature.getDisplay() + "기능을 이용하기 위해선 " + permission.getFirstByFeature(feature).getDisplay() + "이상의 권한이 필요합니다";
        Color color = new Color(217, 17, 62);
        AuthorDto author = new AuthorDto("Dotori 전공동아리");
        FooterDto footer = new FooterDto("", "");

        return new MessageDto(title, description, color, author, footer);
    }

    @Override
    public MessageDto generateChannelChangedMessage(User user) {
        TitleDto title = new TitleDto("투표채널을 성공적으로 변경하였습니다!");
        String description = user.getAsMention() + "이제 해당 채널에서 투표에 참여하실 수 있습니다!";
        Color color = new Color(32, 205, 55);
        AuthorDto author = new AuthorDto("Dotori 전공동아리");
        FooterDto footer = new FooterDto("", "");

        return new MessageDto(title, description, color, author, footer);
    }

    @Override
    public MessageDto generateCheckChannelMessage(User user) {
        TitleDto title = new TitleDto("여기에요 여기!");
        String description = String.format("%s님, 여기가 바로 공식 투표채널입니다", user.getAsMention());
        Color color = new Color(32, 205, 55);
        AuthorDto author = new AuthorDto("Dotori 전공동아리");
        FooterDto footer = new FooterDto("", "");

        return new MessageDto(title, description, color, author, footer);
    }

    @Override
    public MessageDto generateCheckChannelAlarmMessage() {
        TitleDto title = new TitleDto("알림을 확인해주세요!");
        String description = "투표채널에서 이용자님을 멘션하였습니다!";
        Color color = new Color(32, 205, 55);
        AuthorDto author = new AuthorDto("Dotori 전공동아리");
        FooterDto footer = new FooterDto("", "");

        return new MessageDto(title, description, color, author, footer);
    }

    @Override
    public MessageDto generateRankingMessage(RequestRankingDto ranking) {
        TitleDto title = new TitleDto("투표 결과가 도착했어요!");
        String description = "과연 어떤 메뉴가 1위를 차지했을까요?";
        Color color = new Color(32, 75, 205);
        AuthorDto author = new AuthorDto("Dotori 전공동아리");
        FooterDto footer = new FooterDto("", "");

        MessageDto message = new MessageDto(title, description, color, author, footer);

        List<RankingDto> rankings = ranking.getList();
        for (int i = 0; i < 5; i++) {
            RankingDto menu = rankings.get(i);
            message.addSection(
                    new SectionDto(String.format("%d위, %s", i+1, menu.getMenuName()), menu.getNumOfVote() + "표", true));
        }

        int maxNumOfVote = rankings.get(0).getNumOfVote();
        for(int i = 5; i < rankings.size(); i++) {
            RankingDto menu = rankings.get(i);
            int percent = (menu.getNumOfVote() * 100) / maxNumOfVote;
            message.addSection(
                    new SectionDto(menu.getMenuName(), String.format("%23s%s표", getGraph(percent), menu.getNumOfVote()), false));
        }

        return message;
    }

    public String getGraph(int percent) {
        StringBuilder sb = new StringBuilder();
        sb.append("▉".repeat(Math.max(0, percent / 5)));
        switch(percent % 5) {
            case 4:
                sb.append("▊");
                break;
            case 3:
                sb.append("▋");
                break;
            case 2:
                sb.append("▍");
                break;
            case 1:
                sb.append("▏");
                break;
        }
        return sb.toString();
    }
}
