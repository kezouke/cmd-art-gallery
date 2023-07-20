package windows.search_windows;

import com.google.cloud.firestore.Firestore;
import db_connectors.firebase.FirestoreUpdateData;
import db_objects.Picture;
import instruments.window_messages.search_windows.SearchWindowMessage;
import instruments.work_with_firebase.SearchArtObjectsEngine;
import windows.Window;
import windows.show_windows.ShowPicturesWindow;

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
        SearchWindowMessage messageEngine = new SearchWindowMessage();
        boolean running = true;
        while (running) {
            messageEngine.outputGreetings();
            String inputString = scanner.next();
            if (inputString.equals("close")) {
                running = false;
            } else {
                picturesMatched = searchEngine.searchArtObj(
                        inputString
                );
                if (!picturesMatched.isEmpty()) {
                    new ShowPicturesWindow(
                            firestoreUpdater,
                            scanner,
                            firestore,
                            picturesMatched
                    ).execute();
                    running = false;
                } else {
                    messageEngine.outputEmptyResult();
                }
            }
        }

    }
}
