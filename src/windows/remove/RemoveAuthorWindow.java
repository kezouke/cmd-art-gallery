package windows.remove;

import com.google.cloud.firestore.Firestore;
import db_connectors.firebase.FirestoreUpdateData;
import db_connectors.remove.RemoveAuthor;
import representation_instruments.work_with_text.OutputMessage;
import windows.Window;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class RemoveAuthorWindow implements Window {
    private final Firestore database;
    private final FirestoreUpdateData firestoreUpdater;
    private final Scanner scanner;
    private final int id;

    public RemoveAuthorWindow(Firestore database,
                              FirestoreUpdateData firestoreUpdater,
                              Scanner scanner,
                              int id) {
        this.database = database;
        this.firestoreUpdater = firestoreUpdater;
        this.scanner = scanner;
        this.id = id;
    }

    @Override
    public void execute() {
        boolean running = true;
        OutputMessage askIsSure =
                new OutputMessage("files/remove/" +
                        "OutputForRemoveAuthor");
        OutputMessage success =
                new OutputMessage("files/remove/" +
                        "OutputForSuccessWhileRemoving");
        OutputMessage wrongCommand =
                new OutputMessage("files/OutputForError");
        while (running) {
            try {
                askIsSure.display();

                String input = scanner.next();
                switch (input) {
                    case "yes" -> {
                        new RemoveAuthor(
                                database,
                                firestoreUpdater
                        ).remove(id);
                        success.display();
                        firestoreUpdater.updateData();
                        running = false;
                    }
                    case "no" -> running = false;
                    default -> wrongCommand.display();
                }
            } catch (IOException
                     | ExecutionException
                     | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
