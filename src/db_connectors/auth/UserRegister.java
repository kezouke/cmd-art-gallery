package db_connectors.auth;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import db_objects.User;
import java.util.HashMap;
import java.util.Map;

public class UserRegister {

    public UserRegister(User user, Firestore database) {
        registerUser(user, database);
    }

    private void registerUser(User user, Firestore db) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("uid", user.generateHash());
        userMap.put("name", user.username);
        CollectionReference users = db.collection("users");
        users.document().set(userMap);
    }
}
