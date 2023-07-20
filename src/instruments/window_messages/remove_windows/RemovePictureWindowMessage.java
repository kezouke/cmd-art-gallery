package instruments.window_messages.remove_windows;

import instruments.window_messages.WrongCommandMessage;

public class RemovePictureWindowMessage {

    public void outputQuestionIsUserSure() {
        String message = """
                You are going to remove picture.
                You will not be able to undo this action.
                Are you sure? (yes/no)
                """;
        System.out.println(message);
    }
    public void outputSuccessDeletion() {
        new SuccessDeletionMessage().outputMessage();
    }
    public void outputWrongCommandEnteredMessage() {
        new WrongCommandMessage().outputMessage();
    }
}
