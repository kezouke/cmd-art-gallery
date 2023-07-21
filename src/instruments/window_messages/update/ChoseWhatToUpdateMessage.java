package instruments.window_messages.update;

import instruments.window_messages.WrongCommandMessage;

public class ChoseWhatToUpdateMessage {
    public void outputIsUserSure(){
        String message = """
                You are going to update your profile.
                Are you sure? (yes/no)
                """;
        System.out.print(message);
    }

    public void outputForWrongCommand(){
        new WrongCommandMessage().outputMessage();
    }

    public void outputVariants() {
        String variants = """
                If you want to change bio:
                    enter 'bio'
                If you want to change profile link:
                    enter 'link'
                If you want to change all listed fields:
                    enter 'all'
                """;
        System.out.print(variants);
    }

    public void outputException(String exception) {
        String message = "Something went wrong :(\n";
        System.out.println(message + exception);
    }
}
