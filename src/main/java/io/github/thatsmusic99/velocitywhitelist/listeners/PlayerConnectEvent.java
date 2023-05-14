package io.github.thatsmusic99.velocitywhitelist.listeners;

import com.velocitypowered.api.event.ResultedEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.LoginEvent;
import io.github.thatsmusic99.velocitywhitelist.config.WhitelistConfig;
import io.github.thatsmusic99.velocitywhitelist.config.WhitelistList;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class PlayerConnectEvent {

    @Subscribe
    public void onPlayerConnect(LoginEvent event) {
        if (!event.getResult().isAllowed()) return;

        // If the whitelist isn't on, stop there
        if (!WhitelistConfig.get().WHITELIST_ENABLED) return;

        // Check if the user is whitelisted, if not, stop them
        if (WhitelistList.get().WHITELISTED_NAMES.contains(event.getPlayer().getUsername())) return;

        event.setResult(ResultedEvent.ComponentResult.denied(MiniMessage.miniMessage().deserialize(WhitelistConfig.get().ACCESS_DENIED_MESSAGE)));
    }
}
