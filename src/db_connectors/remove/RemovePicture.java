package db_connectors.remove;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import db_connectors.firebase.FirestoreUpdateData;

import java.util.concurrent.ExecutionException;

public class RemovePicture {
    private final Firestore database;
    private final FirestoreUpdateData firestoreUpdateData;

    public RemovePicture(Firestore database,
                         FirestoreUpdateData firestoreUpdateData) {
        this.database = database;
        this.firestoreUpdateData = firestoreUpdateData;
    }

    public void removeById(int id) throws ExecutionException, InterruptedException {
        CollectionReference pictures = database.collection("pictures");
        Query query = pictures.whereEqualTo("picture.id", id);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            document.getReference().delete();
        }

        firestoreUpdateData.updateData();
    }

    public void removeByAuthorId(int id) throws ExecutionException, InterruptedException {
        CollectionReference pictures = database.collection("pictures");
        Query query = pictures.whereEqualTo("picture.author", id);

        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            document.getReference().delete();
        }

        firestoreUpdateData.updateData();

    }
}
