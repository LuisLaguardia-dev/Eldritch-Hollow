package screens;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;

import elements.Barril;
import elements.Bat;
import elements.Bee;
import elements.Enemy;
import elements.Player;
import elements.Solid;
import game.Demo;
import game.Parametros;
import managers.AudioManager;
import managers.ResourceManager;

public abstract class GenericStage extends BScreen{

	Stage mainStage;
	

	public boolean completada = false;
	public boolean casillaInicial;
	
	public GameScreen mapa;

	public OrthographicCamera camara;
	public TiledMap map;
	private OrthogonalTiledMapRenderer renderer;

	float inicioX;
	float inicioY;
	int casillaActualX;
	int casillaActualY;
	
	public GenericStage(Demo game, String direccion, boolean casillaInicial, GameScreen mapa, int casillaActualX, int casillaActualY) {
		super(game);
		mainStage = new Stage();
		renderer = new OrthogonalTiledMapRenderer(map,mainStage.getBatch());
		camara =(OrthographicCamera) mainStage.getCamera();
		camara.setToOrtho(false, Parametros.getAnchoPantalla()*Parametros.zoom, Parametros.getAltoPantalla()*Parametros.zoom);
		renderer.setView(camara);
	}
	
	protected abstract void limpiezaVector(Array<Solid> vector);

	protected abstract void determinarAparicion();

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		super.render(delta);
	     mainStage.act();
	     uiStage.act();
	   colide();
	   estadoSala();
	    centrarCamara();
	    renderer.setView(camara);
	    actualizarInterfaz();
	    //renderer.render();
	        mainStage.draw();
	        //renderer.render(pPlano);
	        uiStage.draw();
	       
	    

	}
	
	protected abstract void estadoSala();

	public abstract void colide();
	
	public abstract void muerte();
	
	public abstract void centrarCamara();
	
	
	public abstract ArrayList<MapObject> getRectangleList(String propertyName);
	
	public abstract ArrayList<Polygon> getPolygonList(String propertyName);
	
	public abstract ArrayList<MapObject> getEnemyList();	
	
	public abstract void actualizarInterfaz();

}
