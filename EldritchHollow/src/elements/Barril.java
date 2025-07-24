package elements;

import com.badlogic.gdx.scenes.scene2d.Stage;


public class Barril extends Element{

	float velocidad = 50;
	float tiempoPatrulla = 3;
	float tiempoPatrullando = 0;

	public Barril(float x, float y, Stage s) {
		super(x, y, s);
		this.loadFullAnimation("assets/maps/images/barrel.png", 1, 1, 1, false);
		this.setRectangle();
		this.velocity.y = velocidad;
	}
	
	public void act(float delta) {
		super.act(delta);
		if(tiempoPatrullando >= tiempoPatrulla) {
			tiempoPatrullando = 0;
			this.velocity.y = this.velocity.y*-1;
		}else {
			tiempoPatrullando+=delta;
		}
		this.applyPhysics(delta);
	}
}
