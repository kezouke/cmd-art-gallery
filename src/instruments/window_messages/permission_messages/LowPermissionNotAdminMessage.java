package instruments.window_messages.permission_messages;

public class LowPermissionNotAdminMessage {
    public void outputMessage() {
        String lowPermissionMessage = """
                You have to be admin to do it :(
                """;
        System.out.println(lowPermissionMessage);
    }
}
