package com.dotori.golababdiscord.domain.discord.command.function;

import com.dotori.golababdiscord.domain.authorize.dto.DomainValidatedUserDto;
import com.dotori.golababdiscord.domain.authorize.dto.UnValidatedUserDto;
import com.dotori.golababdiscord.domain.authorize.service.AuthorizeService;
import com.dotori.golababdiscord.domain.discord.command.LeafCommand;
import com.dotori.golababdiscord.domain.discord.dto.ReceiverDto;
import com.dotori.golababdiscord.domain.discord.exception.WrongArgumentException;
import com.dotori.golababdiscord.domain.discord.service.MessageSenderService;
import com.dotori.golababdiscord.domain.discord.view.MessageViews;
import com.dotori.golababdiscord.infra.service.MailService;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

public class AuthorizeCommand extends LeafCommand {
    private final MailService mailService;
    private final MessageSenderService messageSenderService;
    private final AuthorizeService authorizeService;
    private final MessageViews messageViews;
    private final SpringTemplateEngine templateEngine;

    public AuthorizeCommand(String prefix, MailService mailService, MessageSenderService messageSenderService, AuthorizeService authorizeService, MessageViews messageViews, SpringTemplateEngine templateEngine) {
        super(prefix);
        this.mailService = mailService;
        this.messageSenderService = messageSenderService;
        this.authorizeService = authorizeService;
        this.messageViews = messageViews;
        this.templateEngine = templateEngine;
    }

    @Override//소고야 인증 실명 이메일
    protected void run(User user, MessageChannel channel, String args) {
        if(!checkArgs(args)) throw new WrongArgumentException(args);

        UnValidatedUserDto unValidatedUser = getUnValidatedUser(user, args);
        DomainValidatedUserDto domainValidatedUser = validateDomain(unValidatedUser, args);
        startValidateAuthorizeEmail(domainValidatedUser, channel);
    }

    private void startValidateAuthorizeEmail(DomainValidatedUserDto domainValidatedUser, MessageChannel channel) {
        sendAuthorizeMail(domainValidatedUser);
        sendRequestCheckMailMessage(domainValidatedUser, channel);
    }

    private void sendRequestCheckMailMessage(DomainValidatedUserDto domainValidatedUser, MessageChannel channel) {
        messageSenderService.sendMessage(
                new ReceiverDto(channel),
                messageViews.generateMailSentMessage(domainValidatedUser));
    }

    private void sendAuthorizeMail(DomainValidatedUserDto domainValidatedUser) {
        String authorizeLink = authorizeService.generateAuthorizeLink(domainValidatedUser);

        String name = domainValidatedUser.getName();
        String email = domainValidatedUser.getEmail();
        String subject = getAuthorizeMailSubject();
        String content = getAuthorizeMailContentByLink(authorizeLink, name);

        mailService.sendHtmlEmail(email, subject, content);
    }

    private String getAuthorizeMailSubject() {
        return "[소고봇] SW마이스터고 학생이신가요?";
    }

    private String getAuthorizeMailContentByLink(String authorizeLink, String name) {
        Context context = new Context();
        context.setVariable("link", authorizeLink);
        context.setVariable("name", name);

        return templateEngine.process("authorize/authorize-mail", context);
    }

    private UnValidatedUserDto getUnValidatedUser(User user, String args) {
        String name = getNameByArgs(args);
        return new UnValidatedUserDto(user.getIdLong(), name);
    }

    private DomainValidatedUserDto validateDomain(UnValidatedUserDto unValidatedUser, String args) {
        String email = getEmailByArgs(args);
        return authorizeService.validateDomain(unValidatedUser, email);
    }

    private boolean checkArgs(String args) {
        return args != null &&
                !args.equals("") &&
                (args.length() >= 2);
    }

    private String getNameByArgs(String args) {
        return args.split(" ")[0];
    }
    private String getEmailByArgs(String args) {
        return args.split(" ")[1];
    }
}
