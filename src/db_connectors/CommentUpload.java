package db_connectors;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import db_objects.Comment;

public class CommentUpload {
    private final Firestore database;
    private final FirestoreUpdateData firestoreUpdateData;

    public CommentUpload(Firestore database, FirestoreUpdateData firestoreUpdateData) {
        this.database = database;
        this.firestoreUpdateData = firestoreUpdateData;
    }

    public void uploadComment(Comment comment) {
        CollectionReference pictures = database.collection("comments");
        pictures.document().set(comment.generateCommentMap());
        firestoreUpdateData.updateData();
    }
}
