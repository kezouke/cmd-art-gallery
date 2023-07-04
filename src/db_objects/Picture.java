package db_objects;

import com.google.cloud.Timestamp;

public class Picture {
    public final Integer id;
    public final Author author;
    private final Timestamp year;
    private final String name;
    private final String link;

    private final String border = "__________________________________\n";

    public Picture(Integer id, Author author, Timestamp year, String name, String link) {
        this.id = id;
        this.author = author;
        this.year = year;
        this.name = name;
        this.link = link;
    }

    public String shortInfo() {
        return border +
                "Picture Short Info: \n" +
                border + "\t" +
                "Name: " + name + "\n\t" +
                author.shortInfo() + "\n\t" +
                "Unique Number: " + id + "\n" +
                border;
    }

    public String detailedInfo() {
        return shortInfo().replace("Short", "Detailed") + "\t" +
                "Date of creation:" + year + "\n\t" +
                "Link: " + link + "\n" +
                border;
    }
}

