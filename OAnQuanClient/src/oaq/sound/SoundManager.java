package oaq.sound;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class SoundManager {

	private boolean turnSoundBackGround;
	private boolean turnSound;
	private String backgroundMusic;

	public SoundManager(String backgroundMusic) {
		this.backgroundMusic = backgroundMusic;
		this.turnSound = true;
		playSoundBackGround();
	}

	public void playSoundBackGround() {
		new Thread(new Runnable() {
			File audioFile = new File(backgroundMusic);
			boolean playCompleted = false;

			@Override
			public void run() {
				turnSoundBackGround = true;
				try {
					AudioInputStream audioStream = AudioSystem
							.getAudioInputStream(audioFile);
					AudioFormat format = audioStream.getFormat();
					DataLine.Info info = new DataLine.Info(Clip.class, format);
					Clip audioClip = (Clip) AudioSystem.getLine(info);
					audioClip.addLineListener(new LineListener() {
						@Override
						public void update(LineEvent event) {
							LineEvent.Type type = event.getType();
							if (type == LineEvent.Type.STOP) {
								playCompleted = true;
							}
						}
					});
					audioClip.open(audioStream);
					audioClip.start();
					while (!playCompleted) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException ex) {
						}
					}
					audioClip.loop(-1);
					while (turnSoundBackGround) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException ex) {
						}
					}
					audioClip.close();
				} catch (Exception ex) {
				}
			}
		}).start();
	}

	public void stopPlaySoundBackGround() {
		turnSoundBackGround = false;
	}

	public void playSound(final String strName) {
		if (turnSound) {
			new Thread(new Runnable() {
				String path = "sounds/" + strName + ".wav";
				File audioFile = new File(path);
				boolean playCompleted = false;

				@Override
				public void run() {
					try {
						AudioInputStream audioStream = AudioSystem
								.getAudioInputStream(audioFile);
						AudioFormat format = audioStream.getFormat();
						DataLine.Info info = new DataLine.Info(Clip.class,
								format);
						Clip audioClip = (Clip) AudioSystem.getLine(info);
						audioClip.addLineListener(new LineListener() {
							@Override
							public void update(LineEvent event) {
								LineEvent.Type type = event.getType();
								if (type == LineEvent.Type.STOP) {
									playCompleted = true;
								}
							}
						});
						audioClip.open(audioStream);
						audioClip.start();
						while (!playCompleted) {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException ex) {
							}
						}
						audioClip.close();
					} catch (Exception ex) {
					}
				}
			}).start();
		}
	}

	public void changeTurnOffSound() {
		turnSound = !turnSound;
	}

	public boolean isTurnSoundBackGround() {
		return turnSoundBackGround;
	}

	public boolean isTurnSound() {
		return turnSound;
	}
}