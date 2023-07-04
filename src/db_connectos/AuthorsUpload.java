package db_connectos;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

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
import db_objects.Author;

public class AuthorsUpload {
    private final Map<String, Author> authorsMap;

    public AuthorsUpload() {
        authorsMap = new HashMap<>();
        lazyLoad();
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

            CollectionReference authors = db.collection("author");
            ApiFuture<QuerySnapshot> query = authors.get();
            QuerySnapshot querySnapshot = query.get();

            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                Map<String, Object> author = (Map<String, Object>) document.getData().get("author");
                authorsMap.put(
                        document.getId(),
                        new Author(
                                ((Long) author.get("id")).intValue(),
                                (String) author.get("first_name"),
                                (String) author.get("last_name"),
                                (String) author.get("gender"),
                                (Timestamp) author.get("date_of_birth"),
                                (String) author.get("biography")
                        )
                );
            }
        } catch (IOException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Author receiveAuthor(String documentId) {
        return authorsMap.get(documentId);
    }

}
