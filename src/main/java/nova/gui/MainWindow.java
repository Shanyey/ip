package nova.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import nova.nova.Nova;

/**
 * Controller for the main GUI.
 */
public class MainWindow {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Nova nova;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/userImage.png"));
    private final Image novaImage = new Image(this.getClass().getResourceAsStream("/images/nova.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        showWelcomeMessage();
    }

    private void showWelcomeMessage() {
        String welcomeText = """
                Hello! I'm Nova, your personal assistant.
                How can I help you today?
                If you require assistance, feel free to type help!""";
        dialogContainer.getChildren().add(DialogBox.getNovaDialog(welcomeText, novaImage));
    }

    /** Injects the Nova instance */
    public void setNova(Nova d) {
        nova = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = nova.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getNovaDialog(response, novaImage)
        );
        userInput.clear();
    }
}
