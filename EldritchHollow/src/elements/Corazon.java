package elements;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

import game.Parametros;
import screens.BossStage;
import screens.CurrentStage;
import screens.GameScreen;
import screens.TreasureStage;

public class Corazon extends Element{

	public GameScreen mapa;
	private Animation<TextureRegion> idle;
	private Random vidaAleatoria;
	private int casillaX;
	private int casillaY;
	private boolean entero;
	private boolean recolectado = false;
	
	public Corazon(float x, float y, Stage s, GameScreen mapa, int casillaX, int casillaY) {
		super(x, y, s);
		vidaAleatoria = new Random();
		
		switch (this.vidaAleatoria.nextInt(2)) {
		case 0:
			this.idle = loadFullAnimation("assets/recursos/medioCorazon.png", 1, 1, 0.1f, true);
			entero=false;
			break;
		case 1:
			this.idle = loadFullAnimation("assets/recursos/corazon.png", 1, 1, 0.1f, true);
			entero=true;
			break;
		}
		this.mapa = mapa;
		this.setRectangle();
		this.casillaX = casillaX;
		this.casillaY = casillaY;
		this.setEnabled(false);
	}
	
	public void act(float delta) {
		super.act(delta);
		if(this.mapa.mapaPiso.get(casillaX).get(casillaY) instanceof CurrentStage) {
			CurrentStage casilla = (CurrentStage)this.mapa.mapaPiso.get(casillaX).get(casillaY);
			if(recolectado) {
				this.setEnabled(false);
			}else if (casilla.completada) {
				this.setEnabled(true);
			}
		}else if(this.mapa.mapaPiso.get(casillaX).get(casillaY) instanceof BossStage) {
			BossStage casilla = (BossStage)this.mapa.mapaPiso.get(casillaX).get(casillaY);
			if(recolectado) {
				this.setEnabled(false);
			}else if (casilla.completada) {
				this.setEnabled(true);
			}
		}else if(this.mapa.mapaPiso.get(casillaX).get(casillaY) instanceof TreasureStage) {
			TreasureStage casilla = (TreasureStage)this.mapa.mapaPiso.get(casillaX).get(casillaY);
			if(recolectado) {
				this.setEnabled(false);
			}else if (casilla.completada) {
				this.setEnabled(true);
			}
		}
		colisiones();
		this.applyPhysics(delta);
		
		
	}
	
	public void colisiones() {
		if(getEnabled()) {
			if(this.mapa.mapaPiso.get(casillaX).get(casillaY) instanceof CurrentStage) {
				CurrentStage casilla = (CurrentStage)this.mapa.mapaPiso.get(casillaX).get(casillaY);
				if(casilla.completada && !recolectado) {
					this.setEnabled(true);
				}
				if(this.overlaps(casilla.player.cuerpo)) {
					if(entero) {
						Parametros.vida+=2;
						recolectado = true;
						this.setEnabled(false);
					}else {
						Parametros.vida++;
						recolectado = true;
						this.setEnabled(false);
					}
				}
			}else if(this.mapa.mapaPiso.get(casillaX).get(casillaY) instanceof BossStage) {
				BossStage casilla = (BossStage)this.mapa.mapaPiso.get(casillaX).get(casillaY);
				if(casilla.completada && !recolectado) {
					this.setEnabled(true);
				}
				if(this.overlaps(casilla.player.cuerpo) && this.getEnabled()) {
					if(entero) {
						if(Parametros.maxVida-Parametros.vida>=2) {
							Parametros.vida+=2;
						}else if(Parametros.maxVida-Parametros.vida==1) {
							Parametros.vida++;
						}
						
						recolectado = true;
						this.setEnabled(false);
					}else {
						if(Parametros.maxVida-Parametros.vida>=1) {
							Parametros.vida++;
						}
						recolectado = true;
						this.setEnabled(false);
					}
				}
			}else if(this.mapa.mapaPiso.get(casillaX).get(casillaY) instanceof TreasureStage) {
				TreasureStage casilla = (TreasureStage)this.mapa.mapaPiso.get(casillaX).get(casillaY);
				if(casilla.completada && !recolectado) {
					this.setEnabled(true);
				}
				if(this.overlaps(casilla.player.cuerpo) && this.getEnabled()) {
					if(entero) {
						if(Parametros.maxVida-Parametros.vida>=2) {
							Parametros.vida+=2;
						}else if(Parametros.maxVida-Parametros.vida==1) {
							Parametros.vida++;
						}
						
						recolectado = true;
						this.setEnabled(false);
					}else {
						if(Parametros.maxVida-Parametros.vida>=1) {
							Parametros.vida++;
						}
						recolectado = true;
						this.setEnabled(false);
					}
				}
			}
		}
	}

}
