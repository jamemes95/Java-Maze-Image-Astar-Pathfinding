import java.util.*;
import java.io.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class MazeSolver {
    // Coded by Reuwen James A. Carbonilla
    private static int startX = 0, startY = 11, endX = 209, endY = 198;
    private static int[][] directions = new int[][] { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, 1 }, { 1, 1 }, { 1, 0 },
            { 1, -1 }, { 0, -1 } };

    public static void main(String[] args) {
        String path = System.getProperty("user.dir");
        System.out.println(path);
        File dir = new File(path);
        File[] files = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".png");
            }
        });

        if (files.length <= 0) {
            System.out.println("No images found in directory");
        }

        SetupMazeGrid(files[0]);
    }

    public static void SetupMazeGrid(File f) {
        BufferedImage img = null;

        try {
            img = ImageIO.read(f);
        } catch (IOException e) {
            System.out.println(e);
        }
        int width = img.getWidth();
        int height = img.getHeight();

        int[][] grid = new int[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = img.getRGB(x, y);

                if (p < -1) {
                    grid[x][y] = 1;
                } else {
                    grid[x][y] = 0;
                }
            }
        }
        int blue = Color.blue.getRGB();
        java.util.List<ListItem> destination = getSurroundings(grid, endX, endY);
        int destinationLimit = destination.size();
        for (int i = 0; i < destinationLimit; i++) {
            img.setRGB(destination.get(i).getX(), destination.get(i).getY(), blue);
        }
        if (grid[startX][startY] == 0 && grid[endX][endY] == 0) {
            solveMaze(img, grid);
        } else {
            if (grid[startX][startY] == 1) {
                System.out.println("start point is located at invalid position, please move the start point");
            } else {
                System.out.println("end point is located at invalid position, please move the end point");
            }
        }
    }

    public static void solveMaze(BufferedImage mazeImg, int mazeGrid[][]) {
        // Initialize open and closed lists
        java.util.List<ListItem> openList = new ArrayList<ListItem>();
        java.util.List<ListItem> closedList = new ArrayList<ListItem>();
        // put the starting node on the openlist (you can leave its f at zero)
        openList.add(new ListItem(startX, startY, 0));

        while (openList.size() > 0) {
            System.out.println("calculating path now..");
            // find node with least f on open list
            int indexOfLeastF = 0;
            int openListLimit = openList.size();
            for (int i = 0; i < openListLimit; i++) {
                if (openList.get(indexOfLeastF).getF() > openList.get(i).getF()) {
                    indexOfLeastF = i;
                }
            }

            ListItem q = openList.get(indexOfLeastF);
            int currentX = q.getX();
            int currentY = q.getY();

            if (currentX == endX && currentY == endY) {
                System.out.println("END FOUND");
                ListItem finalNode = new ListItem(endX, endY, 0);
                finalNode.setParent(q.getX(), q.getY(), q);
                tracePath(mazeImg, finalNode);
                break;
            }
            for (int i = 0; i < openListLimit; i++) {
                if (openList.get(i).getX() == q.getX() && openList.get(i).getY() == q.getY()) {
                    openList.remove(i);
                    openListLimit = openList.size();
                }
            }

            closedList.add(q);
            // System.out.println("Current Node added to Closed List");

            // generate q's 8 successors and set their parents to q
            java.util.List<ListItem> surroundings = getSurroundings(mazeGrid, currentX, currentY);
            // set successors parents to q
            for (int i = 0; i < surroundings.size(); i++) {
                ListItem currentNode = surroundings.get(i);
                currentNode.setParent(currentX, currentY, q);

                // Check if node is already in closed List, skip node if true
                boolean containedInClosedList = false;
                int closedListLimit = closedList.size();
                for (int j = 0; j < closedListLimit; j++) {
                    if (closedList.get(j).getX() == currentNode.getX()
                            && closedList.get(j).getY() == currentNode.getY()) {
                        containedInClosedList = true;
                        break;
                    }
                }

                if (containedInClosedList) {
                    // if contained, then skip this node
                    continue;
                }

                if (!openList.contains(currentNode)) {
                    openList.add(currentNode);
                }

            }
        }
        System.out.println("open list count is :" + openList.size());
        System.out.println("closed list count is :" + closedList.size());
        int red = Color.red.getRGB();
        BufferedImage generateImage = mazeImg;
        int closedListLimit = closedList.size();
        for (int i = 0; i < closedListLimit; i++) {
            generateImage.setRGB(closedList.get(i).getX(), closedList.get(i).getY(), red);
            if (closedList.get(i).getX() == endX && closedList.get(i).getY() == endY) {
                System.out.println("End was reached | closed list index is :" + i);
                break;
            }
        }
        generateImage.setRGB(startX, startY, Color.green.getRGB());

        try {
            File outputfile = new File("closedListIMG.png");
            ImageIO.write(mazeImg, "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void tracePath(BufferedImage mazeImg, ListItem finalCoordinates) {
        int red = Color.red.getRGB();
        BufferedImage generateImage = mazeImg;
        generateImage.setRGB(startX, startY, Color.green.getRGB());

        java.util.List<ListItem> finalPath = new ArrayList<>();
        System.out.println("TRACING PATH NOW");
        ListItem currentCoords = finalCoordinates;
        while (currentCoords.getParentX() != startX || currentCoords.getParentY() != startY) {
            finalPath.add(currentCoords);
            currentCoords = currentCoords.getParentInfo();
        }
        System.out.println("Path to destination size = " + finalPath.size());
        for (int i = 0; i < finalPath.size(); i++) {
            System.out.println("#" + i + " X: " + finalPath.get(i).getX() + " | Y: " + finalPath.get(i).getY());
            generateImage.setRGB(finalPath.get(i).getX(), finalPath.get(i).getY(), red);
        }
        try {
            File outputfile = new File("solvedImg.png");
            ImageIO.write(mazeImg, "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static java.util.List<ListItem> getSurroundings(int[][] matrix, int x, int y) {
        java.util.List<ListItem> res = new ArrayList<ListItem>();
        for (int[] direction : directions) {
            int cx = x + direction[0];
            int cy = y + direction[1];
            if (cy >= 0 && cy < matrix.length) {
                if (cx >= 0 && cx < matrix[cy].length) {
                    if (matrix[cx][cy] != 1) {
                        res.add(new ListItem(cx, cy, calculateF(cx, cy)));
                    }
                }
            }
        }
        return res;
    }

    public static int calculateF(int currentX, int currentY) {
        int g = startX + currentX + startY + currentY;
        double h1 = currentX - endX;
        double h2 = currentY - endY;
        double finalH = Math.sqrt(h1 * 2 + h2 * 2);

        int h = (int) finalH;
        int finalF = g + h;

        return finalF;
    }
}