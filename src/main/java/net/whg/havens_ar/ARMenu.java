package net.whg.havens_ar;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import net.whg.utils.math.Quaternion;
import net.whg.utils.math.Transform;
import net.whg.utils.math.Vec3f;

/**
 * Represents an active menu instance that is currently being displayed to a
 * player.
 */
public class ARMenu implements Listener {
    private final List<ARMenuComponent> components = new ArrayList<>();
    private final Transform transform = new Transform();
    private final Player player;
    private final UUID uuid;
    private boolean valid = true;

    /**
     * Creates a new ARMenu. This will also register this menu to begin listening
     * for relevant events. This will create the menu centered around the player's
     * current position and rotation.
     * 
     * @param player - The player that is looking at this menu.
     */
    public ARMenu(Player player) {
        this.player = player;

        uuid = UUID.randomUUID();
        moveMenuToPlayer(true);

        var plugin = Bukkit.getPluginManager().getPlugin("HavensAR");
        Bukkit.getPluginManager().registerEvents(this, plugin);

        HavensAR.logInfo("Opened menu for %s. (UUID: %s)", player.getName(), uuid);
    }

    /**
     * Adds a new menu component to this menu instance. This method should only be
     * called by ARMenuOption instances.
     * 
     * @param component - The menu component to add.
     * @throws IllegalStateException If this menu has already been closed.
     */
    void addOption(ARMenuComponent component) {
        if (!valid)
            throw new IllegalStateException("Menu is not valid!");

        components.add(component);
        component.getTransform().setParent(transform);
    }

    /**
     * Removes a menu component from this menu instance. This method should only be
     * called by ARMenuOption instances.
     * 
     * @param component - The menu component to remove.
     */
    void removeOption(ARMenuComponent component) {
        components.remove(component);
        component.getTransform().setParent(null);
    }

    /**
     * Closes this menu instance and disposes all contained menu components. Marks
     * this menu instance as invalid. This will also remove all listeners attached
     * to this menu.
     */
    public void closeInstance() {
        if (!valid)
            return;

        HavensAR.logInfo("Closing menu for %s. (UUID: %s, Options: %s)", player.getName(), uuid, components.size());

        valid = false;
        for (var option : new ArrayList<>(components))
            option.dispose();

        HandlerList.unregisterAll(this);
    }

    /**
     * Gets the player that is currently looking at this menu.
     * 
     * @return The player.
     */
    Player getPlayer() {
        return player;
    }

    /**
     * Gets whether or not this menu instance is currently valid.
     * 
     * @return True if this menu is valid. False otherwise.
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * When the player looking at this menu leaves the server, close this menu
     * instance.
     * 
     * @param e - The event.
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        if (e.getPlayer() == player)
            closeInstance();
    }

    /**
     * When the player looking at this menu teleports around, close this menu
     * instance.
     * 
     * @param e - The event.
     */
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        if (e.getPlayer() == player)
            closeInstance();
    }

    /**
     * When the player looking at this menu moves, update all menu options to follow
     * the player.
     * 
     * @param e - The event.
     */
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (e.getPlayer() != player)
            return;

        moveMenuToPlayer(false);
    }

    /**
     * Moves the menu to the player's current position.
     * 
     * @param rotate - Whether or not to also change the menu's yaw rotation.
     */
    private void moveMenuToPlayer(boolean rotate) {
        var location = player.getLocation();
        var pos = new Vec3f((float) location.getX(), (float) location.getY(), (float) location.getZ());
        transform.setLocalPosition(pos);

        if (rotate) {
            var rot = Quaternion.euler(location.getYaw(), 0, 0);
            transform.setLocalRotation(rot);
        }
    }

    /**
     * Gets the UUID of this menu instance.
     * 
     * @return The UUID;
     */
    public UUID getUUID() {
        return uuid;
    }
}
