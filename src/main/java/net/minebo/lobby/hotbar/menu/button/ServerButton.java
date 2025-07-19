package net.minebo.lobby.hotbar.menu.button;

import lombok.AllArgsConstructor;
import net.minebo.basalt.models.queue.QueueModel;
import net.minebo.basalt.models.server.UniqueServer;
import net.minebo.basalt.service.queue.QueueService;
import net.minebo.basalt.service.server.UniqueServerService;
import net.minebo.cobalt.menu.construct.Button;
import net.minebo.cobalt.util.format.NumberFormatting;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ServerButton extends Button {

    Player player;

    String name;
    String id;

    Material material;
    List<String> lore;

    public ServerButton(Player player, String name, String id, Material material, List<String> lore) {
        this.player = player;
        this.name = name;
        this.id = id;
        this.material = material;
        this.lore = lore;

        setName(() -> ChatColor.GOLD + name);
        setMaterial(material);
        setLines(() -> getDescription(player));
        addClickAction(ClickType.LEFT, p -> p.performCommand("joinqueue " + id));
        addClickAction(ClickType.RIGHT, p -> p.performCommand("leavequeue"));
    }

    public List<String> getDescription(Player player) {

        UniqueServer server = UniqueServerService.INSTANCE.byId(id);
        QueueModel queue;

        try {
            queue = QueueService.INSTANCE.byId(server.getQueueName()).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        List<String> description = new ArrayList<>(lore);

        if(server != null) {
            if (server.getOnline()) {
                description.add("");
                description.add("&fStatus: " + ChatColor.GREEN + "Online");
                description.add("&fOnline: " + ChatColor.YELLOW + NumberFormatting.addCommas(server.getPlayers().size()));

                if(queue.containsPlayer(player.getUniqueId())) {
                    description.add("");
                    description.add("&eYou are position #" + ChatColor.WHITE + queue.getPosition(player.getUniqueId()) + ChatColor.YELLOW + " of " + ChatColor.WHITE + NumberFormatting.addCommas(queue.getPlayersInQueue().size()) + ChatColor.YELLOW + "!");
                    description.add(ChatColor.RED + "Right click to leave queue!");
                } else {
                    description.add("");
                    description.add(ChatColor.YELLOW + "Click to join queue!");
                }
            } else {
                description.add("");
                description.add("&fStatus: " + (ChatColor.RED + "Offline"));
            }
        } else {
            description.add("");
            description.add("&fStatus: " + (ChatColor.RED + "Offline"));
        }

        return description;
    }

}
