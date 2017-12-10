package project1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FileParser {
    private ArrayList<String> fileArray = new ArrayList<>();
    private String filePath;
    private boolean textStarted = false;

    public FileParser(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<String> parseInputFile() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "Cp1250"))) {
            String sCurrentLine;
            int index = 0;

            while ((sCurrentLine = br.readLine()) != null) {
                if (!textStarted) {
                    if (sCurrentLine.matches("^Rozdział \\w*$") || sCurrentLine.matches("^DZIAŁ \\w*$")) {
                        textStarted = true;
                        fileArray.add(sCurrentLine + "\n");
                    }
                } else {
                    if (sCurrentLine.matches(".*\\d{4}-\\d{2}-\\d{2}.*") || sCurrentLine.matches(".*Kancelaria Sejmu.*"))
                        continue;
                    else if (sCurrentLine.matches("^Rozdział \\w*$") || sCurrentLine.matches("^[^a-z]*$")
                            || sCurrentLine.matches("^Art. [0-9]*.$")) {
                        fileArray.add(sCurrentLine + "\n");
                        index++;
                    } else if (sCurrentLine.substring(sCurrentLine.length() - 1).equals(",")) {
                        String tmp = fileArray.get(index);
                        if (tmp.matches("^Art. [0-9]*.\\D*$") || tmp.substring(0, tmp.length()-2).matches("^\\d*. \\D*.$"))
                            fileArray.add(++index, sCurrentLine + " ");
                        else
                            fileArray.set(index, tmp + sCurrentLine + " ");
                    } else if (sCurrentLine.substring(sCurrentLine.length() - 1).equals(".")) {
                        String tmp = fileArray.get(index);
                        if (tmp.matches("^Art. [0-9]*.\\D*$") || tmp.substring(0, tmp.length()-2).matches("^\\d*. \\D*.$"))
                            fileArray.add(++index, sCurrentLine + "\n");
                        else
                            fileArray.set(index, tmp + sCurrentLine + "\n");
                    } else if (sCurrentLine.substring(sCurrentLine.length() - 1).equals("-")) {
                        String tmp = fileArray.get(index);
                        if (tmp.matches("^Art. [0-9]*.\\D*$") || tmp.substring(0, tmp.length()-2).matches("^\\d*. \\D*.$"))
                            fileArray.add(++index, sCurrentLine.substring(0, sCurrentLine.length() - 1));
                        else
                            fileArray.set(index, tmp + sCurrentLine.substring(0, sCurrentLine.length() - 1));
                    } else if (sCurrentLine.matches("^\\d*. \\D*-$")) {
                        fileArray.add(sCurrentLine.substring(0, sCurrentLine.length() - 1));
                        index++;
                    } else if (sCurrentLine.matches("^\\d*. \\D*[.]$")) {
                        fileArray.add(sCurrentLine + "\n");
                        index++;
                    } else if (sCurrentLine.matches("^\\d*. \\D*,+$")) {
                        fileArray.add(sCurrentLine + "");
                        index++;
                    } else if (sCurrentLine.matches("^\\d*. \\D*$")) {
                        fileArray.add(sCurrentLine + " ");
                        index++;
                    } else {
                        String tmp = fileArray.get(index);
                        fileArray.set(index, tmp + sCurrentLine + " ");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileArray;
    }
}
