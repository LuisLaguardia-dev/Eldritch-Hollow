package elements;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
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


public class Ratfolk extends Enemy{

	private Animation<TextureRegion> drcha;
	private Animation<TextureRegion> izqda;
	private Animation<TextureRegion> shotD;
	private Animation<TextureRegion> shotI;
	private float tiempoMaxAnimShot = 0.7f;
	private float tiempoAnimShot;
	private boolean shotReproduciendose;
	private Animation<TextureRegion> deathD;
	private Animation<TextureRegion> deathI;
	private float tiempoMaxAnimMuerte = 0.5f;
	private float tiempoAnimMuerte;
	private boolean muerteReproduciendose;
	private GenericStage nivel;
	private float tiempoInvulnerable = 0;
	private float tiempoInvulnerabilidad = 0.5f;
	public Element hitboxAtaque;
	private GameScreen mapa;
	private int casillaX;
	private int casillaY;
	private float cadencia = 0.8f;
	private float tiempoDisparo = cadencia;
	private int numBalas = 8;
	private ArrayList<Bala> cargador;
	private int balaActual=0;
	private boolean muerto = false;
	private Vector3 m3d;
	private boolean disparar = false;
	
	public Ratfolk(float x, float y, Stage s, GenericStage genericStage, GameScreen mapa, int casillaX, int casillaY) {
		super(x, y, s, genericStage, mapa, casillaX, casillaY);
		// TODO Auto-generated constructor stub
		drcha = this.loadFullAnimation("assets/enemies/RatfolkRightIdle.png",1,8,0.1f,true);
		izqda = this.loadFullAnimation("assets/enemies/RatfolkLeftIdle.png",1,8,0.1f,true);
		shotD = this.loadFullAnimation("assets/enemies/RatfolkRightShot.png",1,7,0.1f,false);
		shotI = this.loadFullAnimation("assets/enemies/RatfolkLeftShot.png",1,7,0.1f,false);
		deathD = this.loadFullAnimation("assets/enemies/RatfolkRightDeath.png",1,5,0.1f,false);
		deathI = this.loadFullAnimation("assets/enemies/RatfolkLeftDeath.png",1,5,0.1f,false);
		this.setAnimation(drcha);
		this.vida=3;
		this.danyo=1;
		this.nivel = genericStage;
		this.setPolygon(8, this.getWidth()/2, this.getHeight()/2, this.getWidth()/4, this.getHeight()/4);
		hitboxAtaque = new Element(this.getX(), this.getY(), s, this.getWidth()/3, this.getHeight()/3);
		hitboxAtaque.setPolygon(8);
		this.mapa = mapa;
		this.casillaX = casillaX;
		this.casillaY = casillaY;
		this.cargador = new ArrayList<>();
		for(int i=0;i<numBalas;i++) {
			this.cargador.add(new Bala(0,0, s,this.nivel,false, false, mapa, casillaX, casillaY));
		}
		tiempoAnimMuerte = 0f;
		muerteReproduciendose = false;
		tiempoAnimShot = 0f;
		shotReproduciendose = false;
		
		m3d = new Vector3();
		this.setX(this.getX()+6);
		this.setY(this.getY()+6);
	
	}
	public void act(float delta) {
		if(getEnabled()) {
			super.act(delta);
			if(tiempoInvulnerable>=0) {
				tiempoInvulnerable-=delta;
			}
			if(tiempoDisparo>0) {
				tiempoDisparo-=delta;
			}else if(!disparar && !muerto){
				this.tiempoAnimShot = 0f;
				this.shotReproduciendose = true;
				this.animationTime = 0f;
				disparar = true;
			}
			hitboxAtaque.setPosition(this.getX()+this.getWidth()/3, this.getY()+this.getHeight()/3);
			colisiones();
			animaciones();
			this.tiempoAnimMuerte+=delta;
			this.tiempoAnimShot+=delta;
			if(this.muerto && !this.muerteReproduciendose) {
				this.hitboxAtaque.setEnabled(false);
				this.setEnabled(false);
			}
		}
		
	}
	
	private void disparo() {
		AudioManager.playSound("assets/audio/sounds/ratfolkShoot.mp3");
		disparar=false;
		if(this.mapa.mapaPiso.get(casillaX).get(casillaY) instanceof CurrentStage) {
			CurrentStage casilla = (CurrentStage)this.mapa.mapaPiso.get(casillaX).get(casillaY);
			m3d.x=casilla.player.cuerpo.getX()+casilla.player.cuerpo.getWidth()/2;
			m3d.y=casilla.player.cuerpo.getY()+casilla.player.cuerpo.getHeight()/2;
			m3d.z=0;
			
			float x=m3d.x-(this.hitboxAtaque.getX()+this.hitboxAtaque.getWidth()/2);
			float y=m3d.y-(this.hitboxAtaque.getY()+this.hitboxAtaque.getHeight()/2);
			Vector2 vector=new Vector2(x,y).nor();
			
			cargador.get(balaActual).disparar(vector.x, vector.y, this.getX()+this.getWidth()/2, this.getY()+this.getHeight()/2);
			balaActual=(balaActual+1)%numBalas;
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
		if(this.tiempoAnimShot>this.tiempoMaxAnimShot) {
			this.shotReproduciendose = false;
		}
		
		if(this.mapa.mapaPiso.get(casillaX).get(casillaY) instanceof CurrentStage) {
			CurrentStage casilla = (CurrentStage)this.mapa.mapaPiso.get(casillaX).get(casillaY);
			if(shotReproduciendose && !muerteReproduciendose) {
				if(Math.abs(this.getX())>Math.abs(casilla.player.getX()) 
						&& !this.shotI.isAnimationFinished(this.animationTime-this.tiempoAnimShot)) {
					this.setAnimation(shotI);
					if((this.tiempoAnimShot>=0.6f && this.tiempoAnimShot<=0.7f)) {
						if(disparar) {
							disparo();
						}
						
					}
				}else if(Math.abs(this.getX())<Math.abs(casilla.player.getX())
						&& !this.shotD.isAnimationFinished(this.animationTime-this.tiempoAnimShot)) {
					this.setAnimation(shotD);
					if((this.tiempoAnimShot>=0.6f && this.tiempoAnimShot<=0.7f)) {
						if(disparar) {
							disparo();
						}
					}
				}
			}else if(muerteReproduciendose) {
				if(Math.abs(this.getX())>Math.abs(casilla.player.getX()) 
						&& !this.deathI.isAnimationFinished(this.animationTime-this.tiempoAnimMuerte)) {
					this.setAnimation(deathI);
					this.muerto = true;
				}else if(Math.abs(this.getX())<Math.abs(casilla.player.getX())
						&& !this.deathD.isAnimationFinished(this.animationTime-this.tiempoAnimMuerte)) {
					this.setAnimation(deathD);
					this.muerto = true;
				}
			}else {
				if(Math.abs(this.getX())>Math.abs(casilla.player.getX())) {
					this.setAnimation(izqda);
					
				}else if(Math.abs(this.getX())<Math.abs(casilla.player.getX())) {
					this.setAnimation(drcha);
				}
			}
		}
		
	}
	
	public void danyo(int danyo) {
		if(tiempoInvulnerable <= 0) {
			this.vida-=danyo;
			tiempoInvulnerable = tiempoInvulnerabilidad;
			if(this.vida<=0) {
				AudioManager.playSound("assets/audio/sounds/RatfolkDie.mp3");
				this.tiempoAnimMuerte = 0f;
				this.muerteReproduciendose = true;
				this.animationTime = 0f;
			}
		}
		
	}
	
}