package net.minebo.lobby.fun.listener;

import lombok.Getter;
import net.minebo.lobby.Lobby;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DoubleJumpListener implements Listener {

    @Getter
    private static Map<UUID, Boolean> allowSneakJump = new HashMap<>();

    @EventHandler(priority = EventPriority.NORMAL)
    public void onToggleFlight(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if(player.getGameMode() == GameMode.CREATIVE)
            return;
        if(player.isFlying()) {
            allowSneakJump.put(player.getUniqueId(), true);
            player.setFlying(false);
            player.setAllowFlight(false);
            player.setVelocity(player.getLocation().getDirection().multiply(2).setY(1.0D));
            player.playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 20);
            player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 20, 6F / 63F);
            player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 126F / 63F);
            player.playSound(player.getLocation(), Sound.ENTITY_WITHER_SHOOT, 1, 126F / 63F);
            player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_HURT, 1, 63F / 63F);
        }
        if(player.isOnGround() && !player.getAllowFlight()){
            player.setAllowFlight(true);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        final Player player = event.getPlayer();
        if (player.isOnGround() || player.getAllowFlight() || allowSneakJump.get(player.getUniqueId()) == null || !allowSneakJump.get(player.getUniqueId())) {
            return;
        }
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Lobby.instance, () -> player.setVelocity(player.getLocation().getDirection().multiply(4)), 1L);
        player.playSound(player.getLocation(),Sound.ENTITY_WITHER_HURT,10.5F,8.5F);
        player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 5.0F, 2.0F);
        player.playSound(player.getLocation(),Sound.ENTITY_BAT_AMBIENT,4.5F,3.5F);
        allowSneakJump.put(player.getUniqueId(), false);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        if (event.getCause() != EntityDamageEvent.DamageCause.FALL) {
            return;
        }
        final Player player = (Player)event.getEntity();
        player.setAllowFlight(true);
        allowSneakJump.put(player.getUniqueId(), true);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!event.getPlayer().isOnGround()) {
            return;
        }
        if (event.getPlayer().getAllowFlight()) {
            return;
        }
        event.getPlayer().setAllowFlight(true);
        allowSneakJump.put(event.getPlayer().getUniqueId(), true);
    }

}
