package com.dotori.golababdiscord.domain.command.node;

import com.dotori.golababdiscord.domain.discord.exception.ChildNotFoundException;
import com.dotori.golababdiscord.domain.discord.exception.DuplicateTriggerException;
import com.dotori.golababdiscord.domain.command.trigger.CommandTrigger;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
//커맨드 노드 클래스
public abstract class Command {
    @Getter @Setter
    private int depth;//노드의 깊이
    @Getter(AccessLevel.PROTECTED)//해당노드의 모든 자식 노드들을 가져오는 Getter
    private final List<Command> children;//자식 노드들
    protected final CommandTrigger commandTrigger;//커맨드 트리거

    //기본 생성자
    public Command(String prefix) {
        this(new CommandTrigger(prefix));
    }
    //생성자
    private Command(CommandTrigger commandTrigger) {
        this(commandTrigger, new ArrayList<>());
    }
    private Command(CommandTrigger commandTrigger, List<Command> children) {
        this.commandTrigger = commandTrigger;
        this.children = children;
    }

    //커맨드 실행시 할 작업을 명시해야하는 추상메서드
    protected abstract void run(User user, MessageChannel channel, String args);

    //자식 노드를 추가하는 메서드
    public void addChild(Command command) {
        boolean isTriggerDuplicate = !checkTriggerUnique(command);//중복되는 트리거가 있는지 확인
        if(!isTriggerDuplicate)//중복되는 트리거가 없다면
            children.add(command);//자식 노드를 추가
        else throw new DuplicateTriggerException(command.commandTrigger);//중복되는 트리거가 있다면 DuplicateTriggerException 예외를 발생시킨다.
    }

    //트리거가 중복되는지 확인하는 메서드 (중복시 false 반환)
    private boolean checkTriggerUnique(Command command) {
        return this.children.stream().noneMatch(//자식 노드들을 스트림으로 받아서 아래 조건에 부합하는 노드가 있는지 확인
                child -> child.commandTrigger.equals(command.commandTrigger));//자식 노드의 트리거와 새로운 트리거가 같은가
    }

    //커맨드 및 하위커맨드를 실행하는 메서드 (run 은 입력된 커맨드가 해당 커맨드의 하위커맨드일 가능성이 없다는 전제하에 구현)
    public boolean execute(User user, MessageChannel channel, String args) {
        new Executor(children)//Executor 클래스를 생성하고
                .user(user)//유저를 전달하고
                .channel(channel)//채널을 전달하고
                .execute(args);//인자와 함께실행한다.
        return true;//실행이 성공하면(예외가 발생하지 않으면) true 반환
    }

    //자식노드의 깊이를 초기화하는 재귀메서드
    public void initDepth(int depth) {
        setDepth(depth);//해당 커맨드의 깊이를 지정한다
        getChildren().forEach(child -> child.initDepth(depth+1));//모든 자식노드를 이터레이션하여 각 자식노드의 해당 메서드에 기존 깊이에 1 더한 인자를 주어 실행한다
    }

    //커맨드를 실행시켜주는 클래스
    @RequiredArgsConstructor
    private class Executor {
        private final List<Command> children;//자식노드
        private User user;//커맨드를 입력한 유저
        private MessageChannel channel;//커맨드가 입력된 채널

        //커맨드를 입력한 유저를 지정하는 메서드
        public Executor user(User user) {
            this.user = user;
            return this;
        }

        //커맨드가 입력된 채널을 지정하는 메서드
        public Executor channel(MessageChannel channel) {
            this.channel = channel;
            return this;
        }

        //인자와 함께 커맨드를 실행하는 메서드
        public void execute(String args) {
            if (executeChildIfExists(args)) {
                String childPrefix = getChildPrefixByCommandArgs(args);
                executeChildCommand(childPrefix, removeFirstArg(args));
            } else run(user, channel, args);
        }

        //입력된 커멘드가 자식노드의 커멘드인지 검사하는 메서드
        private boolean executeChildIfExists(String args) {
            String inputChildPrefix = getChildPrefixByCommandArgs(args);//입력된 커맨드에서 자식노드인지를 검사할 인자를 추출한다 (해당 커맨드 접두어 바로 다음 인자)
            return checkChildTriggers(inputChildPrefix);//자식노드의 커맨드인지 여부를 반환한다
        }

        //자식노드의 트리거를 검사하는 메서드
        private boolean checkChildTriggers(String inputChildPrefix) {
            return children.stream().anyMatch(//자식 노드들을 스트림으로 받아서 아래 조건에 부합하는 노드가 있는지 확인
                    command -> command.commandTrigger.checkTrigger(inputChildPrefix));//커맨드의 트리거가 발동되었는가 (true 를 반환하였는가)
        }

        //자식노드의 커맨드를 실행하는 메서드
        private void executeChildCommand(String childPrefix, String childArgs) {
            Command child = getChildCommandByPrefix(childPrefix);//자식 커맨드를 가져온다
            child.execute(user, channel, childArgs);//가져온 자식커맨드를 실행한다
        }

        //자식커맨드의 접두어로 추정되는 문자열을 통해 자식커맨드를 가져오는 메서드
        private Command getChildCommandByPrefix(String childPrefix) {
            Optional<Command> child = children.stream().filter(//자식노드들을 스트림으로 받아서 아래 조건에 부합하는 요소만을 추출한다
                    command -> command.commandTrigger.checkTrigger(childPrefix)//커맨드의 트리거가 발동되었는가 (true 를 반환하였는가)
            ).findAny();//조건에 부합하는 커맨드중 하나를 가져온다 (이미 커맨드 등록작업에서 트리거 중복은 제거되었으므로 필터링된 스트림의 요소의 개수는 0~1개이다)

            if (child.isEmpty()) throw new ChildNotFoundException(); //만약 해당 접두어를 가진 자식커맨드가 존재하지 않을경우, 예외를 발생시킨다
            //TODO 해당 메서드의 책임에 위반되지 않는지 생각해보기(해당메서드의 책임은 그저 접두어로 추정되는 문자열을 가져오는것이지 없을경우는 해당사항이아니다)
            return child.get();//자식커맨드를 반환한다
        }

        //커멘드 인자에서 자식커맨드의 접두어로 추정되는 문자열을 가져오는 메서드
        private String getChildPrefixByCommandArgs(String args) {
            return args.split(" ")[0];
        }


        //현재 노드의 커멘드로 전달된 인자를 통해 자식노드의 커멘드에게 전달할 인자를 생성한다
        private String removeFirstArg(String args) {
            if(!args.contains(" ")) return "";//만약 자식노드의 커맨드로 전달할 인자가 존재하지 않을경우 빈 문자열을 반환한다
            return args.substring(args.indexOf(" ") + 1);//해당 커맨드를 지칭하는 접두어를 제거한 문자열을 반환한다
        }
    }
}
