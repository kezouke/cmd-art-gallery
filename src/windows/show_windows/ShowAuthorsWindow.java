package windows.show_windows;

import com.google.cloud.firestore.Firestore;
import db_connectors.firebase.FirestoreUpdateData;
import db_objects.Author;
import db_objects.UserRole;
import instruments.window_messages.show_windows.ShowAuthorsWindowMessage;
import instruments.work_with_firebase.ArtObjectIterator;
import windows.add_windows.AuthorAddWindow;
import windows.Window;
import windows.detailed_view_windows.DetailedAuthorWindow;

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
                if (input.equals("add")) {
                    boolean isDone = addNewAuthor();
                    running = !isDone;
                } else if (input.equals("return")) {
                    running = false;
                } else if (input.equals("next")) {
                    outputNextAuths();
                } else if (input.equals("back")) {
                    outputPrevAuthors();
                } else if (isValidInputForDetailedInfo(input)) {
                    showDetailedInfo(input);
                } else {
                    messageEngine.outputWrongCommandEntered();
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

    private void updateAuthorsDataFromStart() {
        firestoreUpdate.updateData();
        this.authors = new ArtObjectIterator<>(
                initializeSortedAuthors(),
                0,
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

    private boolean isValidInputForDetailedInfo(String input) {
        if (!input.startsWith("author")) {
            return false;
        }
        String numberPart = input.substring(6);
        try {
            Integer.parseInt(numberPart);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void showDetailedInfo(String input) {
        Author author =
                firestoreUpdate
                        .authorsConnect
                        .receiveAuthor(
                                Integer.parseInt(input
                                        .substring(6))
                        );
        new DetailedAuthorWindow(
                author,
                scanner,
                database,
                firestoreUpdate
        ).execute();
        updateAuthorsDataFromStart();
        authors = authors.next();
    }

}
