package db_connectors.connect;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import db_objects.Author;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class SavedAuthorsConnect {
    private final Firestore database;
    private final AuthorsConnect authorsConnect;

    public SavedAuthorsConnect(Firestore database,
                               AuthorsConnect authorsConnect) {
        this.database = database;
        this.authorsConnect = authorsConnect;
    }

    public List<Author> receiveSavedAuthors(String username) {
        List<Author> savedAuthors = new ArrayList<>();
        try {
            CollectionReference collection = database
                    .collection("saved_authors");
            ApiFuture<QuerySnapshot> query = collection.get();
            QuerySnapshot querySnapshot = query.get();

            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                if (document.contains("username")) {
                    if (Objects.equals(
                            document.getString("username"),
                            username)
                    ) {
                        Integer authorId = Objects
                                .requireNonNull(document.getLong("author_id"))
                                .intValue();

                        savedAuthors.add(authorsConnect
                                .receiveAuthor(
                                        authorId
                                )
                        );
                    }
                }
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return savedAuthors;
    }
}
