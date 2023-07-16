package db_connectors;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import db_objects.Comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class CommentsConnect {
    private Map<Integer, List<Comment>> commentsMap;
    public int lastCommentId = 1;

    public CommentsConnect(Firestore database) {
        lazyLoad(database);
    }

    private void lazyLoad(Firestore db) {
        commentsMap = new HashMap<>();
        try {
            CollectionReference comments = db.collection("comments");
            ApiFuture<QuerySnapshot> query = comments.get();
            QuerySnapshot querySnapshot = query.get();

            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                Map<String, Object> comment =
                        (Map<String, Object>) document.getData().get("comment");

                int currentPictureId = ((Long) comment.get("picture_id"))
                        .intValue();
                int currentCommentId = ((Long) comment.get("id"))
                        .intValue();
                if (!commentsMap.containsKey(currentPictureId)) {
                    commentsMap.put(
                            currentPictureId,
                            new ArrayList<>()
                    );
                }
                commentsMap
                        .get(currentPictureId)
                        .add(new Comment(
                                        currentCommentId,
                                        currentPictureId,
                                        (String) comment.get("username"),
                                        (String) comment.get("content")
                                )
                        );
                lastCommentId = Math.max(lastCommentId, currentCommentId);
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Comment> receiveComments(Integer pictureId) {
        if (!commentsMap.containsKey(pictureId)) {
            commentsMap.put(pictureId, new ArrayList<>());
        }
        return commentsMap.get(pictureId);
    }

    public void refreshData(Firestore database) {
        lazyLoad(database);
    }

}
