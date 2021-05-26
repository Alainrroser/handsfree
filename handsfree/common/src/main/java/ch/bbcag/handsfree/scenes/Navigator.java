package ch.bbcag.handsfree.scenes;

import ch.bbcag.handsfree.gui.HandsFreeScene;

import java.util.HashMap;
import java.util.Map;

public class Navigator {
    private Map<ISceneType, HandsFreeScene> sceneMap = new HashMap<>();
    private ISceneType currentSceneType;

    public void registerScene(ISceneType sceneType, HandsFreeScene scene) {
        sceneMap.put(sceneType, scene);
    }

    public void navigateTo(ISceneType sceneType) {
        sceneMap.get(sceneType).apply();
        this.currentSceneType = sceneType;
    }

    public ISceneType getCurrentSceneType() {
        return currentSceneType;
    }
}
