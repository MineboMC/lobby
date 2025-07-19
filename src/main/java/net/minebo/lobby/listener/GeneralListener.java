package net.minebo.lobby.listener;

import net.minebo.lobby.Lobby;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class GeneralListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        if(Lobby.instance.getConfig().getBoolean("welcome-message.enabled")) {
            Lobby.instance.getConfig().getStringList("welcome-message.lines").forEach(m -> {
                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', m));
            });
        }
    }

}
