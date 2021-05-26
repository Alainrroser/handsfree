package ch.bbcag.handsfree.scenes;

import ch.bbcag.handsfree.Const;
import ch.bbcag.handsfree.HandsFreeApplication;
import ch.bbcag.handsfree.gui.*;
import ch.bbcag.handsfree.gui.button.HandsFreeIconButton;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ScrollScene extends HandsFreeScene {

    private String title;

    public ScrollScene(Stage primaryStage, Region contentRoot, HandsFreeSceneConfiguration configuration, String title) {
        super(primaryStage, contentRoot, configuration);
        this.title = title;
    }

    protected VBox initVBox() {
        VBox vBox = new VBox();
        vBox.setSpacing(Const.V_BOX_SPACING);
        vBox.setPadding(Const.PADDING);
        vBox.setMaxHeight(Double.MAX_VALUE);
        return vBox;
    }

    protected HandsFreeListView initList() {
        HandsFreeListView list = new HandsFreeListView();
        list.setMaxWidth(Double.MAX_VALUE);
        list.setMinHeight(600);
        return list;
    }

    protected VBox initTop(HandsFreeApplication application) {
        VBox vBoxTop = new VBox();
        HBox hBoxBack = initBackButton(application);
        HBox hBoxTitle = initTitle();
        vBoxTop.getChildren().addAll(hBoxBack, hBoxTitle);
        return vBoxTop;
    }

    protected HBox initBackButton(HandsFreeApplication application) {
        HBox hBoxBack = new HBox();
        HandsFreeIconButton back = new HandsFreeIconButton("/images/back.png");
        back.setContentSize(64, 48);
        back.setOnAction(event -> application.getNavigator().navigateTo(SceneType.MAIN_MENU));
        hBoxBack.getChildren().add(back);
        return hBoxBack;
    }

    protected HBox initTitle() {
        HBox hBoxTitle = new HBox();
        Label title = new Label(this.title);
        title.setFont(HandsFreeFont.getFont(30));
        title.setTextFill(Colors.FONT);
        hBoxTitle.getChildren().add(title);
        hBoxTitle.setAlignment(Pos.CENTER);
        return hBoxTitle;
    }
}
