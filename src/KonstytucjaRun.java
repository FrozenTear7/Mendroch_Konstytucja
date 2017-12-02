import java.io.*;
import java.util.Scanner;

public class KonstytucjaRun {
    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        //System.out.println("Podaj ścieżkę do pliku: ");
        //String filePath = input.next();
        String filePath = "./konstytucja.txt";
        String finalInput;

        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "Cp1250"))) {
            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.matches(".*\\d{4}-\\d{2}-\\d{2}.*") || sCurrentLine.matches(".*Kancelaria Sejmu.*"))
                    contentBuilder.append("");
                else if(sCurrentLine.endsWith("-"))
                    contentBuilder.append(sCurrentLine.substring(0, sCurrentLine.length()-1));
                else if (!sCurrentLine.endsWith(",") && !sCurrentLine.endsWith("."))
                    contentBuilder.append(sCurrentLine).append(" ");
                else
                    contentBuilder.append(sCurrentLine).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        finalInput = contentBuilder.toString();
        System.out.println(finalInput);
    }
}
