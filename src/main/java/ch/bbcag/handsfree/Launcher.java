package ch.bbcag.handsfree;

import javafx.application.Application;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Launcher {
    
    public static void main(String[] args) throws Exception {
        Application.launch(HandsFreeApplication.class);
        if(true) return;

        JFrame test = new JFrame();
        test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        test.setSize(750, 500);
        test.setVisible(true);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        test.setContentPane(panel);

        JTextArea area = new JTextArea();
        area.setSize(500, 250);
        panel.add(area);

        area.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                try {
                    for(Field field : KeyEvent.class.getDeclaredFields()) {
                        if(Modifier.isStatic(field.getModifiers()) && Modifier.isPublic(field.getModifiers())) {
                            int code = field.getInt(KeyEvent.class);
                            if(code == e.getKeyCode()) {
                                System.out.println(field.getName());
                            }
                        }
                    }
                } catch(Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }
    
}
