package windows.search_windows;

import com.google.cloud.firestore.Firestore;
import db_connectors.firebase.FirestoreUpdateData;
import db_objects.Author;
import representation_instruments.work_with_text.OutputMessage;
import representation_instruments.work_with_firebase.SearchArtObjectsEngine;
import windows.Window;
import windows.show_windows.ShowAuthorsWindow;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class SearchAuthorsWindow implements Window {
    private final Scanner scanner;
    private final FirestoreUpdateData firestoreUpdater;
    private final Firestore firestore;

    public SearchAuthorsWindow(Scanner scanner,
                               FirestoreUpdateData firestoreUpdater,
                               Firestore firestore) {
        this.scanner = scanner;
        this.firestoreUpdater = firestoreUpdater;
        this.firestore = firestore;
    }

    @Override
    public void execute() {
        SearchArtObjectsEngine<Author> searchEngine =
                new SearchArtObjectsEngine<>(
                        firestoreUpdater
                                .authorsConnect
                                .receiveAuthorsList()
                );
        List<Author> authorsMatched;

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
                    authorsMatched = searchEngine.searchArtObj(
                            inputString
                    );
                    if (!authorsMatched.isEmpty()) {
                        new ShowAuthorsWindow(
                                firestore,
                                firestoreUpdater,
                                scanner,
                                authorsMatched
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
