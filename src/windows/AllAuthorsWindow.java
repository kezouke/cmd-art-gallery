package windows;

import com.google.cloud.firestore.Firestore;
import db_connectors.FirestoreUpdateData;
import db_objects.Author;
import representation_instruments.ArtObjectIterator;
import representation_instruments.OutputMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class AllAuthorsWindow implements Window {
    private final int step = 3;
    private final Firestore database;
    private final FirestoreUpdateData firestoreUpdate;
    private final Scanner scanner;
    private ArtObjectIterator<Author> authors;

    public AllAuthorsWindow(Firestore database,
                            FirestoreUpdateData firestoreUpdate,
                            Scanner scanner) {
        this.database = database;
        this.firestoreUpdate = firestoreUpdate;
        this.scanner = scanner;
        this.authors = new ArtObjectIterator<>(
                initializeSortedAuthors(),
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
                    case "yes" -> {
                        addNewAuthor();
                        running = false;
                    }
                    case "no" -> running = false;
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
