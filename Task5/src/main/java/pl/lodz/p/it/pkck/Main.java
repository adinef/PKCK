package pl.lodz.p.it.pkck;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import pl.lodz.p.it.pkck.view.FXUtils;

public class Main extends Application {

    private static Stage mainStage;

    public static Window getMainStage() {
        return mainStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Thread.setDefaultUncaughtExceptionHandler(Main::handleError);

        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
        mainStage = primaryStage;
    }

    private static void handleError(Thread thread, Throwable throwable) {
        System.err.println("Wystąpił błąd aplikacyjny!");
        if (Platform.isFxApplicationThread()) {
            FXUtils.noDataPopup("Błąd", "Wystąpił nieznany błąd. " + throwable.getMessage());
        }
        throwable.printStackTrace();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
