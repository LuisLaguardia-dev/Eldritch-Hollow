package managers;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import game.Parametros;

public class SoundManager {
static Music currentMusic;
static String currentMusicName;
static Sound sound;

static public void playMusic(String path) {
	if (currentMusicName!=path) {
		currentMusic.stop();
		currentMusicName=path;
		currentMusic=ResourceManager.getMusic(path);
		currentMusic.setVolume(Parametros.musicVolume);
		currentMusic.setLooping(true);
		currentMusic.play();
		
	}
	
}

static public void playSound(String path) {
	
	
		sound=ResourceManager.getSound(path);
		sound.play(Parametros.soundVolume);
		
	
	
}







}
