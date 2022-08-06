package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	Clip clip;
	URL sounUrl[] = new URL[30];
	
	public Sound(){
		sounUrl[0] = getClass().getResource("/sounds/theme4.wav");
		sounUrl[1] = getClass().getResource("/sounds/coin2.wav");
		sounUrl[2] = getClass().getResource("/sounds/powerup2.wav");
		sounUrl[3] = getClass().getResource("/sounds/unlock2.wav");
		sounUrl[4] = getClass().getResource("/sounds/fanfare3.wav");
		}
	
		public void setFaile(int i) {
			try {
				
				AudioInputStream ais = AudioSystem.getAudioInputStream(sounUrl[i]);
				clip = AudioSystem.getClip();
				clip.open(ais);
				
			} catch (Exception e) {
				// TODO: handle exception
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
