package com.dotori.golababdiscord.domain.discord.command.node;

import com.dotori.golababdiscord.domain.discord.exception.ChildNotFoundException;
import com.dotori.golababdiscord.domain.discord.exception.DuplicateTriggerException;
import com.dotori.golababdiscord.domain.discord.command.trigger.CommandTrigger;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Command {
    List<Command> children;
    CommandTrigger commandTrigger;

    public Command(String prefix) {
        this(new CommandTrigger(prefix));
    }
    private Command(CommandTrigger commandTrigger) {
        this(commandTrigger, new ArrayList<>());
    }
    private Command(CommandTrigger commandTrigger, List<Command> children) {
        this.commandTrigger = commandTrigger;
        this.children = children;
    }

    protected abstract void run(User user, MessageChannel channel, String args);

    public void addChild(Command command) {
        boolean isTriggerDuplicate = checkTriggerUnique(command);
        if(isTriggerDuplicate)
            children.add(command);
        else throw new DuplicateTriggerException(command.commandTrigger);
    }

    private boolean checkTriggerUnique(Command command) {
        return this.children.stream().noneMatch(
                child -> child.commandTrigger.equals(command.commandTrigger));
    }

    public void execute(User user, MessageChannel channel, String args) {
        new Executor(children)
                .user(user)
                .channel(channel)
                .execute(args);
    }

    @RequiredArgsConstructor
    private class Executor {
        private final List<Command> children;
        private User user;
        private MessageChannel channel;

        public Executor user(User user) {
            this.user = user;
            return this;
        }

        public Executor channel(MessageChannel channel) {
            this.channel = channel;
            return this;
        }

        public void execute(String args) {
            if (executeChildIfExists(args)) {
                String childPrefix = getChildPrefixByCommandArgs(args);
                executeChildCommand(childPrefix, removeFirstArg(args));
            } else run(user, channel, args);
        }

        private boolean executeChildIfExists(String args) {
            String inputChildPrefix = getChildPrefixByCommandArgs(args);
            return checkChildTriggers(inputChildPrefix);
        }

        private boolean checkChildTriggers(String inputChildPrefix) {
            return children.stream()
                    .anyMatch(command -> command.commandTrigger.checkTrigger(inputChildPrefix));
        }

        private void executeChildCommand(String childPrefix, String childArgs) {
            Command child = getChildCommandByPrefix(childPrefix);
            child.execute(user, channel, childArgs);
        }

        private Command getChildCommandByPrefix(String childPrefix) {
            Optional<Command> child = children.stream().filter(
                    command -> command.commandTrigger.checkTrigger(childPrefix)
            ).findAny();

            if (child.isEmpty()) throw new ChildNotFoundException();//TODO 해당 메서드의 책임에 위반되지 않는지 생각해보기
            return child.get();
        }

        private String getChildPrefixByCommandArgs(String args) {
            return args.split(" ")[0];
        }

        private String removeFirstArg(String args) {
            if(!args.contains(" ")) return "";
            return args.substring(args.indexOf(" ") + 1);
        }
    }
}
