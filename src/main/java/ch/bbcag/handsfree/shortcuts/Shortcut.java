package ch.bbcag.handsfree.shortcuts;

import java.util.ArrayList;
import java.util.List;

public class Shortcut {
    
    private List<Click> clicks = new ArrayList<>();
    
    private String name;
    
    public List<Click> getClicks() {
        return clicks;
    }
    
    public void setClicks(List<Click> clicks) {
        this.clicks = clicks;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
