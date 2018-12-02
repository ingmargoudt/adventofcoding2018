package day1;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ChronalCalibration {

    public static void main(String[] args) throws Exception {
        URL calibrationLocation = ChronalCalibration.class.getClassLoader().getResource("day1.txt");
        if (calibrationLocation == null) {
            System.out.println("resource day1.txt not found, quitting");
            return;
        }
        System.out.println(Files.readAllLines(
                Paths.get(calibrationLocation.toURI()))
                .stream().mapToInt(Integer::parseInt).sum());
    }
}
