package project1;

import java.util.Scanner;

public class KonstytucjaRun {
    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        int range1 = 0, range2 = 0;
        String filePath, mode, element, range, plik;

        filePath = "./konstytucja.txt";
/*
        mode = "S";
        element = "Rozdział";
        range = "Z";
        plik = "K";
        range1 = 1;
        range2 = 2;
*/
        /*System.out.println("Podaj ścieżkę do pliku: ");
        filePath = input.nextLine();*/

        System.out.println("Podany plik to: K - Konstytucja.txt, U - UOKIK.txt");
        plik = input.nextLine();

        System.out.println("Podaj tryb działania programu: S - Spis treści, T - Treść podanego pliku");
        mode = input.nextLine();

        System.out.println("Podaj element, który chcesz wyświetlić:\n" +
                "Rozdział, Sekcja, Artykuł, Artykuł 'Nr' Punkt, ALL\n");
        element = input.nextLine();

        System.out.println("Podaj tryb wyświetlenia elementu: P - Pojedynczy element, Z - Zakres elementów");
        range = input.nextLine();

        if (!element.equals("ALL")) {
            switch (range) {
                case "P":
                    System.out.println("Podaj numer elementu do wyświetlenia");
                    range1 = Integer.parseInt(input.nextLine());
                    break;
                case "Z":
                    System.out.println("Podaj początek zakresu elementów do wyświetlenia");
                    range1 = Integer.parseInt(input.nextLine());
                    System.out.println("Podaj koniec zakresu elementów do wyświetlenia");
                    range2 = Integer.parseInt(input.nextLine());
                    break;
                default:
                    System.out.println("Błędne dane!");
                    break;
            }
        }

        switch (plik) {
            case "K":
                KonstytucjaInputParser konstytucjaInputParser = new KonstytucjaInputParser(filePath, mode, element, range1, range2);
                konstytucjaInputParser.parseInputFile();
                break;
            case "U":
                UokikInputParser uokikInputParser = new UokikInputParser(filePath, mode, element, range1, range2);
                uokikInputParser.parseInputFile();
                break;
            default:
                System.out.println("Błędne dane!");
                break;
        }
    }
}
