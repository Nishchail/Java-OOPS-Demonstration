package common;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import processing.core.PConstants;
import processing.core.PGraphics;

/**
 * Visual marker for cities on an earthquake map.
 * Displays as a red triangle with a title popup showing city name, country, and population.
 *
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author Pratyushotpal Madhukar
 */
public class CityMarker extends CommonMarker {

    public static final int TRI_SIZE = 5;

    public CityMarker(Location location) {
        super(location);
    }

    public CityMarker(Feature city) {
        super(((PointFeature) city).getLocation(), city.getProperties());
    }

    @Override
    public void drawMarker(PGraphics pg, float x, float y) {
        pg.pushStyle();
        pg.fill(150, 30, 30);
        pg.triangle(x, y - TRI_SIZE, x - TRI_SIZE, y + TRI_SIZE, x + TRI_SIZE, y + TRI_SIZE);
        pg.popStyle();
    }

    @Override
    public void showTitle(PGraphics pg, float x, float y) {
        pg.pushStyle();
        String name = getCity() + " " + getCountry();
        String pop = "Pop: " + getPopulation() + " Million";
        pg.rectMode(PConstants.CORNER);
        pg.fill(255, 255, 255);
        pg.stroke(110);
        pg.rect(x, y - TRI_SIZE - 39,
                Math.max(pg.textWidth(name), pg.textWidth(pop)) + 6, 39);
        pg.textAlign(PConstants.LEFT, PConstants.TOP);
        pg.fill(0);
        pg.text(name, x + 3, y - TRI_SIZE - 33);
        pg.text(pop, x + 3, y - TRI_SIZE - 18);
        pg.popStyle();
    }

    public String getCity() {
        return getStringProperty("name");
    }

    public String getCountry() {
        return getStringProperty("country");
    }

    public float getPopulation() {
        return Float.parseFloat(getStringProperty("population"));
    }
}