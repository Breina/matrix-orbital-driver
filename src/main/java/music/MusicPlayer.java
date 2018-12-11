package music;

import commands.Util;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sound.midi.*;
import java.io.*;
import java.util.*;
import java.util.function.BiConsumer;

@Log4j2
public class MusicPlayer {

    private static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    private final Map<String, Integer> frequencies;

    private final BiConsumer<Integer, Integer> player;

    public MusicPlayer(BiConsumer<Integer, Integer> player) throws IOException {
        this.player = player;

        Properties properties = new Properties();
        try (InputStream is = MusicPlayer.class.getClassLoader().getResourceAsStream("notes.properties")) {
            properties.load(is);
        }

        frequencies = new HashMap<>();
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            frequencies.put((String) entry.getKey(), Math.round(Float.valueOf((String) entry.getValue())));
        }
    }

    public void playNote(String note, int lengthMs) {
        if (note != null) {
            Integer frequency = frequencies.get(note.toUpperCase());
            if (frequency == null) {
                log.error("Couldn't find note: " + note);
                return;
            }

            player.accept(frequency, lengthMs);
        }

        try {
            Thread.sleep(lengthMs);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    public void playMidi(String path, float slowlessFactor) {
        File file = new File(path);
        log.info("Parsing song: " + file.getName());
        if (!file.exists() || !file.isFile()) {
            log.error("Couldn't find file.");
            return;
        }

        List<NoteDuration> song = new ArrayList<>();
        String currentNote = null;
        long currentTime = 0;

        try {
            Sequence sequence = MidiSystem.getSequence(file);

            for (Track track : sequence.getTracks()) {
                for (int i = 0; i < track.size(); i++) {
                    MidiEvent midiEvent = track.get(i);
                    int duration = Math.toIntExact(midiEvent.getTick() - currentTime);
                    currentTime = midiEvent.getTick();

                    MidiMessage message = midiEvent.getMessage();
                    if (message instanceof ShortMessage) {
                        ShortMessage sm = (ShortMessage) message;

                        int key = sm.getData1();
                        int octave = (key / 12) - 1;
                        int note = key % 12;
                        String noteName = NOTE_NAMES[note] + octave;

                        if (sm.getCommand() == ShortMessage.NOTE_ON) {
                            if (currentNote != null) {
                                song.add(new NoteDuration(noteName, Math.round(duration * slowlessFactor)));
                            }

                            currentNote = noteName;
                        } else {
                            if (currentNote == null) {
                                log.warn("Turning note " + noteName + " off at tick " + currentTime + " but no note was being played.");
                            } else {
                                if (!currentNote.equals(noteName)) {
                                    log.warn("Turning note " + noteName + " off, but " + currentNote + " was still being played.");
                                } else {
                                    song.add(new NoteDuration(currentNote, Math.round(duration * slowlessFactor)));
                                }
                            }
                        }

                    } else if (message instanceof MetaMessage) {
                        MetaMessage metaMessage = (MetaMessage) message;
                        log.info("Type: " + Util.byteArrayToHex((byte) metaMessage.getType()) + ", Data: " + Util.byteArrayToHex(metaMessage.getData()) + " (" + new String(metaMessage.getData()) + ")");
                    } else {
                        log.warn("Other sort of message: " + new String(message.getMessage()));
                    }
                }
            }

        } catch (InvalidMidiDataException | IOException  e) {
            log.error(e.getMessage());
        }

        log.info("Playing song: " + file.getName());
        song.forEach(noteDuration -> playNote(noteDuration.getNote(), noteDuration.getDuration()));
    }

    @Getter(AccessLevel.PRIVATE)
    private class NoteDuration {
        private final String note;
        private final int duration;

        private NoteDuration(String note, int duration) {
            this.note = note;
            this.duration = duration;
        }
    }
}