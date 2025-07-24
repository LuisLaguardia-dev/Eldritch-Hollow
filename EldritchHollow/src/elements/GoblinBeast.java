package elements;

import java.util.Random;

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


public class GoblinBeast extends Enemy{

	private Animation<TextureRegion> drcha;
	private Animation<TextureRegion> izqda;
	private Animation<TextureRegion> arriba;
	private Animation<TextureRegion> abajo;
	private Animation<TextureRegion> muerteR;
	private Animation<TextureRegion> muerteL;
	private Animation<TextureRegion> muerteU;
	private Animation<TextureRegion> muerteD;
	private float tiempoMaxAnimMuerte = 1.0f;
	private float tiempoAnimMuerte;
	private boolean muerteReproduciendose;
	private Animation<TextureRegion> meleeR;
	private Animation<TextureRegion> meleeL;
	private Animation<TextureRegion> meleeU;
	private Animation<TextureRegion> meleeD;
	private float tiempoMaxAnimMelee = 0.8f;
	private float tiempoAnimMelee;
	private boolean meleeReproduciendose;
	private Animation<TextureRegion> rangedR;
	private Animation<TextureRegion> rangedL;
	private Animation<TextureRegion> rangedU;
	private Animation<TextureRegion> rangedD;
	private float tiempoMaxAnimRanged = 1.4f;
	private float tiempoAnimRanged;
	private boolean rangedReproduciendose;
	
	private int numBalas=4;
	public Array<Bala> cargador;
	public float cadencia=0.1f;
	private float tiempoDisparo=0;
	private int balaActual=0;
	private int direccionX=1;
	private int direccionY=1;
	private Vector3 m3d;
	
	private float chasingSpeed;
	private float tiempoInvulnerable = 0;
	private float tiempoInvulnerabilidad = 0.5f;
	private boolean muerto = false;
	public Element hitboxAtaqueCuerpo;
	public Element hitboxMelee;
	private GameScreen mapa;
	private int casillaX;
	private int casillaY;
	private float tiempoMaxPersecucion = 7f;
	private float tiempoPersecucion = tiempoMaxPersecucion;
	private Random siguienteAtaque;
	
