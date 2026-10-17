package application;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class FilterTab {

    private ContactManager manager;
    private ListView<String> allContacts;

    public FilterTab(ContactManager manager) {
        this.manager = manager;
    }

    private void showAlert(Alert.AlertType type, String msg) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public void refresh() {

        allContacts.getItems().clear();

        String all = manager.printAll();

        if (all.isEmpty()) {
            allContacts.getItems().add("No contacts found.");
            return;
        }

        String[] lines = all.split("\n");

        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                allContacts.getItems().add(line);
            }
        }
    }

    public VBox getView() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(20));
        box.setStyle("-fx-background-color: " + Theme.beige + ";");

        Label title = new Label("Filter / Print All");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + Theme.darkGold + ";");

        // فلتر
        ComboBox<String> categoryBox = new ComboBox<>();
        categoryBox.getItems().addAll("Family", "Friend", "Colleague", "Client", "Other");
        categoryBox.setPromptText("Select Category");
        categoryBox.setStyle(Theme.fieldStyle());

        HBox btnBox = new HBox(10);
        Button filterBtn = new Button(" Filter");
        Button printAllBtn = new Button(" Print All A-Z");

        filterBtn.setStyle(Theme.btnStyle());
        printAllBtn.setStyle(Theme.btnStyle());
        btnBox.getChildren().addAll(filterBtn, printAllBtn);

        // تيكست اريا للعرض
        Label allTitle = new Label("All Contacts:");
        allTitle.setStyle("-fx-font-weight: bold; -fx-text-fill: " + Theme.darkGold + ";");

        allContacts = new ListView<>();
        allContacts.setPrefHeight(300);
        allContacts.getItems().add("No contacts yet.");
        
        filterBtn.setOnAction(e -> {
            String category = categoryBox.getValue();
            if (category == null) {
                showAlert(Alert.AlertType.WARNING, "Select a category!");
                return;
            }
            String contacts = manager.filterByCategory(category);
            if (contacts.isEmpty()) {
            	allContacts.getItems().clear();
            	allContacts.getItems().clear();
            	allContacts.getItems().add("No contacts in this category.");
            } else {
            	allContacts.getItems().clear();

            	String[] lines = contacts.split("\n");

            	for(String line : lines){
            	    if(!line.trim().isEmpty()){
            	        allContacts.getItems().add(line);
            	    }
            	}
            }
        });

        printAllBtn.setOnAction(e -> {
            String all = manager.printAll();
            if (all.isEmpty()) {
            	allContacts.getItems().clear();
            	allContacts.getItems().add("No contacts found.");
            } else {
            	allContacts.getItems().clear();
            	String[] lines = all.split("\n");
            	for(String line : lines){
            	    if(!line.trim().isEmpty()){
            	        allContacts.getItems().add(line);
            	    }
            	}
            }
        });

        box.getChildren().addAll(title, categoryBox, btnBox, allTitle, allContacts);
        return box;
    }
}