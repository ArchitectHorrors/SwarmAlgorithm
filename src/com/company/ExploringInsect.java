package com.company;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Optional;
import java.util.Comparator;

public class ExploringInsect {
    private final Scanner scanner = new Scanner(System.in);
    private final List<Point> finalPath = new LinkedList<>();
    private final SearchArea searchArea;
    private final List<Point> cords = new ArrayList<>();
    private Map<Integer, List<Point>> usedPaths = new HashMap<>();
    private Point currentPoint = new Point();

    public ExploringInsect(SearchArea searchArea) {
        this.searchArea = searchArea;
    }

    public void computeBestPath() {
        for (int i = 0; i < 7; i++) {
            createBestPath();
        }

        System.out.println("One of possible shortest path steps: ");
        int size = finalPath.size();
        for (int i = 0; i < size - 1; i++) {
            System.out.printf("From %s to %s%n", finalPath.get(i), finalPath.get(i + 1));
        }
        System.out.printf("From %s to %s%n", finalPath.get(size -1), finalPath.get(0));
    }

    /**
     * Finds all 'ones' and saves it to {@code List<Point>}
     */
    public void findNectar() {
        char[][] area = searchArea.getArea();
        for (int y = 0; y < area.length; y++) {
            for (int x = 0; x < area[y].length; x++) {
                if (area[y][x] == '1') {
                    cords.add(new Point(x, y));
                }
            }
        }
    }

    /**
     * Displays location and sets starting point. <br/>
     * Starting point is entered by user
     */
    public void displayLocations() {
        System.out.println();
        for (int i = 0; i < cords.size(); i++) {
            System.out.println("Location " + (i + 1) + " is " + cords.get(i));
        }

        System.out.print("\nChoose location: ");
        chooseLocation(scanner.nextInt());
    }

    /**
     * Sets starting point of path, adds it to {@code finalPath} and removes it from {@code cords}
     *
     * @param choose - range in which staring position must be located (location 1..8)
     */
    public void chooseLocation(int choose) {
        if (0 < choose && choose < 9) {

            currentPoint.x = this.cords.get(choose - 1).x;
            currentPoint.y = this.cords.get(choose - 1).y;

            System.out.println("\nThe starting location has values: y:" + currentPoint.x + ", x:" + currentPoint.y);
            finalPath.add(currentPoint);
            cords.remove(choose - 1);
        }
    }

    public void createBestPath() {
        Point point = new Point(currentPoint.x, currentPoint.y);
        Point closestPoint = findClosestPoint(point);
        cords.removeIf(c -> c.equals(currentPoint));
        currentPoint = closestPoint;
        finalPath.add(closestPoint);
        usedPaths = new HashMap<>();
    }

    /**
     * Find the closest path to point given as parameter.
     * Maps distance to every point and return closest one.
     *
     * @param startPoint starting point for searching process
     * @return closest point
     */
    private Point findClosestPoint(Point startPoint) {
        for (Point point : cords) {
            List<Integer> paths = new ArrayList<>();
            List<Distance> distances = new ArrayList<>();
            int distanceX, distanceY, fullDistance;
            if (!startPoint.equals(point)) {
                distanceX = Math.abs(startPoint.x - point.x);
                distanceY = Math.abs(startPoint.y - point.y);

                fullDistance = distanceX + distanceY;
                paths.add(fullDistance);
                try {
                    distances.add(new Distance(fullDistance, point));
                } catch (Exception e) {
                    System.err.println("Error while adding new distance");
                }
                addPointToMap(sortPaths(paths), sortDistances(distances).getPoints().get(0));
            }
        }

        return findClosestPoint();
    }

    /**
     * Support method to find the closest point.<br/>
     * Gets the smallest key (closet distance) from mapped values {@code (distance, List<Point>)}
     *
     * @return closest point, null is never returned is this case
     */
    private Point findClosestPoint() {
        Optional<Integer> min = usedPaths.keySet().stream().min(Comparator.naturalOrder());
        return min.map(integer -> usedPaths.get(integer).get(0)).orElse(null);
    }

    /**
     * Maps distance between two points. Distance as a key and List of Points and values.
     *
     * @param key   distance between two points
     * @param value actual point that is compared to current one
     */
    private void addPointToMap(int key, Point value) {
        List<Point> points = usedPaths.get(key);
        if (points == null) {
            ArrayList<Point> objects = new ArrayList<>();
            objects.add(value);
            usedPaths.put(key, objects);
        } else {
            points.add(value);
            usedPaths.put(key, points);
        }
    }

    public int sortPaths(List<Integer> paths) {
        paths.sort(Comparator.naturalOrder());
        return paths.get(0);
    }

    public Distance sortDistances(List<Distance> distances) {
        distances.sort(Distance::comparator);

        return distances.get(0);
    }

    public List<Point> getFinalPath() {
        return finalPath;
    }
}