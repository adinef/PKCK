package pl.lodz.p.it.pkck;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Main extends Application {

    private static Stage mainStage;

    public static Window getMainStage() {
        return mainStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        primaryStage.setTitle("PKCK::: Adrian Fijałkowski (210171)");
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.setMinHeight(750);
        primaryStage.setMinWidth(700);
        primaryStage.show();
        mainStage = primaryStage;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
