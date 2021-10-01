package com.dotori.golababdiscord.domain.discord.listeners;

import com.dotori.golababdiscord.domain.discord.SogoBot;
import com.dotori.golababdiscord.domain.discord.listeners.handler.RoleRemoveHandler;
import net.dv8tion.jda.api.events.role.RoleDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class RoleRemoveListener extends ListenerAdapter {
    private final RoleRemoveHandler roleRemoveHandler;

    public RoleRemoveListener(RoleRemoveHandler roleRemoveHandler, SogoBot sogoBot) {
        this.roleRemoveHandler = roleRemoveHandler;
        sogoBot.addEventListener(this);
    }

    @Override
    public void onRoleDelete(@NotNull RoleDeleteEvent event) {
        roleRemoveHandler.handleEvent(event);
    }
}
