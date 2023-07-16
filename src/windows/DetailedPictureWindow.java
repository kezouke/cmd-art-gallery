package windows;

import com.google.cloud.firestore.Firestore;
import db_connectors.FirestoreUpdateData;
import db_objects.Picture;
import representation_instruments.OutputMessage;

import java.io.IOException;
import java.util.Scanner;

public class DetailedPictureWindow implements Window {
    private final FirestoreUpdateData firestoreUpdater;
    private final Firestore database;
    private final Picture picture;
    private final Scanner scanner;

    public DetailedPictureWindow(FirestoreUpdateData firestoreUpdater,
                                 Firestore database,
                                 Picture picture,
                                 Scanner scanner) {
        this.firestoreUpdater = firestoreUpdater;
        this.database = database;
        this.picture = picture;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        boolean running = true;
        OutputMessage greetingsMessage =
                new OutputMessage("files/OutputForDetailedPicture");
        OutputMessage errorMessage =
                new OutputMessage("files/OutputForError");
        while (running) {
            try {
                System.out.println(picture.detailedInfo());
                greetingsMessage.display();

                String input = scanner.next();
                switch (input) {
                    case "back" -> running = false;
                    case "author" -> new DetailedAuthorWindow(
                            picture.author,
                            scanner
                    ).execute();
                    case "comments" -> new ShowCommentsWindow(
                            firestoreUpdater,
                            scanner,
                            database,
                            picture
                    ).execute();
                    default -> errorMessage.display();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}