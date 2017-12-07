import java.util.Scanner;

public class KonstytucjaRun {
    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        System.out.println("Podaj ścieżkę do pliku: ");
        //String filePath = input.next();
        String filePath = "./konstytucja.txt", mode = "T", element = "Rozdział 1";
        System.out.println("Podaj tryb działania programu: S - Spis treści, T - Treść podanego pliku");
        //String mode = input.next();
        System.out.println("Podaj element, który chcesz wyświetlić:\nLEGENDA:\n" +
                "Rozdział, Sekcja, Artykuł, Ustęp, Pkt\n+ numer");
        //String element = input.next();

        InputParser inputParser = new InputParser(filePath, mode, element);
        inputParser.parseInputFile();


        //System.out.println(inputParser.parseInputFile());
    }
}
