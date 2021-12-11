import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class p2 {

    private static List<String> read_all_lines(String pathname) {
        List<String> all_lines = new ArrayList<>();
        try {
            all_lines = Files.readAllLines(Paths.get(pathname), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return all_lines;
    }

    private static Integer[] calcule_three_measurement(Integer[] measurements) {
        Integer[] three_measurement = new Integer[measurements.length-2];
        for(int i = 0; i < measurements.length-2; i++) {
            three_measurement[i] = measurements[i] + measurements[i+1] + measurements[i+2];
        }
        return three_measurement;
    }

    public static void main(String[] args) {
        String pzzinput_pathname = "pzzinput.txt";
        Integer[] lines = read_all_lines(pzzinput_pathname).stream().map(x -> Integer.parseInt(x)).toArray(Integer[]::new);
        Integer[] three_measurement = calcule_three_measurement(lines);
        int measurement_increases = 0;
        for(int i = 0; i < three_measurement.length-1; i++) {
            if(three_measurement[i] < three_measurement[i+1])
                measurement_increases++;
        }
        System.out.println(measurement_increases);
    }
}