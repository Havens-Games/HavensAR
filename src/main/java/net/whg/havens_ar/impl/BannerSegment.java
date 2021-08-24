package net.whg.havens_ar.impl;

import org.bukkit.entity.ArmorStand;

import dev.lone.itemsadder.api.CustomStack;
import net.whg.havens_ar.ARMenu;
import net.whg.havens_ar.ARMenuEntityComponent;

/**
 * Represents a segment of the circular menu banner.
 */
public class BannerSegment extends ARMenuEntityComponent {
    private final String type;
    private final boolean left;

    /**
     * Creates a new BannerSegment.
     * 
     * @param menu - The menu instance to create this component for.
     * @param type - The banner type.
     * @param left - True if this segment uses the left side of the texture. False
     *             if it uses the right.
     */
    public BannerSegment(ARMenu menu, String type, boolean left) {
        super(menu);

        this.type = type;
        this.left = left;
    }

    @Override
    protected void decorateArmorStand(ArmorStand armorStand) {
        var name = String.format("havensgames:banner_%s_%s", type, left ? "left" : "right");
        var resource = CustomStack.getInstance(name);
        var menuItem = resource.getItemStack();
        armorStand.getEquipment().setHelmet(menuItem);
    }
}
