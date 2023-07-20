package windows.show_windows;

import com.google.cloud.firestore.Firestore;
import db_connectors.firebase.FirestoreUpdateData;
import db_objects.Comment;
import db_objects.Picture;
import db_objects.UserRole;
import representation_instruments.window_messages.show_windows.ShowCommentsWindowMessage;
import representation_instruments.work_with_firebase.ArtObjectIterator;
import windows.add_windows.AddCommentWindow;
import windows.Window;

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
    private final ShowCommentsWindowMessage messageEngine;

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
        this.messageEngine = new ShowCommentsWindowMessage();
    }


    @Override
    public void execute() {
        boolean running = true;
        if (comments.hasNext()) {
            comments = comments.next();
        }
        while (running) {
            try {
                messageEngine.outputMenu(
                        comments.isEmpty(),
                        comments.hasNext(),
                        comments.hasPrev(),
                        firestoreUpdate.currentUser.role
                );

                String input = scanner.next();
                switch (input) {
                    case "return" -> running = false;
                    case "next" -> outputNextComments();
                    case "back" -> outputPrevComments();
                    case "add" -> addNewComment();
                    default -> messageEngine
                            .outputWrongCommandEntered();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private void addNewComment() throws IOException {
        if (firestoreUpdate.currentUser.role != UserRole.UNSIGNED) {
            new AddCommentWindow(
                    scanner,
                    database,
                    firestoreUpdate,
                    picture
            ).execute();
            updateCommentsData();
            comments.showArtObjects();
        } else {
            messageEngine.outputLowPermission();
        }
    }

    private void outputPrevComments() throws IOException {
        if (comments.hasPrev()) {
            comments = comments.back();
        } else {
            messageEngine.outputNoPrevComments();
            comments.showArtObjects();
        }
    }

    private void outputNextComments() throws IOException {
        if (comments.hasNext()) {
            comments = comments.next();
        } else {
            messageEngine.outputNoNextComments();
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
