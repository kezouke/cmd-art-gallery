package representation_instruments.window_messages.remove_windows;

import representation_instruments.window_messages.WrongCommandMessage;

public class RemoveAuthorWindowMessage {
    public void outputQuestionIsUserSure() {
        String question = """
                You are going to remove author.
                You will not be able to undo this action.
                Are you sure? (yes/no)
                """;
        System.out.println(question);
    }

    public void outputSuccessDeletion() {
        new SuccessDeletionMessage().outputMessage();
    }

    public void outputWrongCommandEnteredMessage() {
        new WrongCommandMessage().outputMessage();
    }
}
