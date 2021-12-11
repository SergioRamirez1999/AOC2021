import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class p1 {

    private static List<String> read_all_lines(String pathname) {
        List<String> all_lines = new ArrayList<>();
        try {
            all_lines = Files.readAllLines(Paths.get(pathname), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return all_lines;
    }

    public static void main(String[] args) {
        String pzzinput_pathname = "pzzinput.txt";
        String[] lines = read_all_lines(pzzinput_pathname).stream().toArray(String[]::new);
        int measurement_increases = 0;
        for(int i = 0; i < lines.length-1; i++) {
            if(Integer.parseInt(lines[i]) < Integer.parseInt(lines[i+1]))
                measurement_increases++;
        }
        System.out.println(measurement_increases);
    }
}