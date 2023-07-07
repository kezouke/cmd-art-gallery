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
        userMap.put("uid", user.hashCode());
        CollectionReference users = db.collection("users");
        users.document("user").set(userMap);
        System.out.println("You have been successfully registered");
    }
}
