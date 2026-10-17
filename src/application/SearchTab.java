package application;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class SearchTab {

    private ContactManager manager;
    private FilterTab filterTab;

    public SearchTab(ContactManager manager, FilterTab filterTab) {
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

        Label title = new Label("Search / Update / Delete");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + Theme.darkGold + ";");

        TextField nameField = new TextField();
        nameField.setPromptText("Enter Full Name");
        nameField.setStyle(Theme.fieldStyle());

        HBox btnBox = new HBox(10);
        Button searchBtn = new Button(" Search");
        Button loadBtn = new Button(" Load");
        Button deleteBtn = new Button(" Delete");

        searchBtn.setStyle(Theme.btnStyle());
        loadBtn.setStyle(Theme.btnStyle());
        deleteBtn.setStyle(Theme.btnStyle());
        btnBox.getChildren().addAll(searchBtn, loadBtn, deleteBtn);

        TextArea result = new TextArea();
        result.setEditable(false);
        result.setPrefHeight(80);
        result.setStyle(Theme.fieldStyle());

        Label updateTitle = new Label("Update Contact");
        updateTitle.setStyle("-fx-font-weight: bold; -fx-text-fill: " + Theme.darkGold + ";");

        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone Number (10 digits)");
        phoneField.setStyle(Theme.fieldStyle());
        phoneField.setDisable(true);

        TextField emailField = new TextField();
        emailField.setPromptText("Email Address");
        emailField.setStyle(Theme.fieldStyle());
        emailField.setDisable(true);

        ComboBox<String> categoryBox = new ComboBox<>();
        categoryBox.getItems().addAll("Family", "Friend", "Colleague", "Client", "Other");
        categoryBox.setPromptText("Category");
        categoryBox.setStyle(Theme.fieldStyle());
        categoryBox.setDisable(true);

        Button saveBtn = new Button("Save");
        saveBtn.setStyle(Theme.btnStyle());
        saveBtn.setDisable(true);

        // سيرش
        searchBtn.setOnAction(e -> {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Enter a name!");
                return;
            }
            if (!name.matches("[a-zA-Z ]+")) {
                showAlert(Alert.AlertType.WARNING, "Name must contain letters only!");
                return;
            }
            Contact c = manager.search(name);
            if (c != null) {
                result.setText(c.toString());
            } else {
                result.setText("Contact not found.");
            }
        });

        // لود
        loadBtn.setOnAction(e -> {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Enter a name!");
                return;
            }
            Contact c = manager.search(name);
            if (c != null) {
                phoneField.setText(c.getPhoneNumber());
                emailField.setText(c.getEmail());
                categoryBox.setValue(c.getCategory());
                phoneField.setDisable(false);
                emailField.setDisable(false);
                categoryBox.setDisable(false);
                saveBtn.setDisable(false);
            } else {
                showAlert(Alert.AlertType.ERROR, "Contact not found.");
            }
        });

        // سيف
        saveBtn.setOnAction(e -> {
            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();
            String category = categoryBox.getValue();

            if (!phone.matches("[0-9]{10}")) {
                showAlert(Alert.AlertType.WARNING, "Phone must be 10 digits only!");
                return;
            }
            if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
                showAlert(Alert.AlertType.WARNING, "Invalid email address!");
                return;
            }

            boolean done = manager.update(name, phone, email, category);
            if (done) {
                showAlert(Alert.AlertType.INFORMATION, "Updated successfully!");
                phoneField.setDisable(true);
                emailField.setDisable(true);
                categoryBox.setDisable(true);
                saveBtn.setDisable(true);
                filterTab.refresh();
            } else {
                showAlert(Alert.AlertType.ERROR, "Something went wrong!");
            }
        });

        // دليت
        deleteBtn.setOnAction(e -> {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Enter a name!");
                return;
            }
            Contact c = manager.search(name);
            if (c == null) {
                showAlert(Alert.AlertType.ERROR, "Contact not found.");
                return;
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete " + name + "?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    manager.deleteContact(name, c.getCategory());
                    showAlert(Alert.AlertType.INFORMATION, "Deleted successfully!");
                    nameField.clear();
                    result.clear();
                    filterTab.refresh();
                }
            });
        });

        box.getChildren().addAll(title, nameField, btnBox, result,
                updateTitle, phoneField, emailField, categoryBox, saveBtn);
        return box;
    }
}