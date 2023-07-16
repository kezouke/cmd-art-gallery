package db_connectors.upload;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import db_connectors.firebase.FirestoreUpdateData;
import db_objects.Picture;

public class PictureUpload {
    private final Firestore database;
    private final FirestoreUpdateData firestoreUpdateData;

    public PictureUpload(Firestore database, FirestoreUpdateData firestoreUpdateData) {
        this.database = database;
        this.firestoreUpdateData = firestoreUpdateData;
    }

    public void uploadPicture(Picture picture) {
        CollectionReference pictures = database.collection("pictures");
        pictures.document().set(picture.generatePictureMap());
        firestoreUpdateData.updateData();
    }
}
