import java.util.Scanner;

public class KonstytucjaRun {
    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        System.out.println("Podaj ścieżkę do pliku: ");
        //String filePath = input.next();
        String filePath = "./konstytucja.txt";
        System.out.println("Podaj tryb działania programu: S - Spis treści, T - Treść podanego pliku");
        //String mode = input.next();
        System.out.println("Podaj element, który chcesz wyświetlić:\nLEGENDA:\n" +
                "R - Rozdział, A - Artykuł, U - Ustęp, P - Pkt, PP - Podpunkt\n+ numer\nBrak argumentu wyświetli cały plik lub cały spis treści\n");
        //String element = input.next();

        InputParser inputParser = new InputParser(filePath);
        inputParser.parseInputFile();

        //OptionsParser optionsParser = new OptionsParser(mode, element);

        //System.out.println(inputParser.parseInputFile());
    }
}
