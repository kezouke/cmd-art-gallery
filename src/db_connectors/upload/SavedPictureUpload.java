package db_connectors.upload;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import db_connectors.firebase.FirestoreUpdateData;
import db_objects.Picture;
import db_objects.User;

import java.util.HashMap;
import java.util.Map;

public class SavedPictureUpload {
    private final Firestore database;
    private final FirestoreUpdateData firestoreUpdateData;

    public SavedPictureUpload(Firestore database,
                              FirestoreUpdateData firestoreUpdateData) {
        this.database = database;
        this.firestoreUpdateData = firestoreUpdateData;
    }

    public void savePicture(Picture picture, User user) {
        CollectionReference collection = database
                .collection("saved_pictures");
        Map<String, Object> document = new HashMap<>();
        document.put("username", user.username);
        document.put("picture_id", picture.id);
        collection.document().set(document);
        firestoreUpdateData.updateData();
    }
}
