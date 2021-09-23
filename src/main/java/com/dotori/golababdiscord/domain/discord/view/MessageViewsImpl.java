package com.dotori.golababdiscord.domain.discord.view;

import com.dotori.golababdiscord.domain.authorize.dto.DomainValidatedUserDto;
import com.dotori.golababdiscord.domain.authorize.enum_type.FailureReason;
import com.dotori.golababdiscord.domain.discord.dto.*;
import com.dotori.golababdiscord.domain.discord.enum_type.WrongCommandUsageType;
import com.dotori.golababdiscord.domain.discord.exception.UnknownFailureReasonException;
import com.dotori.golababdiscord.domain.vote.dto.VoteDto;
import com.dotori.golababdiscord.domain.vote.enum_type.VoteEmoji;
import net.dv8tion.jda.api.utils.MarkdownUtil;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class MessageViewsImpl implements MessageViews{

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
        TitleDto title = new TitleDto("SW마이스터 고등학교 재학생이신가요?", "https://mail.google.com/mail");
        String description = "소고야 인증 `이름` `이메일` 명령어를 통해 여러분이 SW마이스터고 재학생임을 인증해주세요!";
        Color color = new Color(32, 205, 55);
        AuthorDto author = new AuthorDto("Dotori 전공동아리");
        FooterDto footer = new FooterDto("");

        MessageDto dto = new MessageDto(title, description, color, author, footer);
        dto.addSection(new SectionDto("", "인증을 마치셔야 골라밥 투표 및 그외 다양한 서비스들을 이용하실 수 있습니다!", false));
        return dto;
    }

    @Override
    public MessageDto generateRequestSchoolEmailMessage() {
        return null;
    }

    @Override
    public MessageDto generateMailSentMessage(DomainValidatedUserDto domainValidatedUser) {
        TitleDto title = new TitleDto("인증메일이 송신되었습니다!", "https://mail.google.com/mail");
        String description = "학교 이메일을 확인해주세요";
        Color color = new Color(32, 205, 55);
        AuthorDto author = new AuthorDto("Dotori 전공동아리");
        FooterDto footer = new FooterDto("");

        MessageDto dto = new MessageDto(title, description, color, author, footer);
        dto.addSection(new SectionDto("이메일이 오지 않으셧나요?", "스팸함과 전체보관함을 확인해주세요!", false));
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
        String description = "더욱 퀄리티 높은 급식을 위해 가장 맛있었던 메뉴에 투표해주세요!";
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
        String description = message;
        Color color = new Color(217, 17, 62);
        AuthorDto author = new AuthorDto("Dotori 전공동아리");
        FooterDto footer = new FooterDto("", "");

        return new MessageDto(title, description, color, author, footer);
    }
}
