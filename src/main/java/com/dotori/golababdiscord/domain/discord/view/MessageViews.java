package com.dotori.golababdiscord.domain.discord.view;

import com.dotori.golababdiscord.domain.authorize.dto.DomainValidatedUserDto;
import com.dotori.golababdiscord.domain.authorize.enum_type.FailureReason;
import com.dotori.golababdiscord.domain.discord.dto.MessageDto;

public interface MessageViews {
    MessageDto generateRequestAuthorizeMessage();
    MessageDto generateRequestSchoolEmailMessage();
    MessageDto generateMailSentMessage(DomainValidatedUserDto domainValidatedUser);
    MessageDto generateAuthorizeFailureEmail();
    MessageDto generateAuthorizedEmail();
    MessageDto generateAuthorizeFailureMessage(FailureReason alreadyEnrolled);
    MessageDto generateAuthorizedMessage();
}