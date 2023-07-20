package windows.update;

import com.google.cloud.firestore.Firestore;
import db_connectors.auth.UserExistenceCheck;
import db_connectors.connect.UsersConnect;
import db_connectors.firebase.FirestoreUpdateData;
import db_connectors.update.ChangeUserRole;
import db_objects.UserRole;
import exceptions.UserNowNotAnAdmin;
import instruments.window_messages.update.UpdateUserRoleWindowMessage;
import windows.InitialWindow;
import windows.Window;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class UpdateUserRoleWindow implements Window {
    private final Firestore database;
    private final FirestoreUpdateData firestoreUpdater;
    private final Scanner scanner;
    private final UpdateUserRoleWindowMessage messageEngine;

    public UpdateUserRoleWindow(Firestore database,
                                FirestoreUpdateData firestoreUpdater,
                                Scanner scanner
    ) {
        this.database = database;
        this.firestoreUpdater = firestoreUpdater;
        this.scanner = scanner;
        this.messageEngine = new UpdateUserRoleWindowMessage();
    }

    @Override
    public void execute() {
        boolean running = true;
        while (running) {
            try {
                messageEngine.outputIsUserSure();
                String input = scanner.next();
                switch (input) {
                    case "yes" -> {
                        updateProcess();
                        running = false;
                    }
                    case "no" -> running = false;
                    default -> messageEngine
                            .outputForWrongCommand();
                }
            } catch (UserNowNotAnAdmin e) {
                throw new UserNowNotAnAdmin();
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void updateProcess() throws ExecutionException, InterruptedException {
        UsersConnect usersConnector = new UsersConnect(database);
        boolean running = true;
        while (running) {
            messageEngine.outputEnterUsername();
            String input = scanner.next();

            if (input.equals("return")) {
                running = false;
            } else if (!isSuchUserExists(input)) {
                messageEngine.outputNoSuchUserExists();
            } else if (isFoundedUserAdmin(usersConnector, input) &&
                    !isFoundedUserIsCurrentUser(input)) {
                messageEngine.outputCannotUpdateAnotherAdmin();
                running = false;
            } else {
                changeUserRole(usersConnector, input);
                checkIsUserStillAdmin();
                running = false;
            }
        }
        firestoreUpdater.updateData();
    }

    private UserRole getRoleFromString(String role) {
        return switch (role) {
            case "admin" -> UserRole.ADMIN;
            case "signed" -> UserRole.SIGNED;
            default -> UserRole.UNSIGNED;
        };
    }

    private boolean isSuchUserExists(String username) {
        return new UserExistenceCheck(database).isExist(username);
    }

    private boolean isFoundedUserAdmin(UsersConnect usersConnector, String username) {
        return usersConnector
                .receiveUserByUsername(username)
                .role == UserRole.ADMIN;
    }

    private boolean isFoundedUserIsCurrentUser(String username) {
        return username.equals(firestoreUpdater.currentUser.username);
    }

    private void changeUserRole(UsersConnect usersConnector, String username)
            throws ExecutionException, InterruptedException {
        messageEngine.outputCurrentRole(
                usersConnector
                        .receiveUserByUsername(username)
                        .role
        );
        messageEngine.outputNewRole();
        new ChangeUserRole(
                database,
                firestoreUpdater
        ).changeRole(username, getRoleFromString(scanner.next()));
        messageEngine.outputForSuccess();
    }

    private void checkIsUserStillAdmin() {
        if (firestoreUpdater.currentUser.role != UserRole.ADMIN) {
            throw new UserNowNotAnAdmin();
        }
    }
}
