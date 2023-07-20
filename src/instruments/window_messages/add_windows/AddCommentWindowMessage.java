package instruments.window_messages.add_windows;

import instruments.window_messages.WrongCommandMessage;

public class AddCommentWindowMessage {
    public void outputIsUserSureToAdd() {
        String message = "You are going to add new Comment. Are you sure? (yes/no)";
        System.out.println(message);
    }

    public void outputSuccessAdd() {
        String message = "You have been successfully add new comment!";
        System.out.println(message);
    }

    public void outputWrongCommandEntered() {
        new WrongCommandMessage().outputMessage();
    }

    public void outputForEnterAComment() {
        String message = """
                Enter a comment:
                Note: You can enter any text with any size.
                      However, enter '<*>' when you will finish.
                      So, I will understand that you entered all comment.
                      Example:
                            "Amazing Picture! Love this author. <*>"
                """;
        System.out.println(message);
    }
}
