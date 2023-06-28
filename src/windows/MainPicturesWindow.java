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
        for (Picture picture : pictures.values()) {
            System.out.println(picture.shortInfo());
        }
        boolean running = true;
        while (running) {
            String input = scanner.next();
            if (input.equals("stop")) {
                return;
            } else if (pictures.containsKey(input)) {
                new DetailedPictureWindow(pictures.get(input), scanner).execute();
            }
        }
    }
}

