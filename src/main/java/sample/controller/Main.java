package sample.controller;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.model.PianoKey;
import sample.view.KeyView;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import java.io.IOException;
import java.util.*;

public class Main extends Application {

    private MidiChannel channel;
    private FlowPane root = new FlowPane(10, 10);
    private final Map<KeyCode, KeyView> pianoKeysView = new LinkedHashMap<>();
     {
        pianoKeysView.put(KeyCode.D, new KeyView(new PianoKey("C", KeyCode.D, 48)));
        pianoKeysView.put(KeyCode.R, new KeyView(new PianoKey("C#", KeyCode.R, 49)));
        pianoKeysView.put(KeyCode.F, new KeyView(new PianoKey("D", KeyCode.F, 50)));
        pianoKeysView.put(KeyCode.T, new KeyView(new PianoKey("D#", KeyCode.T, 51)));
        pianoKeysView.put(KeyCode.G, new KeyView(new PianoKey("E", KeyCode.G, 52)));
        pianoKeysView.put(KeyCode.H, new KeyView(new PianoKey("F", KeyCode.H, 53)));
        pianoKeysView.put(KeyCode.U, new KeyView(new PianoKey("F#", KeyCode.U, 54)));
        pianoKeysView.put(KeyCode.J, new KeyView(new PianoKey("G", KeyCode.J, 55)));
        pianoKeysView.put(KeyCode.I, new KeyView(new PianoKey("G#", KeyCode.I, 56)));
        pianoKeysView.put(KeyCode.K, new KeyView(new PianoKey("A", KeyCode.K, 57)));
        pianoKeysView.put(KeyCode.O, new KeyView(new PianoKey("A#", KeyCode.O, 58)));
        pianoKeysView.put(KeyCode.L, new KeyView(new PianoKey("B", KeyCode.L, 59)));
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Scene scene = new Scene(createScreen());
        final Set<String> pressedKeys = new HashSet<String>();
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                String note = t.getText();
                if (!pressedKeys.contains(note)) {
                    pressedKeys.add(note);
                    PianoKey k = pianoKeysView.get(t.getCode()).getKeys();
                    k.keyUsage(pianoKeysView.get(t.getCode()));
                    channel.noteOn(k.getNumber(), 60);
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                pressedKeys.remove(t.getText());
            }
        });
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void loadChannel() {
        try {
            Synthesizer synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
            synthesizer.loadInstrument(synthesizer.getDefaultSoundbank().getInstruments()[1]);
            channel = synthesizer.getChannels()[0];
        } catch (MidiUnavailableException exception) {
            System.out.println("Can't output synthesizer");
            exception.printStackTrace();
        }
    }

    private Parent createScreen() {
        loadChannel();
        root.setPrefSize(730, 140);
        pianoKeysView.values().forEach(k -> {
            root.getChildren().add(k);
        });
        return root;
    }

}
