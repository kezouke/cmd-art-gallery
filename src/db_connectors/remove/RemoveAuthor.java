package db_connectors.remove;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import db_connectors.firebase.FirestoreUpdateData;

import java.util.concurrent.ExecutionException;

public class RemoveAuthor {
    private final Firestore database;
    private final FirestoreUpdateData firestoreUpdateData;

    public RemoveAuthor(Firestore database,
                        FirestoreUpdateData firestoreUpdateData) {
        this.database = database;
        this.firestoreUpdateData = firestoreUpdateData;
    }

    public void remove(int id) throws ExecutionException, InterruptedException {
        CollectionReference pictures = database.collection("author");
        Query query = pictures.whereEqualTo("author.id", id);

        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            document.getReference().delete();
        }

        firestoreUpdateData.updateData();
    }
}
