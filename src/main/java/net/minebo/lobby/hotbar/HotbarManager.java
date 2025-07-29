package net.minebo.lobby.hotbar;

import net.minebo.cobalt.util.ItemBuilder;
import net.minebo.lobby.Lobby;
import net.minebo.lobby.hotbar.listener.HotbarListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class HotbarManager {

    public static ItemStack SELECTOR = new ItemBuilder(Material.COMPASS).setName(ChatColor.YELLOW + "Server Selector").build();

    public static ItemStack ENDER_BUTT = new ItemBuilder(Material.ENDER_PEARL).setName(ChatColor.LIGHT_PURPLE + "Ender Butt").build();

    public static void init() {
        Bukkit.getPluginManager().registerEvents(new HotbarListener(), Lobby.instance);
    }


}
