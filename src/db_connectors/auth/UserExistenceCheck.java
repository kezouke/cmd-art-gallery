package db_connectors.auth;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;

import java.util.concurrent.ExecutionException;

public class UserExistenceCheck {
    private final Firestore database;

    public UserExistenceCheck(Firestore database) {
        this.database = database;
    }

    public boolean isExist(String username) {
        try {
            CollectionReference users = database.collection("users");
            ApiFuture<QuerySnapshot> query = users.get();
            QuerySnapshot querySnapshot = query.get();
            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                if (document.contains("name")) {
                    if (username.equals(document.getString("name"))) {
                        return true;
                    }
                }
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
