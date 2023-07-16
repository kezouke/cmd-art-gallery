package windows.show_windows;

import com.google.cloud.firestore.Firestore;
import db_connectors.firebase.FirestoreUpdateData;
import db_objects.Author;
import representation_instruments.work_with_firebase.ArtObjectIterator;
import representation_instruments.work_with_text.OutputMessage;
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
    }

    @Override
    public void execute() {
        boolean running = true;
        OutputMessage isWantToAddAuthorMessage =
                new OutputMessage("files/OutputForAllAuthors");
        OutputMessage error =
                new OutputMessage("files/OutputForError");
        OutputMessage nextAuths =
                new OutputMessage("files/OutputForNextAuths");
        OutputMessage prevAuths =
                new OutputMessage("files/OutputForPrevAuths");

        authors = authors.next();
        while (running) {
            try {
                outputMenu(isWantToAddAuthorMessage,
                        nextAuths,
                        prevAuths);

                String input = scanner.next();
                switch (input) {
                    case "add" -> {
                        addNewAuthor();
                        running = false;
                    }
                    case "return" -> running = false;
                    case "next" -> outputNextAuths();
                    case "back" -> outputPrevAuthors();
                    default -> error.display();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void outputMenu(OutputMessage addAuthsMessage,
                            OutputMessage nextAuths,
                            OutputMessage prevAuths) throws IOException {
        if (authors.hasNext()) {
            nextAuths.display();
        }
        if (authors.hasPrev()) {
            prevAuths.display();
        }
        addAuthsMessage.display();
    }

    private void outputNextAuths() throws IOException {
        if (authors.hasNext()) {
            authors = authors.next();
        } else {
            new OutputMessage("files/OutputForAllAuthorsShowed")
                    .display();
            authors.showArtObjects();
        }
    }

    private void outputPrevAuthors() throws IOException {
        if (authors.hasPrev()) {
            authors = authors.back();
        } else {
            new OutputMessage("files/OutputForNoPrevAuths")
                    .display();
            authors.showArtObjects();
        }
    }

    private void addNewAuthor() throws IOException {
        new AuthorAddWindow(
                scanner,
                database,
                firestoreUpdate
        ).execute();
        new OutputMessage("files/author_add/OutputForSuccess")
                .display();
        updateAuthorsData();
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
