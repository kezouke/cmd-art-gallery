package windows;

import java.util.Map;
import java.util.Scanner;

import db_objects.Picture;

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
        while (running) {

            for (Picture picture : pictures.values()) {
                System.out.println(picture.shortInfo());
            }
            System.out.println(
                    """
                    If you want to see detailed info about picture
                    \tenter a text with following format: picture#
                    \twhere # is Unique Number of picture
                    If you want to close application:
                    \tenter 'stop'.
                    """);

            String input = scanner.next();
            if (input.equals("stop")) {
                running = false;
            } else if (pictures.containsKey(input)) {
                new DetailedPictureWindow(pictures.get(input), scanner).execute();
            } else {
                System.out.println("No such command was found!");
            }
        }
    }
}