	public GoblinBeast(float x, float y, Stage s, GenericStage genericStage, GameScreen mapa, int casillaX, int casillaY) {
		super(x, y, s, genericStage, mapa, casillaX, casillaY);
		// TODO Auto-generated constructor stub
		drcha = this.loadFullAnimation("assets/enemies/GoblinBeastRightWalk.png",1,6,0.1f,true);
		izqda = this.loadFullAnimation("assets/enemies/GoblinBeastLeftWalk.png",1,6,0.1f,true);
		arriba = this.loadFullAnimation("assets/enemies/GoblinBeastUpWalk.png",1,6,0.1f,true);
		abajo = this.loadFullAnimation("assets/enemies/GoblinBeastDownWalk.png",1,6,0.1f,true);
		
		muerteR = this.loadFullAnimation("assets/enemies/GoblinBeastRightDeath.png",1,10,0.1f,false);
		muerteL = this.loadFullAnimation("assets/enemies/GoblinBeastLeftDeath.png",1,10,0.1f,false);
		muerteU = this.loadFullAnimation("assets/enemies/GoblinBeastUpDeath.png",1,10,0.1f,false);
		muerteD = this.loadFullAnimation("assets/enemies/GoblinBeastDownDeath.png",1,10,0.1f,false);
		
		meleeR = this.loadFullAnimation("assets/enemies/GoblinBeastRightMeleeAttack.png",1,8,0.1f,false);
		meleeL = this.loadFullAnimation("assets/enemies/GoblinBeastLeftMeleeAttack.png",1,8,0.1f,false);
		meleeU = this.loadFullAnimation("assets/enemies/GoblinBeastUpMeleeAttack.png",1,8,0.1f,false);
		meleeD = this.loadFullAnimation("assets/enemies/GoblinBeastDownMeleeAttack.png",1,8,0.1f,false);
		
		rangedR = this.loadFullAnimation("assets/enemies/GoblinBeastRightRangedAttack.png",1,14,0.1f,false);
		rangedL = this.loadFullAnimation("assets/enemies/GoblinBeastLeftRangedAttack.png",1,14,0.1f,false);
		rangedU = this.loadFullAnimation("assets/enemies/GoblinBeastUpRangedAttack.png",1,14,0.1f,false);
		rangedD = this.loadFullAnimation("assets/enemies/GoblinBeastDownRangedAttack.png",1,14,0.1f,false);
		
		tiempoAnimMuerte = 0f;
		muerteReproduciendose = false;
		tiempoAnimMelee = 0f;
		meleeReproduciendose = false;
		tiempoAnimRanged = 0f;
		rangedReproduciendose = false;
		siguienteAtaque = new Random();
		this.vida=30;
		this.danyo=2;
		this.nivel = genericStage;
		
		chasingSpeed = 27;
		this.deceleration = 110;
		this.setRectangle();
		this.setPolygon(8, this.getWidth()/2, this.getHeight()/2, this.getWidth()/4, this.getHeight()/4);
		hitboxAtaqueCuerpo = new Element(this.getX()+this.getWidth()/3-4 , this.getY()+this.getHeight()/3-6, s, this.getWidth()/2, this.getHeight()/2);
		hitboxAtaqueCuerpo.setPolygon(8);
		hitboxMelee = new Element(this.getX()+this.getWidth()/3-4, this.getY()+this.getHeight()/3-6, s, this.getWidth()/2, this.getHeight()/2);
		hitboxMelee.setPolygon(8);
		this.hitboxMelee.setEnabled(false);
		cargador=new Array<Bala>();
		for(int i=0;i<numBalas;i++) {
			this.cargador.add(new Bala(0,0, s,this.nivel,false, true, mapa, casillaX, casillaY));
		}
		m3d = new Vector3();
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
			hitboxAtaqueCuerpo.setPosition(this.getX()+this.getWidth()/3-4, this.getY()+this.getHeight()/3-6);
			tiempoDisparo-=delta;
			
			if(tiempoPersecucion>=0) {
				tiempoPersecucion-=delta;
				chase(delta);
				this.hitboxMelee.setEnabled(false);
			}else if(!meleeReproduciendose && !rangedReproduciendose){
				switch (siguienteAtaque.nextInt(2)) {
				case 0:
					this.tiempoAnimMelee=0;
					meleeReproduciendose=true;
					this.animationTime = 0f;
					break;
				case 1:
					this.tiempoAnimRanged=0;
					rangedReproduciendose=true;
					this.animationTime = 0f;
					break;
				default:
					break;
				}
			}
			
			animaciones(delta);
			colisiones();
			this.tiempoAnimMuerte+=delta;
			this.tiempoAnimMelee+=delta;
			this.tiempoAnimRanged+=delta;
			if(this.muerto && !this.muerteReproduciendose) {
				this.hitboxAtaqueCuerpo.setEnabled(false);
				this.setEnabled(false);
			}
		}
		
	}
	
	private void colisiones() {
		BossStage casilla = (BossStage)this.mapa.mapaPiso.get(casillaX).get(casillaY);
		if(this.hitboxAtaqueCuerpo.overlaps(casilla.player.cuerpo)) {
			casilla.player.danyo(this.danyo);
		}
		if(this.hitboxMelee.overlaps(casilla.player.cuerpo)) {
			casilla.player.danyo(this.danyo);
		}
	}
	
	private void animaciones(float delta) {

		if(this.tiempoAnimMuerte>this.tiempoMaxAnimMuerte) {
			this.muerteReproduciendose = false;
			this.setAnimation(abajo);
		}
		if(this.tiempoAnimMelee>this.tiempoMaxAnimMelee && this.meleeReproduciendose) {
			this.meleeReproduciendose = false;
			this.tiempoPersecucion = this.tiempoMaxPersecucion;
			this.setAnimation(abajo);
		}
		if(this.tiempoAnimRanged>this.tiempoMaxAnimRanged && this.rangedReproduciendose) {
			this.rangedReproduciendose = false;
			this.tiempoPersecucion = this.tiempoMaxPersecucion;
			this.setAnimation(abajo);
		}
		
		if(this.velocity.x!=0 || this.velocity.y!=0) {
			
			if(Math.abs(this.velocity.x)<(Math.abs(this.velocity.y))) {
				if(this.velocity.y>0) {
					this.setAnimation(arriba);
					
					if(this.muerteReproduciendose 
							&& !this.muerteU.isAnimationFinished(this.animationTime-this.tiempoAnimMuerte)){
						this.setAnimation(muerteU);
						this.muerto = true;
					}else if(this.muerteReproduciendose 
							&& this.muerteU.isAnimationFinished(this.animationTime-this.tiempoAnimMuerte)) {
						this.muerteReproduciendose = false;			}
				}else {
					this.setAnimation(abajo);
					
					if(this.muerteReproduciendose 
							&& !this.muerteD.isAnimationFinished(this.animationTime-this.tiempoAnimMuerte)){
						this.setAnimation(muerteD);
						this.muerto = true;
					}else if(this.muerteReproduciendose 
							&& this.muerteD.isAnimationFinished(this.animationTime-this.tiempoAnimMuerte)) {
						this.muerteReproduciendose = false;
					}
				}
			}else {
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
		}else {
			BossStage casilla = (BossStage)this.mapa.mapaPiso.get(casillaX).get(casillaY);
			if(meleeReproduciendose && !muerteReproduciendose) {
				if(Math.abs(this.getX()-casilla.player.getX())<Math.abs(this.getY()-casilla.player.getY())){
					if(Math.abs(this.getY())>Math.abs(casilla.player.getY())
							&& !this.meleeD.isAnimationFinished(this.animationTime-this.tiempoAnimMelee)) {
						this.setAnimation(meleeD);
						hitboxMelee.setPosition(this.getX()+this.getWidth()/3-4, this.getY()+this.getHeight()/3-26);
						this.hitboxMelee.setEnabled(true);
					}else if(Math.abs(this.getY())<Math.abs(casilla.player.getY())
							&& !this.meleeU.isAnimationFinished(this.animationTime-this.tiempoAnimMelee)) {
						this.setAnimation(meleeU);
						hitboxMelee.setPosition(this.getX()+this.getWidth()/3-4, this.getY()+this.getHeight()/3+14);
						this.hitboxMelee.setEnabled(true);
					}
				}else {
					if(Math.abs(this.getX())>Math.abs(casilla.player.getX()) 
							&& !this.meleeL.isAnimationFinished(this.animationTime-this.tiempoAnimMelee)) {
						this.setAnimation(meleeL);
						hitboxMelee.setPosition(this.getX()+this.getWidth()/3-24, this.getY()+this.getHeight()/3-6);
						this.hitboxMelee.setEnabled(true);
					}else if(Math.abs(this.getX())<Math.abs(casilla.player.getX())
							&& !this.meleeR.isAnimationFinished(this.animationTime-this.tiempoAnimMelee)) {
						this.setAnimation(meleeR);
						hitboxMelee.setPosition(this.getX()+this.getWidth()/3+16, this.getY()+this.getHeight()/3-6);
						this.hitboxMelee.setEnabled(true);
					}
				}
				
			}else if(rangedReproduciendose && !muerteReproduciendose){
				if(Math.abs(this.getX()-casilla.player.getX())<Math.abs(this.getY()-casilla.player.getY())){
					if(Math.abs(this.getY())>Math.abs(casilla.player.getY())
							&& !this.rangedD.isAnimationFinished(this.animationTime-this.tiempoAnimRanged)) {
						this.setAnimation(rangedD);
						if((this.tiempoAnimRanged>=0.3f && this.tiempoAnimRanged<=0.4f)
								|| (this.tiempoAnimRanged>=0.5f && this.tiempoAnimRanged<=0.6f)
								|| (this.tiempoAnimRanged>=1f && this.tiempoAnimRanged<=1.1f)
								|| (this.tiempoAnimRanged>=1.2f && this.tiempoAnimRanged<=1.3f)) {
							if(tiempoDisparo<=0) {
								disparar();
							}
						}
					}else if(Math.abs(this.getY())<Math.abs(casilla.player.getY())
							&& !this.rangedU.isAnimationFinished(this.animationTime-this.tiempoAnimRanged)) {
						this.setAnimation(rangedU);
						if((this.tiempoAnimRanged>=0.3f && this.tiempoAnimRanged<=0.4f)
								|| (this.tiempoAnimRanged>=0.5f && this.tiempoAnimRanged<=0.6f)
								|| (this.tiempoAnimRanged>=1f && this.tiempoAnimRanged<=1.1f)
								|| (this.tiempoAnimRanged>=1.2f && this.tiempoAnimRanged<=1.3f)) {
							if(tiempoDisparo<=0) {
								disparar();
							}
						}
						
					}
				}else {
					if(Math.abs(this.getX())>Math.abs(casilla.player.getX()) 
							&& !this.rangedL.isAnimationFinished(this.animationTime-this.tiempoAnimRanged)) {
						this.setAnimation(rangedL);
						if((this.tiempoAnimRanged>=0.3f && this.tiempoAnimRanged<=0.4f)
								|| (this.tiempoAnimRanged>=0.5f && this.tiempoAnimRanged<=0.6f)
								|| (this.tiempoAnimRanged>=1f && this.tiempoAnimRanged<=1.1f)
								|| (this.tiempoAnimRanged>=1.2f && this.tiempoAnimRanged<=1.3f)) {
							if(tiempoDisparo<=0) {
								disparar();
							}
						}
					}else if(Math.abs(this.getX())<Math.abs(casilla.player.getX())
							&& !this.rangedR.isAnimationFinished(this.animationTime-this.tiempoAnimRanged)) {
						this.setAnimation(rangedR);
						if((this.tiempoAnimRanged>=0.3f && this.tiempoAnimRanged<=0.4f)
								|| (this.tiempoAnimRanged>=0.5f && this.tiempoAnimRanged<=0.6f)
								|| (this.tiempoAnimRanged>=1f && this.tiempoAnimRanged<=1.1f)
								|| (this.tiempoAnimRanged>=1.2f && this.tiempoAnimRanged<=1.3f)) {
							if(tiempoDisparo<=0) {
								disparar();
							}
						}
					}
				}
			}else {
				if(Math.abs(this.getX()-casilla.player.getX())<Math.abs(this.getY()-casilla.player.getY())){
					if(Math.abs(this.getY())>Math.abs(casilla.player.getY())
							&& !this.muerteD.isAnimationFinished(this.animationTime-this.tiempoAnimMuerte)) {
						this.setAnimation(muerteD);
					}else if(Math.abs(this.getY())<Math.abs(casilla.player.getY())
							&& !this.muerteU.isAnimationFinished(this.animationTime-this.tiempoAnimMuerte)) {
						this.setAnimation(muerteU);
					}
				}else {
					if(Math.abs(this.getX())>Math.abs(casilla.player.getX()) 
							&& !this.muerteL.isAnimationFinished(this.animationTime-this.tiempoAnimMuerte)) {
						this.setAnimation(muerteL);
					}else if(Math.abs(this.getX())<Math.abs(casilla.player.getX())
							&& !this.muerteR.isAnimationFinished(this.animationTime-this.tiempoAnimMuerte)) {
						this.setAnimation(muerteR);
					}
				}
			}
		}
		
		
		
	}
	
	private void chase(float delta) {
		BossStage casilla = (BossStage)this.mapa.mapaPiso.get(casillaX).get(casillaY);
		if(!this.muerto) {
			float dirX = (casilla.player.getX()+casilla.player.getWidth()/2)-(this.getX()+this.getWidth()/2);
			float dirY = (casilla.player.getY()+casilla.player.getHeight()/2)-(this.getY()+this.getHeight()/2);
			Vector2 vector=new Vector2(dirX,dirY).nor();
			this.velocity.x=vector.x*this.chasingSpeed;
			this.velocity.y=vector.y*this.chasingSpeed;
		}
		this.applyPhysics(delta);
	}
	
	public void danyo(int danyo) {
		if(tiempoInvulnerable <= 0) {
			this.vida-=danyo;
			tiempoInvulnerable = tiempoInvulnerabilidad;
			if(this.vida<=0) {
				AudioManager.playSound("assets/audio/sounds/goblinbeastDie.mp3");

				this.tiempoAnimMuerte = 0f;
				this.muerteReproduciendose = true;
				this.animationTime = 0f;
			}
		}
		
	}
	
	public void disparar() {
		AudioManager.playSound("assets/audio/sounds/goblinBeastShoot.mp3");
		BossStage casilla = (BossStage)this.mapa.mapaPiso.get(casillaX).get(casillaY);
		m3d.x=casilla.player.cuerpo.getX()+casilla.player.cuerpo.getWidth()/2;
		m3d.y=casilla.player.cuerpo.getY()+casilla.player.cuerpo.getHeight()/2;
		m3d.z=0;
		
		float x=m3d.x-(this.hitboxAtaqueCuerpo.getX()+this.hitboxAtaqueCuerpo.getWidth()/2);
		float y=m3d.y-(this.hitboxAtaqueCuerpo.getY()+this.hitboxAtaqueCuerpo.getHeight()/2);
		Vector2 vector=new Vector2(x,y).nor();
		
		cargador.get(balaActual).disparar(vector.x, vector.y, this.getX()+this.getWidth()/2, this.getY()+this.getHeight()/2);
		balaActual=(balaActual+1)%numBalas;
		this.tiempoDisparo=this.cadencia;
	}
	
}