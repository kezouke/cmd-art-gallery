package db_connectors;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import db_objects.Picture;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class PicturesConnect {
    private final Map<String, Picture> pictures;

    public PicturesConnect(Firestore db) {
        pictures = new HashMap<>();
        lazyLoad(db);
    }

    private void lazyLoad(Firestore db) {
        try {
            CollectionReference collection = db.collection("pictures");
            ApiFuture<QuerySnapshot> query = collection.get();
            QuerySnapshot querySnapshot = query.get();

            AuthorsConnect authors = new AuthorsConnect(db);

            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                Map<String, Object> picture =
                        (Map<String, Object>) document.getData().get("picture");
                pictures.put(
                        "picture" + picture.get("id").toString(),
                        new Picture(
                                ((Long) picture.get("id")).intValue(),
                                authors.receiveAuthor(
                                        (String) picture.get("author")),
                                (Timestamp) picture.get("year"),
                                (String) picture.get("name"),
                                (String) picture.get("link")
                        )
                );
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, Picture> receivePicture() {
        return pictures;
    }
}
