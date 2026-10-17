package application;

public class Theme {
    public static String beige = "#F5F0E8";
    public static String gold = "#C9A84C";
    public static String darkGold = "#A67C2E";
    public static String textColor = "#4A3728";

    public static String btnStyle() {
        return "-fx-background-color: white;" +
               "-fx-text-fill: " + darkGold + ";" +
               "-fx-font-weight: bold;" +
               "-fx-border-color: " + darkGold + ";" +
               "-fx-border-radius: 5px;" +
               "-fx-background-radius: 5px;" +
               "-fx-cursor: hand;";
    }

    public static String fieldStyle() {
        return "-fx-background-color: white;" +
               "-fx-border-color: " + gold + ";" +
               "-fx-border-radius: 5px;" +
               "-fx-background-radius: 5px;" +
               "-fx-padding: 5px;";
    }
}