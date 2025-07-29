package net.minebo.lobby.hotbar;

import net.minebo.cobalt.util.ItemBuilder;
import net.minebo.lobby.Lobby;
import net.minebo.lobby.hotbar.listener.HotbarListener;
import net.minebo.lobby.hotbar.menu.SelectorMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class HotbarManager {

    public static HashMap<Player, Boolean> playerVisibiltyMap;

    public static ItemStack SELECTOR = new ItemBuilder(Material.COMPASS).setName(ChatColor.YELLOW + "Server Selector").build();
    public static ItemStack ENDER_BUTT = new ItemBuilder(Material.ENDER_PEARL).setName(ChatColor.LIGHT_PURPLE + "Ender Butt").build();
    public static ItemStack HIDE_PLAYERS = new ItemBuilder(Material.GRAY_DYE).setName(ChatColor.GRAY + "Hide Players").build();
    public static ItemStack SHOW_PLAYERS = new ItemBuilder(Material.LIME_DYE).setName(ChatColor.GREEN + "Show Players").build();

    public static void init() {
        playerVisibiltyMap = new HashMap<>();

        Bukkit.getPluginManager().registerEvents(new HotbarListener(), Lobby.instance);
    }

    public static void doInteraction(Player player, ItemStack item) {
        if(item.isSimilar(SELECTOR)) {
            new SelectorMenu(player).openMenu(player);
        } else if(item.isSimilar(HIDE_PLAYERS)) {
            Bukkit.getOnlinePlayers().forEach(player::hidePlayer);
            player.sendMessage(ChatColor.YELLOW + "You have hidden all other players.");
            player.getInventory().setItem(8, SHOW_PLAYERS);
        } else if(item.isSimilar(SHOW_PLAYERS)) {
            Bukkit.getOnlinePlayers().forEach(player::showPlayer);
            player.sendMessage(ChatColor.YELLOW + "You have shown all other players.");
            player.getInventory().setItem(8, HIDE_PLAYERS);
        }
    }

    public static Boolean hasPlayersHidden(Player player) {
        return (playerVisibiltyMap.getOrDefault(player, false));
    }

}
