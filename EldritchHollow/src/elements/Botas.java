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

public class Botas extends Objeto{

	public GameScreen mapa;
	private Animation<TextureRegion> idle;
	private int casillaX;
	private int casillaY;
	private boolean recolectado = false;
	public String nombreObjeto;
	
	public Botas(float x, float y, Stage s, GameScreen mapa, int casillaX, int casillaY) {
		super(x, y, s, mapa, casillaY, casillaY);
		this.idle = loadFullAnimation("assets/objetos/Botas.png", 1, 1, 0.1f, true);
		this.mapa = mapa;
		this.setPolygon(8);
		this.setEnabled(true);
		this.casillaX = casillaX;
		this.casillaY = casillaY;
		this.nombreObjeto = "Zapatos de Zephyr";
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
			Parametros.velocidad+=25;
			for(int i=0;i<this.mapa.mapaPiso.size();i++) {
				for(int j=0;j<this.mapa.mapaPiso.get(i).size();j++) {
					if(this.mapa.mapaPiso.get(i).get(j) instanceof CurrentStage) {
						CurrentStage habitacion = (CurrentStage)this.mapa.mapaPiso.get(i).get(j);
						habitacion.player.velocidad=Parametros.velocidad;
					}else if(this.mapa.mapaPiso.get(i).get(j) instanceof BossStage) {
						BossStage habitacion = (BossStage)this.mapa.mapaPiso.get(i).get(j);
						habitacion.player.velocidad=Parametros.velocidad;
					}else if(this.mapa.mapaPiso.get(i).get(j) instanceof TreasureStage) {
						TreasureStage habitacion = (TreasureStage)this.mapa.mapaPiso.get(i).get(j);
						habitacion.player.velocidad=Parametros.velocidad;
					}
					
				}
			}
			recolectado = true;
			Parametros.BotasRecogido = true;
		}
	}

}
