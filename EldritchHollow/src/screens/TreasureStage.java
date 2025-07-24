package screens;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import elements.Botas;
import elements.ContenedorCorazon;
import elements.Enemy;
import elements.Espada;
import elements.Objeto;
import elements.Player;
import elements.Solid;
import elements.Trampilla;
import game.Demo;
import game.Parametros;
import managers.AudioManager;
import managers.ResourceManager;

public class TreasureStage extends GenericStage{

	Stage mainStage;
	
	public Array<Solid> paredes;
	public Array<Solid> puertas;
	public Array<Solid> entradas;
	public Array<Solid> salidas;
	
	public Objeto objeto;

	public boolean completada = false;
	
	public GameScreen mapa;

	public OrthographicCamera camara;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private int tileWidth, tileHeight, mapWidthInTiles, mapHeightInTiles,
	mapWidthInPixels, mapHeightInPixels;
	private int[] pPlano=new int[] {3};


	float inicioX;
	float inicioY;
	int casillaActualX;
	int casillaActualY;
	private Texture interfazVidaCompleta;
	private Texture interfazVidaMedia;
	private SpriteBatch batch;

	private Solid entradaNorte;
	private Solid entradaSur;
	private Solid entradaEste;
	private Solid entradaOeste;
	
	private Solid salidaNorte;
	private Solid salidaSur;
	private Solid salidaEste;
	private Solid salidaOeste;

	private Random generacionObjetos;
		   
	public Player player;
	
