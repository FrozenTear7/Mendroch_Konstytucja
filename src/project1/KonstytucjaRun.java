package project1;

import java.util.Scanner;

public class KonstytucjaRun {
    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        int range1 = 0, range2 = 0;
        String filePath, mode, element, range;

        filePath = "./konstytucja.txt";
        mode = "T";
        element = "Rozdział";
        range = "Z";
        range1 = 1;
        range2 = 3;

        System.out.println("Podaj ścieżkę do pliku: ");
        //filePath = input.next();

        System.out.println("Podaj tryb działania programu: S - Spis treści, T - Treść podanego pliku");
        //mode = input.next();

        System.out.println("Podaj element, który chcesz wyświetlić:\nLEGENDA:\n" +
                "Rozdział, Sekcja, Artykuł, Ustęp, Pkt\n");
        //element = input.next();

        System.out.println("Podaj tryb wyświetlenia elementu: P - Pojedynczy element, Z - Zakres elementów");
        //range = input.next();
/*
        if(range.equals("P")) {
            System.out.println("Podaj numer elementu do wyświetlenia");
            range1 = Integer.parseInt(input.next());
        }
        if(range.equals("Z")) {
            System.out.println("Podaj początek zakresu elementów do wyświetlenia");
            range1 = Integer.parseInt(input.next());
            System.out.println("Podaj koniec zakresu elementów do wyświetlenia");
            range2 = Integer.parseInt(input.next());
        }*/

        KonstytucjaAbstractInputParser konstytucjaInputParser = new KonstytucjaAbstractInputParser(filePath, mode, element, range1, range2);
        konstytucjaInputParser.parseInputFile();


        //System.out.println(konstytucjaInputParser.parseInputFile());
    }
}
