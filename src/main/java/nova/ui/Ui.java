package nova.ui;

public class Ui {

    public String returnMessage(String message) {
        if (message.contains("ERROR: ")) {
            return "BOII! " + message.replace("ERROR: ", "");
        }
        return "Your wish is my command UwU\n" + message;
    }
}
