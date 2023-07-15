package windows;

import com.google.cloud.firestore.Firestore;
import db_connectors.FirestoreUpdateData;
import representation_instruments.OutputMessage;

import java.io.IOException;
import java.util.Scanner;

public class ChooseSearchObjectWindow implements Window {
    private final Scanner scanner;
    private final FirestoreUpdateData firestoreUpdater;
    private final Firestore database;

    public ChooseSearchObjectWindow(Scanner scanner,
                                    FirestoreUpdateData firestoreUpdater,
                                    Firestore database) {
        this.scanner = scanner;
        this.firestoreUpdater = firestoreUpdater;
        this.database = database;
    }

    @Override
    public void execute() {
        OutputMessage chooseObjMessage =
                new OutputMessage("files/OutputToChoseSearchObject");
        OutputMessage wrongCommand =
                new OutputMessage("files/OutputForError");
        boolean running = true;
        while (running) {
            try {
                chooseObjMessage.display();
                String input = scanner.next();
                switch (input) {
                    case "pictures" -> {
                        new SearchPicturesWindow(
                                scanner,
                                firestoreUpdater,
                                database
                        ).execute();
                        running = false;
                    }
                    case "authors" -> {
                        new SearchAuthorsWindow(
                                scanner,
                                firestoreUpdater,
                                database
                        ).execute();
                        running = false;
                    }
                    case "return" -> running = false;
                    default -> wrongCommand.display();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
