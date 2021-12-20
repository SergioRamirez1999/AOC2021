import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
        List<String> lines = read_all_lines(pzzinput_pathname);
        Diagram diagram = new Diagram();
        lines.forEach(line -> diagram.try_draw_vent(line));
        diagram.print_coordinates();
        System.out.println(diagram.count_overlap());
    }
    
}

class Diagram {

    private Map<Position, Integer> coordinates = new HashMap<>();
    private static final String COORDINATES_REGEX = "([0-9]+),([0-9]+) -> ([0-9]+),([0-9]+)";

    private int to_int(String number) {
        return Integer.parseInt(number);
    }

    private List<Position> generate_coords(String vent_line) {
        List<Position> line = new ArrayList<>();
        Pattern pattern = Pattern.compile(COORDINATES_REGEX);
        Matcher matcher = pattern.matcher(vent_line);
        if(matcher.matches()) {
            line.add(new Position(to_int(matcher.group(1)), to_int(matcher.group(2))));
            line.add(new Position(to_int(matcher.group(3)), to_int(matcher.group(4))));
        }
        return line;
    }

    private boolean is_drawable(List<Position> line) {
        return line.get(0).getX() == line.get(1).getX()
            || line.get(0).getY() == line.get(1).getY();
    }

    private String get_direction(List<Position> line) {
        if(line.get(0).getX() == line.get(1).getX())
            return "VERTICAL";
        return "HORIZONTAL";
    }

    private void draw_vent(List<Position> line) {
        Position origin = line.get(0);
        Position destination = line.get(1);
        String direction = get_direction(line);
        if(direction.equals("HORIZONTAL")){
            int minX = Math.min(origin.getX(), destination.getX());
            int maxX = Math.max(origin.getX(), destination.getX());
            int y = destination.getY();
            for(int x = minX; x <= maxX; x++) {
                Position coord = new Position(x, y);
                if(coordinates.containsKey(coord)){
                    coordinates.put(coord, coordinates.get(coord)+1);
                }else {
                    coordinates.put(coord, 1);
                }
            }
        } else {
            int minY = Math.min(origin.getY(), destination.getY());
            int maxY = Math.max(origin.getY(), destination.getY());
            int x = destination.getX();
            for(int y = minY; y <= maxY; y++) {
                Position coord = new Position(x, y);
                if(coordinates.containsKey(coord)){
                    coordinates.put(coord, coordinates.get(coord)+1);
                }else {
                    coordinates.put(coord, 1);
                }
            }
        }
    }

    public boolean try_draw_vent(String vent_line) {
        List<Position> line = generate_coords(vent_line);

        if(!is_drawable(line)) {
            return false;
        }

        draw_vent(line);

        return true;
    }

    public long count_overlap() {
        return coordinates.values().stream().filter(v -> v > 1).count();
    }

    public void print_coordinates() {
        int maxX = coordinates.keySet().stream().map(p -> p.getX()).mapToInt(x -> x).max().getAsInt();
        int maxY = coordinates.keySet().stream().map(p -> p.getY()).mapToInt(y -> y).max().getAsInt();
        for(int y = 0; y <= maxY; y++){
            for(int x = 0; x <= maxX; x++){
                Position coord = new Position(x, y);
                if(coordinates.containsKey(coord))
                    System.out.print(coordinates.get(coord));
                else
                    System.out.print(".");
            }
            System.out.println();
        }
    }

    public Map<Position, Integer> getCoordinates() {
        return coordinates;
    }
}

class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Position [x=" + x + ", y=" + y + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Position other = (Position) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }


    


}

