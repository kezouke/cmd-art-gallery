package windows;

import db_objects.Author;
import representation_instruments.OutputMessage;

import java.io.IOException;
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
        while (running) {
            try {
                System.out.println(author.detailedInfo());
                new OutputMessage("files/OutputForBackAfterAuthor").display();

                String input = scanner.next();
                if (input.equals("back")) {
                    running = false;
                } else {
                    new OutputMessage("files/OutputForError").display();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

