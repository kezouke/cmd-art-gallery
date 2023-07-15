package windows;

import com.google.cloud.firestore.Firestore;
import db_connectors.FirestoreUpdateData;
import db_objects.Picture;
import representation_instruments.OutputMessage;
import representation_instruments.SearchArtObjectsEngine;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class SearchPicturesWindow implements Window {
    private final Scanner scanner;
    private final FirestoreUpdateData firestoreUpdater;
    private final Firestore firestore;

    public SearchPicturesWindow(Scanner scanner,
                                FirestoreUpdateData firestoreUpdater,
                                Firestore firestore) {
        this.scanner = scanner;
        this.firestoreUpdater = firestoreUpdater;
        this.firestore = firestore;
    }

    @Override
    public void execute() {
        SearchArtObjectsEngine<Picture> searchEngine =
                new SearchArtObjectsEngine<>(
                        firestoreUpdater
                                .picturesConnect
                                .receivePicture()
                                .values().stream().toList()
                );
        List<Picture> picturesMatched;

        OutputMessage emptyResult =
                new OutputMessage("files/OutputForEmptySearch");
        OutputMessage greetings =
                new OutputMessage("files/OutputForSearch");

        boolean running = true;
        while (running) {
            try {
                greetings.display();
                String inputString = scanner.next();
                if (inputString.equals("close")) {
                    running = false;
                } else {
                    picturesMatched = searchEngine.searchArtObj(
                            inputString
                    );
                    if (!picturesMatched.isEmpty()) {
                        new MainPicturesWindow(
                                firestoreUpdater,
                                scanner,
                                firestore,
                                picturesMatched
                        ).execute();
                        running = false;
                    } else {
                        emptyResult.display();
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
