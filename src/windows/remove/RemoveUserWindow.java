package windows.remove;

import com.google.cloud.firestore.Firestore;
import db_connectors.auth.UserExistenceCheck;
import db_connectors.connect.UsersConnect;
import db_connectors.firebase.FirestoreUpdateData;
import db_connectors.remove.RemovePicture;
import db_connectors.remove.RemoveUser;
import db_objects.UserRole;
import instruments.window_messages.remove_windows.RemovePictureWindowMessage;
import instruments.window_messages.remove_windows.RemoveUserWindowMessage;
import windows.Window;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class RemoveUserWindow implements Window {
    private final Firestore database;
    private final FirestoreUpdateData firestoreUpdater;
    private final Scanner scanner;
    private final RemoveUserWindowMessage messageEngine;

    public RemoveUserWindow(Firestore database,
                            FirestoreUpdateData firestoreUpdater,
                            Scanner scanner
    ) {
        this.database = database;
        this.firestoreUpdater = firestoreUpdater;
        this.scanner = scanner;
        this.messageEngine = new RemoveUserWindowMessage();
    }

    @Override
    public void execute() {
        boolean running = true;
        while (running) {
            try {
                messageEngine.outputQuestionIsUserSure();
                String input = scanner.next();
                switch (input) {
                    case "yes" -> {
                        removeProcess();
                        running = false;
                    }
                    case "no" -> running = false;
                    default -> messageEngine
                            .outputWrongCommandEnteredMessage();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void removeProcess() throws ExecutionException, InterruptedException {
        boolean running = true;
        while (running) {
            messageEngine.outputEnterUsername();
            String input = scanner.next();
            if (input.equals("return")) {
                running = false;
            } else if (!new UserExistenceCheck(database).isExist(input)) {
                messageEngine.outputNoSuchUserExists();
            } else if (new UsersConnect(database)
                    .receiveUserByUsername(input)
                    .role == UserRole.ADMIN &&
                    !input.equals(
                            firestoreUpdater.currentUser.username
                    )) {
                messageEngine.outputCannotRemoveAnotherAdmin();
                running = false;
            } else {
                new RemoveUser(
                        database,
                        firestoreUpdater
                ).removeByUsername(input);
                messageEngine.outputForSuccess(
                        firestoreUpdater.currentUser.username,
                        input
                );
                running = false;
            }
        }
        firestoreUpdater.updateData();
    }
}
