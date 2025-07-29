package net.minebo.lobby.fun.listener;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDismountEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class EnderButtListener implements Listener {

    @EventHandler
    public void onInteract(final PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.hasItem() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            if (e.getItem().getItemMeta() == null) {
                return;
            }
            if (e.getItem().getItemMeta().getDisplayName() == null) {
                return;
            }
            if (e.getItem().getType() != Material.ENDER_PEARL) {
                return;
            }
            if(!(player.getGameMode() == GameMode.CREATIVE)){
                e.setCancelled(true);}
            Projectile pearl = player.launchProjectile(EnderPearl.class, player.getLocation().getDirection().multiply(1.6));
            pearl.setShooter(null);
            pearl.setPassenger(player);
            e.setUseItemInHand(Event.Result.DENY);
            e.setUseInteractedBlock(Event.Result.DENY);
            e.getPlayer().updateInventory();
            try {
                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    @EventHandler
    public void onHit(ProjectileHitEvent event) {
        if (event.getEntity().getType() == EntityType.ENDER_PEARL) {
            if (event.getEntity().getPassenger() != null){
                event.getEntity().getPassenger().teleport(event.getEntity().getPassenger().getLocation().add(0, 1, 0));
            }
            event.getEntity().remove();
        }
    }

    @EventHandler
    public void onDismount(EntityDismountEvent event) {
        if (event.getDismounted().getType() == EntityType.ENDER_PEARL)
            event.getDismounted().remove();
    }

}
