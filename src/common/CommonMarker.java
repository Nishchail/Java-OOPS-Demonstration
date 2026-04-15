package common;

import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import processing.core.PGraphics;

/**
 * Base marker class for interactive map elements (cities and earthquakes).
 * Provides unified selection/click state management and rendering.
 *
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author Pratyushotpal Madhukar
 */
public abstract class CommonMarker extends SimplePointMarker {

    protected boolean clicked = false;

    public CommonMarker(Location location) {
        super(location);
    }

    public CommonMarker(Location location, java.util.HashMap<String, Object> properties) {
        super(location, properties);
    }

    public boolean getClicked() {
        return clicked;
    }

    public void setClicked(boolean state) {
        clicked = state;
    }

    /**
     * Draws the marker and its title label when selected.
     * Delegates to drawMarker for shape rendering and showTitle for label display.
     */
    public void draw(PGraphics pg, float x, float y) {
        if (!hidden) {
            drawMarker(pg, x, y);
            if (selected) {
                showTitle(pg, x, y);
            }
        }
    }

    public abstract void drawMarker(PGraphics pg, float x, float y);
    public abstract void showTitle(PGraphics pg, float x, float y);
}