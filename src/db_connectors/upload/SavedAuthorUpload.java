package db_connectors.upload;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import db_connectors.firebase.FirestoreUpdateData;
import db_objects.Author;
import db_objects.User;

import java.util.HashMap;
import java.util.Map;

public class SavedAuthorUpload {
    private final Firestore database;
    private final FirestoreUpdateData firestoreUpdateData;

    public SavedAuthorUpload(Firestore database,
                              FirestoreUpdateData firestoreUpdateData) {
        this.database = database;
        this.firestoreUpdateData = firestoreUpdateData;
    }

    public void saveAuthor(Author author, User user) {
        CollectionReference collection = database
                .collection("saved_authors");
        Map<String, Object> document = new HashMap<>();
        document.put("username", user.username);
        document.put("author_id", author.id);
        collection.document().set(document);
        firestoreUpdateData.updateData();
    }
}
