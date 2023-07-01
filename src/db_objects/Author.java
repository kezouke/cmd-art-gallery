package db_objects;

public class Author {
    public final Integer ID;
    private final String fullName;
    private final String livingDate;
    private final String biography;
    private final String border = "__________________________________\n";


    public Author(Integer ID, String fullName, String livingDate, String biography) {
        this.ID = ID;
        this.fullName = fullName;
        this.livingDate = livingDate
        this.biography = biography;
    }


    public String shortInfo() {
        return border +
                "Author Short Info: " + "\n" +
                border + "\t" +
                "Name: " + fullName +
                border;
    }

    public String detailedInfo() {
        return shortInfo() + "\n\t"
                + "Dates: " + livingDate + "\n\t"
                + "Biography: " + "\n\t" + biography +
                border;
    }

}
