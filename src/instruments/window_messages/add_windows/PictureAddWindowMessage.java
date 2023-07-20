package instruments.window_messages.add_windows;

import instruments.window_messages.WrongCommandMessage;

public class PictureAddWindowMessage {
    public void outputIsUserWantToAdd() {
        String message = """
                You are going to add new Picture.
                Are you sure? (yes/no)
                """;
        System.out.println(message);
    }

    public void outputWrongCommandEntered() {
        new WrongCommandMessage().outputMessage();
    }

    public void outputSuccess() {
        String message = """
                You have been successfully add new picture!
                """;
        System.out.println(message);
    }

    public void outputEnterName() {
        String message = """
                Enter a name for picture:
                Note: You can enter any text with any size.
                      However, enter '<*>' when you will finish.
                      So, I will understand that you entered all name.
                      Example:
                              "My first picture <*>"
                """;
        System.out.println(message);
    }

    public void outputEnterDate() {
        String message = """
                Enter a date when picture was created in following format:
                'YYYY-MM-DD'
                """;
        System.out.println(message);
    }

    public void outputEnterALink() {
        String message = """
                Enter a link for picture:
                """;
        System.out.println(message);
    }

    public void outputEnterAuthorId() {
        String message = """
                Enter an author id
                (if you want to take a look at all authors enter 'authors') :
                """;
        System.out.println(message);
    }

    public void outputNoSuchAuthor() {
        String message = """
                ID you have entered doesn't belong to any authors
                """;
        System.out.println(message);
    }

    public void outputNotANumberEntered() {
        String message = """
                You have entered not a number as id!
                """;
        System.out.println(message);
    }
}
