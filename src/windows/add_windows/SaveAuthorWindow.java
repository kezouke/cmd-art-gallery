package windows.add_windows;

import com.google.cloud.firestore.Firestore;
import db_connectors.firebase.FirestoreUpdateData;
import db_connectors.upload.SavedAuthorUpload;
import db_objects.Author;
import instruments.window_messages.add_windows.SaveAuthorWindowMessage;
import windows.Window;

import java.util.Scanner;

public class SaveAuthorWindow implements Window {
    private final Scanner scanner;
    private final Firestore database;

    private final FirestoreUpdateData firestoreUpdate;
    private final SaveAuthorWindowMessage messageEngine;
    private final Author author;

    public SaveAuthorWindow(Scanner scanner,
                             Firestore database,
                             FirestoreUpdateData firestoreUpdate,
                             Author author) {
        this.scanner = scanner;
        this.database = database;
        this.firestoreUpdate = firestoreUpdate;
        this.author = author;
        this.messageEngine = new SaveAuthorWindowMessage();
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
                new SavedAuthorUpload(
                        database,
                        firestoreUpdate
                ).saveAuthor(
                        author, firestoreUpdate.currentUser
                );
                messageEngine.outputSuccess();
                running = false;
            } else {
                messageEngine.outputWrongCommandEntered();
            }

        }
    }
}
