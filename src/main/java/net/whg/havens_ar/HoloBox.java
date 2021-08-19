package net.whg.havens_ar;

import org.bukkit.entity.ArmorStand;

import dev.lone.itemsadder.api.CustomStack;

public class HoloBox extends ARMenuEntityComponent {

    public HoloBox(ARMenu menu) {
        super(menu);
    }

    @Override
    protected void decorateArmorStand(ArmorStand armorStand) {
        var menu1x2 = CustomStack.getInstance("havensgames:menu1x2");
        var menuItem = menu1x2.getItemStack();
        armorStand.getEquipment().setHelmet(menuItem);
    }
}
