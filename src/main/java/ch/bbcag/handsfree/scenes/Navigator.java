package ch.bbcag.handsfree.scenes;

import ch.bbcag.handsfree.control.HandsFreeScene;

import java.util.HashMap;
import java.util.Map;

public class Navigator {
    private Map<SceneType, HandsFreeScene> sceneMap = new HashMap<>();
    private SceneType currentSceneType;
    
    public void registerScene(SceneType sceneType, HandsFreeScene scene) {
        sceneMap.put(sceneType, scene);
    }
    
    public void navigateTo(SceneType sceneType) {
        sceneMap.get(sceneType).apply();
        this.currentSceneType = sceneType;
    }
    
    public SceneType getCurrentSceneType() {
        return currentSceneType;
    }
}
