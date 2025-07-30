package net.minebo.lobby.listener;

import net.minebo.lobby.Lobby;
import net.minebo.lobby.hotbar.HotbarManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.PlayerInventory;

public class LobbyListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);

        resetPlayer(e.getPlayer());

        Bukkit.getOnlinePlayers().forEach(player -> {
            if(HotbarManager.hasPlayersHidden(player)) {
                e.getPlayer().hidePlayer(player);
            }
        });

        for (Player players : Bukkit.getOnlinePlayers()) {
            players.showPlayer(players);
        }
        if(Lobby.instance.getConfig().getBoolean("welcome-message.enabled")) {
            Lobby.instance.getConfig().getStringList("welcome-message.lines").forEach(m -> {
                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', m).replace("%player%", e.getPlayer().getDisplayName()));
            });
        }

    }

    public void resetPlayer(Player player) {
        player.setWalkSpeed(0.5f);
        player.setFoodLevel(20);
        player.setMaxHealth(20.0);
        player.setHealth(20.0);
        player.teleport(Bukkit.getServer().getWorld("world").getSpawnLocation());
        player.setExp(0); // Change if we add network level later.
        PlayerInventory playerInventory = player.getInventory();
        playerInventory.clear();
        playerInventory.setHelmet(null);
        playerInventory.setChestplate(null);
        playerInventory.setLeggings(null);
        playerInventory.setBoots(null);
    }

}
