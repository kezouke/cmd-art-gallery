package db_connectors.auth;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import db_objects.User;
import db_objects.UserRole;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class UserLogin {
    private final User user;
    private final Firestore database;

    public UserLogin(User user, Firestore database) {
        this.user = user;
        this.database = database;
    }

    public UserRole obtainUserRole() {
        String role = " ";
        try {
            CollectionReference users = database.collection("users");
            ApiFuture<QuerySnapshot> query = users.get();
            QuerySnapshot querySnapshot = query.get();
            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                if (document.contains("uid")) {
                    if (user.generateHash().equals(document.getString("uid"))) {
                        role = document.getString("role");
                    }
                }
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (role == null) {
            role = " ";
        }
        switch (role) {
            case "admin" -> {
                return UserRole.ADMIN;
            }
            case "signed" -> {
                return UserRole.SIGNED;
            }
            default -> {
                return UserRole.UNSIGNED;
            }
        }
    }
}
