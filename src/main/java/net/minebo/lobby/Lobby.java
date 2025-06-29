package net.minebo.lobby;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class Lobby extends JavaPlugin {

    public static Lobby instance;

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();

    }

    public void registerListeners() {

    }

}
