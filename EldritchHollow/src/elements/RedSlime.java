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


public class RedSlime extends Enemy{

	private Animation<TextureRegion> upI;
	private Animation<TextureRegion> downI;
	private Animation<TextureRegion> upM;
	private Animation<TextureRegion> downM;
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
	
	public RedSlime(float x, float y, Stage s, GenericStage genericStage, GameScreen mapa, int casillaX, int casillaY) {
		super(x+8, y+8, s, genericStage, mapa, casillaX, casillaY);
		// TODO Auto-generated constructor stub
		upI = this.loadFullAnimation("assets/enemies/redSlimeUpIdle.png",1,5,0.1f,true);
		downI = this.loadFullAnimation("assets/enemies/redSlimeDownIdle.png",1,5,0.1f,true);
		upM = this.loadFullAnimation("assets/enemies/redSlimeUpMove.png",1,11,0.1f,true);
		downM = this.loadFullAnimation("assets/enemies/redSlimeDownMove.png",1,11,0.1f,true);
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
			float alturaPersonaje1 = this.hitboxAtaque.getX()+(this.hitboxAtaque.getWidth()/2);
			float alturaPersonaje2 = casilla.player.cuerpo.getX()+(casilla.player.cuerpo.getWidth()/2);
			float umbral = 10.0f;
			float diferenciaAltura = Math.abs(alturaPersonaje1 - alturaPersonaje2);
			boolean estanAlturasParecidas = diferenciaAltura <= umbral;
			if(estanAlturasParecidas) {
				chasing = true;
				direccion = calcularDireccion();
			}
		}else if(this.mapa.mapaPiso.get(casillaX).get(casillaY) instanceof BossStage) {
			BossStage casilla = (BossStage) this.mapa.mapaPiso.get(casillaX).get(casillaY);
			float alturaPersonaje1 = this.hitboxAtaque.getX()+(this.hitboxAtaque.getWidth()/2);
			float alturaPersonaje2 = casilla.player.cuerpo.getX()+(casilla.player.cuerpo.getWidth()/2);
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
			if(this.hitboxAtaque.getY()>casilla.player.getY()) {
				return -1;
			}else {
				return 1;
			}
		}else if(this.mapa.mapaPiso.get(casillaX).get(casillaY) instanceof BossStage) {
			BossStage casilla = (BossStage)this.mapa.mapaPiso.get(casillaX).get(casillaY);
			if(this.hitboxAtaque.getY()>casilla.player.getY()) {
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
					this.velocity.y=0;
					if(this.hitboxAtaque.getY()>pared.getY()) {
						this.setY(this.getY()+3);
					}else {
						this.setY(this.getY()-3);
					}
				}
			}
			for(Solid puertas : casilla.puertas) {
				if(this.hitboxAtaque.overlaps(puertas)) {
					chasing = false;
					this.velocity.y=0;
					if(this.hitboxAtaque.getY()>puertas.getY()) {
						this.setY(this.getY()+3);
					}else {
						this.setY(this.getY()-3);
					}
				}
			}
			for(Enemy enemigos : casilla.enemigos) {
				if(this.hitboxAtaque.overlaps(enemigos) && this!=enemigos) {
					chasing = false;
					this.velocity.y=0;
					if(this.hitboxAtaque.getY()>enemigos.getY()) {
						this.setY(this.getY()+3);
					}else {
						this.setY(this.getY()-3);
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
					this.velocity.y=0;
					if(this.hitboxAtaque.getY()>pared.getY()) {
						this.setY(this.getY()+3);
					}else {
						this.setY(this.getY()-3);
					}
				}
			}
			for(Solid puertas : casilla.puertas) {
				if(this.hitboxAtaque.overlaps(puertas)) {
					chasing = false;
					this.velocity.y=0;
					if(this.hitboxAtaque.getY()>puertas.getY()) {
						this.setY(this.getY()+3);
					}else {
						this.setY(this.getY()-3);
					}
				}
			}
			for(Enemy enemigos : casilla.enemigos) {
				if(this.hitboxAtaque.overlaps(enemigos) && this!=enemigos) {
					chasing = false;
					this.velocity.y=0;
					if(this.hitboxAtaque.getY()>enemigos.getY()) {
						this.setY(this.getY()+3);
					}else {
						this.setY(this.getY()-3);
					}
				}
				
			}
		}
	}
	private void animaciones() {
		
		if(this.mapa.mapaPiso.get(casillaX).get(casillaY) instanceof CurrentStage) {
			CurrentStage casilla = (CurrentStage) this.mapa.mapaPiso.get(casillaX).get(casillaY);
			if(Math.abs(this.velocity.y)>0) {
				if(this.velocity.y>0) {
					this.setAnimation(upM);
				}else {
					this.setAnimation(downM);
				}
			}else {
				if(this.hitboxAtaque.getY()>casilla.player.cuerpo.getY()) {
					this.setAnimation(downI);
				}else {
					this.setAnimation(upI);
				}
			}
		}
		
	}
	
	private void chase() {
		this.velocity.y=direccion*this.chasingSpeed;
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