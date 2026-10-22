package com.location;

import java.util.ArrayList;
import com.model.Location;

public class DrivingRoute {

    private final Location startingLocation;
    private final Location endingLocation;
    private final ArrayList<Coordinates> routePoints;

    public DrivingRoute(
            Location startingLocation,
            Location endingLocation,
            ArrayList<Coordinates> routePoints
    ) {
        this.startingLocation = startingLocation;
        this.endingLocation = endingLocation;
        this.routePoints = routePoints;
    }

    public Location getStartingLocation() {
        return startingLocation;
    }

    public Location getEndingLocation() {
        return endingLocation;
    }

    public ArrayList<Coordinates> getRoutePoints() {
        return routePoints;
    }
}