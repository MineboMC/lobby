package net.minebo.lobby.fun;

import net.minebo.lobby.Lobby;
import net.minebo.lobby.fun.listener.DoubleJumpListener;
import net.minebo.lobby.fun.listener.EnderButtListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class FunManager {

    public static void init() {
        registerListeners();
    }

    public static void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new DoubleJumpListener(), Lobby.instance);
        Bukkit.getPluginManager().registerEvents(new EnderButtListener(), Lobby.instance);
    }

}
