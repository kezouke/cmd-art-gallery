package db_objects;

public class Author {
    public final Integer ID;
    private final String fullName;
    private final Integer born;
    private Integer died = 0;
    private final String biography;

    public Author(Integer ID, String fullName, Integer born, Integer died, String biography) {
        this.ID = ID;
        this.fullName = fullName;
        this.born = born;
        this.died = died;
        this.biography = biography;
    }

    public Author(Integer ID, String fullName, Integer born, String biography) {
        this.ID = ID;
        this.fullName = fullName;
        this.born = born;
        this.biography = biography;
    }

    public String shortInfo() {
        return fullName;
    }

    public String detailedInfo() {
        return shortInfo() + " \n"
                + "Was born: " + born + "\n"
                + "Biography: " + "\n" + biography;
    }
}
