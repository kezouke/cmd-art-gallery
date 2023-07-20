package windows.detailed_view_windows;

import com.google.cloud.firestore.Firestore;
import db_connectors.firebase.FirestoreUpdateData;
import db_connectors.remove.RemovePicture;
import db_objects.Author;
import db_objects.UserRole;
import exceptions.ObjectWasRemoved;
import instruments.window_messages.detailed_view_window.DetailedAuthorWindowMessage;
import windows.Window;
import windows.remove.RemoveAuthorWindow;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class DetailedAuthorWindow implements Window {
    private final Author author;
    private final Scanner scanner;
    private final Firestore database;
    private final FirestoreUpdateData firestoreUpdater;
    private final DetailedAuthorWindowMessage messageEngine;

    public DetailedAuthorWindow(Author author,
                                Scanner scanner,
                                Firestore database,
                                FirestoreUpdateData firestoreUpdater) {
        this.author = author;
        this.scanner = scanner;
        this.database = database;
        this.firestoreUpdater = firestoreUpdater;
        this.messageEngine = new DetailedAuthorWindowMessage();
    }

    @Override
    public void execute() {
        boolean running = true;
        while (running) {
            try {
                messageEngine.outputMenu(
                        author.detailedInfo(),
                        firestoreUpdater.currentUser.role
                );

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
                    messageEngine.outputWrongCommandWasEntered();
                }
            } catch (ExecutionException
                     | InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ObjectWasRemoved e) {
                throw new ObjectWasRemoved();
            }
        }
    }

    private boolean removeProcess() throws ExecutionException,
            InterruptedException,
            ObjectWasRemoved {
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
            messageEngine.outputLowPermissionMessage();
            return false;
        }

    }
}

