package net.minebo.lobby.cobalt;

import net.minebo.basalt.api.BasaltAPI;
import net.minebo.basalt.models.profile.GameProfile;
import net.minebo.basalt.models.queue.QueueModel;
import net.minebo.basalt.models.ranks.Rank;
import net.minebo.basalt.models.server.UniqueServer;
import net.minebo.basalt.service.profiles.ProfileGameService;
import net.minebo.basalt.service.queue.QueueService;
import net.minebo.basalt.service.server.UniqueServerService;
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

        if(QueueService.INSTANCE.playerAlreadyQueued(player.getUniqueId()) != null) {
            Lobby.instance.getConfig().getStringList("scoreboard.queued-lines").forEach(line -> {
                lines.add(replaceQueuePlaceholders(player, profile, QueueService.INSTANCE.playerAlreadyQueued(player.getUniqueId()), line));
            });
        } else {
            Lobby.instance.getConfig().getStringList("scoreboard.lines").forEach(line -> {
                lines.add(replacePlaceholders(profile, line));
            });
        }

        return lines;
    }

    public String replacePlaceholders(GameProfile profile, String line) {
        Rank rank = profile.getCurrentRank();

        return line.replace("%players%", NumberFormatting.addCommas(getGlobalPlayerCount()))
                   .replace("%rank%", rank.getColor() + rank.getDisplayName());
    }

    public String replaceQueuePlaceholders(Player player, GameProfile profile, QueueModel queueModel, String line) {
        String s = replacePlaceholders(profile, line);

        if(queueModel != null) {
            s = s.replace("%queue%", queueModel.getDisplayName());
            s = s.replace("%place%", NumberFormatting.addCommas(queueModel.getPosition(player.getUniqueId())));
            s = s.replace("%total%", NumberFormatting.addCommas(queueModel.getPlayersInQueue().size()));
        }

        return s;
    }

    public Integer getGlobalPlayerCount() {
        int onlinePlayers = 0;

        for (UniqueServer s : UniqueServerService.INSTANCE.getServers().values()) {
            onlinePlayers += s.getPlayers().size();
        }

        return onlinePlayers;
    }

}
