package windows.update;

import com.google.cloud.firestore.Firestore;
import db_connectors.auth.UserExistenceCheck;
import db_connectors.connect.UsersConnect;
import db_connectors.firebase.FirestoreUpdateData;
import db_connectors.update.ChangeUserRole;
import db_objects.UserRole;
import instruments.window_messages.update.UpdateUserRoleWindowMessage;
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
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void updateProcess() throws ExecutionException, InterruptedException {
        boolean running = true;
        while (running) {
            messageEngine.outputEnterUsername();
            String input = scanner.next();
            UsersConnect usersConnector = new UsersConnect(database);
            if (input.equals("return")) {
                running = false;
            } else if (!new UserExistenceCheck(database).isExist(input)) {
                messageEngine.outputNoSuchUserExists();
            } else if (usersConnector
                    .receiveUserByUsername(input)
                    .role == UserRole.ADMIN &&
                    !input.equals(
                            firestoreUpdater.currentUser.username
                    )) {
                messageEngine.outputCannotUpdateAnotherAdmin();
                running = false;
            } else {
                messageEngine.outputCurrentRole(
                        usersConnector
                                .receiveUserByUsername(input)
                                .role
                );
                messageEngine.outputNewRole();
                new ChangeUserRole(
                        database,
                        firestoreUpdater
                ).changeRole(input, getRoleFromString(scanner.next()));
                messageEngine.outputForSuccess();
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
}
