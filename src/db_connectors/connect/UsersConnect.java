package db_connectors.connect;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import db_objects.User;
import db_objects.UserRole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class UsersConnect {
    private Map<String, User> users;

    public UsersConnect(Firestore database) {
        lazyLoad(database);
    }

    private void lazyLoad(Firestore db) {
        try {
            CollectionReference collection = db.collection("users");
            ApiFuture<QuerySnapshot> query = collection.get();
            QuerySnapshot querySnapshot = query.get();

            users = new HashMap<>();

            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                if (document.contains("name") && document.contains("role")) {
                    String username = document.getString("name");
                    String role = document.getString("role");
                    User user = new User(username);
                    switch (Objects.requireNonNull(role)) {
                        case "admin" -> user.role = UserRole.ADMIN;
                        case "signed" -> user.role = UserRole.SIGNED;
                        default -> user.role = UserRole.UNSIGNED;
                    }
                    users.put(
                            username,
                            user
                    );
                }
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> receiveUsersList() {
        return users.values().stream().toList();
    }

    public User receiveUserByUsername(String username) {
        return users.get(username);
    }

}
