package net.whg.havens_ar;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;

import dev.lone.itemsadder.api.CustomStack;
import net.whg.utils.math.Direction;
import net.whg.utils.math.Vec3f;

/**
 * Represents a selectable menu option within the AR Menu.
 */
public class ARMenuOption {
    private static final float DISTANCE_FROM_PLAYER = 3;
    private static final float FIELD_OF_VIEW = 90;

    private final ARMenu menu;
    private ArmorStand entity;
    private float menuSpawnDirection;

    public ARMenuOption(ARMenu menu) {
        this.menu = menu;

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
        entity.remove();
    }

    /**
     * Creates any entities or particle effects required to display this menu
     * option.
     */
    private void display() {
        var player = menu.getPlayer();
        var world = player.getWorld();
        var location = player.getLocation();

        var pos = new Vec3f((float) location.getX(), (float) location.getY(), (float) location.getZ());
        var dir = new Direction(location.getYaw(), 0);

        menuSpawnDirection = dir.yaw;

        pos = pos.add(dir.vec.multiply(DISTANCE_FROM_PLAYER));
        dir = new Direction(dir.vec.multiply(-1));

        var armorStandLoc = new Location(world, pos.x, pos.y, pos.z, dir.yaw, dir.pitch);
        entity = (ArmorStand) world.spawnEntity(armorStandLoc, EntityType.ARMOR_STAND);
        entity.setGravity(false);
        entity.setInvulnerable(true);
        entity.setVisible(false);

        var menu1x2 = CustomStack.getInstance("havensgames:menu1x2");
        var menuItem = menu1x2.getItemStack();
        entity.getEquipment().setHelmet(menuItem);
    }

    /**
     * Called whenever the player moves to correctly update the relative entity
     * positions.
     */
    void updatePositions() {
        shiftPosition();
        checkForSelection();
    }

    /**
     * Updates the entity's position based on the player's current position and the
     * menu's internal rotation value.
     */
    private void shiftPosition() {
        var player = menu.getPlayer();
        var world = player.getWorld();
        var location = player.getLocation();

        var pos = new Vec3f((float) location.getX(), (float) location.getY(), (float) location.getZ());
        var dir = new Direction(menuSpawnDirection, 0);

        pos = pos.add(dir.vec.multiply(DISTANCE_FROM_PLAYER));
        dir = new Direction(dir.vec.multiply(-1));

        var newLocation = new Location(world, pos.x, pos.y, pos.z, dir.yaw, dir.pitch);
        entity.teleport(newLocation);
    }

    private void checkForSelection() {
        // TODO
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
