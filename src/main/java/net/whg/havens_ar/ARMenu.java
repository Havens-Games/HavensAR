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

/**
 * Represents an active menu instance that is currently being displayed to a
 * player.
 */
public class ARMenu implements Listener {
    private final List<ARMenuOption> options = new ArrayList<>();
    private final Player player;
    private final UUID uuid;
    private boolean valid = true;

    /**
     * Creates a new ARMenu. This will also register this menu to begin listening
     * for relevant events.
     * 
     * @param player - The player that is looking at this menu.
     */
    public ARMenu(Player player) {
        this.player = player;

        uuid = UUID.randomUUID();

        var plugin = Bukkit.getPluginManager().getPlugin("HavensAR");
        Bukkit.getPluginManager().registerEvents(this, plugin);

        HavensAR.logInfo("Opened menu for %s. (UUID: %s)", player.getName(), uuid);
    }

    /**
     * Adds a new menu option to this menu instance. This method should only be
     * called by ARMenuOption instances.
     * 
     * @param option - The menu option to add.
     * @throws IllegalStateException If this menu has already been closed.
     */
    void addOption(ARMenuOption option) {
        if (!valid)
            throw new IllegalStateException("Menu is not valid!");

        options.add(option);
    }

    /**
     * Removes a menu option from this menu instance. This method should only be
     * called by ARMenuOption instances.
     * 
     * @param option - The menu option to remove.
     */
    void removeOption(ARMenuOption option) {
        options.remove(option);
    }

    /**
     * Closes this menu instance and disposes all contained menu options. Marks this
     * menu instance as invalid. This will also remove all listeners attached to
     * this menu.
     */
    public void closeInstance() {
        if (!valid)
            return;

        HavensAR.logInfo("Closing menu for %s. (UUID: %s, Options: %s)", player.getName(), uuid, options.size());

        valid = false;
        for (var option : new ArrayList<>(options))
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
        if (e.getPlayer() != e)
            return;

        for (var option : options)
            option.updatePositions();
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
