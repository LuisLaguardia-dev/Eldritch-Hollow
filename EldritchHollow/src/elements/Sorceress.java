package elements;

import java.util.Random;

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


public class Sorceress extends Enemy{

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
	private Animation<TextureRegion> shootR;
	private Animation<TextureRegion> shootL;
	private Animation<TextureRegion> shootU;
	private Animation<TextureRegion> shootD;
	private float tiempoMaxAnimShot = 0.5f;
	private float tiempoAnimShot;
	private boolean shotReproduciendose;
	private Animation<TextureRegion> aroundR;
	private Animation<TextureRegion> aroundL;
	private Animation<TextureRegion> aroundU;
	private Animation<TextureRegion> aroundD;
	private float tiempoMaxAnimAround = 0.7f;
	private float tiempoAnimAround;
	private boolean aroundReproduciendose;
	private Animation<TextureRegion> areaR;
	private Animation<TextureRegion> areaL;
	private Animation<TextureRegion> areaU;
	private Animation<TextureRegion> areaD;
	private float tiempoMaxAnimArea = 0.7f;
	private float tiempoAnimArea;
	private boolean areaReproduciendose;
	private boolean segundaFase = false;
	
	private int numBalas=100;
	public Array<Bala> cargador;
	public float cadencia=0.2f;
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
	private GameScreen mapa;
	private int casillaX;
	private int casillaY;
	private float tiempoMaxPersecucion = 7f;
	private float tiempoPersecucion = tiempoMaxPersecucion;
	private float tiempoMaxAtaqueSegundaFase = 4f;
	private float tiempoAtaqueSegundaFase = 0;
	private Random siguienteAtaque;
	