	public TreasureStage(Demo game, String direccion, GameScreen mapa, int casillaActualX, int casillaActualY) {
		super(game, direccion, false, mapa, casillaActualY, casillaActualY);

		mainStage=new Stage();
		switch (direccion) {
		
			case "tU":
				map=ResourceManager.getMap("assets/maps/casillaU-T.tmx");
				break;
			case "tD":
				map=ResourceManager.getMap("assets/maps/casillaD-T.tmx");
				break;
			case "tL":
				map=ResourceManager.getMap("assets/maps/casillaL-T.tmx");
				break;
			case "tR":
				map=ResourceManager.getMap("assets/maps/casillaR-T.tmx");
				break;
				
		}
		
		renderer=new OrthogonalTiledMapRenderer(map,mainStage.getBatch());
		
		this.mapa = mapa;
		this.casillaActualX = casillaActualX;
		this.casillaActualY = casillaActualY;
		
		camara=(OrthographicCamera) mainStage.getCamera();
		camara.setToOrtho(false, Parametros.getAnchoPantalla()*Parametros.zoom, Parametros.getAltoPantalla()*Parametros.zoom);
		renderer.setView(camara);
		
		ArrayList<MapObject> elementos;
		elementos=getRectangleList("pared");
		
		
		MapProperties props;
		Solid solido;
		paredes=new Array<Solid>();
		
		for(MapObject solid:elementos) {
			props=solid.getProperties();
			solido=new Solid((float)props.get("x"),(float)props.get("y"),mainStage,(float)props.get("width"),(float)props.get("height"));
			paredes.add(solido);
		}
		
		elementos=getRectangleList("start");
		Solid inicio;
		entradas = new Array<Solid>();
		for(int i=0;i<4;i++) {
			entradas.add(null);
		}
		for(MapObject solid : elementos) {
			props=solid.getProperties();
			if(entradas.get(0) == null || (float)props.get("x") > entradas.get(0).getX()) {
				entradaEste = new Solid((float)props.get("x"),(float)props.get("y"),mainStage,(float)props.get("width"),(float)props.get("height"), "E");
				entradas.set(0, entradaEste);
			}
			if(entradas.get(1) == null || (float)props.get("x") < entradas.get(1).getX()){
				entradaOeste = new Solid((float)props.get("x"),(float)props.get("y"),mainStage,(float)props.get("width"),(float)props.get("height"), "O");
				entradas.set(1, entradaOeste);
			}
			if(entradas.get(2) == null || (float)props.get("y") > entradas.get(2).getY()) {
				entradaNorte = new Solid((float)props.get("x"),(float)props.get("y"),mainStage,(float)props.get("width"),(float)props.get("height"), "N");
				entradas.set(2, entradaNorte);
			}
			if(entradas.get(3) == null || (float)props.get("y") < entradas.get(3).getY()){
				entradaSur = new Solid((float)props.get("x"),(float)props.get("y"),mainStage,(float)props.get("width"),(float)props.get("height"), "S");
				entradas.set(3, entradaSur);
			}
		}
		
		limpiezaVector(entradas);
		
		for(int i=0;i<4;i++) {
			if(entradas.get(i)!=null) {
				entradas.get(i).setEnabled(true);
			}
		}
		
		determinarAparicion();
		
		elementos = getRectangleList("end");
		salidas = new Array<Solid>();
		for(int i=0;i<4;i++) {
			salidas.add(null);
		}
		for(MapObject solid : elementos) {
			props=solid.getProperties();
			if(salidas.get(0) == null || (float)props.get("x") > salidas.get(0).getX()) {
				salidaEste = new Solid((float)props.get("x"),(float)props.get("y"),mainStage,(float)props.get("width"),(float)props.get("height"), "E");
				salidas.set(0, salidaEste);
			}
			if(salidas.get(1) == null || (float)props.get("x") < salidas.get(1).getX()){
				salidaOeste = new Solid((float)props.get("x"),(float)props.get("y"),mainStage,(float)props.get("width"),(float)props.get("height"), "O");
				salidas.set(1, salidaOeste);
			}
			if(salidas.get(2) == null || (float)props.get("y") > salidas.get(2).getY()) {
				salidaNorte = new Solid((float)props.get("x"),(float)props.get("y"),mainStage,(float)props.get("width"),(float)props.get("height"), "N");
				salidas.set(2, salidaNorte);
			}
			if(salidas.get(3) == null || (float)props.get("y") < salidas.get(3).getY()){
				salidaSur = new Solid((float)props.get("x"),(float)props.get("y"),mainStage,(float)props.get("width"),(float)props.get("height"), "S");
				salidas.set(3, salidaSur);
			}
		}
		
		limpiezaVector(salidas);
		
		for(int i=0;i<4;i++) {
			if(salidas.get(i)!=null) {
				salidas.get(i).setEnabled(true);
			}
		}
		
		elementos = getRectangleList("puerta");
		Solid puerta;
		puertas = new Array<Solid>();
		for(MapObject solid : elementos) {
			props=solid.getProperties();
			puerta = new Solid((float)props.get("x"),(float)props.get("y"),mainStage,(float)props.get("width"),(float)props.get("height"));
			puertas.add(puerta);
		}
		 //objeto = new Enemy(0, 0, mainStage, null, mapa, 0, 0);
		 generacionObjetos = new Random();
		if (!(completada && casillaInicial)) {
			for(MapObject ene:getEnemyList()) {
				props=ene.getProperties();
				switch(props.get("enemigo").toString()) {
					case "objeto":
						boolean objetoGenerado = false;
						while(!objetoGenerado) {
							Objeto nuevoObjeto = null;
							switch(generacionObjetos.nextInt(3)) {
								case 0:
									if(!Parametros.contenedorCorazonRecogido) {
										nuevoObjeto=new ContenedorCorazon((float)props.get("x"), (float)props.get("y"), mainStage, mapa, casillaActualX, casillaActualY);
										objetoGenerado = true;
									}
									break;
								case 1:
									if(!Parametros.EspadaRecogido) {
										nuevoObjeto=new Espada((float)props.get("x"), (float)props.get("y"), mainStage, mapa, casillaActualX, casillaActualY);
										objetoGenerado = true;
									}		
									break;
								case 2:
									if(!Parametros.BotasRecogido) {
										nuevoObjeto=new Botas((float)props.get("x"), (float)props.get("y"), mainStage, mapa, casillaActualX, casillaActualY);
										objetoGenerado = true;
									}		
									break;
							}
							if (nuevoObjeto != null) {
								objeto = nuevoObjeto;
								break;
							}
							
						}
					
				
				}
				
				
			}
		}

		player=new Player(inicioX,inicioY,mainStage,this, mapa, casillaActualX, casillaActualY);

		
		uiStage=new Stage();
		
		interfazVidaCompleta = new Texture("assets/interfaz/VidaEntera.png");
		interfazVidaMedia = new Texture("assets/interfaz/VidaMedia.png");
		batch = new SpriteBatch();
		
		
		AudioManager.playMusic("assets/audio/music/musicaGeneral.mp3");
	}
	
	protected void limpiezaVector(Array<Solid> vector) {
		for(int i=0;i<vector.size;i++) {
			if(vector.get(i)!=null) {
				switch (vector.get(i).name) {
				case "E":
					if(vector.get(i).getX()<250) {
						vector.set(i, null);
					}
					break;
				case "O":
					if(vector.get(i).getX()>100) {
						vector.set(i, null);
					}
					break;
				case "N":
					if(vector.get(i).getY()<200) {
						vector.set(i, null);
					}
					break;
				case "S":
					if(vector.get(i).getY()>50) {
						vector.set(i, null);
					}
					break;

				default:
					break;
				}
			}
		}
	}

	protected void determinarAparicion() {
		if(casillaInicial) {
			inicioX=180;
			inicioY=145;
		}else {
			//inicioX=190;
			//inicioY=155;
		}
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		super.render(delta);
	     mainStage.act();
	     uiStage.act();
	   colide();
	   estadoSala();
	    
	    Parametros.jugadorx=player.getX();
	    Parametros.jugadory=player.getY();
	  
	    
	  
	    centrarCamara();
	    renderer.setView(camara);
	    renderer.render();
	    actualizarInterfaz();
	        mainStage.draw();
	        //renderer.render(pPlano);
	        uiStage.draw();
	       
	    

	}
	
