package db_objects;

import com.google.cloud.Timestamp;

public class Author {
    public final Integer id;
    private final String firstName;
    private final String lastName;
    private final String gender;
    private final Timestamp dateOfBirth;

    private final String biography;
    private final String border = "__________________________________\n";


    public Author(Integer id,
                  String firstName,
                  String lastName,
                  String gender,
                  Timestamp dateOfBirth,
                  String biography) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.biography = biography;
    }

    public String shortInfo() {
        return border +
                "Author Short Info: " + "\n" +
                border + "\t" +
                "Name: " + firstName + " " + lastName + "\n" +
                border;
    }

    public String detailedInfo() {
        return shortInfo().replace("Short", "Detailed") + "\n\t"
                + "Dates: " + dateOfBirth.toDate() + "\n\t"
                + "Biography: " + biography + "\n" +
                border;
    }

}
