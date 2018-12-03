package day3;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
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
        List<Claim> claims = lines.stream().map(p -> p.split(" ")).map(Claim::from).collect(Collectors.toList());
        int maxWidth = 0, maxHeight = 0;
        for (Claim claim : claims) {
            int claimWidth = claim.offsetLeft * 2 + claim.width;
            int claimHeight = claim.offsetTop * 2 + claim.height;
            if (claimWidth > maxWidth) {
                maxWidth = claimWidth;
            }
            if (claimHeight > maxHeight) {
                maxHeight = claimHeight;
            }
        }
        int[][] fabric = new int[maxHeight][maxHeight];
        for (Claim claim : claims) {
            for (int i = 0; i < claim.width; i++) {
                for (int j = 0; j < claim.height; j++) {
                    fabric[i + claim.offsetLeft][j + claim.offsetTop]++;
                }
            }
        }
        int overlappedByMoreThanTwo = 0;
        for (int i = 0; i < fabric.length; i++) {
            for (int j = 0; j < fabric[0].length; j++) {
                if (fabric[i][j] > 1) {
                    overlappedByMoreThanTwo++;
                }
            }
        }
        System.out.println(overlappedByMoreThanTwo);
    }


}

class Claim {
    public int id, offsetLeft, offsetTop, width, height;

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
