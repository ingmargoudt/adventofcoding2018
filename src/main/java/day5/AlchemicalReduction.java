package day5;

import aoc.AdventFileReader;

import java.util.HashSet;
import java.util.Set;

public class AlchemicalReduction {

    public static void main(String[] args) {
        int best = Integer.MAX_VALUE;
        char bestPolymerReducation = '1';
        for(char alphabet = 'a'; alphabet <= 'z'; alphabet++) {
            String material = AdventFileReader.read("day5.txt").get(0);
            material = material.replaceAll(alphabet+"","").replaceAll(String.valueOf(alphabet).toUpperCase(),"");
            boolean reduced = false;
            while (!reduced) {
                Set<String> toRemove = new HashSet<>();
                for (int i = 0; i < material.length() - 1; i++) {
                    String one = String.valueOf(material.charAt(i));
                    String other = String.valueOf(material.charAt(i + 1));
                    if (one.equalsIgnoreCase(other)
                            && !one.equals(other)){
                        toRemove.add(one + other);

                    }
                }
                for (String reaction : toRemove) {
                    material = material.replaceFirst(reaction, "");
                }
                reduced = toRemove.isEmpty();
            }
            System.out.println(material.length() + " for character "+alphabet);
            if(material.length() < best){
                best = material.length();
                bestPolymerReducation = alphabet;
                System.out.println("new record ");
                System.out.println(material.length() + " for character "+alphabet);
            }
        }
    }

}
