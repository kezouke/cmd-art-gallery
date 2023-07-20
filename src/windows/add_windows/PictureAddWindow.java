package windows.add_windows;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.Firestore;
import db_connectors.firebase.FirestoreUpdateData;
import db_connectors.upload.PictureUpload;
import db_objects.Author;
import db_objects.Picture;
import representation_instruments.window_messages.add_windows.PictureAddWindowMessage;
import representation_instruments.work_with_text.ParseLongText;
import windows.Window;
import windows.show_windows.ShowAuthorsWindow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PictureAddWindow implements Window {

    private final Scanner scanner;
    private final Firestore database;

    private final FirestoreUpdateData firestoreUpdate;
    private final PictureAddWindowMessage messageEngine;

    public PictureAddWindow(Scanner scanner,
                            Firestore database,
                            FirestoreUpdateData firestoreUpdate) {
        this.scanner = scanner;
        this.database = database;
        this.firestoreUpdate = firestoreUpdate;
        this.messageEngine = new PictureAddWindowMessage();
    }

    @Override
    public void execute() {
        boolean running = true;
        while (running) {
            try {
                messageEngine.outputIsUserWantToAdd();
                String isUserSure = scanner.next();
                if (isUserSure.equals("no")) {
                    running = false;
                } else if (isUserSure.equals("yes")) {
                    new PictureUpload(
                            database,
                            firestoreUpdate
                    ).uploadPicture(askPicture());
                    messageEngine.outputSuccess();
                    running = false;
                } else {
                    messageEngine.outputWrongCommandEntered();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private Picture askPicture() throws IOException {
        // Ask User to Enter picture name
        messageEngine.outputEnterName();
        String name = new ParseLongText(scanner).getText();
        // Ask user to enter picture date of creation
        messageEngine.outputEnterDate();
        Timestamp picDate = Timestamp.parseTimestamp(
                scanner.next() + "T10:00:00Z"
        );
        // Ask user to enter link with picture
        messageEngine.outputEnterALink();
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
                link,
                new ArrayList<>()
        );
    }

    private Author askAuthorId() {
        while (true) {
            messageEngine.outputEnterAuthorId();
            String input = scanner.next();
            if (input.equals("authors")) {
                new ShowAuthorsWindow(database, firestoreUpdate, scanner).execute();
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
                    messageEngine.outputNoSuchAuthor();
                } catch (NumberFormatException e) {
                    messageEngine.outputNotANumberEntered();
                }
            }
        }
    }
}
