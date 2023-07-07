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
        while (running) {
            System.out.println(picture.detailedInfo());
            try {
                new OutputMessage("files/OutputForAuthor").display();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String input = scanner.next();
            if (input.equals("back")) {
                running = false;
            } else if (input.equals("author")) {
                new DetailedAuthorWindow(picture.author, scanner).execute();
            } else {
                try {
                    new OutputMessage("files/OutputForError").display();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}