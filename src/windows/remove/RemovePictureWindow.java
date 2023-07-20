package windows.remove;

import com.google.cloud.firestore.Firestore;
import db_connectors.firebase.FirestoreUpdateData;
import db_connectors.remove.RemovePicture;
import instruments.window_messages.remove_windows.RemovePictureWindowMessage;
import windows.Window;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class RemovePictureWindow implements Window {
    private final Firestore database;
    private final FirestoreUpdateData firestoreUpdater;
    private final Scanner scanner;
    private final int id;

    public RemovePictureWindow(Firestore database,
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
        RemovePictureWindowMessage messageEngine
                = new RemovePictureWindowMessage();
        while (running) {
            try {
                messageEngine.outputQuestionIsUserSure();
                String input = scanner.next();
                switch (input) {
                    case "yes" -> {
                        new RemovePicture(
                                database,
                                firestoreUpdater
                        ).removeById(id);
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
