package net.whg.havens_ar.impl;

import java.util.ArrayList;
import java.util.List;

import net.whg.havens_ar.ARMenu;
import net.whg.havens_ar.ARMenuComponent;
import net.whg.utils.math.Quaternion;
import net.whg.utils.math.Vec3f;

/**
 * A circular banner that surrounds the menu viewer to act as a backdrop.
 */
public class Banner extends ARMenuComponent {
    private static double radiusFromSegmentCount() {
        return 1.0 / (2.0 * Math.tan(Math.PI / SEGMENT_COUNT));
    }

    private static final int SEGMENT_COUNT = 20;
    private static final float RADIUS = (float) radiusFromSegmentCount();

    private final List<BannerSegment> segments = new ArrayList<>();
    private final String bannerType;

    /**
     * Creates a new Banner menu component.
     * 
     * @param menu       - The menu to create this component for.
     * @param bannerType - The banner type.
     */
    public Banner(ARMenu menu, String bannerType) {
        super(menu);

        this.bannerType = bannerType;
        getTransform().setParent(menu.getTransform());
    }

    @Override
    protected void display() {
        var left = true;
        for (var i = 0; i < SEGMENT_COUNT; i++) {
            var angle = (double) i / SEGMENT_COUNT * Math.PI * 2;
            var x = (float) Math.sin(angle) * RADIUS;
            var y = (float) Math.cos(angle) * RADIUS;

            var pos = new Vec3f(x, -0.5f, y);
            var rot = Quaternion.euler((float) Math.toDegrees(angle) + 180, 0, 0);

            var segment = new BannerSegment(getMenu(), bannerType, left);
            segment.getTransform().setLocalPositionAndRotation(pos, rot);
            segment.getTransform().setParent(getTransform());
            left = !left;

            segments.add(segment);
        }
    }

    @Override
    protected void updatePosition() {
        // Nothing to do.
    }

    @Override
    protected void cleanup() {
        for (var segment : segments)
            segment.dispose();

        segments.clear();
    }
}
