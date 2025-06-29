package net.minebo.lobby.selector;

import net.minebo.basalt.models.queue.QueueModel;
import net.minebo.basalt.models.server.UniqueServer;
import net.minebo.basalt.service.queue.QueueService;
import net.minebo.basalt.service.server.UniqueServerService;
import net.minebo.cobalt.menu.construct.Button;
import net.minebo.cobalt.menu.construct.Menu;
import net.minebo.cobalt.util.ColorUtil;
import net.minebo.cobalt.util.format.NumberFormatting;
import net.minebo.cobalt.util.format.StringFormatting;
import net.minebo.lobby.Lobby;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class SelectorHandler {

    static FileConfiguration config = Lobby.instance.getConfig();

    public static Menu createMenu(Player player) {
        Menu menu = new Menu().setTitle(config.getString("selector.title"))
                .setSize(config.getInt("selector.size"));

        for(String key : config.getConfigurationSection("selector.queues").getKeys(false)) {
            String pkey = "selector.queues." + key + ".";

            String name = config.getString(pkey + "name");
            String id = config.getString(pkey + "queue-id");
            int slot = config.getInt(pkey + "slot");
            List<String> desc = config.getStringList(pkey + "description");

            menu.setButton(slot, createButton(player, name, id, Material.valueOf(config.getString(pkey + "material")), desc));
        }

        return menu;
    }

    public static Button createButton(Player player, String name, String id, Material material, List<String> desc) {
        Button button = new Button();

        UniqueServer server = UniqueServerService.INSTANCE.byId(id);
        QueueModel queue;

        try {
            queue = QueueService.INSTANCE.byId(server.getQueueName()).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        button.setName(ChatColor.GOLD + ColorUtil.translateColors(name));
        button.setMaterial(material);

        List<String> description = desc;

        if(server != null) {
            if (server.getOnline()) {
                description.add("");
                description.add("Status: " + ChatColor.GREEN + "Online");
                description.add("Online: " + ChatColor.YELLOW + NumberFormatting.addCommas(server.getPlayers().size()));

                if(queue.containsPlayer(player.getUniqueId())) {
                    description.add("");
                    description.add("You are position #" + ChatColor.AQUA + queue.getPosition(player.getUniqueId()) + ChatColor.WHITE + " of " + ChatColor.AQUA + NumberFormatting.addCommas(queue.getPlayersInQueue().size()));
                    description.add(ChatColor.RED + "Right click to leave queue!");
                } else {
                    description.add("");
                    description.add(ChatColor.YELLOW + "Click to join queue!");
                }
            } else {
                description.add("");
                description.add("Status: " + (ChatColor.RED + "Offline"));
            }
        } else {
            description.add("");
            description.add("Status: " + (ChatColor.RED + "Offline"));
        }

        button.setLines(() -> description);

        button.addClickAction(ClickType.LEFT, p -> p.performCommand("joinqueue " + server.getQueueName()));
        button.addClickAction(ClickType.RIGHT, p -> p.performCommand("leavequeue"));

        return button;
    }
}
