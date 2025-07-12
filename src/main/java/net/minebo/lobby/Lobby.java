package net.minebo.lobby;

import lombok.Getter;
import net.minebo.cobalt.menu.MenuHandler;
import net.minebo.cobalt.scoreboard.ScoreboardHandler;
import net.minebo.lobby.cobalt.ScoreboardImpl;
import net.minebo.lobby.hotbar.HotbarManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Lobby extends JavaPlugin {

    public static Lobby instance;

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();

        MenuHandler.init(this);

        new ScoreboardHandler(new ScoreboardImpl(), this);

        HotbarManager.init();

    }

    public void registerListeners() {

    }

}
