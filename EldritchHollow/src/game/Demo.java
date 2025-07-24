package game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import managers.ResourceManager;
import screens.BScreen;
import screens.LoadScreen;

public class Demo extends Game
{
	
	
	
	SpriteBatch batch;
	public ResourceManager resourceManager;
	
	
	
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		InputMultiplexer im=new InputMultiplexer();
		Gdx.input.setInputProcessor(im);
		
		
		setScreen(new LoadScreen(this));
		
		
		
		
		
		
		
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		this.getScreen().dispose();
	}
}