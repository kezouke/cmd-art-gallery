package db_objects;

import instruments.work_with_text.FormatTextInWindow;

import java.util.HashMap;
import java.util.Map;

public class Comment implements iShortInfo {
    public final int id;
    public final int pictureId;
    private final String username;
    private final String content;

    public Comment(int id, int pictureId, String username, String content) {
        this.id = id;
        this.pictureId = pictureId;
        this.username = username;
        this.content = content;
    }

    @Override
    public String shortInfo() {
        String border = "__________________________________\n";
        return border +
                username + ":\n " +
                new FormatTextInWindow().format(content, 20) + "\n" +
                border;
    }

    public Map<String, Object> generateCommentMap() {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> commentMap = new HashMap<>();
        commentMap.put("id", id);
        commentMap.put("picture_id", pictureId);
        commentMap.put("username", username);
        commentMap.put("content", content);

        resultMap.put("comment", commentMap);
        return resultMap;
    }
}
