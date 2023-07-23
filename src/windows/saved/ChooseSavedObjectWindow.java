package windows.saved;

import com.google.cloud.firestore.Firestore;
import db_connectors.connect.SavedAuthorsConnect;
import db_connectors.connect.SavedPicturesConnect;
import db_connectors.firebase.FirestoreUpdateData;
import db_objects.Author;
import db_objects.Picture;
import instruments.window_messages.saved.ChooseSavedObjectMessage;
import windows.Window;
import windows.show_windows.ShowAuthorsWindow;
import windows.show_windows.ShowPicturesWindow;

import java.util.List;
import java.util.Scanner;

public class ChooseSavedObjectWindow implements Window {
    private final Scanner scanner;
    private final FirestoreUpdateData firestoreUpdater;
    private final Firestore database;
    private final ChooseSavedObjectMessage messageEngine;

    public ChooseSavedObjectWindow(Scanner scanner,
                                   FirestoreUpdateData firestoreUpdater,
                                   Firestore database) {
        this.scanner = scanner;
        this.firestoreUpdater = firestoreUpdater;
        this.database = database;
        this.messageEngine = new ChooseSavedObjectMessage();
    }

    @Override
    public void execute() {
        boolean running = true;
        while (running) {
            messageEngine.outputVariantsToChose();
            String input = scanner.next();
            switch (input) {
                case "pictures" -> {
                    showSavedPictures();
                    running = false;
                }
                case "authors" -> {
                    showSavedAuthors();
                    running = false;
                }
                case "return" -> running = false;
                default -> messageEngine
                        .outputWrongCommandEnteredMessage();
            }
        }
    }

    private void showSavedPictures() {
        List<Picture> savedPictures =
                new SavedPicturesConnect(
                        database,
                        firestoreUpdater.picturesConnect
                ).receiveSavedPictures(
                        firestoreUpdater
                                .currentUser
                                .username
                );
        if (!savedPictures.isEmpty()) {
            new ShowPicturesWindow(
                    firestoreUpdater,
                    scanner,
                    database,
                    savedPictures,
                    false
            ).execute();
        } else {
            messageEngine
                    .outputEmptySavedPictures();
        }
    }

    private void showSavedAuthors() {
        List<Author> savedAuthors =
                new SavedAuthorsConnect(
                        database,
                        firestoreUpdater.authorsConnect
                ).receiveSavedAuthors(
                        firestoreUpdater
                                .currentUser.username
                );
        if (!savedAuthors.isEmpty()) {
            new ShowAuthorsWindow(
                    database,
                    firestoreUpdater,
                    scanner,
                    savedAuthors
            ).execute();
        } else {
            messageEngine
                    .outputEmptySavedAuthors();
        }
    }
}
