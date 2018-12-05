package day2;

import aoc.AdventFileReader;

import java.util.ArrayList;
import java.util.List;

public class IMSLabelFinder {

    public static void main(String[] args) throws Exception {

        List<String> labels = AdventFileReader.read("day2.txt");
        for (String sourceLabel : labels) {
            for (String compareLabel : labels) {
                List<Integer> errorIndex = new ArrayList<>();
                for (int i = 0; i < sourceLabel.length(); i++) {
                    if (sourceLabel.charAt(i) != compareLabel.charAt(i)) {
                        errorIndex.add(i);
                    }
                }
                if (errorIndex.size() == 1) {
                    System.out.println(sourceLabel.substring(0, errorIndex.get(0)) + sourceLabel.substring(errorIndex.get(0) + 1));
                }
            }
        }
    }
}
