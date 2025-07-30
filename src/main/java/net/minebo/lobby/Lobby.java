package net.minebo.lobby;

import net.minebo.cobalt.menu.MenuHandler;
import net.minebo.cobalt.scoreboard.ScoreboardHandler;
import net.minebo.lobby.cobalt.ScoreboardImpl;
import net.minebo.lobby.fun.FunManager;
import net.minebo.lobby.hotbar.HotbarManager;
import net.minebo.lobby.listener.LobbyListener;
import net.minebo.lobby.listener.PreventionListener;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class Lobby extends JavaPlugin {

    public static Lobby instance;

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();

        MenuHandler.init(this);

        new ScoreboardHandler(new ScoreboardImpl(), this);

        World world = Bukkit.getWorld("world");
        world.setTime(1000);
        world.setStorm(false);
        world.setThundering(false);
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);

        registerListeners();

        HotbarManager.init();
        FunManager.init();
    }

    public void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new LobbyListener(), this);
        Bukkit.getPluginManager().registerEvents(new PreventionListener(), this);
    }

}
