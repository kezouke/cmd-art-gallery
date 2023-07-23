package windows.remove;

import com.google.cloud.firestore.Firestore;
import db_connectors.firebase.FirestoreUpdateData;
import db_connectors.remove.RemoveSavedPicture;
import db_connectors.upload.SavedPictureUpload;
import db_objects.Picture;
import instruments.window_messages.add_windows.SavePictureWindowMessage;
import instruments.window_messages.remove_windows.UnSavePictureWindowMessage;
import windows.Window;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class UnSavePictureWindow implements Window {
    private final Scanner scanner;
    private final Firestore database;

    private final FirestoreUpdateData firestoreUpdate;
    private final UnSavePictureWindowMessage messageEngine;
    private final Picture picture;

    public UnSavePictureWindow(Scanner scanner,
                               Firestore database,
                               FirestoreUpdateData firestoreUpdate,
                               Picture picture) {
        this.scanner = scanner;
        this.database = database;
        this.firestoreUpdate = firestoreUpdate;
        this.picture = picture;
        this.messageEngine = new UnSavePictureWindowMessage();
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
                    new RemoveSavedPicture(
                            database,
                            firestoreUpdate
                    ).removeFromSaved(
                            firestoreUpdate.currentUser,
                            picture
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
