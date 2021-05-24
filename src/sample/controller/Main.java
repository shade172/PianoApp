package sample.controller;

import javafx.animation.FillTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.model.PianoKeys;
import sample.view.KeyView;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main extends Application {

    private MidiChannel channel;
    private FlowPane root = new FlowPane(10, 10);
    private List<PianoKeys> pianoKeys = Arrays.asList(
            new PianoKeys("C", KeyCode.D, 48, Color.WHITE),
            new PianoKeys("C#", KeyCode.R, 49, Color.BLACK),
            new PianoKeys("D", KeyCode.F, 50, Color.WHITE),
            new PianoKeys("D#", KeyCode.T, 51, Color.BLACK),
            new PianoKeys("E", KeyCode.G, 52, Color.WHITE),
            new PianoKeys("F", KeyCode.H, 53, Color.WHITE),
            new PianoKeys("F#", KeyCode.U, 54, Color.BLACK),
            new PianoKeys("G", KeyCode.J, 55, Color.WHITE),
            new PianoKeys("G#", KeyCode.I, 56, Color.BLACK),
            new PianoKeys("A", KeyCode.K, 57, Color.WHITE),
            new PianoKeys("A#", KeyCode.O, 58, Color.BLACK),
            new PianoKeys("B", KeyCode.L, 59, Color.WHITE)
    );

    @Override
    public void start(Stage primaryStage) {
        //Parent root = FXMLLoader.load(getClass().getResource("../newsample.fxml"));
        //primaryStage.setTitle("Piano");
        //primaryStage.setScene(new Scene(root, 632, 300));
        //primaryStage.setResizable(false);
        //primaryStage.show();
        //loadChannel();
        Scene scene = new Scene(createScreen());
        final Set<String> pressedKeys = new HashSet<String>();
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                String note = t.getText();
                if (!pressedKeys.contains(note)) {
                    pressedKeys.add(note);

                    keyUsage(t.getCode());
                }
            }
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                pressedKeys.remove(t.getText());
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    private void keyUsage(KeyCode key) {
        root.getChildren().stream().map(k -> (KeyView) k).filter(k -> k.keys.key.equals(key))
                .forEach(k -> {
                    FillTransition ft = new FillTransition(Duration.seconds(0.1), k.r, Color.WHITE, Color.BLACK);
                    ft.play();
                    channel.noteOn(k.keys.number, 60);
                });
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
        pianoKeys.forEach(k -> {
            KeyView view = new KeyView(k);
            root.getChildren().addAll(view);
        });
        return root;
    }
}
