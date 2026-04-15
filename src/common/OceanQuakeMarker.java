package common;

import de.fhpotsdam.unfolding.data.PointFeature;
import processing.core.PGraphics;

/**
 * Marker for earthquakes occurring in the ocean.
 * Rendered as a square whose color reflects depth (yellow=shallow,
 * blue=intermediate, red=deep).
 *
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author Pratyushotpal Madhukar
 */
public class OceanQuakeMarker extends EarthquakeMarker {

    public OceanQuakeMarker(PointFeature quake) {
        super(quake);
        isOnLand = false;
    }

    @Override
    public void drawEarthquake(PGraphics pg, float x, float y) {
        pg.rect(x - radius, y - radius, 2 * radius, 2 * radius);
    }
}