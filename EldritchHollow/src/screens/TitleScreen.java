package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

import game.Demo;
import game.Parametros;
import managers.ResourceManager;

public class TitleScreen extends BScreen{
private Table tabla;
private Label titulo;
private SpriteBatch batch;
private Texture backgroundImage;
	public TitleScreen(Demo game) {
		super(game);
		FreeTypeFontGenerator ftfg= new FreeTypeFontGenerator(Gdx.files.internal("assets/sans.ttf"));
		FreeTypeFontParameter ftfp= new FreeTypeFontParameter();
		
		ftfp.size=36;
		ftfp.color=Color.WHITE;
		ftfp.borderColor=Color.BLACK;
		ftfp.borderWidth=2;
		
		BitmapFont font = ftfg.generateFont(ftfp);;
		font.setColor(Color.GREEN);
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = font;
		labelStyle.fontColor = Color.GREEN;
		
		titulo = new Label("Las Profundidades de Eldrit", labelStyle);
		tabla= new Table();
		
		tabla.setFillParent(true);
		
		this.uiStage.addActor(tabla);
		
		tabla.add(titulo);
		tabla.row();
		Label vacio = new Label("", labelStyle);
		tabla.add(vacio);
		tabla.row();
		tabla.add(vacio);
		tabla.row();
		
		TextButton boton=new TextButton("Jugar",ResourceManager.textButtonStyle);
		boton.addListener(
				(Event e)->{if(!(e instanceof InputEvent)|| !((InputEvent)e).getType().equals(Type.touchDown))
					return false;
				this.dispose();
				game.setScreen(new GameScreen(game));
				return false;
				});
		tabla.add(boton);
		
		tabla.row();
		TextButton botonSalir=new TextButton("Salir", ResourceManager.textButtonStyle);
		botonSalir.addListener(
				(Event e)->{if(!(e instanceof InputEvent)|| !((InputEvent)e).getType().equals(Type.touchDown))
					return false;
				this.dispose();
			Gdx.app.exit();
				return false;
				});
		tabla.add(botonSalir);
		batch = new SpriteBatch();
		backgroundImage = new Texture("assets/fondo/fondoTitulo.png");
	}

	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		super.render(delta);
		batch.begin();
        batch.draw(backgroundImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
		uiStage.act();
        uiStage.draw();
	}
}
