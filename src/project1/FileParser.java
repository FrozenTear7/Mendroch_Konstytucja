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
            String sCurrentLine, sCurrentLine2 = null;
            int index = 0;

            while ((sCurrentLine = br.readLine()) != null) {
                if (!textStarted) {
                    if (sCurrentLine.matches("^Rozdział \\w*$") || sCurrentLine.matches("^DZIAŁ \\w*$")) {
                        textStarted = true;
                        fileArray.add(sCurrentLine + "\n");
                        sCurrentLine = null;
                    } else
                        continue;
                }

                while (sCurrentLine != null) {
                    if (sCurrentLine.matches("^Art. [0-9]*. .*$")) {
                        int countDots = 0, lineIndex = 0;

                        while (countDots != 2) {
                            if (sCurrentLine.charAt(lineIndex) == '.')
                                countDots++;
                            lineIndex++;
                        }

                        sCurrentLine2 = sCurrentLine.substring(lineIndex + 1, sCurrentLine.length());
                        sCurrentLine = sCurrentLine.substring(0, lineIndex);
                    } else {
                        if (sCurrentLine.matches(".*\\d{4}-\\d{2}-\\d{2}.*") || sCurrentLine.matches(".*Kancelaria Sejmu.*"))
                            break;
                        else if (sCurrentLine.matches("^Rozdział \\w*$") || sCurrentLine.matches("^[^a-z]*$")
                                || sCurrentLine.matches("^Art. [0-9]*.$")) {
                            fileArray.add(sCurrentLine + "\n");
                            index++;
                        } else if (sCurrentLine.matches("^\\d{1,3}. \\D*$")
                                || sCurrentLine.matches("^\\d{1,3}\\) \\D*$")
                                || sCurrentLine.matches("^[0-9a-z]{1,4}\\) \\D*$")) {
                            if (sCurrentLine.matches("^.*-$"))
                                fileArray.add(sCurrentLine.substring(0, sCurrentLine.length() - 1));
                            else if (sCurrentLine.matches("^.*[.:;]$") || sCurrentLine.matches("^[a-z]{1,3}\\) \\D*,$"))
                                fileArray.add(sCurrentLine + "\n");
                            else
                                fileArray.add(sCurrentLine + " ");

                            index++;
                        } else if (sCurrentLine.matches("^[a-z]{1,3}\\) \\D*$")) {
                            String tmp = fileArray.get(index);
                            fileArray.set(index, tmp + sCurrentLine + " ");
                        } else {
                            String tmp = fileArray.get(index);
                            if (tmp.matches("^DZIAŁ (.|\\n)*$") || tmp.matches("^Rozdział (.|\\n)*$"))
                                fileArray.set(index, tmp + sCurrentLine + "\n");
                            else if (sCurrentLine.substring(sCurrentLine.length() - 1).equals(".")
                                    || sCurrentLine.substring(sCurrentLine.length() - 1).equals(":")
                                    || sCurrentLine.substring(sCurrentLine.length() - 1).equals(";")
                                    || sCurrentLine.substring(sCurrentLine.length() - 1).equals(")"))
                                fileArray.set(index, tmp + sCurrentLine + "\n");
                            else if (sCurrentLine.substring(sCurrentLine.length() - 1).equals("-"))
                                fileArray.set(index, tmp + sCurrentLine.substring(0, sCurrentLine.length() - 1));
                            else if (tmp.matches("^[a-z]{1,3}\\) .*,$"))
                                fileArray.set(index, tmp + sCurrentLine + "\n");
                            else
                                fileArray.set(index, tmp + sCurrentLine + " ");
                        }

                        if (sCurrentLine2 != null) {
                            sCurrentLine = sCurrentLine2;
                            sCurrentLine2 = null;
                        } else {
                            sCurrentLine = null;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileArray;
    }
}
