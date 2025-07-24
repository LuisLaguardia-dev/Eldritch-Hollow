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


public class Wonwon extends Enemy{

	private Animation<TextureRegion> drcha;
	private Animation<TextureRegion> izqda;
	private Animation<TextureRegion> arriba;
	private Animation<TextureRegion> abajo;
	private GenericStage nivel;
	private float tiempoInvulnerable = 0;
	private float tiempoInvulnerabilidad = 0.5f;
	public Element hitboxAtaque;
	private GameScreen mapa;
	private int casillaX;
	private int casillaY;
	private float cadencia = 1f;
	private float tiempoDisparo = cadencia;
	private int numBalas = 5;
	private ArrayList<Bala> cargador;
	private int balaActual=0;
	int direccion = 0;
	
	public Wonwon(float x, float y, Stage s, GenericStage genericStage, GameScreen mapa, int casillaX, int casillaY) {
		super(x, y, s, genericStage, mapa, casillaX, casillaY);
		// TODO Auto-generated constructor stub
		drcha = this.loadFullAnimation("assets/enemies/wonwonRightIdle.png",1,4,0.1f,true);
		izqda = this.loadFullAnimation("assets/enemies/wonwonLeftIdle.png",1,4,0.1f,true);
		arriba = this.loadFullAnimation("assets/enemies/wonwonUpIdle.png",1,4,0.1f,true);
		abajo = this.loadFullAnimation("assets/enemies/wonwonDownIdle.png",1,4,0.1f,true);
		this.setAnimation(abajo);
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
	
	}
	public void act(float delta) {
		if(getEnabled()) {
			super.act(delta);
			if(tiempoInvulnerable>=0) {
				tiempoInvulnerable-=delta;
			}
			if(tiempoDisparo>0) {
				tiempoDisparo-=delta;
			}else {
				disparo();
				
			}
			hitboxAtaque.setPosition(this.getX()+this.getWidth()/3, this.getY()+this.getHeight()/3);
			colisiones();
			animaciones();
		}
		
	}
	
	private void disparo() {
		AudioManager.playSound("assets/audio/sounds/wonwonShoot.mp3");
		Vector2 vector;
		float x, y;
		switch(direccion) {
		case 0:
			x=0;
			y=-1;
			vector = new Vector2(x,y).nor();
			cargador.get(balaActual).disparar(vector.x, vector.y, this.getX()+this.getWidth()/2, this.getY()+this.getHeight()/2);
			balaActual=(balaActual+1)%numBalas;
			break;
		
		case 1:
			x=-1;
			y=0;
			vector=new Vector2(x,y).nor();
			cargador.get(balaActual).disparar(vector.x, vector.y, this.getX()+this.getWidth()/2, this.getY()+this.getHeight()/2);
			balaActual=(balaActual+1)%numBalas;
			break;
		
		case 2:
			x=0;
			y=1;
			vector=new Vector2(x,y).nor();
			cargador.get(balaActual).disparar(vector.x, vector.y, this.getX()+this.getWidth()/2, this.getY()+this.getHeight()/2);
			balaActual=(balaActual+1)%numBalas;
			break;
		
		case 3:
			x=1;
			y=0;
			vector=new Vector2(x,y).nor();
			cargador.get(balaActual).disparar(vector.x, vector.y, this.getX()+this.getWidth()/2, this.getY()+this.getHeight()/2);
			balaActual=(balaActual+1)%numBalas;
			break;
		}
		direccion++;
		if(direccion==4) {
			direccion = 0;
		}
		tiempoDisparo = cadencia;
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
		switch (direccion) {
		case 0:
			this.setAnimation(abajo);
			break;
		case 1:
			this.setAnimation(izqda);
			break;
		case 2:
			this.setAnimation(arriba);
			break;
		case 3:
			this.setAnimation(drcha);
			break;

		default:
			break;
		}
		
	}
	
	public void danyo(int danyo) {
		if(tiempoInvulnerable <= 0) {
			this.vida-=danyo;
			tiempoInvulnerable = tiempoInvulnerabilidad;
			if(this.vida<=0) {
				AudioManager.playSound("assets/audio/sounds/WonwonDie.mp3");
				this.setEnabled(false);
			}
		}
		
	}
	
}