package db_connectos;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import db_objects.Picture;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class PicturesUpload {
    private final List<Picture> pictures;

    public PicturesUpload() {
        pictures = new ArrayList<>();
    }

    private void lazyLoad() {
        try {
            FileInputStream serviceAccount = new FileInputStream(
                    System.getProperty("user.dir") + "\\cmd-art-gallery-firebase.json"
            );
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .build();
            FirebaseApp.initializeApp(options);

            Firestore db = FirestoreClient.getFirestore();

            CollectionReference collection = db.collection("pictures");
            ApiFuture<QuerySnapshot> query = collection.get();
            QuerySnapshot querySnapshot = query.get();

            AuthorsUpload authors = new AuthorsUpload();

            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                Map<String, Object> picture =
                        (Map<String, Object>) document.getData().get("picture");
                pictures.add(
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
        } catch (IOException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Picture> receivePicture() {
        return pictures;
    }
}
