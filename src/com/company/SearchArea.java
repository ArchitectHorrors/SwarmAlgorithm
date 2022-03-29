package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

public class SearchArea {
    private final Random random = new SecureRandom();
    private FileWriter fileWriter = null;
    private final char[][] area = new char[15][40];

    public SearchArea() {
    }

    /**
     * opening a text file
     */
    public boolean openFile(File file) {
        try {
            this.fileWriter = new FileWriter(file);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void initializeArea() {
        for (char[] chars : this.area) {
            Arrays.fill(chars, '0');
        }
    }

    /**
     * overwriting a text file with a table
     */
    public void writeFile() throws IOException {
        for (char[] chars : this.area) {
            for (char item : chars) {
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

    /**
     * set the value to 1 in random array cell
     */
    void setNectar() {
        int progress = 0;
        do {
            int x = random.nextInt(12) + 1;
            int y = random.nextInt(37) + 1;

            if (area[x][y] == '0') {
                if (area[x - 1][y] == '0' && area[x + 1][y] == '0') {
                    if (area[x][y - 1] == '0' && area[x][y + 1] == '0') {
                        if (area[x - 1][y - 1] == '0' && area[x + 1][y + 1] == '0') {
                            if (area[x - 1][y + 1] == '0' && area[x + 1][y - 1] == '0') {
                                area[x][y] = '1';
                                progress++;
                            }
                        }
                    }
                }
            }
        } while (progress < 8);

        for (char[] areaItem : area) {
            for (char item : areaItem) {
                System.out.print(item + " ");
            }
            System.out.println();
        }
    }

    public char[][] getArea() {
        return this.area;
    }
}

