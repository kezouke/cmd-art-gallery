package db_objects;

import com.google.cloud.Timestamp;
import instruments.work_with_text.FormatTextInWindow;

import java.util.HashMap;
import java.util.Map;

public class Author implements iShortInfo, iMatchWhileSearch {
    public final Integer id;
    private final String firstName;
    private final String lastName;
    private final String gender;
    private final Timestamp dateOfBirth;
    private final String country;

    private final String biography;
    private final String border = "__________________________________\n";


    public Author(Integer id,
                  String firstName,
                  String lastName,
                  String gender,
                  Timestamp dateOfBirth,
                  String biography,
                  String country) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.biography = biography;
        this.country = country;
    }

    public String shortInfo() {
        return border +
                "Author Short Info: " + "\n" +
                border + "\t" +
                "Name: " + firstName + " " + lastName + "\n" +
                border + "\t" +
                gender + "\n" +
                border + "\t" +
                "Id: " + id + "\n" +
                border;
    }

    public String detailedInfo() {
        return shortInfo().replace("Short", "Detailed") + "\n\t"
                + "Date of Birth: " + dateOfBirth.toDate() + "\n" +
                border + "\t" +
                "Biography: " +
                new FormatTextInWindow().format(biography, 20)
                + "\n" + border + "\t" +
                "Country: " + country + "\n" +
                border;
    }

    public Map<String, Object> generateAuthorMap() {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> authorMap = new HashMap<>();
        authorMap.put("biography", biography);
        authorMap.put("country", country);
        authorMap.put("date_of_birth", dateOfBirth);
        authorMap.put("first_name", firstName);
        authorMap.put("last_name", lastName);
        authorMap.put("gender", gender);
        authorMap.put("id", id);

        resultMap.put("author", authorMap);
        return resultMap;
    }

    @Override
    public boolean isMatch(String keyword) {
        return firstName.contains(keyword) ||
                lastName.contains(keyword) ||
                country.equalsIgnoreCase(keyword) ||
                gender.equalsIgnoreCase(keyword) ||
                String.valueOf(id).equalsIgnoreCase(keyword);
    }
}