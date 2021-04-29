package ch.bbcag.handsfree.scenes;

import ch.bbcag.handsfree.Const;
import ch.bbcag.handsfree.HandsFreeApplication;
import ch.bbcag.handsfree.control.Colors;
import ch.bbcag.handsfree.control.HandsFreeFont;
import ch.bbcag.handsfree.control.HandsFreeScene;
import ch.bbcag.handsfree.control.button.HandsFreeButtonPalette;
import ch.bbcag.handsfree.control.button.HandsFreeDefaultButton;
import ch.bbcag.handsfree.control.button.HandsFreeIconButton;
import ch.bbcag.handsfree.control.dialog.HandsFreeConfirmDialog;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ShortcutMenu extends HandsFreeScene {
    public ShortcutMenu(Navigator navigator, HandsFreeApplication application) {
        super(application.getPrimaryStage(), new VBox(), application.getConfiguration());

        VBox vBoxRoot = (VBox) getContentRootPane();
        vBoxRoot.setSpacing(Const.V_BOX_SPACING);
        vBoxRoot.setPadding(new Insets(Const.V_BOX_PADDING_TOP_BOTTOM, Const.V_BOX_PADDING_RIGHT_LEFT, Const.V_BOX_PADDING_TOP_BOTTOM, Const.V_BOX_PADDING_RIGHT_LEFT));
        vBoxRoot.setMinSize(Const.WIDTH, Const.HEIGHT);
        
        VBox vBoxTop = new VBox();
        HBox hBoxBack = new HBox();
        HBox hBoxTitle = new HBox();
    
        HandsFreeIconButton back = new HandsFreeIconButton("/images/back.png");
        back.setPalette(new HandsFreeButtonPalette(Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT));
        back.setPrefSize(64, 48);
        back.setOnAction(event -> navigator.navigateTo(SceneType.MAIN_MENU));
        hBoxBack.getChildren().add(back);
        
        Label title = new Label("Shortcuts");
        title.setFont(HandsFreeFont.getFont(30));
        title.setTextFill(Colors.FONT);
        hBoxTitle.getChildren().add(title);
        hBoxTitle.setAlignment(Pos.CENTER);
        
        vBoxTop.getChildren().addAll(hBoxBack, hBoxTitle);
        
        HandsFreeDefaultButton recordShortcut = new HandsFreeDefaultButton("Record Shortcut");
        HandsFreeDefaultButton deleteShortcut = new HandsFreeDefaultButton("Delete Shortcut");
        deleteShortcut.setOnAction(event -> {
            HandsFreeConfirmDialog dialog = new HandsFreeConfirmDialog("Delete Shortcut", "Do you really want to delete \"Name\"? This shortcut will be lost forever! (A long time!)");
            dialog.show();
        });
        
        vBoxRoot.getChildren().addAll(vBoxTop, recordShortcut, deleteShortcut);
    }
}
