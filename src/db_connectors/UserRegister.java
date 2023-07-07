package db_connectors;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import db_objects.User;
import java.util.HashMap;
import java.util.Map;

public class UserRegister {

    public UserRegister(User user, Firestore db) {
        register(user, db);
    }

    private void register(User user, Firestore db) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("uid", user.generateHash());
        CollectionReference users = db.collection("users");
        users.document().set(userMap);
    }
}
