package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
    URL soundURL[] = new URL[30];
    Clip clip;
    public int volume = 50;
    FloatControl fc;
    
    //Loads all files
    public Sound() {
        soundURL[0] = getClass().getResource("/res/audio/bruh.wav");
        soundURL[1] = getClass().getResource("/res/audio/enemy_death.wav");
    }
    
    //Sets sound being played
    public void setFile(int i) {
        try {
            AudioInputStream sound = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(sound);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            updateVolume();
        } catch(Exception e) {}
    }
    
    //Starts playing sound
    public void play() {
        clip.setFramePosition(0);
        clip.start();
    }
    
    //Plays on repeat
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    //Stops sound
    public void stop() {
        clip.stop();
    }
    
    //Updates current volume
    void updateVolume() {
        fc.setValue((float) (Math.log10(Math.pow(10, volume))/100*86-80));
    }
    
    //Sets volume between 0-100
    public void setVolume(int vol) {
        volume = vol;
        updateVolume();
    }
}
