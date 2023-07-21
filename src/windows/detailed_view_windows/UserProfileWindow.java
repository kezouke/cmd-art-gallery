package windows.detailed_view_windows;

import com.google.cloud.firestore.Firestore;
import db_connectors.connect.UsersConnect;
import db_connectors.firebase.FirestoreUpdateData;
import db_objects.User;
import instruments.window_messages.detailed_view_window.UserProfileWindowMessage;
import windows.Window;
import windows.show_windows.ShowPictureByLink;
import windows.update.ChoseWhatToUpdateWindow;

import java.net.MalformedURLException;
import java.util.Scanner;

public class UserProfileWindow implements Window {
    private User user;
    private final Scanner scanner;
    private final FirestoreUpdateData firestoreUpdater;
    private final Firestore database;
    private final UserProfileWindowMessage messageEngine;

    public UserProfileWindow(User user,
                             Scanner scanner,
                             FirestoreUpdateData firestoreUpdater,
                             Firestore database) {
        this.user = user;
        this.scanner = scanner;
        this.firestoreUpdater = firestoreUpdater;
        this.database = database;
        this.messageEngine = new UserProfileWindowMessage();
    }

    @Override
    public void execute() {
        boolean running = true;
        boolean isCurrentUser =
                user.username.equals(
                        firestoreUpdater
                                .currentUser
                                .username
                );
        while (running) {
            try {
                messageEngine.outputProfile(
                        user.detailedInfo(),
                        isCurrentUser
                );
                String input = scanner.next();
                switch (input) {
                    case "return" -> running = false;
                    case "open" -> new ShowPictureByLink(
                            user.receiveProfilePictureURL(),
                            true
                    ).execute();
                    case "update" -> {
                        if (isCurrentUser) {
                            new ChoseWhatToUpdateWindow(
                                    database,
                                    firestoreUpdater,
                                    scanner
                            ).execute();
                            updateData();
                        } else {
                            messageEngine
                                    .outputCannotUpdateAnotherAccount();
                        }
                    }
                    default -> messageEngine
                            .outputWrongCommandEntered();
                }
            } catch (MalformedURLException e) {
                messageEngine.outputNotAURL();
            }

        }
    }

    private void updateData() {
        firestoreUpdater.updateData();
        user = new UsersConnect(
                database
        ).receiveUserByUsername(user.username);
    }
}
