import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class InputParser {
    private StringBuilder contentBuilder = new StringBuilder();
    private Scanner input = new Scanner(System.in);
    private String filePath;

    public InputParser (String filePath) {
        this.filePath = filePath;
    }

    public String parseInputFile () {
        Tree root = new Tree("KONSTYTUCJA");

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

        return contentBuilder.toString();
    }
}
