package sample.view;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import sample.model.PianoKey;


public class KeyView extends StackPane {
    private FlowPane root = new FlowPane(10, 10);
    private PianoKey keys;
    private Rectangle r = new Rectangle();

    public KeyView(PianoKey keys) {
        this.keys = keys;
        r.setWidth(50);
        r.setHeight(150);
        r.setFill(keys.getColor());
        r.setStroke(Color.BLACK);
        getChildren().addAll(r, new Text(keys.getName()));
    }


    public PianoKey getKeys() {
        return keys;
    }

    public void setKeys(PianoKey keys) {
        this.keys = keys;
    }

    public Rectangle getR() {
        return r;
    }

    public void setR(Rectangle r) {
        this.r = r;
    }

}
