package net.minebo.lobby;

import lombok.Getter;
import net.minebo.cobalt.scoreboard.ScoreboardHandler;
import net.minebo.lobby.cobalt.ScoreboardImpl;
import org.bukkit.plugin.java.JavaPlugin;

public class Lobby extends JavaPlugin {

    public static Lobby instance;

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();

        new ScoreboardHandler(new ScoreboardImpl(), this);

    }

    public void registerListeners() {

    }

}
