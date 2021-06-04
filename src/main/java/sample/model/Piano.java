package sample.model;

import javafx.scene.input.KeyCode;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class Piano {

    Instant beginTime;
    Duration deltaTime;
    PianoKey lastKey = null;
    private boolean record = false;
    private final Set<Integer> pressedKeys = new HashSet<Integer>();
    private final List<Integer> playedNotes = new ArrayList<>();
    private final static Map<Integer, PianoKey> pianoKeys = new LinkedHashMap<>();
    static {
        pianoKeys.put(KeyCode.D.getCode(), new PianoKey("C", KeyCode.D, 48));
        pianoKeys.put(KeyCode.R.getCode(), new PianoKey("C#", KeyCode.R, 49));
        pianoKeys.put(KeyCode.F.getCode(), new PianoKey("D", KeyCode.F, 50));
        pianoKeys.put(KeyCode.T.getCode(), new PianoKey("D#", KeyCode.T, 51));
        pianoKeys.put(KeyCode.G.getCode(), new PianoKey("E", KeyCode.G, 52));
        pianoKeys.put(KeyCode.H.getCode(), new PianoKey("F", KeyCode.H, 53));
        pianoKeys.put(KeyCode.U.getCode(), new PianoKey("F#", KeyCode.U, 54));
        pianoKeys.put(KeyCode.J.getCode(), new PianoKey("G", KeyCode.J, 55));
        pianoKeys.put(KeyCode.I.getCode(), new PianoKey("G#", KeyCode.I, 56));
        pianoKeys.put(KeyCode.K.getCode(), new PianoKey("A", KeyCode.K, 57));
        pianoKeys.put(KeyCode.O.getCode(), new PianoKey("A#", KeyCode.O, 58));
        pianoKeys.put(KeyCode.L.getCode(), new PianoKey("B", KeyCode.L, 59));
    }


    public void playNote(int keyCode) {
        if (!pressedKeys.contains(keyCode)) {
            pressedKeys.add(keyCode);
//            PianoKey k = pianoKeys.get(keyCode);
        }
        if (record) {
            PianoKey k = pianoKeys.get(keyCode);
            if (lastKey != null) putNote();
            beginTime = Instant.now();
            lastKey = k;
        }
    }


    public Piano() {

    }

    public boolean isRecord() {
        return record;
    }

    public void changeRecord() {
        if (record) {
            putNote();
            //playedNotes.clear();
        }
        this.record = !record;
    }

    private void putNote() {
        deltaTime = Duration.between(beginTime, Instant.now());
        playedNotes.add(lastKey.getKey().getCode());
        playedNotes.add(deltaTime.getNano());
    }

    public Set<Integer> getPressedKeys() {
        return pressedKeys;
    }

    public List<Integer> getPlayedNotes() {
        return playedNotes;
    }

    public static Map<Integer, PianoKey> getPianoKeys() {
        return pianoKeys;
    }
}
