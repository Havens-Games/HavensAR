package net.whg.havens_ar;

import org.bukkit.entity.Player;

/**
 * Represents a selectable menu option within the AR Menu.
 */
public class ARMenuOption {
    private final ARMenu menu;
    private final Player player;

    public ARMenuOption(ARMenu menu, Player player) {
        this.menu = menu;
        this.player = player;

        menu.addOption(this);

        display();
        updatePositions();
    }

    /**
     * Destroys this menu option instance and any corresponding entities, listeners,
     * and particle effects.
     */
    void dispose() {
        menu.removeOption(this);
        // TODO
    }

    /**
     * Creates any entities or partical effects required to display this menu
     * option.
     */
    private void display() {
        // TODO
    }

    /**
     * Called whenever the player moves to correctly update the relative entity
     * positions.
     */
    void updatePositions() {
        // TODO
    }

    /**
     * Gets the player that this menu option is targeting.
     * 
     * @return The player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * GEts the ARMenu that this menu option is on.
     * 
     * @return The Menu.
     */
    public ARMenu getMenu() {
        return menu;
    }
}
