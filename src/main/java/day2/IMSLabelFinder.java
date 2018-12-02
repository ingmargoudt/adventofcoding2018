package day2;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class IMSLabelFinder {

    public static void main(String[] args) throws Exception {
        URL inventory = IMSLabelFinder.class.getClassLoader().getResource("day2.txt");
        if (inventory == null) {
            System.out.println("resource day2.txt not found, quitting");
            return;
        }
        List<String> labels = Files.readAllLines(
                Paths.get(inventory.toURI()));
       for(String sourceLabel : labels){s
           for(String compareLabel : labels){
               List<Integer> errorIndex = new ArrayList<>();
               for(int i = 0 ; i< sourceLabel.length(); i++){
                   if(sourceLabel.charAt(i) != compareLabel.charAt(i)) {
                       errorIndex.add(i);
                   }
               }
               if(errorIndex.size() == 1){
                   System.out.println(sourceLabel.substring(0, errorIndex.get(0))+sourceLabel.substring(errorIndex.get(0)+1));
               }
           }
       }
    }
}
