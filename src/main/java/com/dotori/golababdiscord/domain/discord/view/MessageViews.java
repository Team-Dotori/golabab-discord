package com.dotori.golababdiscord.domain.discord.view;

import com.dotori.golababdiscord.domain.authorize.dto.DomainValidatedUserDto;
import com.dotori.golababdiscord.domain.authorize.enum_type.FailureReason;
import com.dotori.golababdiscord.domain.discord.dto.MessageDto;
import com.dotori.golababdiscord.domain.discord.enum_type.WrongCommandUsageType;
import com.dotori.golababdiscord.domain.vote.dto.VoteDto;

public interface MessageViews {
    //Command
    MessageDto generateWrongCommandUsageMessage(WrongCommandUsageType usageType, String args, String usage);
    MessageDto generateArgumentNotFoundMessage();

    //Authorize
    MessageDto generateRequestAuthorizeMessage();
    MessageDto generateRequestSchoolEmailMessage();
    MessageDto generateMailSentMessage(DomainValidatedUserDto domainValidatedUser);
    MessageDto generateAuthorizeFailureMessage();
    MessageDto generateAuthorizeFailureMessage(FailureReason alreadyEnrolled);
    MessageDto generateAuthorizedMessage();
    //Vote
    MessageDto generateVoteOpenedMessage(VoteDto vote);
    MessageDto generateVoteClosedMessage();
    MessageDto generateAlreadyVoteMessage();
    //Tiptic
    MessageDto generateTipticMessage(String message);
}