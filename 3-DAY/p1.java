import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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

    private static int count_binary_number(int position, int binary_value, List<String> binary_numbers) {
        return (int)binary_numbers.stream().map(number -> Character.getNumericValue(number.toCharArray()[position])).filter(number -> number == binary_value).count();
    }

    private static int determine_most_common_bit(int position, List<String> binary_numbers) {
        if(count_binary_number(position, 0, binary_numbers) > count_binary_number(position, 1, binary_numbers))
            return 0;

        return 1;
        
    }

    private static String calcule_gamma_binary(List<String> binary_numbers){
        StringBuilder gamma = new StringBuilder(); 
        for(int i = 0; i < binary_numbers.get(0).length(); i++) {
            gamma.append(determine_most_common_bit(i, binary_numbers));
        }
        return gamma.toString();
    }

    private static String calcule_epilson_binary(List<String> binary_numbers){
        return calcule_gamma_binary(binary_numbers).replaceAll("0", "2").replaceAll("1", "0").replaceAll("2", "1");
    }

    private static Integer calcule_gamma_decimal(List<String> binary_numbers){
        return Integer.parseInt(calcule_gamma_binary(binary_numbers), 2);
    }

    private static Integer calcule_epilson_decimal(List<String> binary_numbers){
        return Integer.parseInt(calcule_epilson_binary(binary_numbers), 2);
    }

    public static void main(String[] args) {
        String pzzinput_pathname = "pzzinput.txt";
        List<String> binary_numbers = read_all_lines(pzzinput_pathname);

        System.out.println(calcule_gamma_decimal(binary_numbers) * calcule_epilson_decimal(binary_numbers));
    }
    
}
