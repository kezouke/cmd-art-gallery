package db_connectors.update;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import db_connectors.firebase.FirestoreUpdateData;

import java.util.concurrent.ExecutionException;

public class ChangeUserBio {
    private final Firestore database;
    private final FirestoreUpdateData firestoreUpdate;

    public ChangeUserBio(Firestore database,
                         FirestoreUpdateData firestoreUpdate) {
        this.database = database;
        this.firestoreUpdate = firestoreUpdate;
    }

    public void changeBio(String username,
                          String bio) throws ExecutionException, InterruptedException {
        CollectionReference users = database.collection("users");
        Query query = users.whereEqualTo("name", username);

        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            document.getReference().update("bio", bio);
        }

        firestoreUpdate.updateData();
    }
}
