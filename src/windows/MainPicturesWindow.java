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
        OutputMessage picturesMessage =
                new OutputMessage("files/OutputForPicture");
        OutputMessage errorMessage =
                new OutputMessage("files/OutputForError");
        while (running) {
            try {
                for (Picture picture : pictures.values()) {
                    System.out.println(picture.shortInfo());
                }
                picturesMessage.display();

                String input = scanner.next();
                if (input.equals("stop")) {
                    running = false;
                } else if (pictures.containsKey(input)) {
                    new DetailedPictureWindow(pictures.get(input), scanner).execute();
                } else {
                    errorMessage.display();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}

