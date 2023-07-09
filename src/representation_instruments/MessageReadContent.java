package representation_instruments;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MessageReadContent {
    private final String fileName;

    public MessageReadContent(String fileName) {
        this.fileName = fileName;
    }

    public String readFileContent() throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line);
                contentBuilder.append(System.lineSeparator());
            }
        }
        return contentBuilder.toString();
    }
}
