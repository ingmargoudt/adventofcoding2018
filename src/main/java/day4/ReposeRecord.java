package day4;

import aoc.AdventFileReader;

import java.util.*;
import java.util.stream.Collectors;

public class ReposeRecord {


    public static void main(String[] args) {
        List<String> entries = AdventFileReader.read("day4.txt");
        Collections.sort(entries);
        List<Shift> allShifts = new ArrayList<>();
        Shift shift = null;
        for (String entry : entries) {
            if (entry.contains("begins shift")) {
                if (shift != null) {
                    allShifts.add(shift);
                }
                shift = new Shift(entry);
            } else {

                shift.addUpdate(GuardUpdate.from(entry));
            }
        }
        Map<Integer, List<Shift>> shiftsByGuard = allShifts.stream().collect(Collectors.groupingBy(Shift::getGuardId));
        Integer longestSleepingGuardId = -1;
        Integer longestSleep = -1;
        for (Map.Entry<Integer, List<Shift>> shifts : shiftsByGuard.entrySet()) {
            System.out.print(shifts.getKey() + " \n");
            int minutesSleeping = shifts.getValue().stream().filter(s -> s.getUpdates().size() > 1).mapToInt(Shift::calculateMinutesSleeping).sum();
            shifts.getValue().forEach(System.out::println);
            if (minutesSleeping > longestSleep) {
                System.out.println("new maximum : "+shifts.getKey() + " ( "+minutesSleeping+")");
                longestSleep = minutesSleeping;
                longestSleepingGuardId = shifts.getKey();
            }

        }
        System.out.println("longest sleeper : " + longestSleepingGuardId);
        int favMinute = calculateFavoriteSleepyMinute(shiftsByGuard.get(longestSleepingGuardId));
        System.out.println("favorite minute : " + favMinute);
        System.out.println("checksum : " + favMinute * longestSleepingGuardId);

    }

    public static int calculateFavoriteSleepyMinute(List<Shift> shifts) {
        List<Integer> sleepyMinutes = new ArrayList<>(Collections.nCopies(60, 0));
        Set<String> days = new HashSet<>();
        int startMinute = -1;
        for (Shift shift : shifts) {
            if (!days.contains(shift.getDay())) {
               days.add(shift.getDay());
                for (GuardUpdate update : shift.getUpdates()) {

                    if (update.getState() == State.FALLS_ASLEEP) {
                        startMinute = update.getMinute();
                    }

                    if (update.getState() == State.WAKES_UP) {
                        for (int i = startMinute; i < update.getMinute(); i++) {
                            sleepyMinutes.set(i, sleepyMinutes.get(i) + 1);

                        }
                        startMinute = -1;
                    }
                }
            }
        }
        int maxIndex = -1;
        int maxValue = -1;
        for (int i = 0; i < sleepyMinutes.size(); i++) {
            if (sleepyMinutes.get(i) > maxValue) {
                maxValue = sleepyMinutes.get(i);
                maxIndex = i;
            }
        }
        return maxIndex;
    }

}


class GuardUpdate {
    private State state;
    private String timestamp;


    public GuardUpdate(String timestamp, State theState) {
        this.timestamp = timestamp;
        this.state = theState;
    }

    public int getMinute() {
        return Integer.parseInt(timestamp.split(" ")[1].split(":")[1]);
    }

    public String getDay() {
        String[] date = timestamp.split(" ")[0].split("-");
        return date[1] + date[2];
    }

    public State getState() {
        return state;
    }

    public static GuardUpdate from(String line) {
        String[] part = line.split("]");
        String timetamp = part[0].substring(1);
        String state = part[1];
        return new GuardUpdate(timetamp, State.from(state));
    }
}

class Shift {
    private int guardId;
    private List<GuardUpdate> updates = new ArrayList<>();

    public int getGuardId() {
        return guardId;
    }

    public String getDay() {
        return updates.get(1).getDay();
    }


    public List<GuardUpdate> getUpdates() {
        return updates;
    }

    public void addUpdate(GuardUpdate update) {
        updates.add(update);
    }

    public String toString() {
        String s = "";
        for (GuardUpdate update : updates) {
            s += update.getMinute() + " " + update.getState() + "\n";
        }
        return s;
    }

    public Shift(String shiftStart) {
        String[] part = shiftStart.split("]");
        String timestamp = part[0].substring(1);
        this.guardId = Integer.parseInt(part[1].trim().split(" ")[1].substring(1));
        updates.add(new GuardUpdate(timestamp, State.BEGINS_SHIFT));
    }

    public int calculateMinutesSleeping() {
        int startMinute = -1;
        int sum = 0;
        for (GuardUpdate update : updates) {
            if (update.getState() == State.FALLS_ASLEEP) {
                startMinute = update.getMinute();
            }
            if (update.getState() == State.WAKES_UP) {
                System.out.println("delta " + update.getMinute() + " - " + startMinute + "( = " + (update.getMinute() - startMinute) + ")");
                sum += (update.getMinute() - startMinute);
            }
        }
        return sum;
    }
}

enum State {
    BEGINS_SHIFT,
    FALLS_ASLEEP,
    WAKES_UP;

    public static State from(String line) {
        switch (line.trim()) {
            case "begins shift":
                return BEGINS_SHIFT;
            case "falls asleep":
                return FALLS_ASLEEP;
            case "wakes up":
                return WAKES_UP;
        }
        return null;
    }
}