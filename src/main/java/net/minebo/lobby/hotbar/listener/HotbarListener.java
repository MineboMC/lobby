package net.minebo.lobby.hotbar.listener;

import net.minebo.lobby.hotbar.HotbarManager;
import net.minebo.lobby.hotbar.menu.SelectorMenu;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class HotbarListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.getInventory().setItem(0, HotbarManager.SELECTOR);
        player.getInventory().setItem(7, HotbarManager.ENDER_BUTT);
        player.getInventory().setItem(8, HotbarManager.HIDE_PLAYERS);
    }

    @EventHandler
    public void onItemUse(PlayerInteractEvent e) {
        if((e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) && e.getPlayer().getItemInHand() == null) {
            return;
        }

        if(e.getItem() == null) {
            return;
        }

        HotbarManager.doInteraction(e.getPlayer(), e.getItem());
    }

}
