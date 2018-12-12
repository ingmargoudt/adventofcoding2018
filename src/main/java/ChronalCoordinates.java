import aoc.AdventFileReader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChronalCoordinates {


    public static void main(String[] args) {
        List<Point> coords = AdventFileReader.read("day6.txt").stream().map(l -> l.split(",")).map(p -> new Point(p[0], p[1])).collect(Collectors.toList());
        int minX = coords.stream().mapToInt(p -> p.x).min().getAsInt();
        int maxX = coords.stream().mapToInt(p -> p.x).max().getAsInt();
        int minY = coords.stream().mapToInt(p -> p.y).min().getAsInt();
        int maxY = coords.stream().mapToInt(p -> p.y).max().getAsInt();
        Map<Point, Color> colors = new HashMap<>();
        for (Point p : coords) {
            colors.put(p, new Color((int) (Math.random() * 0x1000000)));
        }
        Map<Point, Map<Point, Integer>> grid = new HashMap<>();
        coords.removeIf(p -> p.x == minX || p.x == maxX || p.y == minY || p.y == maxY);
        for (int x = minX; x < maxX; x++) {
            for (int y = minY; y < maxY; y++) {
                Point pointer = new Point(x, y);
                grid.put(pointer, new HashMap<>());
                for (Point p : coords) {
                    if (!p.toString().equals(pointer.toString())) {
                        grid.get(pointer).put(p, pointer.manhattan(p));
                    }
                }
            }
        }
        Point[][] voronoiArray = new Point[maxX ][maxY ];
        for(int x = minX; x < (maxX ) ; x++){
            for(int y = minY ; y < (maxY ) ; y++){
                voronoiArray[x][y] = null;
            }
        }
        Map<Point, Point> voronoi = new HashMap<>();
        grid.forEach((p, nearestPoint) -> {
            int smallestValue = nearestPoint.entrySet().stream().min(Map.Entry.comparingByValue()).get().getValue();
            if (nearestPoint.values().stream().filter(v -> v == smallestValue).count() == 1) {
                Point closest = nearestPoint.entrySet().stream().min(Map.Entry.comparingByValue()).get().getKey();
                voronoi.put(p, closest);
                voronoiArray[p.x][p.y] = closest;

            } else {
                System.out.println("on boundary between " + p + " and " + nearestPoint.entrySet().stream().filter(v -> v.getValue() == smallestValue).collect(Collectors.toList()));
            }
        });
        Map<Point, Long> counts = voronoi.values().stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println(counts.entrySet().stream().max(Map.Entry.comparingByValue()).get().getValue());
        //create buffered image object img
        BufferedImage img = new BufferedImage(maxX, maxY, BufferedImage.TYPE_INT_ARGB);
        //file object
        File f = null;
        voronoi.forEach((pixel, closest) ->
        {
            img.setRGB(pixel.x, pixel.y, colors.get(closest).getRGB());

        });
        try {
            f = new File("voronoi.png");
            ImageIO.write(img, "png", f);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
}

class Point {
    int x;
    int y;

    public Point(String x, String y) {
        this.x = Integer.parseInt(x.trim());
        this.y = Integer.parseInt(y.trim());
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return x + "x" + y;
    }

    public int manhattan(Point other) {
        return Math.abs(other.x - x) + Math.abs(other.y - y);
    }
}
