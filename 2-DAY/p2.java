import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    private static void executeStep(Map<String, Integer> coordinates, List<String> step) {
        String action = step.get(0);
        Integer units = Integer.parseInt(step.get(1));
        if(action.equals("up")){
            coordinates.put("aim", coordinates.get("aim") - units);
        }
        else if(action.equals("down")){
            coordinates.put("aim", coordinates.get("aim") + units);
        }
        else {
            coordinates.put("horizontal", coordinates.get("horizontal") + units);
            coordinates.put("depth", coordinates.get("depth") + coordinates.get("aim") * units);
        }
    }
    
    public static void main(String[] args) {
        String pzzinput_pathname = "pzzinput.txt";
        List<List<String>> steps = read_all_lines(pzzinput_pathname).stream().map(x -> Arrays.asList(x.split(" "))).collect(Collectors.toList());
        Map<String, Integer> coordinates = new HashMap<>();
        coordinates.put("horizontal", 0);
        coordinates.put("depth", 0);
        coordinates.put("aim", 0);
        steps.stream().forEach(step -> executeStep(coordinates, step));
        System.out.println(coordinates);
        System.out.println(coordinates.values().stream().collect(Collectors.toList()).subList(0, 2).stream().reduce(1, (x, k) -> x*k));
        
    }
    
}
