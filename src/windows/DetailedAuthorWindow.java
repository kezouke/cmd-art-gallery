package windows;

import db_objects.Author;

import java.util.Scanner;

public class DetailedAuthorWindow implements Window {
    private final Author author;
    private final Scanner scanner;

    public DetailedAuthorWindow(Author author, Scanner scanner) {
        this.author = author;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println(author.detailedInfo());
        boolean running = true;
        while (running) {
            String input = scanner.next();
            if (input.equals("back")) {
                return;
            }
        }
    }
}

