package windows.search_windows;

import com.google.cloud.firestore.Firestore;
import db_connectors.firebase.FirestoreUpdateData;
import instruments.window_messages.search_windows.ChooseSearchObjectWindowMessage;
import windows.Window;

import java.util.Scanner;

public class ChooseSearchObjectWindow implements Window {
    private final Scanner scanner;
    private final FirestoreUpdateData firestoreUpdater;
    private final Firestore database;
    private final ChooseSearchObjectWindowMessage messageEngine;

    public ChooseSearchObjectWindow(Scanner scanner,
                                    FirestoreUpdateData firestoreUpdater,
                                    Firestore database) {
        this.scanner = scanner;
        this.firestoreUpdater = firestoreUpdater;
        this.database = database;
        this.messageEngine = new ChooseSearchObjectWindowMessage();
    }

    @Override
    public void execute() {
        boolean running = true;
        while (running) {
            messageEngine.outputVariantsToChose();
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
                default -> messageEngine
                        .outputWrongCommandEnteredMessage();
            }
        }
    }
}
