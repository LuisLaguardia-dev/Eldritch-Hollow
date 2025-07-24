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


public class GreenSlime extends Enemy{

	private Animation<TextureRegion> drchaI;
	private Animation<TextureRegion> izqdaI;
	private Animation<TextureRegion> drchaM;
	private Animation<TextureRegion> izqdaM;
	private float walkingSpeed;
	private float chasingSpeed;
	private boolean chasing;
	private GenericStage nivel;
	private float tiempoInvulnerable = 0;
	private float tiempoInvulnerabilidad = 0.5f;
	public Element hitboxAtaque;
	private GameScreen mapa;
	private int casillaX;
	private int casillaY;
	private int direccion = 0;
	
	public GreenSlime(float x, float y, Stage s, GenericStage genericStage, GameScreen mapa, int casillaX, int casillaY) {
		super(x+8, y+8, s, genericStage, mapa, casillaX, casillaY);
		// TODO Auto-generated constructor stub
		drchaI = this.loadFullAnimation("assets/enemies/greenSlimeRightIdle.png",1,5,0.1f,true);
		izqdaI = this.loadFullAnimation("assets/enemies/greenSlimeLeftIdle.png",1,5,0.1f,true);
		drchaM = this.loadFullAnimation("assets/enemies/greenSlimeRightMove.png",1,11,0.1f,true);
		izqdaM = this.loadFullAnimation("assets/enemies/greenSlimeLeftMove.png",1,11,0.1f,true);
		this.vida=2;
		this.danyo=1;
		this.nivel = genericStage;
		
		chasing = false;
		walkingSpeed = 0;
		chasingSpeed = 150;
		this.setPolygon(8,this.getWidth(), this.getHeight()/3, 0, 0);
		hitboxAtaque = new Element(this.getX(), this.getY(), s, this.getWidth(), this.getHeight()/3);
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
			hitboxAtaque.setPosition(this.getX(), this.getY());
			if(chasing) {
				chase();
			}else {
				search();
			}
			colisiones();
			animaciones();
		}
		
	}
	
	private void search() {
		if(this.mapa.mapaPiso.get(casillaX).get(casillaY) instanceof CurrentStage) {
			CurrentStage casilla = (CurrentStage) this.mapa.mapaPiso.get(casillaX).get(casillaY);
			float alturaPersonaje1 = this.hitboxAtaque.getY()+(this.hitboxAtaque.getHeight()/2);
			float alturaPersonaje2 = casilla.player.cuerpo.getY()+(casilla.player.cuerpo.getHeight()/2);
			float umbral = 10.0f;
			float diferenciaAltura = Math.abs(alturaPersonaje1 - alturaPersonaje2);
			boolean estanAlturasParecidas = diferenciaAltura <= umbral;
			if(estanAlturasParecidas) {
				chasing = true;
				direccion = calcularDireccion();
			}
		}else if(this.mapa.mapaPiso.get(casillaX).get(casillaY) instanceof BossStage) {
			BossStage casilla = (BossStage) this.mapa.mapaPiso.get(casillaX).get(casillaY);
			float alturaPersonaje1 = this.hitboxAtaque.getY()+(this.hitboxAtaque.getHeight()/2);
			float alturaPersonaje2 = casilla.player.cuerpo.getY()+(casilla.player.cuerpo.getHeight()/2);
			float umbral = 10.0f;
			float diferenciaAltura = Math.abs(alturaPersonaje1 - alturaPersonaje2);
			boolean estanAlturasParecidas = diferenciaAltura <= umbral;
			if(estanAlturasParecidas) {
				chasing = true;
				direccion = calcularDireccion();
			}
		}
		
	}
	private int calcularDireccion() {
		AudioManager.playSound("assets/audio/sounds/slimeStartMove.mp3");
		if(this.mapa.mapaPiso.get(casillaX).get(casillaY) instanceof CurrentStage) {
			CurrentStage casilla = (CurrentStage)this.mapa.mapaPiso.get(casillaX).get(casillaY);
			if(this.hitboxAtaque.getX()>casilla.player.getX()) {
				return -1;
			}else {
				return 1;
			}
		}else if(this.mapa.mapaPiso.get(casillaX).get(casillaY) instanceof BossStage) {
			BossStage casilla = (BossStage)this.mapa.mapaPiso.get(casillaX).get(casillaY);
			if(this.hitboxAtaque.getX()>casilla.player.getX()) {
				return -1;
			}else {
				return 1;
			}
		}
		return 0;
	}
	private void colisiones() {
		AudioManager.playSound("assets/audio/sounds/slimeHitWall.mp3");
		if(this.mapa.mapaPiso.get(casillaX).get(casillaY) instanceof CurrentStage) {
			CurrentStage casilla = (CurrentStage)this.mapa.mapaPiso.get(casillaX).get(casillaY);
			if(this.hitboxAtaque.overlaps(casilla.player.cuerpo)) {
				casilla.player.danyo(this.danyo);
			}
			for(Solid pared : casilla.paredes) {
				if(this.hitboxAtaque.overlaps(pared)) {
					chasing = false;
					this.velocity.x=0;
					if(this.hitboxAtaque.getX()>pared.getX()) {
						this.setX(this.getX()+3);
					}else {
						this.setX(this.getX()-3);
					}
				}
			}
			for(Solid puertas : casilla.puertas) {
				if(this.hitboxAtaque.overlaps(puertas)) {
					chasing = false;
					this.velocity.x=0;
					if(this.hitboxAtaque.getX()>puertas.getX()) {
						this.setX(this.getX()+3);
					}else {
						this.setX(this.getX()-3);
					}
				}
			}
			for(Enemy enemigos : casilla.enemigos) {
				if(this.hitboxAtaque.overlaps(enemigos) && this!=enemigos) {
					chasing = false;
					this.velocity.x=0;
					if(this.hitboxAtaque.getX()>enemigos.getX()) {
						this.setX(this.getX()+3);
					}else {
						this.setX(this.getX()-3);
					}
				}
			}
		}else if(this.mapa.mapaPiso.get(casillaX).get(casillaY) instanceof BossStage) {
			BossStage casilla = (BossStage)this.mapa.mapaPiso.get(casillaX).get(casillaY);
			if(this.hitboxAtaque.overlaps(casilla.player.cuerpo)) {
				casilla.player.danyo(this.danyo);
			}
			for(Solid pared : casilla.paredes) {
				if(this.hitboxAtaque.overlaps(pared)) {
					chasing = false;
					this.velocity.x=0;
					if(this.hitboxAtaque.getX()>pared.getX()) {
						this.setX(this.getX()+3);
					}else {
						this.setX(this.getX()-3);
					}
				}
			}
			for(Solid puertas : casilla.puertas) {
				if(this.hitboxAtaque.overlaps(puertas)) {
					chasing = false;
					this.velocity.x=0;
					if(this.hitboxAtaque.getX()>puertas.getX()) {
						this.setX(this.getX()+3);
					}else {
						this.setX(this.getX()-3);
					}
				}
			}
			for(Enemy enemigos : casilla.enemigos) {
				if(this.hitboxAtaque.overlaps(enemigos) && this!=enemigos) {
					chasing = false;
					this.velocity.x=0;
					if(this.hitboxAtaque.getX()>enemigos.getX()) {
						this.setX(this.getX()+3);
					}else {
						this.setX(this.getX()-3);
					}
				}
			}
		}
	}
	private void animaciones() {
		
		if(this.mapa.mapaPiso.get(casillaX).get(casillaY) instanceof CurrentStage) {
			CurrentStage casilla = (CurrentStage) this.mapa.mapaPiso.get(casillaX).get(casillaY);
			if(Math.abs(this.velocity.x)>0) {
				if(this.velocity.x>0) {
					this.setAnimation(drchaM);
				}else {
					this.setAnimation(izqdaM);
				}
			}else {
				if(this.hitboxAtaque.getX()>casilla.player.cuerpo.getX()) {
					this.setAnimation(izqdaI);
				}else {
					this.setAnimation(drchaI);
				}
			}
		}
		
	}
	
	private void chase() {
		this.velocity.x=direccion*this.chasingSpeed;
	}
	
	public void danyo(int danyo) {
		if(tiempoInvulnerable <= 0) {
			this.vida-=danyo;
			tiempoInvulnerable = tiempoInvulnerabilidad;
			if(this.vida<=0) {
				AudioManager.playSound("assets/audio/sounds/slimeDie.mp3");
				this.setEnabled(false);
			}
		}
		
	}
	
}