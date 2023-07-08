package windows;

import com.google.cloud.firestore.Firestore;
import db_connectors.FirestoreConnection;
import db_connectors.PicturesConnect;
import representation_instruments.OutputMessage;

import java.io.IOException;
import java.util.Scanner;

public class AuthWindow implements Window {
    private final Scanner scanner;

    public AuthWindow(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        Firestore db = new FirestoreConnection().db;
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
                        new LoginWindow(scanner, db).execute();
                        running = false;
                    }
                    case "register" -> {
                        new RegisterWindow(scanner, db).execute();
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
        new MainPicturesWindow(new PicturesConnect(db).receivePicture(), scanner).execute();
    }
}
