package windows.detailed_view_windows;

import com.google.cloud.firestore.Firestore;
import db_connectors.firebase.FirestoreUpdateData;
import db_objects.Picture;
import db_objects.UserRole;
import exceptions.ObjectWasRemoved;
import representation_instruments.work_with_text.OutputMessage;
import windows.Window;
import windows.remove.RemovePictureWindow;
import windows.show_windows.ShowCommentsWindow;

import java.io.IOException;
import java.util.Scanner;

public class DetailedPictureWindow implements Window {
    private final FirestoreUpdateData firestoreUpdater;
    private final Firestore database;
    private final Picture picture;
    private final Scanner scanner;

    public DetailedPictureWindow(FirestoreUpdateData firestoreUpdater,
                                 Firestore database,
                                 Picture picture,
                                 Scanner scanner) {
        this.firestoreUpdater = firestoreUpdater;
        this.database = database;
        this.picture = picture;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        boolean running = true;
        OutputMessage greetingsMessage =
                new OutputMessage("files/OutputForDetailedPicture");
        OutputMessage errorMessage =
                new OutputMessage("files/OutputForError");
        OutputMessage adminOption =
                new OutputMessage("files/OutputForRemovePicture");
        while (running) {
            try {
                showMenu(picture.detailedInfo(),
                        greetingsMessage,
                        adminOption);

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
                    default -> errorMessage.display();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ObjectWasRemoved e) {
                running = false;
            }

        }
    }

    private void showMenu(String detailedInfo,
                          OutputMessage greetings,
                          OutputMessage adminOptions) throws IOException {
        System.out.println(detailedInfo);
        greetings.display();
        if (firestoreUpdater.currentUser.role == UserRole.ADMIN) {
            adminOptions.display();
        }
    }

    private boolean removeProcess() throws IOException {
        if (firestoreUpdater.currentUser.role == UserRole.ADMIN) {
            new RemovePictureWindow(database,
                    firestoreUpdater,
                    scanner,
                    picture.id)
                    .execute();
            return true;
        } else {
            new OutputMessage("files" +
                    "/OutputForLowPermissionsAdmin")
                    .display();
            return false;
        }
    }
}