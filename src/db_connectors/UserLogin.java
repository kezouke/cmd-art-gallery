package db_connectors;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import db_objects.User;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public class UserLogin {
    private final User user;
    private final Firestore db;

    public UserLogin(User user, Firestore db) {
        this.user = user;
        this.db = db;
    }

    public boolean login() {
        try {
            CollectionReference users = db.collection("users");
            ApiFuture<QuerySnapshot> query = users.get();
            QuerySnapshot querySnapshot = query.get();
            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                Map<String, Object> userMap =
                        (Map<String, Object>) document.getData().get("user");
                if (user.hashCode() == (int) userMap.get("uid")) {
                    return true;
                }
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
