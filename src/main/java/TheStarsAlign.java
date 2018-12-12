import aoc.AdventFileReader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TheStarsAlign {

    public static void main(String[] args) {
        List<Star> starfield = AdventFileReader.read("day10.txt").stream().map(Star::from).collect(Collectors.toList());
        int max = starfield.stream().map(Star::getX).reduce(Integer::max).get();
        int min = starfield.stream().map(Star::getX).reduce(Integer::min).get();
        for (int i = 0; i < max - min; i++) {
            for (Star star : starfield) {
                star.update();
            }
            System.out.println(i);
            writeImage(starfield, i);
            starfield.stream().map(Star::getX).reduce(Integer::max).get();
            min = starfield.stream().map(Star::getX).reduce(Integer::min).get();
        }
    }

    private static void writeImage(List<Star> starfield, int iteration) {
        int maxX = starfield.stream().map(Star::getX).reduce(Integer::max).get();
        int minX = starfield.stream().map(Star::getX).reduce(Integer::min).get();

        int maxY = starfield.stream().map(Star::getY).reduce(Integer::max).get();
        int minY = starfield.stream().map(Star::getY).reduce(Integer::min).get();
        if((maxX - minX)  > 200){
            return;
        }
        try {
            BufferedImage img = new BufferedImage((maxX - minX)*2, (maxX-minX)*2, BufferedImage.TYPE_INT_ARGB);

            //file object
            File f = null;
            System.out.println(img.getWidth() + "" + img.getHeight());
            starfield.forEach(star ->
            {
                System.out.println(star.getX()-minX + " "+(star.getY()-minY));
                img.setRGB(star.getX() - minX, star.getY() - minY, Color.BLACK.getRGB());

            });
            try {
                f = new File(iteration + ".png");
                ImageIO.write(img, "png", f);
            } catch (IOException e) {
                System.out.println("Error: " + e);
            }
        } catch (NegativeArraySizeException nse) {
            return;
        }
    }
}

class Star {
    private int x;
    private int y;
    private int deltaX;
    private int deltaY;

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    public Star(int x, int y, int deltaX, int deltaY) {
        this.x = x;
        this.y = y;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public void update() {
        x += deltaX;
        y += deltaY;
    }

    public static Star from(String line) {
        System.out.println(line);
        String[] parts = line.split("velocity");
        String positionPart = parts[0];
        positionPart = positionPart.replace("position=<", "").replace(">", "");
        String[] positions = positionPart.split(",");
        int x = Integer.parseInt(positions[0].trim());
        int y = Integer.parseInt(positions[1].trim());
        String velocityPart = parts[1];
        velocityPart = velocityPart.replace("=<", "").replace(">", "");
        String[] velocity = velocityPart.split(",");
        int deltaX = Integer.parseInt(velocity[0].trim());
        int deltaY = Integer.parseInt(velocity[1].trim());

        return new Star(x, y, deltaX, deltaY);
    }
}
