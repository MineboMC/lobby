package net.minebo.lobby.hotbar.menu;

import net.minebo.cobalt.menu.construct.Menu;
import net.minebo.lobby.Lobby;
import net.minebo.lobby.hotbar.menu.button.ServerButton;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class SelectorMenu extends Menu {

    static FileConfiguration config = Lobby.instance.getConfig();

    public SelectorMenu(Player player) {
        setTitle(config.getString("selector.title"));
        setSize(config.getInt("selector.size"));
        setAutoUpdate(true);
        setUpdateAfterClick(true);

        for(String key : config.getConfigurationSection("selector.queues").getKeys(false)) {
            String pkey = "selector.queues." + key + ".";

            String name = config.getString(pkey + "name");
            String id = config.getString(pkey + "queue-id");
            int slot = config.getInt(pkey + "slot");
            List<String> desc = config.getStringList(pkey + "description");

            setButton(slot, new ServerButton(player, name, id, Material.valueOf(config.getString(pkey + "item")), desc));
        }
    }

}