	protected void estadoSala() {
		this.completada = true;
		for(Solid puerta : puertas) {
    		puerta.setEnabled(false);
		}
	    
	}

	public void colide() {
		
		for(Solid solid:paredes) {
			if(player.overlaps(solid)) {
				player.preventOverlap(solid);
				
			}
		}
		
		for(Solid solid:puertas) {
			if(solid.getEnabled() && player.overlaps(solid)) {
				player.preventOverlap(solid);
				
			}
		}

		for(int i=0;i<salidas.size;i++) {
			if(salidas.get(i)!=null && this.player.overlaps(salidas.get(i))) {
				this.player.velocity.x=0;
				this.player.velocity.y=0;
				switch (salidas.get(i).name) {
				case "E":
					this.mapa.cambioNivel(this.casillaActualX, this.casillaActualY+1, salidas.get(i).name);
					break;
				case "O":
					this.mapa.cambioNivel(this.casillaActualX, this.casillaActualY-1, salidas.get(i).name);
					break;
				case "N":
					this.mapa.cambioNivel(this.casillaActualX-1, this.casillaActualY, salidas.get(i).name);
					break;
				case "S":
					this.mapa.cambioNivel(this.casillaActualX+1, this.casillaActualY, salidas.get(i).name);
					break;
				default:
					break;
				}
			}
		}
		
		
	}
	
	public void muerte() {
		Parametros.vida = 6;
		Parametros.maxVida=6;
		Parametros.maxTotalVida=24;
		this.game.setScreen(new DeathScreen(game));
	}
	
	public void centrarCamara() {
		this.camara.position.x=210;
		this.camara.position.y=165;
		camara.update();
		
	}
	
	
	public ArrayList<MapObject> getRectangleList(String propertyName){
		ArrayList<MapObject> list =new ArrayList<MapObject>();
		for(MapLayer layer: map.getLayers()) {
			for(MapObject obj: layer.getObjects()) {
				if(!(obj instanceof RectangleMapObject))
					continue;
				MapProperties props= obj.getProperties();
				if(props.containsKey("name") &&  props.get("name").equals(propertyName))
				{
					list.add(obj);
				}
				
			}
			
		}
		
		return list;
	}
	
	public ArrayList<Polygon> getPolygonList(String propertyName){
		
		Polygon poly;
		ArrayList<Polygon> list =new ArrayList<Polygon>();
		for(MapLayer layer: map.getLayers()) {
			for(MapObject obj: layer.getObjects()) {
				
				
				if(!(obj instanceof PolygonMapObject))
					continue;
				MapProperties props= obj.getProperties();
				if(props.containsKey("name") &&  props.get("name").equals(propertyName))
				{
					
					poly=((PolygonMapObject)obj).getPolygon();
					list.add(poly);
				}
				
			}
			
		}
		
		return list;
	}
	
	
	
	
	
	public ArrayList<MapObject> getEnemyList(){
		ArrayList<MapObject> list =new ArrayList<MapObject>();
		for(MapLayer layer: map.getLayers()) {
			for(MapObject obj: layer.getObjects()) {
				if(!(obj instanceof TiledMapTileMapObject))
					continue;
				MapProperties props= obj.getProperties();
				
				
				TiledMapTileMapObject tmtmo=(TiledMapTileMapObject) obj;
				TiledMapTile t=tmtmo.getTile();
				MapProperties defaultProps=t.getProperties();
				if(defaultProps.containsKey("enemigo")) {
					list.add(obj);	
				}
				if(props.containsKey("enemigo")) {
					list.add(obj);	
				}
				
				
				Iterator<String> propertyKeys=defaultProps.getKeys();
				while(propertyKeys.hasNext()) {
					String key =propertyKeys.next();
					
					if(props.containsKey(key))
						continue;
					else {
						Object value=defaultProps.get(key);
						props.put(key, value);
					}
						
				}
				
			}
			
		}
		
		return list;
	}
	
	
	public void actualizarInterfaz() {
		this.batch.begin();
		for (int i=0;i<Parametros.vida/2;i++) {
	        batch.draw(interfazVidaCompleta, 10+(i * (interfazVidaCompleta.getWidth() + 3)), Parametros.getAltoPantalla()-Parametros.getAltoPantalla()/8-10);
	    }
		if(Parametros.vida%2==1) {
			batch.draw(interfazVidaMedia, 10+((Parametros.vida/2) * (interfazVidaMedia.getWidth() + 3)), Parametros.getAltoPantalla()-Parametros.getAltoPantalla()/8-10);
		}
		this.batch.end();
	}

}
