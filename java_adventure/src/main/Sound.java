package main;

import util.Const;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound implements Const {
	Clip clip;
	URL[] soundUrl = new URL[30];
	
	public Sound(){
		soundUrl[0] = getClass().getResource("/sounds/theme4.wav");
		soundUrl[1] = getClass().getResource("/sounds/coin2.wav");
		soundUrl[2] = getClass().getResource("/sounds/power2.wav");
		soundUrl[3] = getClass().getResource("/sounds/unlock2.wav");
		soundUrl[4] = getClass().getResource("/sounds/fanfare3.wav");
		}
	
		public void setFile(int i) {
			try {
				
				AudioInputStream ais = AudioSystem.getAudioInputStream(soundUrl[i]);
				clip = AudioSystem.getClip();
				clip.open(ais);
				
			} catch (Exception e) {
				throw new RuntimeException(ERROR_MS_SOUND_CANNOT_LOAD);
			}
		}
		public void play() {
			clip.start();
			
		}
		public void loop() {
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		public void stop() {
			clip.stop();
		}
	}
