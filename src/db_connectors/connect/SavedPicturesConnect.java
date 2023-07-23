package db_connectors.connect;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import db_objects.Picture;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class SavedPicturesConnect {
    private final Firestore database;
    private final PicturesConnect picturesConnect;

    public SavedPicturesConnect(Firestore database,
                                PicturesConnect picturesConnect) {
        this.database = database;
        this.picturesConnect = picturesConnect;
    }

    public List<Picture> receiveSavedPictures(String username) {
        List<Picture> savedPictures = new ArrayList<>();
        try {
            CollectionReference collection = database
                    .collection("saved_pictures");
            ApiFuture<QuerySnapshot> query = collection.get();
            QuerySnapshot querySnapshot = query.get();

            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                if (document.contains("username")) {
                    if (Objects.equals(
                            document.getString("username"),
                            username)
                    ) {
                        savedPictures.add(picturesConnect
                                .receivePicture().get(
                                        "picture" +
                                        document.get("picture_id")
                                )
                        );
                    }
                }
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return savedPictures;
    }
}
