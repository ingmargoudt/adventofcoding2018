package day1;

import aoc.AdventFileReader;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ChronalCalibrationPartTwo {

    public static void main(String[] args) throws Exception {
        List<Integer> deltas = AdventFileReader.read("day1.txt")
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
