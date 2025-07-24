package elements;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

import game.Parametros;
import screens.BossStage;
import screens.CurrentStage;
import screens.GameScreen;
import screens.TreasureStage;

public class ContenedorCorazon extends Objeto{

	public boolean tocoSuelo;
	public GameScreen mapa;
	private Animation<TextureRegion> idle;
	private int casillaX;
	private int casillaY;
	private boolean recolectado = false;
	public String nombreObjeto;
	
	public ContenedorCorazon(float x, float y, Stage s, GameScreen mapa, int casillaX, int casillaY) {
		super(x, y, s, mapa, casillaY, casillaY);
		this.idle = loadFullAnimation("assets/objetos/contenedorCorazon.png", 1, 1, 0.1f, true);
		this.mapa = mapa;
		this.setPolygon(8);
		this.setEnabled(true);
		this.tocoSuelo = false;
		this.casillaX = casillaX;
		this.casillaY = casillaY;
		this.nombreObjeto = "Reliquia del Vigor";
	}
	
	public void act(float delta) {
		super.act(delta);
			if(recolectado) {
				this.setEnabled(false);
			}else {
				colisiones();
			}
			
			this.applyPhysics(delta);
		
		
	}
	
	public void colisiones() {
		TreasureStage casilla = (TreasureStage)this.mapa.mapaPiso.get(casillaX).get(casillaY);
		if(this.overlaps(casilla.player)) {
			if(Parametros.maxVida<Parametros.maxTotalVida) {
				Parametros.maxVida+=2;
				Parametros.vida+=2;
			}
			recolectado = true;
			Parametros.contenedorCorazonRecogido = true;
		}
	}

}
