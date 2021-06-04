package sample.model;

import javafx.scene.input.KeyCode;
import java.time.Duration;
import sample.controller.Main;
import sample.view.KeyView;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class PianoKeyTest {

    Piano piano = new Piano();

    @org.junit.jupiter.api.Test
    void test() {
        piano.changeRecord();
        piano.playNote(KeyCode.D.getCode());
        piano.playNote(KeyCode.R.getCode());
        piano.changeRecord();
        List<Integer> actual = piano.getPlayedNotes();
        List<Integer> expected = new ArrayList<>();
        expected.add(KeyCode.D.getCode());
        expected.add(actual.get(1));
        expected.add(KeyCode.R.getCode());
        expected.add(actual.get(1));

        assertEquals(expected, actual);

    }
}