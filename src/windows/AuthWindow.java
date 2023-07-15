package windows;

import com.google.cloud.firestore.Firestore;
import db_connectors.FirestoreUpdateData;
import representation_instruments.OutputMessage;

import java.io.IOException;
import java.util.Scanner;

public class AuthWindow implements Window {
    private final Scanner scanner;
    private final Firestore database;

    public AuthWindow(Scanner scanner, Firestore database) {
        this.scanner = scanner;
        this.database = database;
    }

    @Override
    public void execute() {
        boolean running = true;
        OutputMessage authMessage =
                new OutputMessage("files/OutputForAuthPage");
        OutputMessage errorMessage =
                new OutputMessage("files/OutputForError");
        while (running) {
            try {
                authMessage.display();
                String input = scanner.next();
                switch (input) {
                    case "login" -> {
                        new LoginWindow(scanner, database).execute();
                        running = false;
                    }
                    case "register" -> {
                        new RegisterWindow(scanner, database).execute();
                        running = false;
                    }
                    case "close" -> {
                        return;
                    }
                    default -> errorMessage.display();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        new MenuWindow(
                scanner,
                new FirestoreUpdateData(database),
                database
        ).execute();
    }
}
