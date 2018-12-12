import aoc.AdventFileReader;

import java.util.*;
import java.util.stream.Collectors;

public class TheSumOfItsParts {

    public static void main(String[] args) {
        List<String> rules = AdventFileReader.read("day7.txt");
        Map<String, List<String>> chaining = new HashMap<>();
        for (String rule : rules) {
            String precondition = rule.split("Step")[1].trim().split(" ")[0];
            String consequence = rule.split("Step")[1].trim().split("step")[1].trim().split(" ")[0];
            List<String> pre = chaining.getOrDefault(consequence, new ArrayList<>());
            pre.add(precondition);
            chaining.put(consequence, pre);
        }
        List<String> initialFullfillment = new ArrayList<>();
        chaining.keySet().forEach(key -> {
            if (!chaining.values().stream().flatMap(List::stream).collect(Collectors.toSet()).contains(key)) {
                initialFullfillment.add(key);
            }
        });
        Collections.sort(initialFullfillment);
        List<String> fullfilled = new ArrayList<>();
        List<String> available = chaining.values().stream().flatMap(List::stream).distinct().collect(Collectors.toList());
        available.removeAll(chaining.keySet());
        while (!available.isEmpty()) {
            Collections.sort(available);
            fullfilled.add(available.remove(0));
            for (Map.Entry<String, List<String>> entry : chaining.entrySet()) {
                String consequence = entry.getKey();

                if (!fullfilled.contains(consequence) && !available.contains(consequence)) {
                    List<String> preconditions = new ArrayList<>(entry.getValue());

                    preconditions.removeAll(fullfilled);
                    if (preconditions.isEmpty()) {
                        System.out.println(entry.getKey() + " can be fullfilled because " + fullfilled);
                        if (!available.contains(consequence)) {
                            available.add(consequence);
                        }
                    }
                }
            }
        }
        System.out.println(String.join("", fullfilled));
    }
}
