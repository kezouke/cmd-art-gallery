package representation_instruments;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class OutputMessage {
    private final FileReader file;

    public OutputMessage(String fileName) {
        try {
            file = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void display() throws IOException {
        int c;
        while((c=file.read())!=-1){

            System.out.print((char)c);
        }
    }
}