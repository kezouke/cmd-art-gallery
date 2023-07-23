package db_connectors.remove;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import db_connectors.firebase.FirestoreUpdateData;
import db_objects.Author;
import db_objects.User;

import java.util.concurrent.ExecutionException;

public class RemoveSavedAuthor {
    private final Firestore database;
    private final FirestoreUpdateData firestoreUpdateData;

    public RemoveSavedAuthor(Firestore database,
                             FirestoreUpdateData firestoreUpdateData) {
        this.database = database;
        this.firestoreUpdateData = firestoreUpdateData;
    }

    public void removeFromSaved(User user, Author author)
            throws ExecutionException, InterruptedException {
        CollectionReference collection = database.collection("saved_authors");
        Query query = collection
                .whereEqualTo("username", user.username)
                .whereEqualTo("author_id", author.id);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            document.getReference().delete();
        }

        firestoreUpdateData.updateData();
    }
}
