package sample.view;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import sample.model.PianoKeys;

public class KeyView extends StackPane {
    public PianoKeys keys;
    public Rectangle r = new Rectangle();

    KeyView(PianoKeys keys) {
        this.keys = keys;
        r.setWidth(50);
        r.setHeight(150);
        r.setFill(PianoKeys.color);
        r.setStroke(Color.BLACK);
        getChildren().addAll(r, new Text(keys.name));
    }
}
