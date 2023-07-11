package windows;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.Firestore;
import db_connectors.FirestoreUpdateData;
import db_connectors.PictureUpload;
import db_objects.Author;
import db_objects.Picture;
import representation_instruments.OutputMessage;
import representation_instruments.ParseLongText;

import java.io.IOException;
import java.util.Scanner;

public class PictureAddWindow implements Window {

    private final Scanner scanner;
    private final Firestore database;

    private final FirestoreUpdateData firestoreUpdate;

    public PictureAddWindow(Scanner scanner,
                            Firestore database,
                            FirestoreUpdateData firestoreUpdate) {
        this.scanner = scanner;
        this.database = database;
        this.firestoreUpdate = firestoreUpdate;
    }

    @Override
    public void execute() {
        boolean running = true;
        OutputMessage greetings =
                new OutputMessage(
                        "files/picture_add/OutputForGreetingsWhileAddingPicture"
                );
        OutputMessage error =
                new OutputMessage("files/OutputForError");
        while (running) {
            try {
                greetings.display();
                String isUserSure = scanner.next();
                if (isUserSure.equals("no")) {
                    running = false;
                } else if (isUserSure.equals("yes")) {
                    new PictureUpload(
                            database,
                            firestoreUpdate
                    ).uploadPicture(askPicture());
                    new OutputMessage("files/picture_add/OutputForSuccess")
                            .display();
                    running = false;
                } else {
                    error.display();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private Picture askPicture() throws IOException {
        // Ask User to Enter picture name
        new OutputMessage("files/picture_add/OutputForEnterPicName")
                .display();
        String name = new ParseLongText(scanner).getText();
        // Ask user to enter picture date of creation
        new OutputMessage("files/picture_add/OutputForEnterPicDate")
                .display();
        Timestamp picDate = Timestamp.parseTimestamp(
                scanner.next() + "T10:00:00Z"
        );
        // Ask user to enter link with picture
        new OutputMessage("files/picture_add/OutputForEnterPicLink")
                .display();
        String link = scanner.next();
        // Ask for author id
        Author author = askAuthorId();

        return new Picture(
                ++firestoreUpdate
                        .picturesConnect
                        .lastPictureId,
                author,
                picDate,
                name,
                link
        );
    }

    private Author askAuthorId() throws IOException {
        OutputMessage question =
                new OutputMessage("files/picture_add/OutputForEnterPicAuthor");
        OutputMessage errorNotFound =
                new OutputMessage("files/picture_add/OutputForNotSuchAuthorExists");
        OutputMessage errorNotNumber =
                new OutputMessage("files/picture_add/OutputForNotNumberId");

        while (true) {
            question.display();
            String input = scanner.next();
            if (input.equals("authors")) {
                new AllAuthorsWindow(database, firestoreUpdate, scanner).execute();
            } else {
                try {
                    int id = Integer.parseInt(input);
                    if (firestoreUpdate
                            .authorsConnect
                            .receiveAuthor(id) != null) {
                        return firestoreUpdate
                                .authorsConnect
                                .receiveAuthor(id);
                    }
                    errorNotFound.display();
                } catch (NumberFormatException e) {
                    errorNotNumber.display();
                }
            }
        }
    }
}
