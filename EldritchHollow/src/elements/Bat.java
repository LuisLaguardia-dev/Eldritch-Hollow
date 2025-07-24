package elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import game.Parametros;
import managers.AudioManager;
import screens.BossStage;
import screens.CurrentStage;
import screens.GameScreen;
import screens.GenericStage;


public class Bat extends Enemy{

	private Animation<TextureRegion> drcha;
	private Animation<TextureRegion> izqda;
	private Animation<TextureRegion> muerteR;
	private Animation<TextureRegion> muerteL;
	private float tiempoMaxAnimMuerte = 0.5f;
	private float tiempoAnimMuerte;
	private boolean muerteReproduciendose;
	private float walkingSpeed;
	private float chasingSpeed;
	private float distanciaVision;
	private boolean chasing;
	private GenericStage nivel;
	private float tiempoInvulnerable = 0;
	private float tiempoInvulnerabilidad = 0.5f;
	private boolean muerto = false;
	public Element hitboxAtaque;
	private GameScreen mapa;
	private int casillaX;
	private int casillaY;
	
	public Bat(float x, float y, Stage s, GenericStage genericStage, GameScreen mapa, int casillaX, int casillaY) {
		super(x, y, s, genericStage, mapa, casillaX, casillaY);
		// TODO Auto-generated constructor stub
		drcha = this.loadFullAnimation("assets/enemies/batIdleRight.png",1,4,0.1f,true);
		izqda = this.loadFullAnimation("assets/enemies/batIdleLeft.png",1,4,0.1f,true);
		muerteR = this.loadFullAnimation("assets/enemies/batDeathRight.png",1,5,0.1f,false);
		muerteL = this.loadFullAnimation("assets/enemies/batDeathLeft.png",1,5,0.1f,false);
		tiempoAnimMuerte = 0f;
		muerteReproduciendose = false;
		this.vida=4;
		this.danyo=1;
		this.nivel = genericStage;
		
		chasing = false;
		walkingSpeed = 10;
		chasingSpeed = 20;
		this.deceleration = 110;
		distanciaVision = 400;
		this.setPolygon(8, this.getWidth()/2, this.getHeight()/2, this.getWidth()/4, this.getHeight()/4);
		hitboxAtaque = new Element(this.getX(), this.getY(), s, this.getWidth()/3, this.getHeight()/3);
		hitboxAtaque.setPolygon(8);
		this.mapa = mapa;
		this.casillaX = casillaX;
		this.casillaY = casillaY;
	
	}
	public void act(float delta) {
		if(getEnabled()) {
			super.act(delta);
			if(tiempoInvulnerable>=0) {
				tiempoInvulnerable-=delta;
			}
			hitboxAtaque.setPosition(this.getX()+this.getWidth()/3, this.getY()+this.getHeight()/3);
			if(chasing) {
				if(!vision(distanciaVision*4/3)) {
					chasing = false;
				}else {
					chase(delta);
				}
			}else {
				if(vision(distanciaVision)) {
					chasing = true;
				}else {
					patrol(delta);
				}
			}
			
			colisiones();
			animaciones();
			this.tiempoAnimMuerte+=delta;
			if(this.muerto && !this.muerteReproduciendose) {
				this.hitboxAtaque.setEnabled(false);
				this.setEnabled(false);
			}
		}
		
	}
	
