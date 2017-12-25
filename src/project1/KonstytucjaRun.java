package project1;

import java.util.ArrayList;
import java.util.Scanner;

public class KonstytucjaRun {
    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        String filePath, mode, element, file, range1, range2;
        Tree root = null;
        OptionsParser optionsParser;

        //filePath = "./konstytucja.txt";
        //mode = "T";
        //element = "Art. 241.";
        //file = "K";
        range1 = "";
        range2 = "";

        System.out.println("Ścieżka do pliku | plik - K - konstytucja, U - uokik | tryb - S - spis treści, T - treść");
        filePath = input.next();
        file = input.next();
        mode = input.next();

        if(!file.equals("K") && !file.equals("U")){
            System.out.println("Błędne dane!");
            return;
        }

        System.out.println("Podaj element, który chcesz wyświetlić:\n" +
                "ALL - wyświetl całość dokumentu, Z - zakres artykułów, Element dokładnie podany ze spisu treści\n" +
                "Jeśli chcesz wyświetlić zakres artykułów podaj w kolejnych liniach odczytu ich nazwy\n");

        element = input.nextLine(); //consume the \n from the previous input
        element = input.nextLine();

        FileParser fileParser = new FileParser(filePath);
        ArrayList<String> fileToArray = fileParser.parseInputFile();

        if (file.equals("K")) {
            KonstytucjaInputParser konstytucjaInputParser = new KonstytucjaInputParser(fileToArray);
            root = konstytucjaInputParser.parseInputFile();
        } else if (file.equals("U")) {
            UokikInputParser uokikInputParser = new UokikInputParser(fileToArray);
            root = uokikInputParser.parseInputFile();
        } else {
            System.out.println("Błędne dane!");
            return;
        }

        if (element.equals("Z")) {
            range1 = input.nextLine();
            range2 = input.nextLine();
            optionsParser = new OptionsParser(mode, range1, range2, root);
        } else {
            optionsParser = new OptionsParser(mode, element, root);
        }


        optionsParser.printOutput();
    }
}
