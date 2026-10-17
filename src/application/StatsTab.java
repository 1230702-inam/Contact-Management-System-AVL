package application;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class StatsTab {

    private ContactManager manager;

    public StatsTab(ContactManager manager) {
        this.manager = manager;
    }

    public VBox getView() {
        VBox box = new VBox(15);
        box.setPadding(new Insets(20));
        box.setStyle("-fx-background-color: " + Theme.beige + ";");

        Label title = new Label(" Statistics");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + Theme.darkGold + ";");

        Button refreshBtn = new Button(" Refresh");
        refreshBtn.setStyle(Theme.btnStyle());

        // كاونت
        Label countTitle = new Label("--- Contact Count ---");
        countTitle.setStyle("-fx-font-weight: bold; -fx-text-fill: " + Theme.textColor + ";");

        Label familyLbl = new Label("Family: 0");
        Label friendLbl = new Label("Friend: 0");
        Label colleagueLbl = new Label("Colleague: 0");
        Label clientLbl = new Label("Client: 0");
        Label otherLbl = new Label("Other: 0");
        Label totalLbl = new Label("Total: 0");

        String lblStyle = "-fx-text-fill: " + Theme.textColor + "; -fx-font-size: 13px;";
        familyLbl.setStyle(lblStyle);
        friendLbl.setStyle(lblStyle);
        colleagueLbl.setStyle(lblStyle);
        clientLbl.setStyle(lblStyle);
        otherLbl.setStyle(lblStyle);
        totalLbl.setStyle(
                "-fx-text-fill: " + Theme.darkGold + ";" +
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold;"
        );

        // هايت
        Label heightTitle = new Label("--- Tree Heights ---");
        heightTitle.setStyle("-fx-font-weight: bold; -fx-text-fill: " + Theme.textColor + ";");

        Label heightFamily = new Label("Family Tree Height: 0");
        Label heightFriend = new Label("Friend Tree Height: 0");
        Label heightColleague = new Label("Colleague Tree Height: 0");
        Label heightClient = new Label("Client Tree Height: 0");
        Label heightOther = new Label("Other Tree Height: 0");

        heightFamily.setStyle(lblStyle);
        heightFriend.setStyle(lblStyle);
        heightColleague.setStyle(lblStyle);
        heightClient.setStyle(lblStyle);
        heightOther.setStyle(lblStyle);
        
        VBox countCard = new VBox(10);
        countCard.setPadding(new Insets(15));
        countCard.setStyle(
                "-fx-background-color: white;" +
                "-fx-border-color: " + Theme.gold + ";" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 10;" +
                "-fx-background-radius: 10;"
        );

        countCard.getChildren().addAll(
                countTitle,
                familyLbl,
                friendLbl,
                colleagueLbl,
                clientLbl,
                otherLbl,
                totalLbl
        );

        VBox heightCard = new VBox(10);
        heightCard.setPadding(new Insets(15));
        heightCard.setStyle(
                "-fx-background-color: white;" +
                "-fx-border-color: " + Theme.darkGold + ";" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 10;" +
                "-fx-background-radius: 10;"
        );

        heightCard.getChildren().addAll(
                heightTitle,
                heightFamily,
                heightFriend,
                heightColleague,
                heightClient,
                heightOther
        );

        refreshBtn.setOnAction(e -> {
            familyLbl.setText("Family: " + manager.countByCategory("Family"));
            friendLbl.setText("Friend: " + manager.countByCategory("Friend"));
            colleagueLbl.setText("Colleague: " + manager.countByCategory("Colleague"));
            clientLbl.setText("Client: " + manager.countByCategory("Client"));
            otherLbl.setText("Other: " + manager.countByCategory("Other"));
            totalLbl.setText("Total: " + manager.totalCount());

            heightFamily.setText("Family Tree Height: " + manager.treeHeight("Family"));
            heightFriend.setText("Friend Tree Height: " + manager.treeHeight("Friend"));
            heightColleague.setText("Colleague Tree Height: " + manager.treeHeight("Colleague"));
            heightClient.setText("Client Tree Height: " + manager.treeHeight("Client"));
            heightOther.setText("Other Tree Height: " + manager.treeHeight("Other"));
        });

        box.getChildren().addAll(
                title,
                refreshBtn,
                countCard,
                heightCard
        );

        return box;
    }
    
}