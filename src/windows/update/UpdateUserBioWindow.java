package windows.update;

import com.google.cloud.firestore.Firestore;
import db_connectors.firebase.FirestoreUpdateData;
import db_connectors.update.ChangeUserBio;
import exceptions.UnSuccessfulUserBioUpdate;
import instruments.window_messages.update.UpdateUserBioMessage;
import instruments.work_with_text.ParseLongText;
import windows.Window;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class UpdateUserBioWindow implements Window {
    private final Firestore database;
    private final FirestoreUpdateData firestoreUpdater;
    private final Scanner scanner;
    private final UpdateUserBioMessage messageEngine;

    public UpdateUserBioWindow(Firestore database,
                               FirestoreUpdateData firestoreUpdater,
                               Scanner scanner
    ) {
        this.database = database;
        this.firestoreUpdater = firestoreUpdater;
        this.scanner = scanner;
        this.messageEngine = new UpdateUserBioMessage();
    }

    @Override
    public void execute() {
        ParseLongText longTextReader = new ParseLongText(
                scanner
        );
        messageEngine.outputBio();
        String bio = longTextReader.getText();
        try {
            new ChangeUserBio(
                    database,
                    firestoreUpdater
            ).changeBio(
                    firestoreUpdater
                            .currentUser
                            .username,
                    bio
            );
            messageEngine.outputSuccess();
        } catch (ExecutionException | InterruptedException e) {
            throw new UnSuccessfulUserBioUpdate();
        }
    }
}