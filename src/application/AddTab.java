package application;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class AddTab {

    private ContactManager manager;
    private FilterTab filterTab;

    public AddTab(ContactManager manager, FilterTab filterTab) {
        this.manager = manager;
        this.filterTab = filterTab;
    }

    private void showAlert(Alert.AlertType type, String msg) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public VBox getView() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(20));
        box.setStyle("-fx-background-color: " + Theme.beige + ";");

        Label title = new Label("Add New Contact");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + Theme.darkGold + ";");

        TextField nameField = new TextField();
        nameField.setPromptText("Full Name");
        nameField.setStyle(Theme.fieldStyle());

        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone Number (10 digits)");
        phoneField.setStyle(Theme.fieldStyle());

        TextField emailField = new TextField();
        emailField.setPromptText("Email Address");
        emailField.setStyle(Theme.fieldStyle());

        ComboBox<String> categoryBox = new ComboBox<>();
        categoryBox.getItems().addAll("Family", "Friend", "Colleague", "Client", "Other");
        categoryBox.setPromptText("Select Category");
        categoryBox.setStyle(Theme.fieldStyle());

        Button addBtn = new Button("Add Contact");
        addBtn.setStyle(Theme.btnStyle());

        addBtn.setOnAction(e -> {
            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();
            String category = categoryBox.getValue();

            if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || category == null) {
                showAlert(Alert.AlertType.WARNING, "Please fill all fields!");
                return;
            }

            if (!name.matches("[a-zA-Z ]+")) {
                showAlert(Alert.AlertType.WARNING, "Name must contain letters only!");
                return;
            }

            if (!phone.matches("[0-9]{10}")) {
                showAlert(Alert.AlertType.WARNING, "Phone must be 10 digits only!");
                return;
            }

            if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
                showAlert(Alert.AlertType.WARNING, "Invalid email address!");
                return;
            }

            Contact c = new Contact(name, phone, email, category);
            boolean done = manager.addContact(c);

            if (done) {
                showAlert(Alert.AlertType.INFORMATION, "Contact added successfully!");
                nameField.clear();
                phoneField.clear();
                emailField.clear();
                categoryBox.setValue(null);
                filterTab.refresh();
            } else {
                showAlert(Alert.AlertType.ERROR, "Contact already exists!");
            }
        });

        box.getChildren().addAll(title, nameField, phoneField, emailField, categoryBox, addBtn);
        return box;
    }
}