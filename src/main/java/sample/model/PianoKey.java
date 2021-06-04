package sample.model;

import javafx.animation.FillTransition;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import sample.view.KeyView;

public class PianoKey {
    private String name;
    private KeyCode key;
    private int number;
    private Color color;

    public PianoKey(String name, KeyCode key, int number) {
        this.name = name;
        this.key = key;
        this.number = number;
    }

    public void keyUsage(KeyView key) {
        FillTransition ft = new FillTransition(Duration.seconds(0.1), key.getR(), Color.WHITE, Color.BLACK);
        ft.setCycleCount(2);
        ft.setAutoReverse(true);
        ft.play();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public KeyCode getKey() {
        return key;
    }

    public void setKey(KeyCode key) {
        this.key = key;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return name;
    }
}
