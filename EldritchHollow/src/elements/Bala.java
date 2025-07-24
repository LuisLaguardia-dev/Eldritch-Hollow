package elements;

import com.badlogic.gdx.scenes.scene2d.Stage;

import game.Parametros;
import screens.BossStage;
import screens.CurrentStage;
import screens.GameScreen;
import screens.GenericStage;
import screens.TreasureStage;

public class Bala extends Element{
private GenericStage nivel;
public int danoFromPlayer;
public int danoFromEnemy;
public float velocidad;
public float duracionBala;
private float tiempoActiva;
private boolean balaPlayer;
private GameScreen mapa;
private int casillaX;
private int casillaY;

	public Bala(float x, float y, Stage s, GenericStage nivel, boolean balaPlayer,boolean esJefe, GameScreen mapa, int casillaX, int casillaY) {
		super(x, y, s);
		this.nivel = nivel;
		this.balaPlayer=balaPlayer;
		if(this.balaPlayer) {
			this.loadFullAnimation("assets/player/disparo.png", 1,1,1,false);
			this.danoFromPlayer=Parametros.danyo;
			this.duracionBala=1f;
		}else {
			this.loadFullAnimation("assets/enemies/disparoEnemigo.png", 1,1,1,false);
			if(esJefe) {
				this.danoFromEnemy=2;
				this.duracionBala=6f;
			}else {
				this.danoFromEnemy=1;
				this.duracionBala=6f;
			}
		}
		this.setRectangle();
		//this.setPolygon(10);
		// TODO Auto-generated constructor stub
		this.setEnabled(false);
		this.velocidad=70;
		this.casillaX = casillaX;
		this.casillaY = casillaY;
		this.mapa = mapa;
	}
	
	public void act(float delta) {
		if(this.getEnabled()) {
			super.act(delta);
			this.applyPhysics(delta);
			if(this.tiempoActiva>=this.duracionBala) {
				this.setEnabled(false);
			}else {
				this.tiempoActiva+=delta;
			}
			colide();
			
		}
		
		
	}
	private void colide() {
		
		if(this.mapa.mapaPiso.get(this.casillaX).get(this.casillaY) instanceof CurrentStage) {
			CurrentStage casilla = (CurrentStage) this.mapa.mapaPiso.get(this.casillaX).get(this.casillaY);
			if(balaPlayer) {
				for(Enemy e:casilla.enemigos) {
					if(e.getEnabled() && this.overlaps(e) && e.vida>0) {
						e.danyo(this.danoFromPlayer);
						this.setEnabled(false);
					}
				}
			}
			else {
				if( this.overlaps(casilla.player)) {
					casilla.player.danyo(this.danoFromEnemy);
					this.setEnabled(false);
				}
			}
			 for(Solid s:casilla.paredes) {
				 if( this.overlaps(s)) {
					 this.setEnabled(false);
				 }
			 }
		}else if(this.mapa.mapaPiso.get(this.casillaX).get(this.casillaY) instanceof BossStage) {
			BossStage casilla = (BossStage) this.mapa.mapaPiso.get(this.casillaX).get(this.casillaY);
			if(balaPlayer) {
				for(Enemy e:casilla.enemigos) {
					if(e.getEnabled() && this.overlaps(e) && e.vida>0) {
						e.danyo(this.danoFromPlayer);
						this.setEnabled(false);
					}
				}
			}
			else {
				if( this.overlaps(casilla.player)) {
					casilla.player.danyo(this.danoFromEnemy);
					this.setEnabled(false);
				}
			}
			 for(Solid s:casilla.paredes) {
				 if( this.overlaps(s)) {
					 this.setEnabled(false);
				 }
			 }
		}else if(this.mapa.mapaPiso.get(this.casillaX).get(this.casillaY) instanceof TreasureStage) {
			TreasureStage casilla = (TreasureStage) this.mapa.mapaPiso.get(this.casillaX).get(this.casillaY);
			if(!balaPlayer) {				 
				if( this.overlaps(casilla.player)) {
					casilla.player.danyo(this.danoFromEnemy);
					this.setEnabled(false);
					
				} 
			}
			 for(Solid s:casilla.paredes) {
				 if( this.overlaps(s)) {
					 this.setEnabled(false);
				 }
			 }
		}
		
		
		
		 
	}
	
	public void disparar(float dirX, float dirY, float x, float y) {
		this.setEnabled(true);
		this.tiempoActiva=0;
		if(this.mapa.mapaPiso.get(casillaX).get(casillaY) instanceof CurrentStage) {
			CurrentStage casilla = (CurrentStage)this.mapa.mapaPiso.get(casillaX).get(casillaY);
			this.setPosition(x,y);
			if(balaPlayer) {
				this.velocity.x=dirX*velocidad+casilla.player.velocity.x*1.1f;
				this.velocity.y=dirY*velocidad+casilla.player.velocity.y*1.1f;
			}else {
				this.velocity.x=dirX*velocidad;
				this.velocity.y=dirY*velocidad;
			}
		}else if(this.mapa.mapaPiso.get(casillaX).get(casillaY) instanceof BossStage) {
			BossStage casilla = (BossStage)this.mapa.mapaPiso.get(casillaX).get(casillaY);
			this.setPosition(x,y);
			if(balaPlayer) {
				this.velocity.x=dirX*velocidad+casilla.player.velocity.x*1.1f;
				this.velocity.y=dirY*velocidad+casilla.player.velocity.y*1.1f;
			}else {
				this.velocity.x=dirX*velocidad;
				this.velocity.y=dirY*velocidad;
			}
		}
		else if(this.mapa.mapaPiso.get(casillaX).get(casillaY) instanceof TreasureStage) {
			TreasureStage casilla = (TreasureStage)this.mapa.mapaPiso.get(casillaX).get(casillaY);
			this.setPosition(x,y);
			if(balaPlayer) {
				this.velocity.x=dirX*velocidad+casilla.player.velocity.x*1.1f;
				this.velocity.y=dirY*velocidad+casilla.player.velocity.y*1.1f;
			}else {
				this.velocity.x=dirX*velocidad;
				this.velocity.y=dirY*velocidad;
			}
		}
		
	}

}