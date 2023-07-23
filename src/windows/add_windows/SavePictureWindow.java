package windows.add_windows;

import com.google.cloud.firestore.Firestore;
import db_connectors.firebase.FirestoreUpdateData;
import db_connectors.upload.SavedPictureUpload;
import db_objects.Picture;
import instruments.window_messages.add_windows.SavePictureWindowMessage;
import windows.Window;

import java.util.Scanner;

public class SavePictureWindow implements Window {
    private final Scanner scanner;
    private final Firestore database;

    private final FirestoreUpdateData firestoreUpdate;
    private final SavePictureWindowMessage messageEngine;
    private final Picture picture;

    public SavePictureWindow(Scanner scanner,
                            Firestore database,
                            FirestoreUpdateData firestoreUpdate,
                             Picture picture) {
        this.scanner = scanner;
        this.database = database;
        this.firestoreUpdate = firestoreUpdate;
        this.picture = picture;
        this.messageEngine = new SavePictureWindowMessage();
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
                new SavedPictureUpload(
                        database,
                        firestoreUpdate
                ).savePicture(
                        picture,
                        firestoreUpdate.currentUser
                );
                messageEngine.outputSuccess();
                running = false;
            } else {
                messageEngine.outputWrongCommandEntered();
            }

        }
    }
}
