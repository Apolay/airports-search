import autocomlete.Autocomplete;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Scanner;

public class AirportsSearch {
    public static void main(String[] args) throws IOException {

        Path p = Path.of("airports.csv");
        try(Autocomplete autocomplete = new Autocomplete(Integer.parseInt(args[0]), p)) {
            Scanner scanner = new Scanner(System.in);
            String input;
            do {
                System.out.println("Enter text: ");
                input = scanner.nextLine();
                if(input.isBlank()) continue;
                autocomplete.find(input);
            } while (!input.equals("!quit"));
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
