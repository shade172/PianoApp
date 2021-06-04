package sample.controller;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import sample.model.Piano;
import sample.model.PianoKey;
import sample.view.KeyView;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import javax.swing.*;
import java.io.*;
import java.time.Duration;
import java.util.*;

public class Main extends Application {

    private boolean record = false;
    private MidiChannel channel;
    private final Piano piano = new Piano();
    private final FlowPane root = new FlowPane(10, 10);
    private final Map<Integer, KeyView> pianoKeyView = new LinkedHashMap<>();
    {
        pianoKeyView.put(KeyCode.D.getCode(), new KeyView(Piano.getPianoKeys().get(KeyCode.D.getCode())));
        pianoKeyView.put(KeyCode.R.getCode(), new KeyView(Piano.getPianoKeys().get(KeyCode.R.getCode())));
        pianoKeyView.put(KeyCode.F.getCode(), new KeyView(Piano.getPianoKeys().get(KeyCode.F.getCode())));
        pianoKeyView.put(KeyCode.T.getCode(), new KeyView(Piano.getPianoKeys().get(KeyCode.T.getCode())));
        pianoKeyView.put(KeyCode.G.getCode(), new KeyView(Piano.getPianoKeys().get(KeyCode.G.getCode())));
        pianoKeyView.put(KeyCode.H.getCode(), new KeyView(Piano.getPianoKeys().get(KeyCode.H.getCode())));
        pianoKeyView.put(KeyCode.U.getCode(), new KeyView(Piano.getPianoKeys().get(KeyCode.U.getCode())));
        pianoKeyView.put(KeyCode.J.getCode(), new KeyView(Piano.getPianoKeys().get(KeyCode.J.getCode())));
        pianoKeyView.put(KeyCode.I.getCode(), new KeyView(Piano.getPianoKeys().get(KeyCode.I.getCode())));
        pianoKeyView.put(KeyCode.K.getCode(), new KeyView(Piano.getPianoKeys().get(KeyCode.K.getCode())));
        pianoKeyView.put(KeyCode.O.getCode(), new KeyView(Piano.getPianoKeys().get(KeyCode.O.getCode())));
        pianoKeyView.put(KeyCode.L.getCode(), new KeyView(Piano.getPianoKeys().get(KeyCode.L.getCode())));
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource("newsample.fxml"));
//        primaryStage.setTitle("Hello");
//        primaryStage.setScene(new Scene(root, 630, 300));
//        primaryStage.show();
        Scene scene = new Scene(createScreen());
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                KeyCode keyCode = t.getCode();
                piano.playNote(keyCode.getCode());
                PianoKey k = pianoKeyView.get(keyCode.getCode()).getKeys();
                k.keyUsage(pianoKeyView.get(keyCode.getCode()));
                channel.noteOn(Piano.getPianoKeys().get(keyCode.getCode()).getNumber(), 60);
                //playNote(keyCode.getCode());
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                KeyCode keyCode = t.getCode();
                piano.getPressedKeys().remove(keyCode.getCode());
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
        pianoKeyView.values().forEach(k -> {
            root.getChildren().add(k);
        });
        Button btn = new Button("Save");
        btn.setOnAction(event -> {
            System.out.println("RECORD:  " + record);
            if (piano.isRecord()) writeToFile();
            piano.changeRecord();
        });

        Button btn1 = new Button("Play from file");
        btn1.setOnAction(event -> {
            getFile();
        });
        root.getChildren().add(btn);
        root.getChildren().add(btn1);
        return root;
    }

    private void getFile() {
        JFileChooser chooser = new JFileChooser();
        int jf = chooser.showSaveDialog(null);
        if (jf == JFileChooser.APPROVE_OPTION) {
            try {
                FileReader fr = new FileReader(chooser.getSelectedFile());
                BufferedReader bf = new BufferedReader(fr);
                String[] args = bf.readLine().split(", ");
                for (int i = 0; i < args.length - 1; i += 2) {
                    //playNote(Integer.parseInt(args[i]));
                    int keyCode = Integer.parseInt(args[i]);
//                    channel.noteOn(Piano.getPianoKeys().get(keyCode).getNumber(), 60);
                    PianoKey k = pianoKeyView.get(keyCode).getKeys();
                    k.keyUsage(pianoKeyView.get(keyCode));
                    channel.noteOn(Piano.getPianoKeys().get(keyCode).getNumber(), 60);
                    Duration sleep = Duration.ofNanos(Long.parseLong(args[i + 1]));
                    Thread.sleep(sleep.toMillis());
                }
                fr.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    public void writeToFile() {
        JFileChooser chooser = new JFileChooser();
        int jf = chooser.showSaveDialog(null);
        if (jf == JFileChooser.APPROVE_OPTION) {
            try {
                FileWriter fw = new FileWriter(chooser.getSelectedFile() + ".txt");
                List<Integer> playedNotes = piano.getPlayedNotes();
                for (int k = 0; k < playedNotes.size() - 1; k += 2) {
                    fw.write(playedNotes.get(k) + ", ");
                    fw.write(playedNotes.get(k + 1) + ", ");
                }
                fw.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        piano.getPlayedNotes().clear();
    }

}
