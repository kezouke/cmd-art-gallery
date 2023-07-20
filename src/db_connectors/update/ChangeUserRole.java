package db_connectors.update;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import db_connectors.firebase.FirestoreUpdateData;
import db_objects.Author;
import db_objects.UserRole;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ChangeUserRole {
    private final Firestore database;
    private final FirestoreUpdateData firestoreUpdate;

    public ChangeUserRole(Firestore database, FirestoreUpdateData firestoreUpdate) {
        this.database = database;
        this.firestoreUpdate = firestoreUpdate;
    }

    private String getStringRole(UserRole role) {
        return switch (role) {
            case ADMIN -> "admin";
            case SIGNED -> "signed";
            case UNSIGNED -> "unsigned";
        };
    }

    public void changeRole(String username,
                           UserRole role) throws ExecutionException, InterruptedException {
        CollectionReference users = database.collection("users");
        Query query = users.whereEqualTo("name", username);

        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            document.getReference().update("role",
                    getStringRole(role));
        }

        firestoreUpdate.updateData();
    }
}
