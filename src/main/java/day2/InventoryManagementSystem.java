package day2;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryManagementSystem {

    public static void main(String[] args) throws Exception {
        URL inventory = InventoryManagementSystem.class.getClassLoader().getResource("day2.txt");
        if (inventory == null) {
            System.out.println("resource day2.txt not found, quitting");
            return;
        }
        List<String> labels = Files.readAllLines(
                Paths.get(inventory.toURI()));
        int twoCount = 0;
        int threeCount = 0;
        for (String label : labels) {
            Map<String, Integer> letterCount = analyzeLabel(label);
            if (letterCount.values().contains(2)) {
                twoCount++;
            }
            if (letterCount.values().contains(3)) {
                threeCount++;
            }
        }
        System.out.println(twoCount * threeCount);
    }

    private static Map<String, Integer> analyzeLabel(String label) {
        Map<String, Integer> characterCount = new HashMap<>();
        for (char c : label.toCharArray()) {
            int count = characterCount.getOrDefault(String.valueOf(c), 0) + 1;
            characterCount.put(String.valueOf(c), count);
        }
        return characterCount;
    }
}
