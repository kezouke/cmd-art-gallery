package windows.remove;

import com.google.cloud.firestore.Firestore;
import db_connectors.firebase.FirestoreUpdateData;
import db_connectors.remove.RemoveSavedAuthor;
import db_objects.Author;
import instruments.window_messages.remove_windows.UnSaveAuthorWindowMessage;
import windows.Window;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class UnSaveAuthorWindow implements Window {
    private final Scanner scanner;
    private final Firestore database;

    private final FirestoreUpdateData firestoreUpdate;
    private final UnSaveAuthorWindowMessage messageEngine;
    private final Author author;

    public UnSaveAuthorWindow(Scanner scanner,
                              Firestore database,
                              FirestoreUpdateData firestoreUpdate,
                              Author author) {
        this.scanner = scanner;
        this.database = database;
        this.firestoreUpdate = firestoreUpdate;
        this.author = author;
        this.messageEngine = new UnSaveAuthorWindowMessage();
    }

    @Override
    public void execute() {
        boolean running = true;
        while (running) {
            messageEngine.outputIsUserWantToSave();
            String isUserWant = scanner.next();
            if (isUserWant.equals("no")) {
                running = false;
            } else if (isUserWant.equals("yes")) {
                try {
                    new RemoveSavedAuthor(
                            database,
                            firestoreUpdate
                    ).removeFromSaved(
                            firestoreUpdate.currentUser,
                            author
                    );
                    messageEngine.outputSuccess();
                } catch (ExecutionException |
                         InterruptedException e) {
                    throw new RuntimeException(e);
                }
                running = false;
            } else {
                messageEngine.outputWrongCommandEntered();
            }

        }
    }
}
