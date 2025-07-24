package screens;

import com.badlogic.gdx.graphics.Texture;

import game.Demo;
import managers.ResourceManager;

public class LoadScreen extends BScreen{
	private float loadDelay=5;
	private float loadCount=0;
	
	
	public LoadScreen(Demo game){
		
	super(game);
	//this.resourceManager=new ResourceManager();
	//game.resourceManager=this.resourceManager;
	
	ResourceManager.loadAllResources();
	//while(!ResourceManager.update()){}
	
	
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		super.render(delta);
		if(ResourceManager.update()) {
		
			ResourceManager.botones();
			game.setScreen(new TitleScreen(game));
			
		}
		
		
		
	}
	
	
	
}
