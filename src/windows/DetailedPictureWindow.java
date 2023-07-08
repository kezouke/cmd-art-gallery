package windows;

import db_objects.Picture;
import representation_instruments.OutputMessage;

import java.io.IOException;
import java.util.Scanner;

public class DetailedPictureWindow implements Window {
    private final Picture picture;
    private final Scanner scanner;

    public DetailedPictureWindow(Picture picture, Scanner scanner) {
        this.picture = picture;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        boolean running = true;
        OutputMessage authorMessage =
                new OutputMessage("files/OutputForAuthor");
        OutputMessage errorMessage =
                new OutputMessage("files/OutputForError");
        while (running) {
            try {
                System.out.println(picture.detailedInfo());
                authorMessage.display();

                String input = scanner.next();
                if (input.equals("back")) {
                    running = false;
                } else if (input.equals("author")) {
                    new DetailedAuthorWindow(picture.author, scanner).execute();
                } else {
                    errorMessage.display();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}