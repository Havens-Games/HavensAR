package net.whg.havens_ar;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

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
    private float playerYaw;

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

        playerYaw = dir.yaw;

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
        shiftRotation();
        shiftPosition();
        checkForSelection();
    }

    /**
     * Updates the entity's position based on the player's current position and the
     * menu's internal rotation value.
     */
    private void shiftPosition() {
        var player = menu.getPlayer();
        var location = player.getLocation();

        var pos = new Vec3f((float) location.getX(), (float) location.getY(), (float) location.getZ());
        var dir = new Direction(playerYaw, 0);

        pos = pos.add(dir.vec.multiply(DISTANCE_FROM_PLAYER));
        dir = new Direction(dir.vec.multiply(-1));

        var entityLoc = entity.getLocation();
        entityLoc.set(pos.x, pos.y, pos.z);
        entityLoc.setYaw(dir.yaw);
        entityLoc.setPitch(dir.pitch);
    }

    /**
     * Updates the menu's internal rotation value based on the player's current
     * rotation.
     */
    private void shiftRotation() {
        var player = menu.getPlayer();
        var location = player.getLocation();

        var delta = getYawDistance(playerYaw, location.getYaw());
        playerYaw += delta + 360;
        playerYaw %= 360;
    }

    /**
     * Gets the delta yaw to apply the current entity yaw in order for it to be
     * within the player's field of view.
     * 
     * @param yaw1 - The menu's yaw.
     * @param yaw2 - The player's yaw.
     * @return The yaw delta.
     */
    private float getYawDistance(float yaw1, float yaw2) {
        float d1 = (yaw1 - yaw2 + 360) % 360; // cw
        float d2 = (yaw2 - yaw1 + 360) % 360; // ccw

        if (d1 < d2) {
            return Math.max(d1 - FIELD_OF_VIEW / 2, 0);
        } else {
            return Math.min(-d2 + FIELD_OF_VIEW / 2, 0);
        }
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
