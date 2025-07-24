package managers;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import game.Parametros;

public class AudioManager {
	public static Music currentMusic;
	public static String currentMusicName="";
	public static Sound sound;
	
	
	
public static void playMusic(String path) {
	if( !currentMusicName.equals(path)) {
		if(currentMusic!=null)
		currentMusic.stop();
		
		currentMusicName=path;
		currentMusic=ResourceManager.getMusic(path);
		currentMusic.setVolume(Parametros.musicVolume);
		currentMusic.setLooping(true);
		currentMusic.play();
		
		
	}
	

	
	
	
}
	
public static void playSound(String path) {
	sound=ResourceManager.getSound(path);
	sound.play(Parametros.soundVolume);
	
}

public static void applyVolume() {
	
	currentMusic.setVolume(Parametros.musicVolume);
	
}
	
	

}
