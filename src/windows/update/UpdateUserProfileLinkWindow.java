package windows.update;

import com.google.cloud.firestore.Firestore;
import db_connectors.firebase.FirestoreUpdateData;
import db_connectors.update.ChangeUserProfileLink;
import exceptions.UnSuccessfulUserProfileLinkUpdate;
import instruments.window_messages.update.UpdateUserProfileLinkMessage;
import windows.Window;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class UpdateUserProfileLinkWindow implements Window {
    private final Firestore database;
    private final FirestoreUpdateData firestoreUpdater;
    private final Scanner scanner;
    private final UpdateUserProfileLinkMessage messageEngine;

    public UpdateUserProfileLinkWindow(Firestore database,
                               FirestoreUpdateData firestoreUpdater,
                               Scanner scanner
    ) {
        this.database = database;
        this.firestoreUpdater = firestoreUpdater;
        this.scanner = scanner;
        this.messageEngine = new UpdateUserProfileLinkMessage();
    }

    @Override
    public void execute() {
        messageEngine.outputLink();
        String link = scanner.next();
        try {
            new ChangeUserProfileLink(
                database,
                firestoreUpdater
            ).changeRole(
                    firestoreUpdater.currentUser.username,
                    link
            );
            messageEngine.outputSuccess();
        } catch (ExecutionException | InterruptedException e) {
            throw new UnSuccessfulUserProfileLinkUpdate();
        }
    }
}
