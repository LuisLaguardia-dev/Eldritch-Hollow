package elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import game.Parametros;
import managers.AudioManager;
import screens.CurrentStage;
import screens.GameScreen;
import screens.BossStage;
import screens.GenericStage;

public class Player extends Element{
private Animation<TextureRegion> frente;
private Animation<TextureRegion> espalda;
private Animation<TextureRegion> drcha;
private Animation<TextureRegion> izqda;
private Animation<TextureRegion> idle;
public float velocidad;
public Array<Bala> cargador;
private int numBalas=50;
private int balaActual=0;
private int direccionX=1;
private int direccionY=1;
public float cadencia=1f;
private float tiempoDisparo=0;
private GenericStage nivel;
private float tiempoInvulnerabilidad = 0.5f;
private float tiempoInvulnerable = 0;
private Cabeza head;
public Element cuerpo;

	public Player(float x, float y, Stage s, GenericStage nivel, GameScreen mapa, int casillaX, int casillaY) {
		super(x, y, s);
		this.velocidad=Parametros.velocidad;
		if(nivel.getClass().getSimpleName().equals("CurrentStage")) {
			this.nivel = (CurrentStage)nivel;
		}else if(nivel.getClass().getSimpleName().equals("BossStage")) {
			this.nivel = (BossStage)nivel;
		}
		head = new Cabeza(x, y, s, this.nivel, mapa, casillaX, casillaY);
		frente=loadFullAnimation("assets/player/aitorBodyWalkFront.png",1,8,0.1f,true);
		espalda=loadFullAnimation("assets/player/aitorBodyWalkBack.png",1,8,0.1f, true);
		drcha=loadFullAnimation("assets/player/aitorBodyWalkRight.png",1,8, 0.1f, true);
		izqda=loadFullAnimation("assets/player/aitorBodyWalkLeft.png",1,8,0.1f, true);
		idle=loadFullAnimation("assets/player/aitorBodyIdleFront.png",1,8,0.1f,true);
		
		
		
		cargador=new Array<Bala>();
		for(int i=0;i<numBalas;i++) {
			this.cargador.add(new Bala(0,0, s,this.nivel,true, false, mapa, casillaX, casillaY));
		}
		this.setPolygon(8, this.getWidth()/3+10, this.getHeight()/3+10, this.getWidth()/4-1, this.getHeight()/7);
		cuerpo = new Element(this.getX(), this.getY(), s, this.getWidth()/3, this.getHeight()/3);
		cuerpo.setPolygon(8);
		cuerpo.setEnabled(true);
		
	}


	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
		if(tiempoDisparo>=0) {
			this.tiempoDisparo-=delta;
		}
		if(tiempoInvulnerable>=0) {
			tiempoInvulnerable-=delta;
		}
		float magnitudActual = this.velocity.len();

		if(magnitudActual > velocidad) {
		    this.velocity.nor().scl(velocidad);
		}

		
		if(Parametros.vida>Parametros.maxVida) {
			Parametros.vida=Parametros.maxVida;
		}
		posicionarCuerpo();
		controles();
		this.applyPhysics(delta);
		//colocarPies();
	
		animaciones();
		
	}
	
	private void posicionarCuerpo() {
		this.cuerpo.setPosition(this.getX()+this.getWidth()/3-2, this.getY()+this.getHeight()/6);
		if(Gdx.input.isKeyPressed(Keys.W)) {
			this.cuerpo.setPosition(this.getX()+this.getWidth()/3+2, this.getY()+this.getHeight()/6-4);
		}
		if(Gdx.input.isKeyPressed(Keys.A)) {
			this.cuerpo.setPosition(this.getX()+this.getWidth()/3+2, this.getY()+this.getHeight()/6);
		}
	}
	
	private void controles() {
				
		//Disparo
		if(Gdx.input.isKeyPressed(Keys.UP) && tiempoDisparo<=0) {
			direccionY = 1;
			direccionX = 0;
			disparar();
		}
		if(Gdx.input.isKeyPressed(Keys.DOWN) && tiempoDisparo<=0) {
			direccionY = -1;
			direccionX = 0;
			disparar();
		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT) && tiempoDisparo<=0) {
			direccionY = 0;
			direccionX = 1;
			disparar();
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT) && tiempoDisparo<=0) {
			direccionY = 0;
			direccionX = -1;
			disparar();
		}
		
		//Movimiento
		if(Gdx.input.isKeyJustPressed(Keys.W)){
			this.acceleration.y=400;
		}else if(Gdx.input.isKeyPressed(Keys.W)) {
			this.acceleration.y=200;
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.S)){
			this.acceleration.y=-400;	
		}else if(Gdx.input.isKeyPressed(Keys.S)) {
			this.acceleration.y=-200;
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.D)){
			this.acceleration.x=400;
		}else if(Gdx.input.isKeyPressed(Keys.D)) {
			this.acceleration.x=200;
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.A)){
			this.acceleration.x=-400;
		}else if(Gdx.input.isKeyPressed(Keys.A)) {
			this.acceleration.x=-200;
		}
			
		if(!(Gdx.input.isKeyPressed(Keys.W)
				|| Gdx.input.isKeyPressed(Keys.S))) {
			
			if(this.velocity.y>10) {
				this.acceleration.y=-100;
			}else if(this.velocity.y<-10) {
				this.acceleration.y=100;
			}else {
				this.acceleration.y=0;
				this.velocity.y=0;
			}
		}
		if(!(Gdx.input.isKeyPressed(Keys.A)
				|| Gdx.input.isKeyPressed(Keys.D))) {
			if(this.velocity.x>10) {
				this.acceleration.x=-100;
			}else if(this.velocity.x<-10) {
				this.acceleration.x=100;
			}else {
				this.acceleration.x=0;
				this.velocity.x=0;
			}			
		}
	
		
	}
	
	public void animaciones() {
		
		if(this.velocity.x == 0 && this.velocity.y == 0) {
			this.setAnimation(idle);
			
		}
		
		if(this.velocity.x>0) {
			this.setAnimation(drcha);
			direccionX=1;
		}
		else if(this.velocity.x<0){
			this.setAnimation(izqda);
			direccionX=-1;
		}
		
		if(this.velocity.y>0){
			this.setAnimation(espalda);
			direccionY=1;
		}else if(this.velocity.y<0) {
			this.setAnimation(frente);
			direccionY=-1;
		}
		
	}

	public void danyo(int danyo) {
		if(tiempoInvulnerable <= 0) {
			AudioManager.playSound("assets/audio/sounds/playerHurt.mp3");
			Parametros.vida-=danyo;
			nivel.actualizarInterfaz();
			tiempoInvulnerable = tiempoInvulnerabilidad;
			if(Parametros.vida<=0) {
				nivel.muerte();
			}
		}
		
	}
	
	public void disparar() {
		AudioManager.playSound("assets/audio/sounds/playerShoot.mp3");
		tiempoDisparo=cadencia;
		this.cargador.get(balaActual).disparar(direccionX,direccionY,this.getX()+this.getWidth()/2,this.getY()+this.getHeight()/2);
		this.balaActual=(this.balaActual+1)%this.numBalas;
	}

}
