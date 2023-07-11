package windows;

import java.io.IOException;
import java.util.Scanner;

import com.google.cloud.firestore.Firestore;
import db_connectors.FirestoreUpdateData;
import db_objects.Picture;
import representation_instruments.OutputMessage;

public class MainPicturesWindow implements Window {
    private final FirestoreUpdateData firestoreUpdate;
    private final Scanner scanner;
    private final Firestore database;

    public MainPicturesWindow(FirestoreUpdateData firestoreUpdate,
                              Scanner scanner,
                              Firestore database) {
        this.firestoreUpdate = firestoreUpdate;
        this.scanner = scanner;
        this.database = database;
    }

    @Override
    public void execute() {
        boolean running = true;
        OutputMessage picturesMessage =
                new OutputMessage("files/OutputForPicture");
        OutputMessage errorMessage =
                new OutputMessage("files/OutputForError");
        while (running) {
            try {
                for (Picture picture : firestoreUpdate.
                        picturesConnect.
                        receivePicture()
                        .values()) {
                    System.out.println(picture.shortInfo());
                }
                picturesMessage.display();

                String input = scanner.next();
                if (input.equals("stop")) {
                    running = false;
                } else if (input.equals("logout")) {
                    running = false;
                    logout();
                } else if (input.equals("add")) {
                    new PictureAddWindow(
                            scanner,
                            database,
                            firestoreUpdate
                    ).execute();
                    firestoreUpdate.updateData();
                } else if (firestoreUpdate
                        .picturesConnect
                        .receivePicture()
                        .containsKey(input)) {
                    new DetailedPictureWindow(
                            firestoreUpdate
                                    .picturesConnect
                                    .receivePicture()
                                    .get(input),
                            scanner).execute();
                } else {
                    errorMessage.display();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private void logout() throws IOException {
        new OutputMessage("files/OutputForLogout").display();
        new AuthWindow(scanner, database).execute();
    }
}

