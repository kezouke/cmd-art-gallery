package db_connectors.remove;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import db_connectors.firebase.FirestoreUpdateData;
import db_objects.Picture;
import db_objects.User;

import java.util.concurrent.ExecutionException;

public class RemoveSavedPicture {
    private final Firestore database;
    private final FirestoreUpdateData firestoreUpdateData;

    public RemoveSavedPicture(Firestore database,
                              FirestoreUpdateData firestoreUpdateData) {
        this.database = database;
        this.firestoreUpdateData = firestoreUpdateData;
    }

    public void removeFromSaved(User user, Picture picture)
            throws ExecutionException, InterruptedException {
        CollectionReference collection = database.collection("saved_pictures");
        Query query = collection
                .whereEqualTo("username", user.username)
                .whereEqualTo("picture_id", picture.id);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            document.getReference().delete();
        }

        firestoreUpdateData.updateData();
    }
}
