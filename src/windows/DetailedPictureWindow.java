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
        boolean running = true;
        while (running) {
            System.out.println(picture.detailedInfo());
            System.out.println("""
                    If you want to see detailed info about picture's author
                    \tenter 'author'
                    If you want to return back:
                    \tenter 'back'.
                    """);

            String input = scanner.next();
            if (input.equals("back")) {
                running = false;
            } else if (input.equals("author")) {
                new DetailedAuthorWindow(picture.author, scanner).execute();
            } else {
                System.out.println("No such command was found!");
            }
        }
    }
}