	public Sorceress(float x, float y, Stage s, GenericStage genericStage, GameScreen mapa, int casillaX, int casillaY) {
		super(x, y, s, genericStage, mapa, casillaX, casillaY);
		// TODO Auto-generated constructor stub
		drcha = this.loadFullAnimation("assets/enemies/SorceressRightWalk.png",1,6,0.1f,true);
		izqda = this.loadFullAnimation("assets/enemies/SorceressLeftWalk.png",1,6,0.1f,true);
		arriba = this.loadFullAnimation("assets/enemies/SorceressUpWalk.png",1,6,0.1f,true);
		abajo = this.loadFullAnimation("assets/enemies/SorceressDownWalk.png",1,6,0.1f,true);
		
		muerteR = this.loadFullAnimation("assets/enemies/SorceressRightDeath.png",1,10,0.1f,false);
		muerteL = this.loadFullAnimation("assets/enemies/SorceressLeftDeath.png",1,10,0.1f,false);
		muerteU = this.loadFullAnimation("assets/enemies/SorceressUpDeath.png",1,10,0.1f,false);
		muerteD = this.loadFullAnimation("assets/enemies/GoblinBeastDownDeath.png",1,10,0.1f,false);
		
		shootR = this.loadFullAnimation("assets/enemies/SorceressRightShot.png",1,5,0.1f,false);
		shootL = this.loadFullAnimation("assets/enemies/SorceressLeftShot.png",1,5,0.1f,false);
		shootU = this.loadFullAnimation("assets/enemies/SorceressUpShot.png",1,5,0.1f,false);
		shootD = this.loadFullAnimation("assets/enemies/SorceressDownShot.png",1,5,0.1f,false);
		
		aroundR = this.loadFullAnimation("assets/enemies/SorceressRightAround.png",1,7,0.1f,false);
		aroundL = this.loadFullAnimation("assets/enemies/SorceressLeftAround.png",1,7,0.1f,false);
		aroundU = this.loadFullAnimation("assets/enemies/SorceressUpAround.png",1,7,0.1f,false);
		aroundD = this.loadFullAnimation("assets/enemies/SorceressDownAround.png",1,7,0.1f,false);
		
		areaR = this.loadFullAnimation("assets/enemies/SorceressRightArea.png",1,7,0.1f,false);
		areaL = this.loadFullAnimation("assets/enemies/SorceressLeftArea.png",1,7,0.1f,false);
		areaU = this.loadFullAnimation("assets/enemies/SorceressUpArea.png",1,7,0.1f,false);
		areaD = this.loadFullAnimation("assets/enemies/SorceressDownArea.png",1,7,0.1f,false);
		
		tiempoAnimMuerte = 0f;
		muerteReproduciendose = false;
		tiempoAnimShot = 0f;
		shotReproduciendose = false;
		tiempoAnimAround = 0f;
		aroundReproduciendose = false;
		tiempoAnimArea = 0f;
		areaReproduciendose = false;
		siguienteAtaque = new Random();
		this.vida=50;
		this.danyo=2;
		this.nivel = genericStage;
		
		chasingSpeed = 15;
		this.deceleration = 110;
		this.setRectangle();
		this.setPolygon(8, this.getWidth()/2, this.getHeight()/2, this.getWidth()/4, this.getHeight()/4);
		hitboxAtaqueCuerpo = new Element(this.getX()+this.getWidth()/3-4 , this.getY()+this.getHeight()/3-6, s, this.getWidth()/2, this.getHeight()/2);
		hitboxAtaqueCuerpo.setPolygon(8);
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
			
			if(!segundaFase) {
				if(this.vida<=25) {
					this.tiempoAnimArea=0;
					areaReproduciendose=true;
					this.animationTime = 0f;
					segundaFase=true;
				}
			}else if(tiempoAtaqueSegundaFase>=0){
				tiempoAtaqueSegundaFase-=delta;
				
			}else {
				disparar("Area");
			}
			
			if(tiempoPersecucion>=0) {
				if(!areaReproduciendose) {
					tiempoPersecucion-=delta;
					chase(delta);
				}
			}else if(!shotReproduciendose && !aroundReproduciendose){
				switch (siguienteAtaque.nextInt(2)) {
				case 0:
					this.tiempoAnimShot=0;
					shotReproduciendose=true;
					this.animationTime = 0f;
					break;
				case 1:
					this.tiempoAnimAround=0;
					aroundReproduciendose=true;
					this.animationTime = 0f;
					break;
				default:
					break;
				}
			}
			
			animaciones(delta);
			colisiones();
			this.tiempoAnimMuerte+=delta;
			this.tiempoAnimShot+=delta;
			this.tiempoAnimAround+=delta;
			this.tiempoAnimArea+=delta;
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
	}
	
