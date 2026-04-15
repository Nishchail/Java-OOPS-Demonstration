package common;

import de.fhpotsdam.unfolding.data.PointFeature;
import processing.core.PGraphics;

/**
 * Marker for earthquakes occurring on land.
 * Rendered as a circle whose color reflects depth (yellow=shallow,
 * blue=intermediate, red=deep).
 *
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author Pratyushotpal Madhukar
 */
public class LandQuakeMarker extends EarthquakeMarker {

    public LandQuakeMarker(PointFeature quake) {
        super(quake);
        isOnLand = true;
    }

    @Override
    public void drawEarthquake(PGraphics pg, float x, float y) {
        pg.ellipse(x, y, 2 * radius, 2 * radius);
    }

    public String getCountry() {
        return (String) getProperty("country");
    }
}