package com.dotori.golababdiscord.domain.discord.view;

import com.dotori.golababdiscord.domain.authorize.dto.DomainValidatedUserDto;
import com.dotori.golababdiscord.domain.authorize.enum_type.FailureReason;
import com.dotori.golababdiscord.domain.discord.dto.*;
import com.dotori.golababdiscord.domain.discord.exception.UnknownFailureReasonException;
import net.dv8tion.jda.api.utils.MarkdownUtil;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class MessageViewsImpl implements MessageViews{
    @Override
    public MessageDto generateRequestAuthorizeMessage() {
        return null;
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
    public MessageDto generateAuthorizeFailureEmail() {
        TitleDto title = new TitleDto("인증메일이 전송중 문제가 발생하였습니다!");
        String description = "관리자에게 문의해주세요!";
        Color color = new Color(32, 205, 55);
        AuthorDto author = new AuthorDto("Dotori 전공동아리");
        FooterDto footer = new FooterDto("");

        return new MessageDto(title, description, color, author, footer);
    }

    @Override
    public MessageDto generateAuthorizedEmail() {
        return null;
    }

    @Override
    public MessageDto generateAuthorizeFailureMessage(FailureReason reason) {
        MessageDto dto;
        switch (reason) {
            case ALREADY_ENROLLED:
                TitleDto title = new TitleDto("이미 인증된 이메일입니다!");
                String description = "이미 해당 계정으로 인증된 유저가 있습니다";
                Color color = new Color(32, 205, 55);
                AuthorDto author = new AuthorDto("Dotori 전공동아리");
                FooterDto footer = new FooterDto("");
                dto = new MessageDto(title, description, color, author, footer);
                String checklist = getChecklist();
                dto.addSection(new SectionDto("다음중 하나를 확인해주세요", checklist, false));
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
}
