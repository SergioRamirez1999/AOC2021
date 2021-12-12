import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
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

    private static List<Board> generate_boards(List<String> lines) {
        List<Board> boards = new ArrayList<>();
        List<String> lines_boards = lines.subList(1, lines.size());
        for(int i = 0; i < lines_boards.size()/5; i++){
            Map<Integer, Position> coordinates = new HashMap<>();
            List<String> string_board = lines_boards.subList(i*5, i*5+5);
            for(int h = 0; h < string_board.size(); h++){
                List<Integer> row_numbers = Arrays.asList(string_board.get(h).split(" ")).stream().filter(n -> !n.isBlank()).map(n -> n.trim()).map(Integer::parseInt).collect(Collectors.toList());
                for(int k = 0; k < row_numbers.size(); k++){
                    coordinates.put(row_numbers.get(k), new Position(h, k));
                }
            }
            boards.add(new Board(coordinates));
        }

        return boards;
    }

    private static int play_bingo(List<Integer> numbers, List<Board> boards) {
        int winner_score = 0;
        LinkedHashSet<Board> winning_boards = new LinkedHashSet<>();
        for(int i = 0; i < numbers.size() && winner_score == 0; i++){
            Integer number = numbers.get(i);
            for(int h = 0; h < boards.size(); h++){
                Board board = boards.get(h);
                if(!winning_boards.contains(board) && winning_boards.size() != boards.size()){
                    if(board.fill_tile(number)){
                        winning_boards.add(board);
                        if(winning_boards.size() == boards.size()){
                            winner_score = board.sum_unmarked() * number;
                            break;
                        }
                    }
                }
            }
        }
        return winner_score;
    }

    public static void main(String[] args) {
        String pzzinput_pathname = "pzzinput.txt";
        List<String> lines = read_all_lines(pzzinput_pathname);
        lines = lines.stream().filter(line -> !line.isBlank()).collect(Collectors.toList());
        List<Integer> numbers = Arrays.asList(lines.get(0).split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());
        List<Board> boards = generate_boards(lines);
        int winner_score = play_bingo(numbers, boards);
        System.out.println(winner_score);
    }
    
}

class Board {

    private Map<Integer, Position> coordinates;
    private List<Integer> rows_match = new ArrayList<>();
    private List<Integer> columns_match = new ArrayList<>();

    public Board(Map<Integer, Position> coordinates) {
        this.coordinates = coordinates;
        init_rows_columns_match();
    }

    private void init_rows_columns_match() {
        for(int i = 0; i < 5; i++){
            rows_match.add(0);
            columns_match.add(0);
        }
    }

    public boolean fill_tile(int number) {
        if(!coordinates.containsKey(number))
            return false;
        Position pos = coordinates.get(number);
        pos.setMarked(true);
        add_to_matches(pos);
        return has_bingo(pos);
    }

    private void add_to_matches(Position pos){
        rows_match.set(pos.getX(), rows_match.get(pos.getX())+1);
        columns_match.set(pos.getY(), columns_match.get(pos.getY())+1);
    }

    private boolean has_bingo(Position pos) {
       return rows_match.get(pos.getX()) == 5 || columns_match.get(pos.getY()) == 5;
    }

    public int sum_unmarked(){
        return coordinates.entrySet().stream().filter(k_v -> !k_v.getValue().isMarked()).map(k_v -> k_v.getKey()).mapToInt(x -> x).sum();
    }

    public Map<Integer, Position> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Map<Integer, Position> coordinates) {
        this.coordinates = coordinates;
    }

    public List<Integer> getRows_match() {
        return rows_match;
    }

    public void setRows_match(List<Integer> rows_match) {
        this.rows_match = rows_match;
    }

    public List<Integer> getColumns_match() {
        return columns_match;
    }

    public void setColumns_match(List<Integer> columns_match) {
        this.columns_match = columns_match;
    }

    @Override
    public String toString() {
        return "Board [columns_match=" + columns_match + ", coordinates=" + coordinates + ", rows_match=" + rows_match
                + "]";
    }
    
}

class Position {
    private int x;
    private int y;
    private boolean marked = false;

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

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    @Override
    public String toString() {
        return "Position [marked=" + marked + ", x=" + x + ", y=" + y + "]";
    }

    
}
