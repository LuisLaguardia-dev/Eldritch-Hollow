package elements;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Solid extends Element{
	
	public String name;

	public Solid(float x, float y, Stage s, float w, float h) {
		super(x, y, s, w, h);
		// TODO Auto-generated constructor stub
		this.name = "";
		float[] vertices= {0,0,w,0,w,h,0,h};
		colision=new Polygon(vertices);
		this.setSize(w, h);
	}
	
	public Solid(float x, float y, Stage s, float w, float h, String name) {
		super(x, y, s, w, h);
		// TODO Auto-generated constructor stub
		this.name = name;
		float[] vertices= {0,0,w,0,w,h,0,h};
		colision=new Polygon(vertices);
		this.setSize(w, h);
	}

}
