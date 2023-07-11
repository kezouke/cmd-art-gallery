package db_connectors;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import db_objects.Author;

public class AuthorUpload {
    private final Firestore database;
    private final FirestoreUpdateData firestoreUpdate;

    public AuthorUpload(Firestore database, FirestoreUpdateData firestoreUpdate) {
        this.database = database;
        this.firestoreUpdate = firestoreUpdate;
    }

    public void uploadAuthor(Author author) {
        CollectionReference authors = database.collection("author");
        authors.document().set(author.generateAuthorMap());
        firestoreUpdate.updateData();
    }
}
