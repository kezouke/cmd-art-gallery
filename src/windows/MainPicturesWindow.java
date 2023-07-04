package windows;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import db_objects.Picture;
import representation_instruments.OutputMessage;

public class MainPicturesWindow implements Window {
    private final Map<String, Picture> pictures;
    private final Scanner scanner;

    public MainPicturesWindow(Map<String, Picture> pictures, Scanner scanner) {
        this.pictures = pictures;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        boolean running = true;
        boolean wrongCommandWasEntered = false;
        while (running) {

            for (Picture picture : pictures.values()) {
                System.out.println(picture.shortInfo());
            }
            try {
                new OutputMessage("files/OutputForPicture").display();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (wrongCommandWasEntered) {
                try {
                    new OutputMessage("files/OutputForError").display();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                wrongCommandWasEntered = false;
            }

            String input = scanner.next();
            if (input.equals("stop")) {
                running = false;
            } else if (pictures.containsKey(input)) {
                new DetailedPictureWindow(pictures.get(input), scanner).execute();
            } else {
                wrongCommandWasEntered = true;
            }
        }
    }
}

