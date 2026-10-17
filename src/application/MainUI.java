package application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class MainUI {

    private ContactManager manager;
    private Stage stage;
    private FileManager fileManager;
    private Label statusLbl;
    private FilterTab filterTab;

    public MainUI(ContactManager manager, Stage stage) {
        this.manager = manager;
        this.stage = stage;
        this.fileManager = new FileManager(manager);
    }

    public void show() {
        VBox root = new VBox(0);
        root.setStyle("-fx-background-color: " + Theme.beige + ";");

        // هيدر
        HBox header = new HBox(10);
        header.setPadding(new Insets(15, 20, 15, 20));
        header.setStyle("-fx-background-color: " + Theme.gold + ";");

        Label appTitle = new Label("📒  Address Book");
        appTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");

        Region space = new Region();
        HBox.setHgrow(space, Priority.ALWAYS);

        Button loadBtn = new Button("📂 Load File");
        Button saveBtn = new Button("💾 Save File");
        statusLbl = new Label("Ready");

        loadBtn.setStyle(Theme.btnStyle());
        saveBtn.setStyle(Theme.btnStyle());
        statusLbl.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");

        header.getChildren().addAll(appTitle, space, loadBtn, saveBtn, statusLbl);

        // نعمل الـ filterTab أولاً عشان نبعته للتانيين
        filterTab = new FilterTab(manager);

        // تبويبات
        TabPane tabs = new TabPane();
        tabs.setStyle("-fx-tab-min-width: 120px; -fx-background-color: " + Theme.beige + ";");

        Tab addTab = new Tab("➕  Add", new AddTab(manager, filterTab).getView());
        Tab searchTab = new Tab("  Search", new SearchTab(manager, filterTab).getView());
        Tab filterTabUI = new Tab(" Filter", filterTab.getView());
        Tab statsTab = new Tab(" Stats", new StatsTab(manager).getView());

        addTab.setClosable(false);
        searchTab.setClosable(false);
        filterTabUI.setClosable(false);
        statsTab.setClosable(false);

        tabs.getTabs().addAll(addTab, searchTab, filterTabUI, statsTab);

        // لود
        loadBtn.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("Open File");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            File file = fc.showOpenDialog(stage);
            if (file != null) {
                int count = fileManager.loadFromFile(file.getAbsolutePath());
                statusLbl.setText(" Loaded " + count + " contacts!");
                filterTab.refresh();
            }
        });

        // سيف
        saveBtn.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("Save File");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            File file = fc.showSaveDialog(stage);
            if (file != null) {
                fileManager.saveToFile(file.getAbsolutePath());
                statusLbl.setText(" Saved!");
            }
        });

        root.getChildren().addAll(header, tabs);
        VBox.setVgrow(tabs, Priority.ALWAYS);

        Scene scene = new Scene(root, 750, 550);
        stage.setTitle("Address Book");
        stage.setScene(scene);
        stage.show();
    }
}