	private void colisiones() {
		if(this.mapa.mapaPiso.get(casillaX).get(casillaY) instanceof CurrentStage) {
			CurrentStage casilla = (CurrentStage)this.mapa.mapaPiso.get(casillaX).get(casillaY);
			if(this.hitboxAtaque.overlaps(casilla.player.cuerpo)) {
				casilla.player.danyo(this.danyo);
			}
		}else if(this.mapa.mapaPiso.get(casillaX).get(casillaY) instanceof BossStage) {
			BossStage casilla = (BossStage)this.mapa.mapaPiso.get(casillaX).get(casillaY);
			if(this.hitboxAtaque.overlaps(casilla.player.cuerpo)) {
				casilla.player.danyo(this.danyo);
			}
		}
	}
	private void animaciones() {
		
		if(this.tiempoAnimMuerte>this.tiempoMaxAnimMuerte) {
			this.muerteReproduciendose = false;
		}
		
		if(this.velocity.x>0) {
			this.setAnimation(drcha);
			
			if(this.muerteReproduciendose 
					&& !this.muerteR.isAnimationFinished(this.animationTime-this.tiempoAnimMuerte)){
				this.setAnimation(muerteR);
				this.muerto = true;
			}else if(this.muerteReproduciendose 
					&& this.muerteR.isAnimationFinished(this.animationTime-this.tiempoAnimMuerte)) {
				this.muerteReproduciendose = false;			}
		}else {
			this.setAnimation(izqda);
			
			if(this.muerteReproduciendose 
					&& !this.muerteL.isAnimationFinished(this.animationTime-this.tiempoAnimMuerte)){
				this.setAnimation(muerteL);
				this.muerto = true;
			}else if(this.muerteReproduciendose 
					&& this.muerteL.isAnimationFinished(this.animationTime-this.tiempoAnimMuerte)) {
				this.muerteReproduciendose = false;
			}
		}
		
		
	}
	
	private void chase(float delta) {
		if(this.mapa.mapaPiso.get(casillaX).get(casillaY) instanceof CurrentStage) {
			CurrentStage casilla = (CurrentStage)this.mapa.mapaPiso.get(casillaX).get(casillaY);
			if(!this.muerto) {
				float dirX = (casilla.player.getX()+casilla.player.getWidth()/2)-(this.getX()+this.getWidth()/2);
				float dirY = (casilla.player.getY()+casilla.player.getHeight()/2)-(this.getY()+this.getHeight()/2);
				Vector2 vector=new Vector2(dirX,dirY).nor();
				this.velocity.x=vector.x*this.chasingSpeed;
				this.velocity.y=vector.y*this.chasingSpeed;
			}
		}else if(this.mapa.mapaPiso.get(casillaX).get(casillaY) instanceof BossStage) {
			BossStage casilla = (BossStage)this.mapa.mapaPiso.get(casillaX).get(casillaY);
			if(!this.muerto) {
				float dirX = (casilla.player.getX()+casilla.player.getWidth()/2)-(this.getX()+this.getWidth()/2);
				float dirY = (casilla.player.getY()+casilla.player.getHeight()/2)-(this.getY()+this.getHeight()/2);
				Vector2 vector=new Vector2(dirX,dirY).nor();
				this.velocity.x=vector.x*this.chasingSpeed;
				this.velocity.y=vector.y*this.chasingSpeed;
			}
		}
		
		this.applyPhysics(delta);
	}
	
	private boolean vision(float distanciaVision) {
		if(this.mapa.mapaPiso.get(casillaX).get(casillaY) instanceof CurrentStage) {
			CurrentStage casilla = (CurrentStage)this.mapa.mapaPiso.get(casillaX).get(casillaY);
			return Math.sqrt(Math.pow(casilla.player.getX()-this.getX(), 2) + Math.pow(casilla.player.getY()-this.getY(), 2))<distanciaVision;
		}else if(this.mapa.mapaPiso.get(casillaX).get(casillaY) instanceof BossStage) {
			BossStage casilla = (BossStage)this.mapa.mapaPiso.get(casillaX).get(casillaY);
			return Math.sqrt(Math.pow(casilla.player.getX()-this.getX(), 2) + Math.pow(casilla.player.getY()-this.getY(), 2))<distanciaVision;
		}
		return false;
	}
	
	private void patrol(float delta) {
		acceleration.add(0, 0);
		this.applyPhysics(delta);
	}
	
	public void danyo(int danyo) {
		if(tiempoInvulnerable <= 0) {
			this.vida-=danyo;
			tiempoInvulnerable = tiempoInvulnerabilidad;
			if(this.vida<=0) {
				AudioManager.playSound("assets/audio/sounds/batDie.mp3");
				this.tiempoAnimMuerte = 0f;
				this.muerteReproduciendose = true;
				this.animationTime = 0f;
			}
		}
		
	}
	
}