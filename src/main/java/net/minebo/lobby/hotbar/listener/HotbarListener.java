package net.minebo.lobby.hotbar.listener;

import net.minebo.cosmetics.cosmetics.CosmeticHandler;
import net.minebo.lobby.hotbar.HotbarManager;
import net.minebo.lobby.hotbar.menu.SelectorMenu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;

public class HotbarListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.getInventory().setItem(0, HotbarManager.SELECTOR);

        if(Bukkit.getPluginManager().isPluginEnabled("Cosmetics")) {
            player.getInventory().setItem(4, HotbarManager.COSMETICS);
        }

        player.getInventory().setItem(7, HotbarManager.HIDE_PLAYERS);
        player.getInventory().setItem(8, HotbarManager.ENDER_BUTT);
    }

    @EventHandler
    public void onItemUse(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            return;
        }

        if(e.getHand() != EquipmentSlot.HAND) {
            return;
        }

        if(e.getItem() == null) {
            return;
        }

        HotbarManager.doInteraction(e.getPlayer(), e.getItem());
    }

}
