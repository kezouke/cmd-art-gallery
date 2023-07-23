package windows.detailed_view_windows;

import com.google.cloud.firestore.Firestore;
import db_connectors.connect.SavedPicturesConnect;
import db_connectors.firebase.FirestoreUpdateData;
import db_objects.Picture;
import db_objects.UserRole;
import exceptions.ObjectWasRemoved;
import instruments.window_messages.detailed_view_window.DetailedPictureWindowMessage;
import windows.Window;
import windows.add_windows.SavePictureWindow;
import windows.remove.RemovePictureWindow;
import windows.remove.UnSavePictureWindow;
import windows.show_windows.ShowCommentsWindow;
import windows.show_windows.ShowPictureByLink;

import java.net.MalformedURLException;
import java.util.Scanner;

public class DetailedPictureWindow implements Window {
    private final FirestoreUpdateData firestoreUpdater;
    private final Firestore database;
    private final Picture picture;
    private final Scanner scanner;
    private final DetailedPictureWindowMessage messageEngine;

    public DetailedPictureWindow(FirestoreUpdateData firestoreUpdater,
                                 Firestore database,
                                 Picture picture,
                                 Scanner scanner) {
        this.firestoreUpdater = firestoreUpdater;
        this.database = database;
        this.picture = picture;
        this.scanner = scanner;
        this.messageEngine = new DetailedPictureWindowMessage();
    }

    @Override
    public void execute() {
        boolean running = true;
        while (running) {
            try {
                messageEngine.outputMenu(
                        picture.detailedInfo(),
                        firestoreUpdater.currentUser.role,
                        isSaved()
                );

                String input = scanner.next();
                switch (input) {
                    case "back" -> running = false;
                    case "author" -> new DetailedAuthorWindow(
                            picture.author,
                            scanner,
                            database,
                            firestoreUpdater
                    ).execute();
                    case "comments" -> new ShowCommentsWindow(
                            firestoreUpdater,
                            scanner,
                            database,
                            picture
                    ).execute();
                    case "remove" -> {
                        boolean isDone = removeProcess();
                        // if isDone => we can stop showing this window
                        // since it is removed
                        running = !isDone;
                    }
                    case "open" -> new ShowPictureByLink(
                            picture.receiveURL(),
                            false
                    ).execute();
                    case "save" -> savePicture();
                    case "unsave" -> unsavePicture();
                    default -> messageEngine
                            .outputWrongCommandEnteredMessage();
                }
            } catch (ObjectWasRemoved e) {
                running = false;
            } catch (MalformedURLException e) {
                messageEngine.outputWrongURL();
            }

        }
    }

    private boolean removeProcess() {
        if (firestoreUpdater.currentUser.role == UserRole.ADMIN) {
            new RemovePictureWindow(database,
                    firestoreUpdater,
                    scanner,
                    picture.id)
                    .execute();
            return true;
        } else {
            messageEngine.outputLowPermissionsAdmin();
            return false;
        }
    }

    private boolean isSaved() {
        return new SavedPicturesConnect(
                database,
                firestoreUpdater.picturesConnect
        ).receiveSavedPictures(
                firestoreUpdater.currentUser.username
        ).contains(picture);
    }

    private void savePicture() {
        if (firestoreUpdater.currentUser.role
            != UserRole.UNSIGNED) {
            new SavePictureWindow(
                    scanner,
                    database,
                    firestoreUpdater,
                    picture
            ).execute();
        } else {
            messageEngine
                    .outputLowPermissionsUnsigned();
        }
    }

    private void unsavePicture() {
        if (firestoreUpdater.currentUser.role
            != UserRole.UNSIGNED) {
            new UnSavePictureWindow(
                    scanner,
                    database,
                    firestoreUpdater,
                    picture
            ).execute();
        } else {
            messageEngine
                    .outputLowPermissionsUnsigned();
        }
    }
}