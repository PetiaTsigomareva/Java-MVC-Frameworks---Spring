package realestateagency.util;

import java.io.*;

public class HtmlReader {


    public String readHtmlFile(String htmlPath) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(htmlPath))));
        StringBuilder htmlFileContent = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            htmlFileContent.append(line).append(System.lineSeparator());
        }

        return htmlFileContent.toString().trim();
    }
}
