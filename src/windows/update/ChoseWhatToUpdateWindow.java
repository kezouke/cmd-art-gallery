package windows.update;

import com.google.cloud.firestore.Firestore;
import db_connectors.firebase.FirestoreUpdateData;
import exceptions.UnSuccessfulUserBioUpdate;
import exceptions.UnSuccessfulUserProfileLinkUpdate;
import exceptions.UserNowNotAnAdmin;
import instruments.window_messages.update.ChoseWhatToUpdateMessage;
import windows.Window;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class ChoseWhatToUpdateWindow implements Window {
    private final Firestore database;
    private final FirestoreUpdateData firestoreUpdater;
    private final Scanner scanner;
    private final ChoseWhatToUpdateMessage messageEngine;

    public ChoseWhatToUpdateWindow(Firestore database,
                                   FirestoreUpdateData firestoreUpdater,
                                   Scanner scanner
    ) {
        this.database = database;
        this.firestoreUpdater = firestoreUpdater;
        this.scanner = scanner;
        this.messageEngine = new ChoseWhatToUpdateMessage();
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
                        chooseVariantsProcess();
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

    private void chooseVariantsProcess() throws ExecutionException, InterruptedException {
        boolean running = true;
        while (running) {
            messageEngine.outputVariants();
            String input = scanner.next();
            try {
                switch (input) {
                    case "bio" -> {
                        new UpdateUserBioWindow(
                                database,
                                firestoreUpdater,
                                scanner
                        ).execute();
                        running = false;
                    }
                    case "profile" -> {
                        new UpdateUserProfileLinkWindow(
                                database,
                                firestoreUpdater,
                                scanner
                        ).execute();
                        running = false;
                    }
                    case "all" -> {
                        new UpdateUserBioWindow(
                                database,
                                firestoreUpdater,
                                scanner
                        ).execute();
                        new UpdateUserProfileLinkWindow(
                                database,
                                firestoreUpdater,
                                scanner
                        ).execute();
                        running = false;
                    }
                    default -> messageEngine.outputForWrongCommand();
                }
            } catch (UnSuccessfulUserBioUpdate
                     | UnSuccessfulUserProfileLinkUpdate e) {
                messageEngine.outputException(
                        e.getMessage()
                );
            }
        }
        firestoreUpdater.updateData();
    }

}
