package elements;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

import game.Demo;
import game.Parametros;
import screens.BossStage;
import screens.CurrentStage;
import screens.DeathScreen;
import screens.GameScreen;
import screens.GenericStage;
import screens.TitleScreen;
import screens.WinScreen;


public class Trampilla extends Enemy{
	
	private Animation<TextureRegion> imagen;
	private float x;
	private float y;
	private Stage mainStage;
	private GenericStage nivel;
	private GameScreen mapa;
	private int casillaX;
	private int casillaY;
	private Demo game;

	public Trampilla(float x, float y, Stage mainStage, GenericStage nivel, GameScreen mapa, int casillaX,
			int casillaY, Demo game) {
		super(x, y, mainStage, nivel, mapa, casillaX, casillaY);
		this.loadFullAnimation("assets/maps/images/Trampilla.png",1,1,1,false);
		this.x = x;
		this.y = y;
		this.mainStage = mainStage;
		this.nivel = nivel;
		this.mapa = mapa;
		this.casillaX = casillaX;
		this.casillaY = casillaY;
		this.game = game;
		this.setEnabled(false);
	}
	
	public void act(float delta) {
		if(getEnabled()) {
			super.act(delta);
			colisiones();
			this.applyPhysics(delta);
		}
	}

	private void colisiones() {
		if(this.getEnabled()) {
			if(this.mapa.mapaPiso.get(casillaX).get(casillaY) instanceof BossStage) {
				BossStage casilla = (BossStage)this.mapa.mapaPiso.get(casillaX).get(casillaY);
				if(this.overlaps(casilla.player.cuerpo)) {
					if(Parametros.nivel==3) {
						Parametros.vida = 6;
						Parametros.maxVida=6;
						Parametros.maxTotalVida=24;
						Parametros.danyo = 1;
						Parametros.velocidad = 50;
						Parametros.nivel = 1;
						this.game.setScreen(new WinScreen(game));
					}else {
						Parametros.nivel++;
						this.game.setScreen(new GameScreen(game));
					}
				
				}
			}
		}
	}

}
