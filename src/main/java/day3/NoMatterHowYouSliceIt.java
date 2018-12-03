package day3;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class NoMatterHowYouSliceIt {

    public static void main(String[] args) throws Exception {
        URL inventory = NoMatterHowYouSliceIt.class.getClassLoader().getResource("day3.txt");
        if (inventory == null) {
            System.out.println("resource day3.txt not found, quitting");
            return;
        }
        List<String> lines = Files.readAllLines(
                Paths.get(inventory.toURI()));
        Map<Integer, Claim> claims = lines.stream().map(p -> p.split(" ")).map(Claim::from).collect(Collectors.toMap(Claim::getId, p -> p));
        int maxWidth = 0, maxHeight = 0;
        for (Claim claim : claims.values()) {
            int claimWidth = claim.getOffsetLeft() * 2 + claim.getWidth();
            int claimHeight = claim.getOffsetTop() * 2 + claim.getHeight();
            if (claimWidth > maxWidth) {
                maxWidth = claimWidth;
            }
            if (claimHeight > maxHeight) {
                maxHeight = claimHeight;
            }
        }
        // this is bad, but it works.
        List<Integer>[][] fabric = new ArrayList[maxWidth][maxHeight];
        for (Claim claim : claims.values()) {
            for (int i = 0; i < claim.getWidth(); i++) {
                for (int j = 0; j < claim.getHeight(); j++) {
                    if (fabric[i + claim.getOffsetLeft()][j + claim.getOffsetTop()] == null) {
                        fabric[i + claim.getOffsetLeft()][j + claim.getOffsetTop()] = new ArrayList<>();
                    }
                    fabric[i + claim.getOffsetLeft()][j + claim.getOffsetTop()].add(claim.getId());
                }
            }
        }
        int overlappedByMoreThanTwo = 0;
        for (int i = 0; i < fabric.length; i++) {
            for (int j = 0; j < fabric[0].length; j++) {
                if (fabric[i][j] != null && fabric[i][j].size() > 1) {
                    overlappedByMoreThanTwo++;
                }
            }
        }
        System.out.println(overlappedByMoreThanTwo);
        Set<Integer> possibleTargets = new HashSet<>();
        for (int i = 0; i < fabric.length; i++) {
            for (int j = 0; j < fabric[0].length; j++) {
                if (fabric[i][j] != null) {
                    if (fabric[i][j].size() == 1) {
                        possibleTargets.add(fabric[i][j].get(0));
                    }
                }
            }
        }
        for (int claimId : possibleTargets) {
            Claim theClaim = claims.get(claimId);
            boolean correct = true;
            for (int i = 0; i < theClaim.getWidth(); i++) {
                for (int j = 0; j < theClaim.getHeight(); j++) {
                    correct &= fabric[i + theClaim.getOffsetTop()][j + theClaim.getOffsetTop()].size() == 1;
                }
            }
            if (correct) {
                System.out.println(claimId);
            }
        }


    }
}

class Claim {
    private int id, offsetLeft, offsetTop, width, height;

    public int getId() {
        return id;
    }

    public int getWidth(){
        return width;
    }

    public int getOffsetTop(){
        return offsetTop;
    }

    public int getHeight(){
        return height;
    }

    public int getOffsetLeft(){
        return offsetLeft;
    }

    public Claim(int id, int offsetLeft, int offsetTop, int width, int height) {
        this.id = id;
        this.offsetLeft = offsetLeft;
        this.offsetTop = offsetTop;
        this.width = width;
        this.height = height;
    }

    public static Claim from(String[] parts) {
        String id = parts[0];
        String[] offsets = parts[2].replaceAll(":", "").split(",");
        String[] dimensions = parts[3].split("x");
        int claimId = Integer.parseInt(id.replace("#", ""));
        return new Claim(claimId, Integer.parseInt(offsets[0]), Integer.parseInt(offsets[1]),
                Integer.parseInt(dimensions[0]), Integer.parseInt(dimensions[1]));
    }
}
