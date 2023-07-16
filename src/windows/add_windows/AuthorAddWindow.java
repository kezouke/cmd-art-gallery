package windows.add_windows;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.Firestore;
import db_connectors.upload.AuthorUpload;
import db_connectors.firebase.FirestoreUpdateData;
import db_objects.Author;
import representation_instruments.work_with_text.OutputMessage;
import representation_instruments.work_with_text.ParseLongText;
import windows.Window;

import java.io.IOException;
import java.util.Scanner;

public class AuthorAddWindow implements Window {

    private final Scanner scanner;
    private final Firestore database;
    private final FirestoreUpdateData firestoreUpdate;

    public AuthorAddWindow(Scanner scanner,
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
                        "files/author_add/" +
                                "OutputForGreetingsWhileAddingAuthor"
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
                    new AuthorUpload(database, firestoreUpdate)
                            .uploadAuthor(askData());
                    running = false;
                } else {
                    error.display();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private Author askData() throws IOException {
        ParseLongText longTextReader = new ParseLongText(scanner);
        // Ask User to Enter author name
        new OutputMessage("files/author_add/OutputForEnterName")
                .display();
        String first_name = scanner.next();
        String last_name = scanner.next();
        // Ask user to enter author's gender
        String gender = askGender();
        // Ask user to enter author date of born
        new OutputMessage("files/author_add/OutputForEnterDate")
                .display();
        Timestamp date = Timestamp.parseTimestamp(
                scanner.next() + "T10:00:00Z"
        );
        // Ask user to country where author was born
        new OutputMessage("files/author_add/OutputForEnterCountry")
                .display();
        String country = longTextReader.getText();
        // Ask about bio
        new OutputMessage("files/author_add/OutputForEnterBiography")
                .display();
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

    private String askGender() throws IOException {
        OutputMessage question =
                new OutputMessage("files/author_add/OutputForEnterGender");
        while (true) {
            // Ask user to enter author's gender
            question.display();
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
