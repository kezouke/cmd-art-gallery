package representation_instruments;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OutputMessage {
    private final String fileName;
    private String fileContent;
    private boolean isRead = false;

    public OutputMessage(String fileName) {
        this.fileName = fileName;
    }

    private void readFileContent() throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = br.readLine()) != null) {
            contentBuilder.append(line);
            contentBuilder.append(System.lineSeparator());
        }
        fileContent = contentBuilder.toString();

    }

    public void display() throws IOException {
        if (!isRead) {
            readFileContent();
            isRead = true;
        }
        System.out.print(fileContent);
    }
}
