package elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

import screens.BossStage;
import screens.CurrentStage;
import screens.GameScreen;
import screens.GenericStage;
import screens.TreasureStage;

public class Cabeza extends Element{
	
	private Animation<TextureRegion> headFrente;
	private Animation<TextureRegion> headEspalda;
	private Animation<TextureRegion> headDrcha;
	private Animation<TextureRegion> headIzqda;
	private GenericStage nivel;
	private GameScreen mapa;
	private int casillaX;
	private int casillaY;

	public Cabeza(float x, float y, Stage s, GenericStage nivel, GameScreen mapa, int casillaX, int casillaY) {
		super(x, y, s);
		this.headDrcha = loadFullAnimation("assets/player/aitorHeadRight.png",1,8, 0.1f, true);
		this.headIzqda = loadFullAnimation("assets/player/aitorHeadLeft.png",1,8,0.1f, true);
		this.headFrente = loadFullAnimation("assets/player/aitorHeadFront.png",1,8,0.1f,true);
		this.headEspalda = loadFullAnimation("assets/player/aitorHeadBack.png",1,8,0.1f, true);
		this.nivel = nivel;
		this.mapa = mapa;
		this.casillaX = casillaX;
		this.casillaY = casillaY;
	}
	
	public void act(float delta) {
		super.act(delta);
		posicionarCabeza();
		animaciones();
		this.applyPhysics(delta);
	}
	
	private void posicionarCabeza() {
		if(this.mapa.mapaPiso.get(casillaX).get(casillaY) instanceof CurrentStage) {
			CurrentStage casilla = (CurrentStage)this.mapa.mapaPiso.get(casillaX).get(casillaY);
			this.setPosition(casilla.player.getX(), casilla.player.getY());
		}else if(this.mapa.mapaPiso.get(casillaX).get(casillaY) instanceof BossStage) {
			BossStage casilla = (BossStage)this.mapa.mapaPiso.get(casillaX).get(casillaY);
			this.setPosition(casilla.player.getX(), casilla.player.getY());
		}else if(this.mapa.mapaPiso.get(casillaX).get(casillaY) instanceof TreasureStage) {
			TreasureStage casilla = (TreasureStage)this.mapa.mapaPiso.get(casillaX).get(casillaY);
			this.setPosition(casilla.player.getX(), casilla.player.getY());
		}
		
	}
	
	private void animaciones() {
		if(Gdx.input.isKeyPressed(Keys.UP)
				|| Gdx.input.isKeyPressed(Keys.DOWN)
				|| Gdx.input.isKeyPressed(Keys.LEFT)
				|| Gdx.input.isKeyPressed(Keys.RIGHT)) {
			if(Gdx.input.isKeyPressed(Keys.UP)) {
				this.setAnimation(headEspalda);
			}
			if(Gdx.input.isKeyPressed(Keys.DOWN)) {
				this.setAnimation(headFrente);
			}
			if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
				this.setAnimation(headDrcha);
			}
			if(Gdx.input.isKeyPressed(Keys.LEFT)) {
				this.setAnimation(headIzqda);
			}
		}else {
			if(this.mapa.mapaPiso.get(casillaX).get(casillaY) instanceof CurrentStage) {
				CurrentStage casilla = (CurrentStage)this.mapa.mapaPiso.get(casillaX).get(casillaY);
				if(Math.abs(casilla.player.velocity.x)>0 || Math.abs(casilla.player.velocity.y)>0){
					if(casilla.player.velocity.x<0) {
						this.setAnimation(headIzqda);
					}else if(casilla.player.velocity.x>0) {
						this.setAnimation(headDrcha);
					}
					
					if(casilla.player.velocity.y>0) {
						this.setAnimation(headEspalda);
					}else if(casilla.player.velocity.y<0) {
						this.setAnimation(headFrente);
					}
				}else {
					this.setAnimation(headFrente);
				}
			}else if(this.mapa.mapaPiso.get(casillaX).get(casillaY) instanceof BossStage) {
				BossStage casilla = (BossStage)this.mapa.mapaPiso.get(casillaX).get(casillaY);
				if(Math.abs(casilla.player.velocity.x)>0 || Math.abs(casilla.player.velocity.y)>0){
					if(casilla.player.velocity.x<0) {
						this.setAnimation(headIzqda);
					}else if(casilla.player.velocity.x>0) {
						this.setAnimation(headDrcha);
					}
					
					if(casilla.player.velocity.y>0) {
						this.setAnimation(headEspalda);
					}else if(casilla.player.velocity.y<0) {
						this.setAnimation(headFrente);
					}
				}else {
					this.setAnimation(headFrente);
				}
			}else if(this.mapa.mapaPiso.get(casillaX).get(casillaY) instanceof TreasureStage) {
				TreasureStage casilla = (TreasureStage)this.mapa.mapaPiso.get(casillaX).get(casillaY);
				if(Math.abs(casilla.player.velocity.x)>0 || Math.abs(casilla.player.velocity.y)>0){
					if(casilla.player.velocity.x<0) {
						this.setAnimation(headIzqda);
					}else if(casilla.player.velocity.x>0) {
						this.setAnimation(headDrcha);
					}
					
					if(casilla.player.velocity.y>0) {
						this.setAnimation(headEspalda);
					}else if(casilla.player.velocity.y<0) {
						this.setAnimation(headFrente);
					}
				}else {
					this.setAnimation(headFrente);
				}
			}
		
		}
	}

}
