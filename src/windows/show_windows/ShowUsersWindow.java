package windows.show_windows;

import com.google.cloud.firestore.Firestore;
import db_connectors.connect.UsersConnect;
import db_connectors.firebase.FirestoreUpdateData;
import db_objects.User;
import instruments.window_messages.show_windows.ShowUsersWindowMessage;
import instruments.work_with_firebase.ArtObjectIterator;
import windows.Window;
import windows.remove.RemoveUserWindow;
import windows.update.UpdateUserRoleWindow;

import java.io.IOException;
import java.util.Scanner;

public class ShowUsersWindow implements Window {
    private final int step = 3;
    private final FirestoreUpdateData firestoreUpdate;
    private ArtObjectIterator<User> users;
    private final Scanner scanner;
    private final Firestore database;
    private final ShowUsersWindowMessage messageEngine;

    public ShowUsersWindow(FirestoreUpdateData firestoreUpdate,
                           Scanner scanner,
                           Firestore database) {
        this.firestoreUpdate = firestoreUpdate;
        this.scanner = scanner;
        this.database = database;
        this.users = new ArtObjectIterator<>(
                new UsersConnect(
                        database
                ).receiveUsersList(),
                step
        );
        this.messageEngine = new ShowUsersWindowMessage();
    }


    @Override
    public void execute() {
        boolean running = true;
        users = users.next();
        while (running) {
            try {
                messageEngine.outputMenu(
                        users.hasNext(),
                        users.hasPrev()
                );

                String input = scanner.next();
                switch (input) {
                    case "return" -> running = false;
                    case "next" -> outputNextUsers();
                    case "back" -> outputPrevUsers();
                    case "remove-user" -> removeUser();
                    case "change-role" -> updateUser();
                    default -> messageEngine
                            .outputWrongCommandEntered();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }


    private void outputPrevUsers() throws IOException {
        if (users.hasPrev()) {
            users = users.back();
        } else {
            messageEngine.outputNoPrevUsers();
            users.showArtObjects();
        }
    }

    private void outputNextUsers() throws IOException {
        if (users.hasNext()) {
            users = users.next();
        } else {
            messageEngine.outputNoNextUsers();
            users.showArtObjects();
        }
    }

    private void removeUser() {
        new RemoveUserWindow(
                database,
                firestoreUpdate,
                scanner
        ).execute();
        updateUsersData();
        users = users.next();
    }

    private void updateUser() {
        new UpdateUserRoleWindow(
                database,
                firestoreUpdate,
                scanner
        ).execute();
        updateUsersData();
        users = users.next();
    }

    private void updateUsersData() {
        this.firestoreUpdate.updateData();
        this.users = new ArtObjectIterator<>(
                new UsersConnect(database).receiveUsersList(),
                0,
                step
        );
    }

}
