package sample.model;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class PianoKeys {
    public String name;
    public KeyCode key;
    public int number;
    public static Color color;

    public PianoKeys(String name, KeyCode key, int number, Color color) {
        this.name = name;
        this.key = key;
        this.number = number;
        this.color = color;
    }
}
