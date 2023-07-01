package db_objects;

public class Picture {
    public final Integer ID;
    public final Author author;
    private final Integer year;
    private final String name;
    private final String link;

    private final String border = "__________________________________\n";

    public Picture(Integer ID, Author author, Integer year, String name, String link) {
        this.ID = ID;
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
                "Author: " + author.fullName + "\n\t" +
                "Unique Number: " + ID + "\n" +
                border;
    }

    public String detailedInfo() {
        return shortInfo().replace("Short", "Detailed") + "\n\t" +
                "Date of creation:" + year + "\n\t" +
                "Link: " + link +
                border;
    }
}

