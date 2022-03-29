package com.company;

import java.util.ArrayList;
import java.util.List;

public class Distance {
    private final int distance;
    private final List<Point> points = new ArrayList<>();

    public Distance(int distance, Point p) {
        this.distance = distance;
        addPoint(p);
    }

    public void addPoint(Point p) {
        if (p == null || p.x < 0 || p.y < 0) {
            throw new IllegalArgumentException();
        }
        this.points.add(p);
    }

    public static int comparator(Distance d1, Distance d2) {
        return Integer.compare(d1.getDistance(), d2.getDistance());
    }

    public int getDistance() {
        return this.distance;
    }

    public List<Point> getPoints() {
        return this.points;
    }
}
