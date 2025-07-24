package elements;

import com.badlogic.gdx.scenes.scene2d.Stage;

import screens.BossStage;
import screens.CurrentStage;
import screens.GameScreen;
import screens.GenericStage;

public class Enemy extends Element{
	
	public int vida;
	public int danyo;
	public int velocidad;
	public GenericStage nivel;
	public GameScreen mapa;
	public int casillaX;
	public int casillaY;
	
	public Enemy(float x, float y, Stage s, GenericStage genericStage, GameScreen mapa, int casillaX, int casillaY) {
		super(x, y, s);
		this.nivel = genericStage;
		this.setEnabled(true);
		this.mapa = mapa;
		this.casillaX = casillaX;
		this.casillaY = casillaY;
	}
	
	public void act(float delta) {
		super.act(delta);
		if(this.getEnabled()) {
			
			
			
			collide();
				
		}
		this.applyPhysics(delta);
		
	}
	
	public void collide() {
		if(this.mapa.mapaPiso.get(casillaX).get(casillaY) instanceof CurrentStage) {
			CurrentStage casilla = (CurrentStage)this.mapa.mapaPiso.get(casillaX).get(casillaY);
			if(this.overlaps(casilla.player)) {
				//this.nivel.player.danyo(danyo);
				//nivel.actualizarInterfaz();
			}
		}else if(this.mapa.mapaPiso.get(casillaX).get(casillaY) instanceof BossStage) {
			BossStage casilla = (BossStage)this.mapa.mapaPiso.get(casillaX).get(casillaY);
			if(this.overlaps(casilla.player)) {
				//this.nivel.player.danyo(danyo);
				//nivel.actualizarInterfaz();
			}
		}
		
	}
	
	public void danyo(int danyo) {
		System.out.println(danyo);
		this.vida-=danyo;
	}

}