	private void animaciones(float delta) {

		if(this.tiempoAnimMuerte>this.tiempoMaxAnimMuerte) {
			this.muerteReproduciendose = false;
			this.setAnimation(abajo);
		}
		if(this.tiempoAnimShot>this.tiempoMaxAnimShot && this.shotReproduciendose) {
			this.shotReproduciendose = false;
			this.tiempoPersecucion = this.tiempoMaxPersecucion;
			this.setAnimation(abajo);
		}
		if(this.tiempoAnimAround>this.tiempoMaxAnimAround && this.aroundReproduciendose) {
			this.aroundReproduciendose = false;
			this.tiempoPersecucion = this.tiempoMaxPersecucion;
			this.setAnimation(abajo);
		}
		if(this.tiempoAnimArea>this.tiempoMaxAnimArea && this.areaReproduciendose) {
			this.areaReproduciendose = false;
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
			if(areaReproduciendose && !muerteReproduciendose){
				if(Math.abs(this.getX()-casilla.player.getX())<Math.abs(this.getY()-casilla.player.getY())){
					if(Math.abs(this.getY())>Math.abs(casilla.player.getY())
							&& !this.areaD.isAnimationFinished(this.animationTime-this.tiempoAnimArea)) {
						this.setAnimation(areaD);
						if((this.tiempoAnimArea>=0.4f && this.tiempoAnimArea<=0.5f)) {
							if(tiempoAtaqueSegundaFase<=0) {
								disparar("Area");
							}
						}
					}else if(Math.abs(this.getY())<Math.abs(casilla.player.getY())
							&& !this.areaU.isAnimationFinished(this.animationTime-this.tiempoAnimArea)) {
						this.setAnimation(areaU);
						if((this.tiempoAnimArea>=0.4f && this.tiempoAnimArea<=0.5f)) {
							if(tiempoAtaqueSegundaFase<=0) {
								disparar("Area");
							}
						}
						
					}
				}else {
					if(Math.abs(this.getX())>Math.abs(casilla.player.getX()) 
							&& !this.areaL.isAnimationFinished(this.animationTime-this.tiempoAnimArea)) {
						this.setAnimation(areaL);
						if((this.tiempoAnimArea>=0.4f && this.tiempoAnimArea<=0.5f)) {
							if(tiempoAtaqueSegundaFase<=0) {
								disparar("Area");
							}
						}
					}else if(Math.abs(this.getX())<Math.abs(casilla.player.getX())
							&& !this.areaR.isAnimationFinished(this.animationTime-this.tiempoAnimArea)) {
						this.setAnimation(areaR);
						if((this.tiempoAnimArea>=0.4f && this.tiempoAnimArea<=0.5f)) {
							if(tiempoAtaqueSegundaFase<=0) {
								disparar("Area");
							}
						}
					}
				}
			}else if(aroundReproduciendose && !muerteReproduciendose){
				if(Math.abs(this.getX()-casilla.player.getX())<Math.abs(this.getY()-casilla.player.getY())){
					if(Math.abs(this.getY())>Math.abs(casilla.player.getY())
							&& !this.aroundD.isAnimationFinished(this.animationTime-this.tiempoAnimAround)) {
						this.setAnimation(aroundD);
						if((this.tiempoAnimAround>=0.4f && this.tiempoAnimAround<=0.5f)) {
							if(tiempoDisparo<=0) {
								disparar("Around");
							}
						}
					}else if(Math.abs(this.getY())<Math.abs(casilla.player.getY())
							&& !this.aroundU.isAnimationFinished(this.animationTime-this.tiempoAnimAround)) {
						this.setAnimation(aroundU);
						if((this.tiempoAnimAround>=0.4f && this.tiempoAnimAround<=0.5f)) {
							if(tiempoDisparo<=0) {
								disparar("Around");
							}
						}
						
					}
				}else {
					if(Math.abs(this.getX())>Math.abs(casilla.player.getX()) 
							&& !this.aroundL.isAnimationFinished(this.animationTime-this.tiempoAnimAround)) {
						this.setAnimation(aroundL);
						if((this.tiempoAnimAround>=0.4f && this.tiempoAnimAround<=0.5f)) {
							if(tiempoDisparo<=0) {
								disparar("Around");
							}
						}
					}else if(Math.abs(this.getX())<Math.abs(casilla.player.getX())
							&& !this.aroundR.isAnimationFinished(this.animationTime-this.tiempoAnimAround)) {
						this.setAnimation(aroundR);
						if((this.tiempoAnimAround>=0.4f && this.tiempoAnimAround<=0.5f)) {
							if(tiempoDisparo<=0) {
								disparar("Around");
							}
						}
					}
				}
			}else if(shotReproduciendose && !muerteReproduciendose) {
				if(Math.abs(this.getX()-casilla.player.getX())<Math.abs(this.getY()-casilla.player.getY())){
					if(Math.abs(this.getY())>Math.abs(casilla.player.getY())
							&& !this.shootD.isAnimationFinished(this.animationTime-this.tiempoAnimShot)) {
						this.setAnimation(shootD);
						if((this.tiempoAnimShot>=0.2f && this.tiempoAnimShot<=0.3f)) {
							if(tiempoDisparo<=0) {
								disparar("Shot");
							}
						}
					}else if(Math.abs(this.getY())<Math.abs(casilla.player.getY())
							&& !this.shootU.isAnimationFinished(this.animationTime-this.tiempoAnimShot)) {
						this.setAnimation(shootU);
						if((this.tiempoAnimShot>=0.2f && this.tiempoAnimShot<=0.3f)) {
							if(tiempoDisparo<=0) {
								disparar("Shot");
							}
						}
					}
				}else {
					if(Math.abs(this.getX())>Math.abs(casilla.player.getX()) 
							&& !this.shootL.isAnimationFinished(this.animationTime-this.tiempoAnimShot)) {
						this.setAnimation(shootL);
						if((this.tiempoAnimShot>=0.2f && this.tiempoAnimShot<=0.3f)) {
							if(tiempoDisparo<=0) {
								disparar("Shot");
							}
						}
					}else if(Math.abs(this.getX())<Math.abs(casilla.player.getX())
							&& !this.shootR.isAnimationFinished(this.animationTime-this.tiempoAnimShot)) {
						this.setAnimation(shootR);
						if((this.tiempoAnimShot>=0.2f && this.tiempoAnimShot<=0.3f)) {
							if(tiempoDisparo<=0) {
								disparar("Shot");
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
				AudioManager.playSound("assets/audio/sounds/SorceressDie.mp3");
				this.tiempoAnimMuerte = 0f;
				this.muerteReproduciendose = true;
				this.animationTime = 0f;
			}
		}
		
	}
	
	public void disparar(String tipoDisparo) {
		AudioManager.playSound("assets/audio/sounds/sorceressShoot.mp3");
		if(tipoDisparo.equals("Shot")) {
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
		}else if(tipoDisparo.equals("Around")){			
			BossStage casilla = (BossStage)this.mapa.mapaPiso.get(casillaX).get(casillaY);
			m3d.x=casilla.player.cuerpo.getX()+casilla.player.cuerpo.getWidth()/2;
			m3d.y=casilla.player.cuerpo.getY()+casilla.player.cuerpo.getHeight()/2;
			m3d.z=0;
			
			float x=m3d.x-(this.hitboxAtaqueCuerpo.getX()+this.hitboxAtaqueCuerpo.getWidth()/2);
			float y=m3d.y-(this.hitboxAtaqueCuerpo.getY()+this.hitboxAtaqueCuerpo.getHeight()/2);
			Vector2 vector=new Vector2(x,y).nor();
			
			float anguloSeparacion = 22.5f;
			
			for (int i = 0; i < 16; i++) {
			    float anguloRad = (float) Math.toRadians(anguloSeparacion * i);
			    float cos = MathUtils.cos(anguloRad);
			    float sin = MathUtils.sin(anguloRad);

			    Vector2 vectorRotado = new Vector2(
	    		vector.x * cos - vector.y * sin,
	    		vector.x * sin + vector.y * cos
			    );
			    
			    cargador.get(balaActual).disparar(vectorRotado.x, vectorRotado.y, this.getX()+this.getWidth()/2, this.getY()+this.getHeight()/2);
				balaActual=(balaActual+1)%numBalas;

				this.tiempoDisparo=this.cadencia;
			}
		}else {
			float x=1;
			float y=1;
			Vector2 vector=new Vector2(x,y).nor();
			
			float anguloSeparacion = 45f;
			
			for (int i = 0; i < 8; i++) {
			    float anguloRad = (float) Math.toRadians(anguloSeparacion * i);
			    float cos = MathUtils.cos(anguloRad);
			    float sin = MathUtils.sin(anguloRad);

			    Vector2 vectorRotado = new Vector2(
	    		vector.x * cos - vector.y * sin,
	    		vector.x * sin + vector.y * cos
			    );
			    
			    cargador.get(balaActual).disparar(vectorRotado.x, vectorRotado.y, this.getX()+this.getWidth()/2, this.getY()+this.getHeight()/2);
				balaActual=(balaActual+1)%numBalas;
				
				this.tiempoAtaqueSegundaFase = this.tiempoMaxAtaqueSegundaFase;
			}
		}
	}
	
}