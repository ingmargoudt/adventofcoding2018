package day2;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ChronalCalibrationPartTwo {

    public static void main(String[] args) throws Exception {
        URL calibrationLocation = ChronalCalibrationPartTwo.class.getClassLoader().getResource("day1.txt");
        if (calibrationLocation == null) {
            System.out.println("resource day1.txt not found, quitting");
            return;
        }
        List<Integer> deltas = Files.readAllLines(
                Paths.get(calibrationLocation.toURI()))
                .stream().map(Integer::parseInt).collect(Collectors.toList());
        Set<Integer> seenFrequencies = new HashSet<>();
        int currentFrequency = 0;
        while (true) {
            for (int delta : deltas) {
                currentFrequency += delta;
                if (seenFrequencies.contains(currentFrequency)) {
                    System.out.println(currentFrequency);
                    return;
                } else {
                    seenFrequencies.add(currentFrequency);
                }
            }
        }
    }
}
