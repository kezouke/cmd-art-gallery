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
    private final CommentsConnect comments;
    private final AuthorsConnect authors;
    public int lastPictureId = 1;

    public PicturesConnect(Firestore database,
                           AuthorsConnect authors,
                           CommentsConnect comments) {
        this.authors = authors;
        this.comments = comments;
        pictures = new HashMap<>();
        lazyLoad(database);
    }

    private void lazyLoad(Firestore db) {
        try {
            CollectionReference collection = db.collection("pictures");
            ApiFuture<QuerySnapshot> query = collection.get();
            QuerySnapshot querySnapshot = query.get();

            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                Map<String, Object> picture =
                        (Map<String, Object>) document.getData().get("picture");
                int currentPictureId = ((Long) picture.get("id")).intValue();

                pictures.put(
                        "picture" + currentPictureId,
                        new Picture(
                                currentPictureId,
                                authors.receiveAuthor(
                                        ((Long) picture.get("author")).intValue()),
                                (Timestamp) picture.get("year"),
                                (String) picture.get("name"),
                                (String) picture.get("link"),
                                comments.receiveComments(currentPictureId)
                        )
                );
                lastPictureId = Math.max(lastPictureId, ((Long) picture.get("id")).intValue());
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, Picture> receivePicture() {
        return pictures;
    }

    public void refreshData(Firestore database) {
        lazyLoad(database);
    }

}
