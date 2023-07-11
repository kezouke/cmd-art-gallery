package windows;

import com.google.cloud.firestore.Firestore;
import db_connectors.FirestoreUpdateData;
import db_objects.Author;
import representation_instruments.OutputMessage;

import java.io.IOException;
import java.util.Scanner;

public class AllAuthorsWindow implements Window {
    private final Firestore database;
    private final FirestoreUpdateData firestoreUpdate;
    private final Scanner scanner;

    public AllAuthorsWindow(Firestore database,
                            FirestoreUpdateData firestoreUpdate,
                            Scanner scanner) {
        this.database = database;
        this.firestoreUpdate = firestoreUpdate;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        boolean running = true;
        OutputMessage isWantToAddAuthorMessage =
                new OutputMessage("files/OutputForAllAuthors");
        OutputMessage error =
                new OutputMessage("files/OutputForError");
        while (running) {
            try {
                for (Author author : firestoreUpdate
                        .authorsConnect
                        .receiveAuthorsList()) {
                    System.out.println(author.shortInfo());
                }
                isWantToAddAuthorMessage.display();
                String isWant = scanner.next();
                if (isWant.equals("yes")) {
                    new AuthorAddWindow(
                            scanner,
                            database,
                            firestoreUpdate
                    ).execute();
                    new OutputMessage("files/author_add/OutputForSuccess")
                            .display();
                    firestoreUpdate.updateData();
                    running = false;
                } else if (isWant.equals("no")) {
                    running = false;
                } else {
                    error.display();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
