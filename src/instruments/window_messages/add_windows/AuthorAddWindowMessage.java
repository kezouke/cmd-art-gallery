package instruments.window_messages.add_windows;

import instruments.window_messages.WrongCommandMessage;

public class AuthorAddWindowMessage {
    public void outputIsUserSureToAdd() {
        String message = "You are going to add new Author. Are you sure? (yes/no)";
        System.out.println(message);
    }

    public void outputWrongCommandEntered() {
        new WrongCommandMessage().outputMessage();
    }

    public void outputForEnterName() {
        String message = """
                Enter a name of author in the following format:
                'FirstName LastName'
                """;
        System.out.println(message);
    }

    public void outputForEnterBirthDate() {
        String message = """
                Enter a date when author was born in following format:
                'YYYY-MM-DD'
                """;
        System.out.println(message);
    }

    public void outputForEnterCountry() {
        String message = """
                Enter a country where author was born:
                Note: You can enter any text with any size.
                      However, enter '<*>' when you will finish.
                      So, I will understand that you entered your country.
                      Example:
                              "Russian Federation <*>"
                """;
        System.out.println(message);
    }

    public void outputForEnterBio() {
        String message = """
                Enter a biography of author:
                Note: You can enter any text with any size.
                      However, enter '<*>' when you will finish.
                      So, I will understand that you entered all bio.
                      Example:
                            "Pushkin, known as the father of modern Russian
                             literature, was a renowned poet and writer
                             whose works continue to captivate readers
                             with their lyrical beauty and profound
                             exploration of human emotions. <*>"
                """;
        System.out.println(message);
    }

    public void outputForEnterGender() {
        String message = """
                Enter gender in following format (male/female):
                """;
        System.out.println(message);
    }
}
