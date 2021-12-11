import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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

    private static int count_binary_number(int position, int binary_value, List<String> binary_numbers) {
        return (int)binary_numbers.stream().map(number -> Character.getNumericValue(number.toCharArray()[position])).filter(number -> number == binary_value).count();
    }

    private static int determine_most_common_bit_oxygen(int position, List<String> binary_numbers) {
        if(count_binary_number(position, 0, binary_numbers) > count_binary_number(position, 1, binary_numbers))
            return 0;
        return 1;
        
    }

    private static int determine_most_common_bit_c02(int position, List<String> binary_numbers) {
        if(count_binary_number(position, 0, binary_numbers) > count_binary_number(position, 1, binary_numbers))
            return 1;
        return 0;
    }

    private static List<String> get_binary_matches_by_position_and_binary_value_oxygen(int position, List<String> binary_numbers) {
        int most_common_bit = determine_most_common_bit_oxygen(position, binary_numbers);
        return binary_numbers.stream().filter(number -> Character.getNumericValue(number.toCharArray()[position]) == most_common_bit).collect(Collectors.toList());
    }

    private static List<String> get_binary_matches_by_position_and_binary_value_c02(int position, List<String> binary_numbers) {
        int most_common_bit = determine_most_common_bit_c02(position, binary_numbers);
        return binary_numbers.stream().filter(number -> Character.getNumericValue(number.toCharArray()[position]) == most_common_bit).collect(Collectors.toList());
    }

    private static int determine_oxygen_generator_rating_decimal(List<String> binary_numbers) {
        int iterations = binary_numbers.get(0).length();
        for(int i = 0; i < iterations; i++){
            if(binary_numbers.size() == 1)
                break;
            binary_numbers = get_binary_matches_by_position_and_binary_value_oxygen(i, binary_numbers);
        }
        return Integer.parseInt(binary_numbers.get(0), 2);
    }

    private static int determine_c02_scrubber_rating_decimal(List<String> binary_numbers) {
        int iterations = binary_numbers.get(0).length();
        for(int i = 0; i < iterations; i++){
            if(binary_numbers.size() == 1)
                break;
            binary_numbers = get_binary_matches_by_position_and_binary_value_c02(i, binary_numbers);
        }
        return Integer.parseInt(binary_numbers.get(0), 2);
    }
    public static void main(String[] args) {
        String pzzinput_pathname = "pzzinput.txt";
        List<String> binary_numbers = read_all_lines(pzzinput_pathname);
        System.out.println(determine_oxygen_generator_rating_decimal(binary_numbers) * determine_c02_scrubber_rating_decimal(binary_numbers));
    }
    
}
