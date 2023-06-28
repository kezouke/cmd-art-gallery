package windows;

import db_objects.Picture;

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
        System.out.println(picture.detailedInfo());
        boolean running = true;
        while (running) {
            String input = scanner.next();
            if (input.equals("author")) {
                new DetailedAuthorWindow(picture.author, scanner).execute();
            }
            if (input.equals("back")) {
                return;
            }
        }
    }
}