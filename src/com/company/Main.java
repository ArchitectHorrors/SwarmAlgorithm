package com.company;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        File file = new File("C:\\Insect\\Algh\\src\\com\\company\\output\\path.txt");
        SearchArea searchArea = new SearchArea();
        ExploringInsect exploringInsect = new ExploringInsect(searchArea);
        CreateVisualPath visualPath = new CreateVisualPath(exploringInsect, searchArea);

        searchArea.initializeArea();
        searchArea.setNectar();

        if (searchArea.openFile(file)) {
            try {
                searchArea.writeFile();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                searchArea.closeFile();
            }
        }

        exploringInsect.findNectar();
        exploringInsect.displayLocations();
        exploringInsect.computeBestPath();

        try {
            visualPath.createVisualPath(new File("C:\\Insect\\Algh\\src\\com\\company\\output\\visual-path.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}