package representation_instruments;

import java.io.IOException;

public class OutputMessage {
    private final String fileName;
    private String fileContent;
    private boolean isRead = false;

    public OutputMessage(String fileName) {
        this.fileName = fileName;
    }


    public void display() throws IOException {
        if (!isRead) {
            fileContent = new MessageReadContent(fileName).readFileContent();
            isRead = true;
        }
        System.out.print(fileContent);
    }
}
