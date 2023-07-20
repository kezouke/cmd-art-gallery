package db_connectors.remove;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import db_connectors.firebase.FirestoreUpdateData;

import java.util.concurrent.ExecutionException;

public class RemoveUser {
    private final Firestore database;
    private final FirestoreUpdateData firestoreUpdateData;

    public RemoveUser(Firestore database,
                         FirestoreUpdateData firestoreUpdateData) {
        this.database = database;
        this.firestoreUpdateData = firestoreUpdateData;
    }

    public void removeByUsername(String username) throws ExecutionException, InterruptedException {
        CollectionReference pictures = database.collection("users");
        Query query = pictures.whereEqualTo("name", username);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            document.getReference().delete();
        }

        firestoreUpdateData.updateData();
    }
}
