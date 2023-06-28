package db_objects;

public class Picture {
    public final Integer ID;
    public final Author author;
    private final Integer year;
    private final String name;
    private final String link;

    public Picture(Integer ID, Author author, Integer year, String name, String link) {
        this.ID = ID;
        this.author = author;
        this.year = year;
        this.name = name;
        this.link = link;
    }

    public String shortInfo() {
        return "Picture#" + ID + "Name: " + name + "\n" + "Author: " + author;
    }

    public String detailedInfo() {
        return shortInfo() + "\n" + "Date of creation:" + year + "\n" + "Link: " + link;
    }
}

