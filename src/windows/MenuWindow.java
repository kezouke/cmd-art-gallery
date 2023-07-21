package windows;

import com.google.cloud.firestore.Firestore;
import db_connectors.firebase.FirestoreUpdateData;
import db_objects.UserRole;
import exceptions.UserRemovedHisSelf;
import instruments.window_messages.MenuWindowMessage;
import windows.detailed_view_windows.UserProfileWindow;
import windows.search_windows.ChooseSearchObjectWindow;
import windows.show_windows.ShowAuthorsWindow;
import windows.show_windows.ShowPicturesWindow;
import windows.show_windows.ShowUsersWindow;

import java.io.IOException;
import java.util.Scanner;

public class MenuWindow implements Window {
    private final Scanner scanner;
    private final FirestoreUpdateData firestoreUpdater;
    private final Firestore database;
    private final MenuWindowMessage menuMessages;

    public MenuWindow(Scanner scanner,
                      FirestoreUpdateData firestoreUpdater,
                      Firestore database) {
        this.scanner = scanner;
        this.firestoreUpdater = firestoreUpdater;
        this.database = database;
        this.menuMessages = new MenuWindowMessage();
    }

    @Override
    public void execute() {
        boolean running = true;
        while (running) {
            try {
                menuMessages.outputGreeting(firestoreUpdater
                        .currentUser
                        .role);
                String input = scanner.next();
                switch (input) {
                    case "pictures" -> new ShowPicturesWindow(
                            firestoreUpdater,
                            scanner,
                            database
                    ).execute();
                    case "authors" -> new ShowAuthorsWindow(
                            database,
                            firestoreUpdater,
                            scanner
                    ).execute();
                    case "search" -> new ChooseSearchObjectWindow(
                            scanner,
                            firestoreUpdater,
                            database
                    ).execute();
                    case "change" -> {
                        logout();
                        running = false;
                    }
                    case "profile" -> new UserProfileWindow(
                            firestoreUpdater.currentUser,
                            scanner,
                            firestoreUpdater,
                            database
                    ).execute();
                    case "users" -> showUsers();
                    case "close" -> running = false;
                    default -> menuMessages
                            .outputWrongCommandEnteredMessage();
                }
            } catch (UserRemovedHisSelf e) {
                new InitialWindow(
                        database,
                        scanner
                ).execute();
                running = false;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void logout() throws IOException {
        menuMessages.outputSuccessLogout();
        new InitialWindow(
                database,
                scanner
        ).execute();
    }

    private void showUsers() {
        if (firestoreUpdater.currentUser.role == UserRole.ADMIN) {
            new ShowUsersWindow(
                    firestoreUpdater,
                    scanner,
                    database
            ).execute();
            firestoreUpdater.updateData();
        } else {
            menuMessages.outputForLowPermissions();
        }
    }
}
