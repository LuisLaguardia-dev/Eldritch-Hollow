package managers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public final class ResourceManager {
	private ResourceManager() {}
	public static AssetManager assets=new AssetManager();
	public static LabelStyle buttonStyle;
	public static TextButtonStyle textButtonStyle;
	
	
	
	
	public static void loadAllResources(){

		//mapas
		assets.setLoader(TiledMap.class, new TmxMapLoader());
		assets.load("assets/maps/mapa0.tmx", TiledMap.class);
		assets.load("assets/maps/bossD.tmx", TiledMap.class);
		assets.load("assets/maps/bossL.tmx", TiledMap.class);
		assets.load("assets/maps/bossR.tmx", TiledMap.class);
		assets.load("assets/maps/bossU.tmx", TiledMap.class);
		assets.load("assets/maps/casillaAll.tmx", TiledMap.class);
		assets.load("assets/maps/casillaAll-Db.tmx", TiledMap.class);
		assets.load("assets/maps/casillaAll-Ub.tmx", TiledMap.class);
		assets.load("assets/maps/casillaAll-Lb.tmx", TiledMap.class);
		assets.load("assets/maps/casillaAll-Rb.tmx", TiledMap.class);
		assets.load("assets/maps/casillaD.tmx", TiledMap.class);
		assets.load("assets/maps/casillaD-T.tmx", TiledMap.class);
		assets.load("assets/maps/casillaL.tmx", TiledMap.class);
		assets.load("assets/maps/casillaL-T.tmx", TiledMap.class);
		assets.load("assets/maps/casillaLD.tmx", TiledMap.class);
		assets.load("assets/maps/casillaLD-Lb.tmx", TiledMap.class);
		assets.load("assets/maps/casillaLD-Db.tmx", TiledMap.class);
		assets.load("assets/maps/casillaLDR.tmx", TiledMap.class);
		assets.load("assets/maps/casillaLDR-Lb.tmx", TiledMap.class);
		assets.load("assets/maps/casillaLDR-Db.tmx", TiledMap.class);
		assets.load("assets/maps/casillaLDR-Rb.tmx", TiledMap.class);
		assets.load("assets/maps/casillaLR.tmx", TiledMap.class);
		assets.load("assets/maps/casillaLR-Lb.tmx", TiledMap.class);
		assets.load("assets/maps/casillaLR-Rb.tmx", TiledMap.class);
		assets.load("assets/maps/casillaR.tmx", TiledMap.class);
		assets.load("assets/maps/casillaR-T.tmx", TiledMap.class);
		assets.load("assets/maps/casillaRD.tmx", TiledMap.class);
		assets.load("assets/maps/casillaRD-Rb.tmx", TiledMap.class);
		assets.load("assets/maps/casillaRD-Db.tmx", TiledMap.class);
		assets.load("assets/maps/casillaU.tmx", TiledMap.class);
		assets.load("assets/maps/casillaU-T.tmx", TiledMap.class);
		assets.load("assets/maps/casillaUD.tmx", TiledMap.class);
		assets.load("assets/maps/casillaUD-Ub.tmx", TiledMap.class);
		assets.load("assets/maps/casillaUD-Db.tmx", TiledMap.class);
		assets.load("assets/maps/casillaUDR.tmx", TiledMap.class);
		assets.load("assets/maps/casillaUDR-Ub.tmx", TiledMap.class);
		assets.load("assets/maps/casillaUDR-Db.tmx", TiledMap.class);
		assets.load("assets/maps/casillaUDR-Rb.tmx", TiledMap.class);
		assets.load("assets/maps/casillaUL.tmx", TiledMap.class);
		assets.load("assets/maps/casillaUL-Ub.tmx", TiledMap.class);
		assets.load("assets/maps/casillaUL-Lb.tmx", TiledMap.class);
		assets.load("assets/maps/casillaULD.tmx", TiledMap.class);
		assets.load("assets/maps/casillaULD-Ub.tmx", TiledMap.class);
		assets.load("assets/maps/casillaULD-Lb.tmx", TiledMap.class);
		assets.load("assets/maps/casillaULD-Db.tmx", TiledMap.class);
		assets.load("assets/maps/casillaULR.tmx", TiledMap.class);
		assets.load("assets/maps/casillaULR-Ub.tmx", TiledMap.class);
		assets.load("assets/maps/casillaULR-Lb.tmx", TiledMap.class);
		assets.load("assets/maps/casillaULR-Rb.tmx", TiledMap.class);
		assets.load("assets/maps/casillaUR.tmx", TiledMap.class);
		assets.load("assets/maps/casillaUR-Ub.tmx", TiledMap.class);
		assets.load("assets/maps/casillaUR-Rb.tmx", TiledMap.class);
		assets.load("assets/maps/mapa1.tmx", TiledMap.class);
		assets.load("assets/maps/mapa2.tmx", TiledMap.class);
        //elementos de mapa
        //assets.load("maps/Images/arbol.png", Texture.class);
        assets.load("assets/maps/images/Trampilla.png", Texture.class);
        assets.load("assets/maps/images/boton.png", Texture.class);
        //enemigos
        assets.load("assets/enemies/disparoEnemigo.png", Texture.class);
        //Bat
        assets.load("assets/enemies/batIdleRight.png", Texture.class);
        assets.load("assets/enemies/batIdleLeft.png", Texture.class);
        assets.load("assets/enemies/batDeathRight.png", Texture.class);
        assets.load("assets/enemies/batDeathLeft.png", Texture.class);
        //Bee
        assets.load("assets/enemies/beeWalkRight.png", Texture.class);
        assets.load("assets/enemies/beeWalkLeft.png", Texture.class);
        assets.load("assets/enemies/beeWalkUp.png", Texture.class);
        assets.load("assets/enemies/beeWalkDown.png", Texture.class);
        assets.load("assets/enemies/beeDeathRight.png", Texture.class);
        assets.load("assets/enemies/beeDeathLeft.png", Texture.class);
        assets.load("assets/enemies/beeDeathUp.png", Texture.class);
        assets.load("assets/enemies/beeDeathDown.png", Texture.class);
        //Wonwon
        assets.load("assets/enemies/wonwonRightIdle.png", Texture.class);
        assets.load("assets/enemies/wonwonLeftIdle.png", Texture.class);
        assets.load("assets/enemies/wonwonDownIdle.png", Texture.class);
        assets.load("assets/enemies/wonwonUpIdle.png", Texture.class);
        //GreenSlime
        assets.load("assets/enemies/greenSlimeRightIdle.png", Texture.class);
        assets.load("assets/enemies/greenSlimeLeftIdle.png", Texture.class);
        assets.load("assets/enemies/greenSlimeRightMove.png", Texture.class);
        assets.load("assets/enemies/greenSlimeLeftMove.png", Texture.class);
        //RedSlime
        assets.load("assets/enemies/redSlimeUpIdle.png", Texture.class);
        assets.load("assets/enemies/redSlimeDownIdle.png", Texture.class);
        assets.load("assets/enemies/redSlimeUpMove.png", Texture.class);
        assets.load("assets/enemies/redSlimeDownMove.png", Texture.class);
        //Ratfolk
        assets.load("assets/enemies/RatfolkRightIdle.png", Texture.class);
        assets.load("assets/enemies/RatfolkLeftIdle.png", Texture.class);
        assets.load("assets/enemies/RatfolkRightDeath.png", Texture.class);
        assets.load("assets/enemies/RatfolkLeftDeath.png", Texture.class);
        assets.load("assets/enemies/RatfolkRightShot.png", Texture.class);
        assets.load("assets/enemies/RatfolkLeftShot.png", Texture.class);
        //GoblinBeast
        assets.load("assets/enemies/GoblinBeastRightWalk.png", Texture.class);
        assets.load("assets/enemies/GoblinBeastLeftWalk.png", Texture.class);
        assets.load("assets/enemies/GoblinBeastUpWalk.png", Texture.class);
        assets.load("assets/enemies/GoblinBeastDownWalk.png", Texture.class);
        assets.load("assets/enemies/GoblinBeastUpDeath.png", Texture.class);
        assets.load("assets/enemies/GoblinBeastDownDeath.png", Texture.class);
        assets.load("assets/enemies/GoblinBeastRightDeath.png", Texture.class);
        assets.load("assets/enemies/GoblinBeastLeftDeath.png", Texture.class);
        assets.load("assets/enemies/GoblinBeastUpMeleeAttack.png", Texture.class);
        assets.load("assets/enemies/GoblinBeastDownMeleeAttack.png", Texture.class);
        assets.load("assets/enemies/GoblinBeastRightMeleeAttack.png", Texture.class);
        assets.load("assets/enemies/GoblinBeastLeftMeleeAttack.png", Texture.class);
        assets.load("assets/enemies/GoblinBeastUpRangedAttack.png", Texture.class);
        assets.load("assets/enemies/GoblinBeastDownRangedAttack.png", Texture.class);
        assets.load("assets/enemies/GoblinBeastRightRangedAttack.png", Texture.class);
        assets.load("assets/enemies/GoblinBeastLeftRangedAttack.png", Texture.class);
        //Cultist
        assets.load("assets/enemies/CultistRightWalk.png", Texture.class);
        assets.load("assets/enemies/CultistLeftWalk.png", Texture.class);
        assets.load("assets/enemies/CultistUpWalk.png", Texture.class);
        assets.load("assets/enemies/CultistDownWalk.png", Texture.class);
        assets.load("assets/enemies/CultistUpDeath.png", Texture.class);
        assets.load("assets/enemies/CultistDownDeath.png", Texture.class);
        assets.load("assets/enemies/CultistRightDeath.png", Texture.class);
        assets.load("assets/enemies/CultistLeftDeath.png", Texture.class);
        assets.load("assets/enemies/CultistUpAttack.png", Texture.class);
        assets.load("assets/enemies/CultistDownAttack.png", Texture.class);
        assets.load("assets/enemies/CultistRightAttack.png", Texture.class);
        assets.load("assets/enemies/CultistLeftAttack.png", Texture.class);
        //Sorceress
        assets.load("assets/enemies/SorceressRightWalk.png", Texture.class);
        assets.load("assets/enemies/SorceressLeftWalk.png", Texture.class);
        assets.load("assets/enemies/SorceressUpWalk.png", Texture.class);
        assets.load("assets/enemies/SorceressDownWalk.png", Texture.class);
        assets.load("assets/enemies/SorceressUpDeath.png", Texture.class);
        assets.load("assets/enemies/SorceressDownDeath.png", Texture.class);
        assets.load("assets/enemies/SorceressRightDeath.png", Texture.class);
        assets.load("assets/enemies/SorceressLeftDeath.png", Texture.class);
        assets.load("assets/enemies/SorceressUpShot.png", Texture.class);
        assets.load("assets/enemies/SorceressDownShot.png", Texture.class);
        assets.load("assets/enemies/SorceressRightShot.png", Texture.class);
        assets.load("assets/enemies/SorceressLeftShot.png", Texture.class);
        assets.load("assets/enemies/SorceressUpAround.png", Texture.class);
        assets.load("assets/enemies/SorceressDownAround.png", Texture.class);
        assets.load("assets/enemies/SorceressRightAround.png", Texture.class);
        assets.load("assets/enemies/SorceressLeftAround.png", Texture.class);
        assets.load("assets/enemies/SorceressUpArea.png", Texture.class);
        assets.load("assets/enemies/SorceressDownArea.png", Texture.class);
        assets.load("assets/enemies/SorceressRightArea.png", Texture.class);
        assets.load("assets/enemies/SorceressLeftArea.png", Texture.class);
        //jugador
        assets.load("assets/player/aitorBodyWalkFront.png",Texture.class);
        assets.load("assets/player/aitorHeadFront.png",Texture.class);
        assets.load("assets/player/aitorBodyIdleFront.png",Texture.class);
        assets.load("assets/player/aitorBodyWalkBack.png",Texture.class);
        assets.load("assets/player/aitorHeadBack.png",Texture.class);
        assets.load("assets/player/aitorBodyWalkLeft.png",Texture.class);
        assets.load("assets/player/aitorBodyWalkRight.png",Texture.class);
        assets.load("assets/player/aitorHeadLeft.png",Texture.class);
        assets.load("assets/player/aitorHeadRight.png",Texture.class);
        assets.load("assets/player/disparo.png",Texture.class);
        //recusos
        assets.load("assets/recursos/corazon.png",Texture.class);
        assets.load("assets/recursos/medioCorazon.png",Texture.class);
        //objetos
        assets.load("assets/objetos/contenedorCorazon.png",Texture.class);
        assets.load("assets/objetos/Espada.png",Texture.class);
        assets.load("assets/objetos/Botas.png",Texture.class);
        //Audio
        assets.load("assets/audio/music/musicaGeneral.mp3", Music.class);
        assets.load("assets/audio/music/musicaJefe.mp3", Music.class);
        assets.load("assets/audio/sounds/playerShoot.mp3", Sound.class);
        assets.load("assets/audio/sounds/wonwonShoot.mp3", Sound.class);
        assets.load("assets/audio/sounds/goblinBeastShoot.mp3", Sound.class);
        assets.load("assets/audio/sounds/sorceressShoot.mp3", Sound.class);
        assets.load("assets/audio/sounds/ratfolkShoot.mp3", Sound.class);
        assets.load("assets/audio/sounds/slimeStartMove.mp3", Sound.class);
        assets.load("assets/audio/sounds/slimeHitWall.mp3", Sound.class);
        assets.load("assets/audio/sounds/CultistShot.mp3", Sound.class);
        assets.load("assets/audio/sounds/batDie.mp3", Sound.class);
        assets.load("assets/audio/sounds/beeDie.mp3", Sound.class);
        assets.load("assets/audio/sounds/WonwonDie.mp3", Sound.class);
        assets.load("assets/audio/sounds/slimeDie.mp3", Sound.class);
        assets.load("assets/audio/sounds/RatfolkDie.mp3", Sound.class);
        assets.load("assets/audio/sounds/goblinbeastDie.mp3", Sound.class);
        assets.load("assets/audio/sounds/CultistDie.mp3", Sound.class);
        assets.load("assets/audio/sounds/SorceressDie.mp3", Sound.class);
        assets.load("assets/audio/sounds/playerHurt.mp3", Sound.class);
        //Interfaz
        assets.load("assets/interfaz/VidaEntera.png",Texture.class);
        assets.load("assets/interfaz/VidaMedia.png",Texture.class);
        //UI
        assets.load("assets/ui/rojo.jpg", Texture.class);
        assets.load("assets/ui/morado.jpg", Texture.class);
 
	//a�adir m�s elementos
        
        
		
	
	}
	
	public static boolean update(){
		return assets.update();
	}
	public static void botones() {
		
		//estilo para botones
        FreeTypeFontGenerator ftfg= new FreeTypeFontGenerator(Gdx.files.internal("assets/sans.ttf"));
		FreeTypeFontParameter ftfp= new FreeTypeFontParameter();
		
		ftfp.size=36;
		ftfp.color=Color.WHITE;
		ftfp.borderColor=Color.BLACK;
		ftfp.borderWidth=2;
		
		BitmapFont fuentePropia=ftfg.generateFont(ftfp);
		buttonStyle=new LabelStyle();
		buttonStyle.font=fuentePropia;
		textButtonStyle=new TextButtonStyle();
		Texture buttonText = ResourceManager.getTexture("assets/maps/images/boton.png");
		NinePatch buttonPatch = new NinePatch(buttonText);
		textButtonStyle.up=new NinePatchDrawable(buttonPatch);
		textButtonStyle.font=fuentePropia;
		
		
	}
	
	/*public static TextureAtlas getAtlas(String path){
		return assets.get(path, TextureAtlas.class);
		
	}*/
	
	public static Texture getTexture(String path) {
		return assets.get(path, Texture.class);
	}
	
	public static Music getMusic(String path){
		return assets.get(path, Music.class);
	}
	
	public static Sound getSound(String path)
	{
		return assets.get(path, Sound.class);
	}
	
	public static TiledMap getMap(String path){
		return assets.get(path, TiledMap.class);
	}

	public static void dispose(){
		assets.dispose();
	}
}
