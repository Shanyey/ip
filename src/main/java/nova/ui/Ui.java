package nova.ui;

public class Ui {

    public String returnMessage(String message) {
        if (message.contains("ERROR: ")) {
            return "BOII! " + message.replace("ERROR: ", "") + ".\nType help for more information.";
        }
        return "Your wish is my command UwU\n" + message;
    }
}
