package ch.bbcag.handsfree.scenes;

import ch.bbcag.handsfree.ApplicationConstants;
import ch.bbcag.handsfree.HandsFreeApplication;
import ch.bbcag.handsfree.HandsFreeContext;
import ch.bbcag.handsfree.control.speechcontrol.Command;
import ch.bbcag.handsfree.gui.HandsFreeListView;
import ch.bbcag.handsfree.gui.HandsFreeScrollPane;
import javafx.scene.layout.VBox;

public class CommandsList extends ApplicationScene {

    public CommandsList(HandsFreeApplication application, HandsFreeContext context) {
        super(application.getPrimaryStage(), new HandsFreeScrollPane(), application.getConfiguration(), "Commands List");

        getContentRoot().setMinSize(ApplicationConstants.WIDTH, ApplicationConstants.HEIGHT);
        getContentRoot().setMaxSize(ApplicationConstants.WIDTH, ApplicationConstants.HEIGHT);

        initGUI(application, context);
    }

    private void initGUI(HandsFreeApplication application, HandsFreeContext context) {
        HandsFreeScrollPane scrollPane = (HandsFreeScrollPane) getContentRoot();

        VBox vBox = initVBox();
        VBox vBoxTop = initTop(application);
        HandsFreeListView list = initList();
        addCommands(list, context);

        vBox.getChildren().addAll(vBoxTop, list);
        scrollPane.setContent(vBox);
    }

    private void addCommands(HandsFreeListView list, HandsFreeContext context) {
        for(Command command : context.getSpeechRecognizer().getCommands()) {
            list.addItem(command.getName() + ": " + command.getDescription());
        }
    }

}
