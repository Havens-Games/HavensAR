package net.whg.havens_ar;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import net.whg.utils.math.Quaternion;
import net.whg.utils.math.Vec3f;

public abstract class ARMenuEntityComponent extends ARMenuComponent {
    private ArmorStand entity;

    /**
     * Creates an ARMenuEntityComponent and triggers the attached armor stand entity
     * to be displayed at the correct location.
     * 
     * @param menu - The ARMenu this component is attached to.
     */
    protected ARMenuEntityComponent(ARMenu menu) {
        super(menu);

        var transform = getTransform();
        transform.setLocalPositionAndRotation(new Vec3f(0, 0, -3f), Quaternion.euler(0, 0, 0));
    }

    @Override
    protected void display() {
        var location = getTargetLocation();

        entity = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        entity.setGravity(false);
        entity.setInvulnerable(true);
        entity.setVisible(false);

        decorateArmorStand(entity);
    }

    @Override
    protected void updatePosition() {
        var location = getTargetLocation();
        entity.teleport(location);
    }

    /**
     * Gets the target location for where this entity should be placed based on the
     * current transform state.
     * 
     * @return The target location for this menu component's entity.
     */
    private Location getTargetLocation() {
        var transform = getTransform();
        var world = getMenu().getPlayer().getWorld();

        var pos = transform.getWorldPosition();
        var rot = transform.getWorldRotation();

        return new Location(world, pos.x, pos.y, pos.z, rot.getYaw(), rot.getPitch());
    }

    @Override
    protected void cleanup() {
        entity.remove();
        entity = null;
    }

    /**
     * Gets the armor stand entity this component is managing.
     * 
     * @return The armor stand entity, or null if this component has been disposed.
     */
    public ArmorStand getEntity() {
        return entity;
    }

    /**
     * Called when the armor stand entity is first spawned in order to add any
     * decorative armor pieces, block heads, potion effects, etc, to it.
     * 
     * @param armorStand - The armor stand that was spawned.
     */
    protected abstract void decorateArmorStand(ArmorStand armorStand);
}
