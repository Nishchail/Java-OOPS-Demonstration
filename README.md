# UnfoldingMaps — Interactive Geographic Data Visualization

> A Java/Swing desktop application that visualizes real-time earthquake data, global flight routes, and country-level health statistics on interactive maps. Built with the [Unfolding Maps](http://unfoldingmaps.org/) library and [Processing](https://processing.org/).

![Java](https://img.shields.io/badge/Java-17+-orange)
![Processing](https://img.shields.io/badge/Processing-3.x-blue)
![License](https://img.shields.io/badge/License-Educational-blue)

---

## Project Overview

This was my primary project for the **UC San Diego Intermediate Software Development** MOOC on Coursera. It evolved across six progressive modules, each introducing new software engineering concepts — from basic marker rendering to custom data structures, event-driven UI, and algorithm implementation.

The final application (`module6`) demonstrates:
- **Real-time data parsing** from USGS Earthquake Feed (Atom/RSS) and OpenFlights data files
- **Custom marker hierarchies** with polymorphic rendering (land quakes = circles, ocean quakes = squares, cities = triangles)
- **Spatial reasoning** — threat-circle computation, distance-based filtering, GeoJSON country intersection
- **Interactive map UX** — click/hover markers, dynamic map provider switching, pop-up city statistics
- **Clean architecture** — shared `common` marker package used by both `module5` and `module6`

---

## Architecture

### Marker Hierarchy

```
SimplePointMarker
└── CommonMarker          (common/)
    ├── CityMarker       (common/) — red triangle, shows name/country/population on click
    └── EarthquakeMarker (common/) — implements Comparable, depth-based color coding
        ├── LandQuakeMarker  (common/) — filled circle
        └── OceanQuakeMarker (common/) — filled square
```

Key design decisions visible in the code:
- **`CommonMarker`** — unifies `selected`/`clicked` state management and `draw()` hook pattern for all interactive markers
- **`EarthquakeMarker`** — abstract base with magnitude-based radius, depth-based color, `Comparable` implementation for sorting, and `threatCircle()` for spatial queries
- **`CityMarker`** — pure display marker with `drawMarker`/`showTitle` popup rendering

### Package Structure

```
src/
├── common/           # Shared marker hierarchy (used by module5 & module6)
│   ├── CommonMarker.java
│   ├── EarthquakeMarker.java
│   ├── CityMarker.java
│   ├── LandQuakeMarker.java
│   └── OceanQuakeMarker.java
├── parsing/          # Data source abstraction
│   └── ParseFeed.java — USGS Atom feeds, OpenFlights CSV, World Bank CSV
├── module3/          # Earthquake map — basic markers and event handling
├── module4/          # Earthquake map — custom marker types, threat circles
├── module5/          # Earthquake map — CommonMarker, pop-up UI, map providers
├── module6/          # Earthquake map (full) + Airport map
├── guimodule/        # Life expectancy choropleth (World Bank data)
└── demos/            # Core Java concepts — inheritance, sorting, location
```

---

## Features

### Earthquake City Map (primary)

| Feature | Implementation |
|---------|----------------|
| Real-time USGS feed parsing | `ParseFeed.parseEarthquake()` — custom RSS/Atom XML parser |
| Land vs Ocean quake differentiation | `isLand()` — GeoJSON country intersection via `AbstractShapeMarker.isInsideByLocation()` |
| Depth-based coloring | Yellow (shallow <70km) → Blue (intermediate 70-300km) → Red (deep >300km) |
| Magnitude-based sizing | Radius = 1.75 × magnitude; shape: circle (land) / square (ocean) |
| Threat circle | Distance-based filtering: `20 × 1.8^(2m−5)` miles converted to km |
| City pop-up statistics | Click city → nearby quake count, avg magnitude, recent quake count |
| Multiple map providers | Google Maps / Microsoft Aerial / Google Terrain (keys 1/2/3) |
| Map reload | Key `R` — re-instantiates map tiles (handles blank tile issues) |
| Live coordinates | Mouse position → lat/lon display in status bar |

### Airport Route Map

| Feature | Implementation |
|---------|----------------|
| OpenFlights data parsing | `ParseFeed.parseAirports()` / `parseRoutes()` — CSV with quoted-field splitting |
| Airport → Location indexing | `HashMap<Integer, Location>` for O(1) route lookup |
| Route line rendering | `SimpleLinesMarker` from `ShapeFeature` locations |

### Life Expectancy Choropleth

| Feature | Implementation |
|---------|----------------|
| World Bank CSV parsing | `loadLifeExpectancyFromCSV()` — finds most recent non-null year per country |
| GeoJSON country shapes | `GeoJSONReader.loadData()` → `MapUtils.createSimpleMarkers()` |
| Continuous color scale | `map(lifeExp, 40, 90, 10, 255)` → blue (low) ↔ red (high) |

---

## Key Controls

| Input | Action |
|-------|--------|
| **Click** marker | Select; show title pop-up |
| **Hover** marker | Highlight with selection ring |
| **Click city** | Pop-up: city name, country, nearby quake count, avg magnitude, recent count |
| **Keys 1 / 2 / 3** | Switch map provider (Google Maps / Microsoft Aerial / Google Terrain) |
| **Key R** | Reload current map tiles |
| **Pan / Scroll** | Navigate map |

---

## Building & Running

### Prerequisites
- Java 17+
- Eclipse IDE (recommended)
- All JARs in `lib/` (Unfolding Maps 0.9.7, Processing, JSON4Processing, etc.)

### Import into Eclipse
```bash
# Clone the repository
git clone https://github.com/Nishchail/Java-OOPS-Demonstration.git

# In Eclipse:
# File → Import → General → Existing Projects into Workspace → Select root directory
# Add all *.jar from lib/ to Java Build Path
```

### Run the Application
Right-click the entry point and run as Java Application:

| Entry Point | Description |
|-------------|-------------|
| `module6.EarthquakeCityMap` | Full earthquake map with all features |
| `module6.AirportMap` | Airport route visualization |
| `guimodule.LifeExpectancy` | World Bank life expectancy choropleth |

---

## Data Sources

- **Earthquakes** — [USGS Earthquake Feed](http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom) (live or cached Atom)
- **Airports / Routes** — [OpenFlights](https://openflights.org/data.html) (`airports.dat`, `routes.dat`)
- **Country Shapes** — Natural Earth GeoJSON (`countries.geo.json`)
- **Life Expectancy** — [World Bank Data](http://data.worldbank.org/indicator/SP.DYN.LE00.IN)
- **Cities** — Custom dataset (`city-data.json`)

---

## What I Built / My Contributions

This was originally a UCSD MOOC starter codebase. My specific additions and improvements:

1. **Consolidated duplicate marker classes** — extracted a shared `common/` package from the parallel module5/module6 hierarchies into one clean marker hierarchy, eliminating ~600 lines of duplicated code.

2. **City pop-up statistics panel** — on city click, computed and displayed nearby quake count, average magnitude, and count of recent quakes (past hour/day).

3. **Dynamic multi-provider maps** — implemented switchable map providers (Google Maps, Microsoft Aerial, Google Terrain) with key press handlers and tile reload mechanism.

4. **Live coordinate display** — `currentMap.getLocation(mouseX, mouseY)` shown at cursor position with adaptive text color for map contrast.

5. **Airport route network visualization** — parsed OpenFlights data files, indexed airports by ID for O(1) route lookup, rendered route lines as `SimpleLinesMarker`.

6. **`Comparable<EarthquakeMarker>` implementation** — enabled sorting earthquake markers by magnitude for analysis.

---

## Skills Demonstrated

- **Object-Oriented Design** — abstract classes, interfaces, inheritance hierarchies, polymorphism
- **Processing Framework** — PApplet lifecycle, `PGraphics` custom rendering, `PFont` text rendering
- **Data Parsing** — custom RSS/Atom XML parsing, CSV with quoted-field handling, GeoJSON reading
- **Event-Driven UI** — mouse/keyboard event handlers, marker selection state machine
- **Spatial Algorithms** — point-in-polygon (country intersection), Haversine-style distance, threat circle radius
- **Swing / Java2D** — OpenGL rendering via Processing, map tile providers

---

## License

This project was developed for educational purposes under the UC San Diego Coursera MOOC. See the original course license for terms. The consolidated `common/` package and my additions are available for reference.

---

*Built as part of the UC San Diego — Intermediate Software Development MOOC (Coursera)*
*Author: Nishchail Jain*
