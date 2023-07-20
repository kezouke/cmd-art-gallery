package windows.add_windows;


import com.google.cloud.firestore.Firestore;
import db_connectors.upload.CommentUpload;
import db_connectors.firebase.FirestoreUpdateData;
import db_objects.Comment;
import db_objects.Picture;
import instruments.window_messages.add_windows.AddCommentWindowMessage;
import instruments.work_with_text.ParseLongText;
import windows.Window;

import java.io.IOException;
import java.util.Scanner;

public class AddCommentWindow implements Window {
    private final Scanner scanner;
    private final Firestore database;
    private final FirestoreUpdateData firestoreUpdate;
    private final Picture picture;
    private final AddCommentWindowMessage messageEngine;

    public AddCommentWindow(Scanner scanner,
                           Firestore database,
                           FirestoreUpdateData firestoreUpdate,
                            Picture picture) {
        this.scanner = scanner;
        this.database = database;
        this.firestoreUpdate = firestoreUpdate;
        this.picture = picture;
        this.messageEngine = new AddCommentWindowMessage();
    }

    @Override
    public void execute() {
        boolean running = true;
        while (running) {
            try {
                messageEngine.outputIsUserSureToAdd();
                String isUserSure = scanner.next();
                if (isUserSure.equals("no")) {
                    running = false;
                } else if (isUserSure.equals("yes")) {
                    new CommentUpload(database, firestoreUpdate)
                            .uploadComment(askData());
                    messageEngine.outputSuccessAdd();
                    running = false;
                } else {
                    messageEngine.outputWrongCommandEntered();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private Comment askData() throws IOException {
        ParseLongText longTextReader = new ParseLongText(scanner);
        // Ask User to Enter comment
        messageEngine.outputForEnterAComment();
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
