package day1;

import aoc.AdventFileReader;

import java.util.List;

public class ChronalCalibration {

    public static void main(String[] args) throws Exception {
        List<String> calibrations = AdventFileReader.read("day1.txt");

        System.out.println(calibrations
                .stream().mapToInt(Integer::parseInt).sum());
    }
}
