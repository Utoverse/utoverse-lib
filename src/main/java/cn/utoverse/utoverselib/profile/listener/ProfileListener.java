package cn.utoverse.utoverselib.profile.listener;

import cn.utoverse.utoverselib.AbstractUtoverseLibPlugin;
import cn.utoverse.utoverselib.profile.listener.handler.JoinEventHandler;
import cn.utoverse.utoverselib.profile.listener.handler.QuitEventHandler;
import lombok.AllArgsConstructor;
import me.lucko.helper.Events;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.annotation.Nonnull;

@AllArgsConstructor
public class ProfileListener implements TerminableModule {
    private AbstractUtoverseLibPlugin plugin;

    @Override
    public void setup(@Nonnull TerminableConsumer consumer) {
        Events.subscribe(PlayerJoinEvent.class)
                .handler(new JoinEventHandler(plugin))
                .bindWith(consumer);

        Events.subscribe(PlayerQuitEvent.class)
                .handler(new QuitEventHandler(plugin))
                .bindWith(consumer);
    }
}
