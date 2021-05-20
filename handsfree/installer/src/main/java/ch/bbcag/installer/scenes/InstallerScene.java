package ch.bbcag.installer.scenes;

import ch.bbcag.handsfree.gui.HandsFreeScene;
import ch.bbcag.handsfree.gui.HandsFreeSceneConfiguration;
import ch.bbcag.handsfree.gui.button.HandsFreeButtonPalette;
import ch.bbcag.handsfree.gui.button.HandsFreeTextButton;
import ch.bbcag.installer.Const;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class InstallerScene extends HandsFreeScene {

    private HBox buttonHBox;

    public InstallerScene(Stage stage, HandsFreeSceneConfiguration configuration) {
        super(stage, new BorderPane(), configuration);

        buttonHBox = new HBox(Const.BOX_SPACING);
        buttonHBox.setAlignment(Pos.BOTTOM_RIGHT);
        getBorderPane().setBottom(buttonHBox);

        getContentRoot().setMinSize(Const.WIDTH, Const.HEIGHT);
        getContentRoot().setMaxSize(Const.WIDTH, Const.HEIGHT);
        getContentRoot().setPadding(new Insets(Const.BOX_PADDING_TOP_BOTTOM, Const.BOX_PADDING_RIGHT_LEFT, Const.BOX_PADDING_TOP_BOTTOM, Const.BOX_PADDING_RIGHT_LEFT));
    }

    public BorderPane getBorderPane() {
        return (BorderPane) getContentRoot();
    }

    public void addButton(String text, HandsFreeButtonPalette palette, Runnable onClicked) {
        HandsFreeTextButton button = new HandsFreeTextButton(text);
        button.setPrefWidth(Const.BUTTON_WIDTH);
        button.setPadding(Const.BUTTON_PADDING);
        button.setPalette(palette);
        button.setOnAction(event -> {
            if(onClicked != null) {
                onClicked.run();
            }
        });
        buttonHBox.getChildren().add(button);
    }

}
