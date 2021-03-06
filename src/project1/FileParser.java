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
                    if (sCurrentLine.matches("^Rozdział .*$") || sCurrentLine.matches("^DZIAŁ .*$")) {
                        textStarted = true;
                        fileArray.add(sCurrentLine);
                        sCurrentLine = null;
                    } else
                        continue;
                }

                while (sCurrentLine != null) {
                    if (sCurrentLine.matches("^Art. [0-9]*[a-z]?. .*$")) {
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
                        else if (sCurrentLine.matches("^Rozdział \\w*$") || sCurrentLine.matches("^[A-Z ]*$") || sCurrentLine.matches("^DZIAŁ .*$")) {
                            fileArray.add(sCurrentLine);
                            index++;
                        } else if (sCurrentLine.matches("^Art. [0-9]*[a-z]?.$")) {
                            fileArray.add(sCurrentLine);
                            index++;
                        } else if (sCurrentLine.matches("^\\d{1,3}[a-z]?\\. .*$")
                                || sCurrentLine.matches("^\\d{1,3}[a-z]?\\) .*$")
                                || sCurrentLine.matches("^[a-z]{1,3}\\) .*$")) {
                            if (sCurrentLine.matches("^.*-$"))
                                fileArray.add(sCurrentLine.substring(0, sCurrentLine.length() - 1));
                            else
                                fileArray.add(sCurrentLine + " ");

                            index++;
                        } else {
                            String tmp = fileArray.get(index);
                            if ((sCurrentLine.substring(sCurrentLine.length() - 1).equals(".")
                                    || sCurrentLine.substring(sCurrentLine.length() - 1).equals(":")
                                    || sCurrentLine.substring(sCurrentLine.length() - 1).equals(";")
                                    || sCurrentLine.substring(sCurrentLine.length() - 1).equals(")")
                                    || sCurrentLine.substring(sCurrentLine.length() - 1).equals(",")
                                    || !sCurrentLine.matches("^(.|\\n)*-$")
                                    || tmp.matches("^[a-z]{1,3}\\) .*,$"))
                                    && (tmp.matches("^Art. \\d*[a-z]?.$")
                                    || tmp.matches("^Rozdział \\w*$")
                                    || tmp.matches("^DZIAŁ \\w*$")))
                                fileArray.set(index, tmp + "\n" + sCurrentLine + " ");
                            else if ((sCurrentLine.substring(sCurrentLine.length() - 1).equals(".")
                                    || sCurrentLine.substring(sCurrentLine.length() - 1).equals(":")
                                    || sCurrentLine.substring(sCurrentLine.length() - 1).equals(";")
                                    || sCurrentLine.substring(sCurrentLine.length() - 1).equals(")")
                                    || sCurrentLine.substring(sCurrentLine.length() - 1).equals(",")
                                    || !sCurrentLine.matches("^(.|\\n)*-$")
                                    || tmp.matches("^[a-z]{1,3}\\) .*,$")))
                                fileArray.set(index, tmp + sCurrentLine + " ");
                            else if (sCurrentLine.substring(sCurrentLine.length() - 1).equals("-") && tmp.matches("^Art. \\d*.$"))
                                fileArray.set(index, tmp + "\n" + sCurrentLine.substring(0, sCurrentLine.length() - 1));
                            else if (sCurrentLine.substring(sCurrentLine.length() - 1).equals("-"))
                                fileArray.set(index, tmp + sCurrentLine.substring(0, sCurrentLine.length() - 1));
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
