package windows;

import com.google.cloud.firestore.Firestore;
import db_connectors.FirestoreUpdateData;
import db_objects.Comment;
import db_objects.Picture;
import representation_instruments.ArtObjectIterator;
import representation_instruments.OutputMessage;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ShowCommentsWindow implements Window {
    private final int step = 3;
    private final FirestoreUpdateData firestoreUpdate;
    private final Picture picture;
    private ArtObjectIterator<Comment> comments;
    private final Scanner scanner;
    private final Firestore database;

    public ShowCommentsWindow(FirestoreUpdateData firestoreUpdate,
                              Scanner scanner,
                              Firestore database,
                              Picture picture) {
        this.firestoreUpdate = firestoreUpdate;
        this.scanner = scanner;
        this.database = database;
        this.picture = picture;
        this.comments = new ArtObjectIterator<>(
                initializeSortedComments(),
                step
        );
    }


    @Override
    public void execute() {
        boolean running = true;
        OutputMessage commentsMessage =
                new OutputMessage("files/OutputForComments");
        OutputMessage errorMessage =
                new OutputMessage("files/OutputForError");
        OutputMessage nextComments =
                new OutputMessage("files/OutputForNextComments");
        OutputMessage prevComments =
                new OutputMessage("files/OutputForPrevComments");
        OutputMessage emptyComments =
                new OutputMessage("files/OutputForNoComments");

        if (comments.hasNext()) {
            comments = comments.next();
        }
        while (running) {
            try {
                outputMenu(commentsMessage,
                        nextComments,
                        prevComments,
                        emptyComments);

                String input = scanner.next();
                switch (input) {
                    case "return" -> running = false;
                    case "next" -> outputNextComments();
                    case "back" -> outputPrevComments();
                    case "add" -> addNewComment();
                    default -> errorMessage.display();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private void addNewComment() {
        new AddCommentWindow(
                scanner,
                database,
                firestoreUpdate,
                picture
        ).execute();
        updateCommentsData();
        comments.showArtObjects();
    }

    private void outputMenu(OutputMessage commentsMessage,
                            OutputMessage nextComments,
                            OutputMessage prevComments,
                            OutputMessage emptyComments) throws IOException {
        if (comments.isEmpty()) {
            emptyComments.display();
        } else {
            if (comments.hasNext()) {
                nextComments.display();
            }
            if (comments.hasPrev()) {
                prevComments.display();
            }
        }

        commentsMessage.display();
    }

    private void outputPrevComments() throws IOException {
        if (comments.hasPrev()) {
            comments = comments.back();
        } else {
            new OutputMessage("files/OutputForNoPrevComments")
                    .display();
            comments.showArtObjects();
        }
    }

    private void outputNextComments() throws IOException {
        if (comments.hasNext()) {
            comments = comments.next();
        } else {
            new OutputMessage("files/OutputForAllPicturesShowed")
                    .display();
            comments.showArtObjects();
        }
    }


    private void updateCommentsData() {
        this.firestoreUpdate.updateData();
        this.comments = new ArtObjectIterator<>(
                initializeSortedComments(),
                comments.currentStart,
                step
        );
    }

    private List<Comment> initializeSortedComments() {
        List<Comment> sortedComments =
                firestoreUpdate.commentsConnect.receiveComments(
                        picture.id
                );
        sortedComments.sort(Comparator
                .comparingInt(comment -> comment.id));
        return sortedComments;
    }

}
