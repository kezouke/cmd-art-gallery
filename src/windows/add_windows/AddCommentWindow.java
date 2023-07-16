package windows.add_windows;


import com.google.cloud.firestore.Firestore;
import db_connectors.upload.CommentUpload;
import db_connectors.firebase.FirestoreUpdateData;
import db_objects.Comment;
import db_objects.Picture;
import representation_instruments.work_with_text.OutputMessage;
import representation_instruments.work_with_text.ParseLongText;
import windows.Window;

import java.io.IOException;
import java.util.Scanner;

public class AddCommentWindow implements Window {
    private final Scanner scanner;
    private final Firestore database;
    private final FirestoreUpdateData firestoreUpdate;
    private final Picture picture;

    public AddCommentWindow(Scanner scanner,
                           Firestore database,
                           FirestoreUpdateData firestoreUpdate,
                            Picture picture) {
        this.scanner = scanner;
        this.database = database;
        this.firestoreUpdate = firestoreUpdate;
        this.picture = picture;
    }

    @Override
    public void execute() {
        boolean running = true;
        OutputMessage greetings =
                new OutputMessage(
                        "files/comment_add/" +
                                "OutputForGreetingsWhileAddingComment"
                );
        OutputMessage success =
                new OutputMessage("files/comment_add/" +
                        "OutputForSuccess");
        OutputMessage error =
                new OutputMessage("files/OutputForError");
        while (running) {
            try {
                greetings.display();
                String isUserSure = scanner.next();
                if (isUserSure.equals("no")) {
                    running = false;
                } else if (isUserSure.equals("yes")) {
                    new CommentUpload(database, firestoreUpdate)
                            .uploadComment(askData());
                    success.display();
                    running = false;
                } else {
                    error.display();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private Comment askData() throws IOException {
        ParseLongText longTextReader = new ParseLongText(scanner);
        // Ask User to Enter comment
        new OutputMessage("files/comment_add/OutputForEnterComment")
                .display();
        String comment = longTextReader.getText();
        return new Comment(
                ++firestoreUpdate
                        .commentsConnect
                        .lastCommentId,
                picture.id,
                firestoreUpdate
                        .currentUser
                        .username,
                comment
        );
    }

}
