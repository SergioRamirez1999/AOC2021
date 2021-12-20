import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        String pzzinput_pathname = "pzzinput_mock.txt";
        List<String> lines = read_all_lines(pzzinput_pathname);
        List<Integer> daysProcreateInital = Arrays.stream(lines.get(0).split(",")).map(Integer::parseInt).collect(() -> new ArrayList<Integer>(), ArrayList::add, ArrayList::addAll);
        List<Lanternfish> initial_fishes = daysProcreateInital.stream().map(d -> new Lanternfish(d, 18)).collect(Collectors.toList());
        Lanternfish fish = initial_fishes.get(1);
        List<Lanternfish> childs = fish.foo();
        System.out.println(fish);
        System.out.println(childs);

    }


}


class Lanternfish {
    public int daysProcreate = 8;
    public int daysRemaining;

    public Lanternfish() {
    }

    public Lanternfish(int daysProcreate) {
        this.daysProcreate = daysProcreate;
    }

    public Lanternfish(int daysProcreate, int daysRemaining) {
        this.daysProcreate = daysProcreate;
        this.daysRemaining = daysRemaining;
    }

    public List<Lanternfish> foo() {
        List<Lanternfish> childs = new ArrayList<>();
        while(daysRemaining > daysProcreate+1) {
            daysRemaining -= daysProcreate;
            daysProcreate = 6;
            childs.add(new Lanternfish(8, daysRemaining));
        }
        return childs;
    }

    @Override
    public String toString() {
        return "Lanternfish [daysProcreate=" + daysProcreate + ", daysRemaining=" + daysRemaining + "]";
    }

    
}
