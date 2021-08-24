package net.whg.havens_ar;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import net.whg.utils.math.Transform;

/**
 * Represents a 3D component within an AR Menu.
 */
public abstract class ARMenuComponent {
    private final Transform transform = new Transform();
    private final ARMenu menu;
    private boolean disposed = false;

    /**
     * Creates an ARMenuComponent and triggers the attached 3D components, entities,
     * and particles to be displayed at the correct location.
     * 
     * @param menu - The ARMenu this component is attached to.
     */
    protected ARMenuComponent(ARMenu menu) {
        this.menu = menu;
        menu.addOption(this);

        new BukkitRunnable() {
            @Override
            public void run() {
                display();

                transform.addListener(a -> {
                    if (disposed)
                        return;

                    updatePosition();
                });
            }
        }.runTaskLater(Bukkit.getPluginManager().getPlugin("HavensAR"), 1);
    }

    /**
     * Gets the mutable transform for this menu component.
     * 
     * @return The transform.
     */
    public Transform getTransform() {
        return transform;
    }

    /**
     * Disposes this menu component and all attached 3D objects. This function
     * preforms no action if this menu component is already disposed.
     */
    public void dispose() {
        if (disposed)
            return;

        disposed = true;
        menu.removeOption(this);
        cleanup();
    }

    /**
     * Checks whether or not this menu component is disposed.
     * 
     * @return True if this menu component is disposed. False otherwise.
     */
    public boolean isDisposed() {
        return disposed;
    }

    /**
     * Gets the ARMenu this component is attached to.
     * 
     * @return The ARMenu
     */
    public ARMenu getMenu() {
        return menu;
    }

    /**
     * Creates and displays the 3D entity or particle for this menu component. This
     * function is only called once on startup.
     */
    protected abstract void display();

    /**
     * Called each time the position of this menu component changes.
     */
    protected abstract void updatePosition();

    /**
     * Called when this menu component is disposed in order to clean up all attached
     * entities and particles.
     */
    protected abstract void cleanup();
}
