package sixseven;

import javafx.application.Application;

/**
 * A launcher class to workaround classpath issues when running JavaFX from a JAR.
 */
public class Launcher {
    public static void main(String[] args) {
        Application.launch(MainApp.class, args);
    }
}
