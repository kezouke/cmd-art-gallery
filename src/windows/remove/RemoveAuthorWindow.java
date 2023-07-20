package windows.remove;

import com.google.cloud.firestore.Firestore;
import db_connectors.firebase.FirestoreUpdateData;
import db_connectors.remove.RemoveAuthor;
import representation_instruments.window_messages.remove_windows.RemoveAuthorWindowMessage;
import windows.Window;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class RemoveAuthorWindow implements Window {
    private final Firestore database;
    private final FirestoreUpdateData firestoreUpdater;
    private final Scanner scanner;
    private final int id;

    public RemoveAuthorWindow(Firestore database,
                              FirestoreUpdateData firestoreUpdater,
                              Scanner scanner,
                              int id) {
        this.database = database;
        this.firestoreUpdater = firestoreUpdater;
        this.scanner = scanner;
        this.id = id;
    }

    @Override
    public void execute() {
        boolean running = true;
        RemoveAuthorWindowMessage messageEngine =
                new RemoveAuthorWindowMessage();
        while (running) {
            try {
                messageEngine.outputQuestionIsUserSure();
                String input = scanner.next();
                switch (input) {
                    case "yes" -> {
                        new RemoveAuthor(
                                database,
                                firestoreUpdater
                        ).remove(id);
                        messageEngine.outputSuccessDeletion();
                        firestoreUpdater.updateData();
                        running = false;
                    }
                    case "no" -> running = false;
                    default -> messageEngine
                            .outputWrongCommandEnteredMessage();
                }
            } catch (ExecutionException
                     | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
