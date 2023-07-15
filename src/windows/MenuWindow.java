package windows;

import com.google.cloud.firestore.Firestore;
import db_connectors.FirestoreUpdateData;
import representation_instruments.OutputMessage;

import java.io.IOException;
import java.util.Scanner;

public class MenuWindow implements Window {
    private final Scanner scanner;
    private final FirestoreUpdateData firestoreUpdater;
    private final Firestore database;

    public MenuWindow(Scanner scanner,
                      FirestoreUpdateData firestoreUpdater,
                      Firestore database) {
        this.scanner = scanner;
        this.firestoreUpdater = firestoreUpdater;
        this.database = database;
    }

    @Override
    public void execute() {
        OutputMessage menuGreetings =
                new OutputMessage("files/OutputForMenu");
        OutputMessage wrongCommand =
                new OutputMessage("files/OutputForError");
        boolean running = true;
        while (running) {
            try {
                menuGreetings.display();
                String input = scanner.next();
                switch (input) {
                    case "pictures" -> new ShowPicturesWindow(
                            firestoreUpdater,
                            scanner,
                            database
                    ).execute();
                    case "authors" -> new ShowAuthorsWindow(
                            database,
                            firestoreUpdater,
                            scanner
                    ).execute();
                    case "search" -> new ChooseSearchObjectWindow(
                            scanner,
                            firestoreUpdater,
                            database
                    ).execute();
                    case "logout" -> {
                        logout();
                        running = false;
                    }
                    case "close" -> running = false;
                    default -> wrongCommand.display();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void logout() throws IOException {
        new OutputMessage("files/OutputForLogout").display();
        new AuthWindow(scanner, database).execute();
    }
}
