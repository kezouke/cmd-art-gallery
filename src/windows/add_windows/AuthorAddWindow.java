package windows.add_windows;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.Firestore;
import db_connectors.upload.AuthorUpload;
import db_connectors.firebase.FirestoreUpdateData;
import db_objects.Author;
import instruments.window_messages.add_windows.AuthorAddWindowMessage;
import instruments.work_with_text.ParseLongText;
import windows.Window;

import java.io.IOException;
import java.util.Scanner;

public class AuthorAddWindow implements Window {

    private final Scanner scanner;
    private final Firestore database;
    private final FirestoreUpdateData firestoreUpdate;
    private final AuthorAddWindowMessage messageEngine;

    public AuthorAddWindow(Scanner scanner,
                           Firestore database,
                           FirestoreUpdateData firestoreUpdate) {
        this.scanner = scanner;
        this.database = database;
        this.firestoreUpdate = firestoreUpdate;
        this.messageEngine = new AuthorAddWindowMessage();
    }

    @Override
    public void execute() {
        boolean running = true;
        while (running) {
            try {
                messageEngine.outputIsUserSureToAdd();
                String isUserSure = scanner.next();
                if (isUserSure.equals("no")) {
                    running = false;
                } else if (isUserSure.equals("yes")) {
                    new AuthorUpload(database, firestoreUpdate)
                            .uploadAuthor(askData());
                    running = false;
                } else {
                    messageEngine
                            .outputWrongCommandEntered();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private Author askData() throws IOException {
        ParseLongText longTextReader = new ParseLongText(scanner);
        // Ask User to Enter author name
        messageEngine.outputForEnterName();
        String first_name = scanner.next();
        String last_name = scanner.next();
        // Ask user to enter author's gender
        String gender = askGender();
        // Ask user to enter author date of born
        messageEngine.outputForEnterBirthDate();
        Timestamp date = Timestamp.parseTimestamp(
                scanner.next() + "T10:00:00Z"
        );
        // Ask user to country where author was born
        messageEngine.outputForEnterCountry();
        String country = longTextReader.getText();
        // Ask about bio
        messageEngine.outputForEnterBio();
        String biography = longTextReader.getText();
        return new Author(
                ++firestoreUpdate
                        .authorsConnect
                        .lastAuthorId,
                first_name,
                last_name,
                gender,
                date,
                biography,
                country
        );
    }

    private String askGender() {
        while (true) {
            // Ask user to enter author's gender
            messageEngine.outputForEnterGender();
            String gender = scanner.next();
            if (gender.equalsIgnoreCase("male")
                    || gender.equalsIgnoreCase("female")) {
                return gender.toLowerCase();
            } else {
                System.out.println("Wrong format");
            }
        }
    }
}
