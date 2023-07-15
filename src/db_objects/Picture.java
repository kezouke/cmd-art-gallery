package db_objects;

import com.google.cloud.Timestamp;

import java.util.HashMap;
import java.util.Map;

public class Picture implements iShortInfo, iMatchWhileSearch {
    public final Integer id;
    public final Author author;
    private final Timestamp year;
    private final String name;
    private final String link;

    public Picture(Integer id,
                   Author author,
                   Timestamp year,
                   String name,
                   String link) {
        this.id = id;
        this.author = author;
        this.year = year;
        this.name = name;
        this.link = link;
    }

    public String shortInfo() {
        String border = "__________________________________\n";
        return border +
                "Picture Short Info: \n" +
                border + "\t" +
                "Name: " + name + "\n" +
                border + "\t" +
                "Unique Number: " + id + "\n" +
                border;
    }

    public String detailedInfo() {
        return shortInfo().replace("Picture Short", "Picture Detailed") + "\t" +
                "Date of creation:" + year.toDate() + "\n\t" +
                "Link: " + link + "\n" +
                author.shortInfo();
    }

    public Map<String, Object> generatePictureMap() {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> pictureMap = new HashMap<>();
        pictureMap.put("id", id);
        pictureMap.put("name", name);
        pictureMap.put("link", link);
        pictureMap.put("year", year);
        pictureMap.put("author", author.id);
        resultMap.put("picture", pictureMap);
        return resultMap;
    }

    @Override
    public boolean isMatch(String keyword) {
        return name.equalsIgnoreCase(keyword) ||
                String.valueOf(year).equalsIgnoreCase(keyword) ||
                link.equalsIgnoreCase(keyword) ||
                String.valueOf(id).equalsIgnoreCase(keyword) ||
                author.isMatch(keyword);
    }
}

