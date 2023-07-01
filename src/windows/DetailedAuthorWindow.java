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
        boolean running = true;
        boolean wrongCommandWasEntered = false;
        while (running) {
            System.out.println(author.detailedInfo());
            System.out.println("""
                    If you want to return back:
                    \tenter 'back'.
                    """);
            if (wrongCommandWasEntered) {
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!");
                System.out.println("No such command was found!");
                wrongCommandWasEntered = false;
            }

            String input = scanner.next();
            if (input.equals("back")) {
                running = false;
            } else {
                wrongCommandWasEntered = true;
            }
        }
    }
}

