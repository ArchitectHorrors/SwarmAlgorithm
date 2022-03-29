package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author szymon.medrala@medapp.com
 * @since 1.0
 * @version 1.0
 */
public class CreateVisualPath {
    private final ExploringInsect insect;
    private final SearchArea searchArea;
    private final char[][] area;
    private final List<Point> finalPath;
    private FileWriter fileWriter = null;

    public CreateVisualPath(ExploringInsect insect, SearchArea searchArea){
        this.searchArea = searchArea;
        this.insect = insect;
        this.area = searchArea.getArea();
        finalPath = insect.getFinalPath();
    }

    public void createVisualPath(File outputFile) throws IOException {
        for (int i = 0; i < finalPath.size() -1; i++) {
            createPathFromTo(finalPath.get(i), finalPath.get(i+1));
        }
        createPathFromTo(finalPath.get(finalPath.size() -1), finalPath.get(0));
        printAreaToFile(outputFile);
    }


    private void shiftHorizontal(int x1, int x2, int y, boolean moveRight){
        if (moveRight){
            for (int x = x1 +1; x <= x2; x++){
                if (area[y][x] == '1') continue;
                printSignIfCrossedHorizontally(y, x);
            }
        } else {
            for (int x = x1 -1; x >= x2; x--){
                if (area[y][x] == '1') continue;
                printSignIfCrossedHorizontally(y, x);
            }
        }
    }

    private void printSignIfCrossedVertically(int y, int x){
        if (area[y][x] == '-') {
            area[y][x] = '+';
        } else {
            area[y][x] = '|';
        }
    }

    private void printSignIfCrossedHorizontally(int y, int x){
        if (area[y][x] == '|') {
            area[y][x] = '+';
        } else {
            area[y][x] = '-';
        }
    }

    private void shiftVertical(int y1, int y2, int x, boolean moveRight){
        if (moveRight){
            if (y1 < y2){
                for (int y = y1; y < y2; y++) {
                    if (area[y][x] == '1') continue;
                    printSignIfCrossedVertically(y, x);
                }
            } else if(y1 > y2) {
                for (int y = y1; y > y2; y--) {
                    if (area[y][x] == '1') continue;
                    printSignIfCrossedVertically(y, x);
                }
            }
        } else {
            if (y1 < y2){
                for (int y = y1 +1; y < y2; y++) {
                    if (area[y][x] == '1') continue;
                    printSignIfCrossedVertically(y, x);
                }
            } else if(y1 > y2) {
                for (int y = y1 -1; y > y2; y--) {
                    if (area[y][x] == '1') continue;
                    printSignIfCrossedVertically(y, x);
                }
            }
        }
    }

    /**
     * Creates path from point {@code p1} to {@code p2} and marks it as '-' in text file
     * @param p1 starting point
     * @param p2 ending point
     */
    public void createPathFromTo(Point p1, Point p2){
        if (p1.x < p2.x){
            shiftHorizontal(p1.x, p2.x, p1.y, true);
            shiftVertical(p1.y, p2.y, p2.x, true);
        } else if (p1.x > p2.x) {
            shiftHorizontal(p1.x, p2.x, p1.y, false);
            shiftVertical(p1.y, p2.y, p2.x, false);
        }
    }

    public void printAreaToFile(File outputFile) throws IOException {
        openFile(outputFile);
        writeFile();
        closeFile();
    }

    /**
     * opening a text file
     */
    public void openFile(File file) {
        try {
            this.fileWriter = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * overwriting a text file with a table
     * @throws IOException when ...
     */
    public void writeFile() throws IOException {
        for (char[] chars : this.area) {
            for (char item : chars) {
                if (item == '0')
                    this.fileWriter.write("  ");
                else
                    this.fileWriter.write(item + " ");
            }
            this.fileWriter.write("\n");
        }
    }

    /**
     * closing a text file
     */
    public void closeFile() {
        try {
            this.fileWriter.flush();
            this.fileWriter.close();
        } catch (IOException e) {
            System.err.println("Error closing file in " + SearchArea.class.getName());
            e.printStackTrace();
        }
    }
}