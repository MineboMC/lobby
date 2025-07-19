package net.minebo.lobby.cobalt;

import net.minebo.basalt.api.BasaltAPI;
import net.minebo.basalt.models.profile.GameProfile;
import net.minebo.basalt.models.queue.QueueModel;
import net.minebo.basalt.service.queue.QueueService;
import net.minebo.basalt.util.NetworkUtil;
import net.minebo.cobalt.scoreboard.provider.ScoreboardProvider;
import net.minebo.cobalt.util.format.NumberFormatting;
import net.minebo.lobby.Lobby;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardImpl extends ScoreboardProvider {

    @Override
    public String getTitle(Player player) {
        return Lobby.instance.getConfig().getString("scoreboard.title");
    }

    @Override
    public List<String> getLines(Player player) {

        List<String> lines = new ArrayList<String>();

        GameProfile profile = BasaltAPI.INSTANCE.syncFindProfile(player.getUniqueId());

        NetworkUtil.INSTANCE.getPlayerCount("ALL");

        if(QueueService.INSTANCE.playerAlreadyQueued(player.getUniqueId()) != null) {
            Lobby.instance.getConfig().getStringList("scoreboard.queued-lines").forEach(line -> {
                lines.add(replaceQueuePlaceholders(player, QueueService.INSTANCE.playerAlreadyQueued(player.getUniqueId()), line));
            });
        } else {
            Lobby.instance.getConfig().getStringList("scoreboard.lines").forEach(line -> {
                lines.add(replacePlaceholders(player, line));
            });
        }

        return lines;
    }

    public String replacePlaceholders(Player player, String line) {
        return line.replace("%players%", NumberFormatting.addCommas(getGlobalPlayerCount()))
                   .replace("%rank%", BasaltAPI.INSTANCE.getPlayerRankString(player.getUniqueId()));
    }

    public String replaceQueuePlaceholders(Player player, QueueModel queueModel, String line) {
        String s = replacePlaceholders(player, line);

        if(queueModel != null) {
            s = s.replace("%queue%", queueModel.getDisplayName());
            s = s.replace("%place%", NumberFormatting.addCommas(queueModel.getPosition(player.getUniqueId())));
            s = s.replace("%total%", NumberFormatting.addCommas(queueModel.getPlayersInQueue().size()));
        }

        return s;
    }

    public Integer getGlobalPlayerCount() {
        Integer onlinePlayers = 0;

        for(Integer i : NetworkUtil.INSTANCE.getPlayerCounts().values()) {
            onlinePlayers += i;
        }

        return onlinePlayers;
    }

}
