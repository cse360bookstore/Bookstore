package Bookstore.components;

import javafx.scene.control.Alert;
import javafx.stage.Window;

public class AlertHelper {
    private static Window getDefaultWindow() {
        return javafx.stage.Window.getWindows()
                .stream()
                .filter(Window::isShowing)
                .findFirst()
                .orElse(null);
    }

    public static void showAlert(Alert.AlertType alertType, String title, String message) {
        Window window = getDefaultWindow();
        Alert alert = new Alert(alertType);
        alert.initOwner(window);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public static Alert createAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert;
    }
}
