package windows.show_windows;

import com.google.cloud.firestore.Firestore;
import db_connectors.firebase.FirestoreUpdateData;
import db_objects.Author;
import db_objects.UserRole;
import representation_instruments.window_messages.show_windows.ShowAuthorsWindowMessage;
import representation_instruments.work_with_firebase.ArtObjectIterator;
import windows.add_windows.AuthorAddWindow;
import windows.Window;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ShowAuthorsWindow implements Window {
    private final int step = 3;
    private final Firestore database;
    private final FirestoreUpdateData firestoreUpdate;
    private final Scanner scanner;
    private final ShowAuthorsWindowMessage messageEngine;
    private ArtObjectIterator<Author> authors;

    public ShowAuthorsWindow(Firestore database,
                             FirestoreUpdateData firestoreUpdate,
                             Scanner scanner) {
        this.database = database;
        this.firestoreUpdate = firestoreUpdate;
        this.scanner = scanner;
        this.authors = new ArtObjectIterator<>(
                initializeSortedAuthors(),
                step);
        this.messageEngine = new ShowAuthorsWindowMessage();
    }

    public ShowAuthorsWindow(Firestore database,
                             FirestoreUpdateData firestoreUpdate,
                             Scanner scanner,
                             List<Author> authors) {
        this.database = database;
        this.firestoreUpdate = firestoreUpdate;
        this.scanner = scanner;
        authors.sort(Comparator.comparingInt(author -> author.id));
        this.authors = new ArtObjectIterator<>(
                authors,
                step);
        this.messageEngine = new ShowAuthorsWindowMessage();
    }

    @Override
    public void execute() {
        boolean running = true;
        authors = authors.next();
        while (running) {
            try {
                messageEngine.outputMenu(
                        authors.hasNext(),
                        authors.hasPrev(),
                        firestoreUpdate.currentUser.role
                );

                String input = scanner.next();
                switch (input) {
                    case "add" -> {
                        boolean isDone = addNewAuthor();
                        // if adding is done,
                        // we don't need to show
                        // this window again
                        running = !isDone;
                    }
                    case "return" -> running = false;
                    case "next" -> outputNextAuths();
                    case "back" -> outputPrevAuthors();
                    default -> messageEngine.outputWrongCommandEntered();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void outputNextAuths() throws IOException {
        if (authors.hasNext()) {
            authors = authors.next();
        } else {
            messageEngine.outputNoMoreAuthors();
            authors.showArtObjects();
        }
    }

    private void outputPrevAuthors() throws IOException {
        if (authors.hasPrev()) {
            authors = authors.back();
        } else {
            messageEngine.outputNoPrevAuthors();
            authors.showArtObjects();
        }
    }

    private boolean addNewAuthor() throws IOException {
        if (firestoreUpdate.currentUser.role !=
                UserRole.UNSIGNED) {
            new AuthorAddWindow(
                    scanner,
                    database,
                    firestoreUpdate
            ).execute();
            messageEngine.outputSuccessAddedAuthor();
            updateAuthorsData();
            return true;
        } else {
            messageEngine.outputLowPermissionMessage();
            return false;
        }
    }

    private void updateAuthorsData() {
        firestoreUpdate.updateData();
        this.authors = new ArtObjectIterator<>(
                initializeSortedAuthors(),
                authors.currentStart,
                step
        );
    }

    private List<Author> initializeSortedAuthors() {
        List<Author> sortedAuthors = new ArrayList<>(
                firestoreUpdate.authorsConnect.receiveAuthorsList()
        );
        sortedAuthors.sort(Comparator.comparingInt(author -> author.id));
        return sortedAuthors;
    }

}
