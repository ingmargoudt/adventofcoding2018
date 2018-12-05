package aoc;

import day1.ChronalCalibration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class AdventFileReader {

    public static List<String> read(String filename) {
        URL calibrationLocation = ChronalCalibration.class.getClassLoader().getResource(filename);
        if (calibrationLocation == null) {
            System.out.println("resource day1.txt not found, quitting");
            System.exit(0);
        }
        try {
            return Files.readAllLines(Paths.get(calibrationLocation.toURI()));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

}


