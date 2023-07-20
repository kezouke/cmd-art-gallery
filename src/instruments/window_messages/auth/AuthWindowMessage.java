package instruments.window_messages.auth;

import instruments.window_messages.WrongCommandMessage;

public class AuthWindowMessage {
    public void outputGreeting() {
        String greetMessage = """
                Welcome to our cmd library app!
                                
                ~ If you already have account, please,
                    enter 'login'
                                
                ~ If you have to continue be unregistered,
                    enter 'continue' (you'll have a narrow range of options)
                                
                ~ In other cases you have to be registered, please, enter 'register'
                                
                ____________________________________________________________________
                If you don't want to continue, enter 'close' to stop program.
                                
                """;
        System.out.println(greetMessage);
    }

    public void outputNoteForUnsignedUsers() {
        String message = """
                No problem! You will be unsigned,
                however you will not be able to
                add new pictures and authors.
                """;
        System.out.println(message);
    }

    public void outputWrongCommandEntered() {
        new WrongCommandMessage().outputMessage();
    }
}
