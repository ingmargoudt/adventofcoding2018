package day5;

import aoc.AdventFileReader;

import java.util.HashSet;
import java.util.Set;

public class AlchemicalReduction {

    public static void main(String[] args) {
        String material = AdventFileReader.read("day5.txt").get(0);
        boolean reduced = false;
        while (!reduced) {
            Set<String> toRemove = new HashSet<>();
            for (int i = 0; i < material.length() - 1; i++) {
                String one = String.valueOf(material.charAt(i));
                String other = String.valueOf(material.charAt(i+1));
                if (one.equalsIgnoreCase(other)
                        && !one.equals(other)) {
                     toRemove.add(one+other);

                }
            }
            for (String reaction : toRemove) {
                material = material.replaceFirst(reaction, "");
            }
            System.out.println(material.length() +" : " +material);
            reduced = toRemove.isEmpty();
        }
        System.out.println(material.length());
    }

}
