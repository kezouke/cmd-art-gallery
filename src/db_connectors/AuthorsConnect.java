package db_connectors;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import db_objects.Author;

public class AuthorsConnect {
    private final Map<String, Author> authorsMap;

    public AuthorsConnect(Firestore db) {
        authorsMap = new HashMap<>();
        lazyLoad(db);
    }

    private void lazyLoad(Firestore db) {
        try {
            CollectionReference authors = db.collection("author");
            ApiFuture<QuerySnapshot> query = authors.get();
            QuerySnapshot querySnapshot = query.get();

            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                Map<String, Object> author =
                        (Map<String, Object>) document.getData().get("author");
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
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Author receiveAuthor(String documentId) {
        return authorsMap.get(documentId);
    }

}
