import java.util.Scanner;

public class KonstytucjaRun {
    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        System.out.println("Podaj ścieżkę do pliku: ");
        //String filePath = input.next();
        String filePath = "./konstytucja.txt", mode = "T", element = "Rozdział", range = "Z";
        int range1 = 1, range2 = 3;
        System.out.println("Podaj tryb działania programu: S - Spis treści, T - Treść podanego pliku");
        //String mode = input.next();
        System.out.println("Podaj element, który chcesz wyświetlić:\nLEGENDA:\n" +
                "Rozdział, Sekcja, Artykuł, Ustęp, Pkt\n");
        //String element = input.next();
        System.out.println("Podaj tryb wyświetlenia elementu: P - Pojedynczy element, Z - Zakres elementów");
        //String range = input.next();
        if(range.equals("P")) {
            System.out.println("Podaj numer elementu do wyświetlenia");
            //String range1 = input.next();
        }
        if(range.equals("Z")) {
            System.out.println("Podaj początek zakresu elementów do wyświetlenia");
            //String range1 = input.next();
            System.out.println("Podaj koniec zakresu elementów do wyświetlenia");
            //String range2 = input.next();
        }

        InputParser inputParser = new InputParser(filePath, mode, element, range1, range2);
        inputParser.parseInputFile();


        //System.out.println(inputParser.parseInputFile());
    }
}
