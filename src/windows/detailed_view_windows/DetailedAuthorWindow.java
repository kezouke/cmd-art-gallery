package windows.detailed_view_windows;

import com.google.cloud.firestore.Firestore;
import db_connectors.firebase.FirestoreUpdateData;
import db_connectors.remove.RemovePicture;
import db_objects.Author;
import db_objects.UserRole;
import exceptions.ObjectWasRemoved;
import representation_instruments.work_with_text.OutputMessage;
import windows.Window;
import windows.remove.RemoveAuthorWindow;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class DetailedAuthorWindow implements Window {
    private final Author author;
    private final Scanner scanner;
    private final Firestore database;
    private final FirestoreUpdateData firestoreUpdater;

    public DetailedAuthorWindow(Author author,
                                Scanner scanner,
                                Firestore database,
                                FirestoreUpdateData firestoreUpdater) {
        this.author = author;
        this.scanner = scanner;
        this.database = database;
        this.firestoreUpdater = firestoreUpdater;
    }

    @Override
    public void execute() {
        boolean running = true;
        OutputMessage backMessage =
                new OutputMessage("files/OutputForDetailedAuthor");
        OutputMessage errorMessage =
                new OutputMessage("files/OutputForError");
        OutputMessage adminOption =
                new OutputMessage("files/OutputForRemoveAuthor");
        while (running) {
            try {
                showMenu(author.detailedInfo(),
                        backMessage,
                        adminOption);

                String input = scanner.next();
                if (input.equals("back")) {
                    running = false;
                } else if (input.equals("remove")) {
                    boolean isDone = removeProcess();
                    // is user is admin -> successful remove is done
                    // -> isDone is true
                    // -> we don't need to show this window
                    running = !isDone;
                } else {
                    errorMessage.display();
                }
            } catch (IOException
                     | ExecutionException
                     | InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ObjectWasRemoved e) {
                throw new ObjectWasRemoved();
            }
        }
    }

    private void showMenu(String detailedInfo,
                          OutputMessage initialOptions,
                          OutputMessage adminOptions) throws IOException {
        System.out.println(detailedInfo);
        initialOptions.display();
        if (firestoreUpdater.currentUser.role
                == UserRole.ADMIN) {
            adminOptions.display();
        }
    }

    private boolean removeProcess() throws ExecutionException,
            InterruptedException,
            ObjectWasRemoved, IOException {
        if (firestoreUpdater.currentUser.role == UserRole.ADMIN) {
            new RemoveAuthorWindow(
                    database,
                    firestoreUpdater,
                    scanner,
                    author.id
            ).execute();
            new RemovePicture(
                    database,
                    firestoreUpdater
            ).removeByAuthorId(author.id);
            throw new ObjectWasRemoved();
        } else {
            new OutputMessage("files/" +
                    "OutputForLowPermissionsAdmin")
                    .display();
            return false;
        }

    }
}

