package screens;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import elements.Corazon;
import elements.Enemy;
import elements.GreenSlime;
import elements.Player;
import elements.Ratfolk;
import elements.RedSlime;
import elements.Solid;
import elements.Wonwon;
import game.Demo;
import game.Parametros;
import managers.AudioManager;
import managers.ResourceManager;

public class CurrentStage extends GenericStage{

	Stage mainStage;
	
	public Array<Solid> paredes;
	public Array<Solid> puertas;
	public Array<Solid> entradas;
	public Array<Solid> salidas;
	
	public Array<Enemy> enemigos;

	public boolean completada = false;
	public boolean casillaInicial;
	
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

	private Random generacionEnemigos;
	private Random generacionTiposEnemigos;
	
	private Random aparicionRecurso;
		   
	public Player player;
	
	public CurrentStage(Demo game, String direccion, boolean casillaInicial, GameScreen mapa, int casillaActualX, int casillaActualY) {
		super(game, direccion, casillaInicial, mapa, casillaActualY, casillaActualY);

		this.mainStage=new Stage();
		switch (direccion) {
		
			case "U":
				this.map=ResourceManager.getMap("assets/maps/casillaU.tmx");
				break;
			case "D":
				this.map=ResourceManager.getMap("assets/maps/casillaD.tmx");
				break;
			case "L":
				this.map=ResourceManager.getMap("assets/maps/casillaL.tmx");
				break;
			case "R":
				this.map=ResourceManager.getMap("assets/maps/casillaR.tmx");
				break;
			case "RL":
				this.map=ResourceManager.getMap("assets/maps/casillaLR.tmx");
				break;
			case "RL-Rb":
				this.map=ResourceManager.getMap("assets/maps/casillaLR-Rb.tmx");
				break;
			case "RL-Lb":
				this.map=ResourceManager.getMap("assets/maps/casillaLR-Lb.tmx");
				break;
			case "DU":
				this.map=ResourceManager.getMap("assets/maps/casillaUD.tmx");
				break;
			case "DU-Db":
				this.map=ResourceManager.getMap("assets/maps/casillaUD-Db.tmx");
				break;
			case "DU-Ub":
				this.map=ResourceManager.getMap("assets/maps/casillaUD-Ub.tmx");
				break;
			case "UR":
				this.map=ResourceManager.getMap("assets/maps/casillaUR.tmx");
				break;
			case "UR-Ub":
				this.map=ResourceManager.getMap("assets/maps/casillaUR-Ub.tmx");
				break;
			case "UR-Rb":
				this.map=ResourceManager.getMap("assets/maps/casillaUR-Rb.tmx");
				break;
			case "DR":
				this.map=ResourceManager.getMap("assets/maps/casillaRD.tmx");
				break;
			case "DR-Db":
				this.map=ResourceManager.getMap("assets/maps/casillaRD-Db.tmx");
				break;
			case "DR-Rb":
				this.map=ResourceManager.getMap("assets/maps/casillaRD-Rb.tmx");
				break;
			case "UL":
				this.map=ResourceManager.getMap("assets/maps/casillaUL.tmx");
				break;
			case "UL-Ub":
				this.map=ResourceManager.getMap("assets/maps/casillaUL-Ub.tmx");
				break;
			case "UL-Lb":
				this.map=ResourceManager.getMap("assets/maps/casillaUL-Lb.tmx");
				break;
			case "DL":
				this.map=ResourceManager.getMap("assets/maps/casillaLD.tmx");
				break;
			case "DL-Db":
				this.map=ResourceManager.getMap("assets/maps/casillaLD-Db.tmx");
				break;
			case "DL-Lb":
				this.map=ResourceManager.getMap("assets/maps/casillaLD-Lb.tmx");
				break;
			case "URL":
				this.map=ResourceManager.getMap("assets/maps/casillaULR.tmx");
				break;
			case "URL-Ub":
				this.map=ResourceManager.getMap("assets/maps/casillaULR-Ub.tmx");
				break;
			case "URL-Rb":
				this.map=ResourceManager.getMap("assets/maps/casillaULR-Rb.tmx");
				break;
			case "URL-Lb":
				this.map=ResourceManager.getMap("assets/maps/casillaULR-Lb.tmx");
				break;
			case "DRL":
				this.map=ResourceManager.getMap("assets/maps/casillaLDR.tmx");
				break;
			case "DRL-Db":
				this.map=ResourceManager.getMap("assets/maps/casillaLDR-Db.tmx");
				break;
			case "DRL-Rb":
				this.map=ResourceManager.getMap("assets/maps/casillaLDR-Rb.tmx");
				break;
			case "DRL-Lb":
				this.map=ResourceManager.getMap("assets/maps/casillaLDR-Lb.tmx");
				break;
			case "DUR":
				this.map=ResourceManager.getMap("assets/maps/casillaUDR.tmx");
				break;
			case "DUR-Db":
				this.map=ResourceManager.getMap("assets/maps/casillaUDR-Db.tmx");
				break;
			case "DUR-Ub":
				this.map=ResourceManager.getMap("assets/maps/casillaUDR-Ub.tmx");
				break;
			case "DUR-Rb":
				this.map=ResourceManager.getMap("assets/maps/casillaUDR-Rb.tmx");
				break;
			case "DUL":
				this.map=ResourceManager.getMap("assets/maps/casillaULD.tmx");
				break;
			case "DUL-Db":
				this.map=ResourceManager.getMap("assets/maps/casillaULD-Db.tmx");
				break;
			case "DUL-Ub":
				this.map=ResourceManager.getMap("assets/maps/casillaULD-Ub.tmx");
				break;
			case "DUL-Lb":
				this.map=ResourceManager.getMap("assets/maps/casillaULD-Lb.tmx");
				break;
			case "DURL":
				this.map=ResourceManager.getMap("assets/maps/casillaAll.tmx");
				break;
			case "DURL-Db":
				this.map=ResourceManager.getMap("assets/maps/casillaAll-Db.tmx");
				break;
			case "DURL-Ub":
				this.map=ResourceManager.getMap("assets/maps/casillaAll-Ub.tmx");
				break;
			case "DURL-Rb":
				this.map=ResourceManager.getMap("assets/maps/casillaAll-Rb.tmx");
				break;
			case "DURL-Lb":
				this.map=ResourceManager.getMap("assets/maps/casillaAll-Lb.tmx");
				break;
				
		}
		
		
		this.casillaInicial = casillaInicial;
		if(casillaInicial) {
			this.completada = true;
		}
		
		renderer=new OrthogonalTiledMapRenderer(map,mainStage.getBatch());
		
		this.mapa = mapa;
		this.casillaActualX = casillaActualX;
		this.casillaActualY = casillaActualY;
		
		this.camara=(OrthographicCamera) mainStage.getCamera();
		this.camara.setToOrtho(false, Parametros.getAnchoPantalla()*Parametros.zoom, Parametros.getAltoPantalla()*Parametros.zoom);
		this.renderer.setView(camara);
		
		ArrayList<MapObject> elementos;
		elementos=getRectangleList("pared");
		
		
		MapProperties props;
		Solid solido;
		this.paredes=new Array<Solid>();
		
		for(MapObject solid:elementos) {
			props=solid.getProperties();
			solido=new Solid((float)props.get("x"),(float)props.get("y"),mainStage,(float)props.get("width"),(float)props.get("height"));
			this.paredes.add(solido);
		}
		
		elementos=getRectangleList("start");
		Solid inicio;
		this.entradas = new Array<Solid>();
		for(int i=0;i<4;i++) {
			this.entradas.add(null);
		}
		for(MapObject solid : elementos) {
			props=solid.getProperties();
			if(this.entradas.get(0) == null || (float)props.get("x") > this.entradas.get(0).getX()) {
				this.entradaEste = new Solid((float)props.get("x"),(float)props.get("y"),this.mainStage,(float)props.get("width"),(float)props.get("height"), "E");
				this.entradas.set(0, this.entradaEste);
			}
			if(this.entradas.get(1) == null || (float)props.get("x") < this.entradas.get(1).getX()){
				this.entradaOeste = new Solid((float)props.get("x"),(float)props.get("y"),this.mainStage,(float)props.get("width"),(float)props.get("height"), "O");
				this.entradas.set(1, this.entradaOeste);
			}
			if(this.entradas.get(2) == null || (float)props.get("y") > this.entradas.get(2).getY()) {
				this.entradaNorte = new Solid((float)props.get("x"),(float)props.get("y"),this.mainStage,(float)props.get("width"),(float)props.get("height"), "N");
				this.entradas.set(2, this.entradaNorte);
			}
			if(this.entradas.get(3) == null || (float)props.get("y") < this.entradas.get(3).getY()){
				this.entradaSur = new Solid((float)props.get("x"),(float)props.get("y"),this.mainStage,(float)props.get("width"),(float)props.get("height"), "S");
				this.entradas.set(3, this.entradaSur);
			}
		}
		
		limpiezaVector(this.entradas);
		
		for(int i=0;i<4;i++) {
			if(this.entradas.get(i)!=null) {
				this.entradas.get(i).setEnabled(true);
			}
		}
		
		determinarAparicion();
		
		elementos = getRectangleList("end");
		this.salidas = new Array<Solid>();
		for(int i=0;i<4;i++) {
			this.salidas.add(null);
		}
		for(MapObject solid : elementos) {
			props=solid.getProperties();
			if(this.salidas.get(0) == null || (float)props.get("x") > this.salidas.get(0).getX()) {
				this.salidaEste = new Solid((float)props.get("x"),(float)props.get("y"),this.mainStage,(float)props.get("width"),(float)props.get("height"), "E");
				this.salidas.set(0, this.salidaEste);
			}
			if(this.salidas.get(1) == null || (float)props.get("x") < this.salidas.get(1).getX()){
				this.salidaOeste = new Solid((float)props.get("x"),(float)props.get("y"),this.mainStage,(float)props.get("width"),(float)props.get("height"), "O");
				this.salidas.set(1, this.salidaOeste);
			}
			if(this.salidas.get(2) == null || (float)props.get("y") > this.salidas.get(2).getY()) {
				this.salidaNorte = new Solid((float)props.get("x"),(float)props.get("y"),this.mainStage,(float)props.get("width"),(float)props.get("height"), "N");
				this.salidas.set(2, this.salidaNorte);
			}
			if(this.salidas.get(3) == null || (float)props.get("y") < this.salidas.get(3).getY()){
				this.salidaSur = new Solid((float)props.get("x"),(float)props.get("y"),this.mainStage,(float)props.get("width"),(float)props.get("height"), "S");
				this.salidas.set(3, this.salidaSur);
			}
		}
		
		limpiezaVector(this.salidas);
		
		for(int i=0;i<4;i++) {
			if(this.salidas.get(i)!=null) {
				this.salidas.get(i).setEnabled(true);
			}
		}
		
		elementos = getRectangleList("puerta");
		Solid puerta;
		this.puertas = new Array<Solid>();
		for(MapObject solid : elementos) {
			props=solid.getProperties();
			puerta = new Solid((float)props.get("x"),(float)props.get("y"),this.mainStage,(float)props.get("width"),(float)props.get("height"));
			this.puertas.add(puerta);
		}
		
		this.enemigos=new Array<Enemy>();
		this.generacionEnemigos = new Random();
		this.generacionTiposEnemigos = new Random();
		if (!(this.completada && casillaInicial)) {
			for(MapObject ene:getEnemyList()) {
				props=ene.getProperties();
				switch(props.get("enemigo").toString()) {
					case "simple":
						if(this.generacionEnemigos.nextBoolean()) {
							Enemy nuevoEnemigo = null;
							switch(Parametros.nivel) {
							case 1:
								switch(this.generacionTiposEnemigos.nextInt(3)) {
								case 0:
									if((float)props.get("width")>20 && (float)props.get("height")>20) {
										nuevoEnemigo=new Bat((float)props.get("x"), (float)props.get("y"), this.mainStage, this, mapa, casillaActualX, casillaActualY);
									}else {
										nuevoEnemigo=new Bat((float)props.get("x")-(float)props.get("width")/2, (float)props.get("y")-(float)props.get("height")/2, this.mainStage, this, mapa, casillaActualX, casillaActualY);
									}
									break;
								case 1:
									if((float)props.get("width")>20 && (float)props.get("height")>20) {
										nuevoEnemigo=new Bee((float)props.get("x"), (float)props.get("y"), this.mainStage, this, mapa, casillaActualX, casillaActualY);
									}else {
										nuevoEnemigo=new Bee((float)props.get("x")-(float)props.get("width")/2, (float)props.get("y")-(float)props.get("height")/2, this.mainStage, this, mapa, casillaActualX, casillaActualY);
									}
									break;
								case 2:
									if((float)props.get("width")>20 && (float)props.get("height")>20) {
										nuevoEnemigo=new Wonwon((float)props.get("x"), (float)props.get("y"), this.mainStage, this, mapa, casillaActualX, casillaActualY);
									}else {
										nuevoEnemigo=new Wonwon((float)props.get("x")-(float)props.get("width")/2, (float)props.get("y")-(float)props.get("height")/2, this.mainStage, this, mapa, casillaActualX, casillaActualY);
									}
									break;
								default:
									break;
								}
								break;
							case 2:
								switch(this.generacionTiposEnemigos.nextInt(5)) {
								case 0:
									if((float)props.get("width")>20 && (float)props.get("height")>20) {
										nuevoEnemigo=new Bat((float)props.get("x"), (float)props.get("y"), this.mainStage, this, mapa, casillaActualX, casillaActualY);
									}else {
										nuevoEnemigo=new Bat((float)props.get("x")-(float)props.get("width")/2, (float)props.get("y")-(float)props.get("height")/2, this.mainStage, this, mapa, casillaActualX, casillaActualY);
									}
									break;
								case 1:
									if((float)props.get("width")>20 && (float)props.get("height")>20) {
										nuevoEnemigo=new Bee((float)props.get("x"), (float)props.get("y"), this.mainStage, this, mapa, casillaActualX, casillaActualY);
									}else {
										nuevoEnemigo=new Bee((float)props.get("x")-(float)props.get("width")/2, (float)props.get("y")-(float)props.get("height")/2, this.mainStage, this, mapa, casillaActualX, casillaActualY);
									}
									break;
								case 2:
									if((float)props.get("width")>20 && (float)props.get("height")>20) {
										nuevoEnemigo=new Wonwon((float)props.get("x"), (float)props.get("y"), this.mainStage, this, mapa, casillaActualX, casillaActualY);
									}else {
										nuevoEnemigo=new Wonwon((float)props.get("x")-(float)props.get("width")/2, (float)props.get("y")-(float)props.get("height")/2, this.mainStage, this, mapa, casillaActualX, casillaActualY);
									}
									break;
								case 3:
									if((float)props.get("width")>20 && (float)props.get("height")>20) {
										nuevoEnemigo=new GreenSlime((float)props.get("x"), (float)props.get("y"), this.mainStage, this, mapa, casillaActualX, casillaActualY);
									}else {
										nuevoEnemigo=new GreenSlime((float)props.get("x")-(float)props.get("width")/2, (float)props.get("y")-(float)props.get("height")/2, this.mainStage, this, mapa, casillaActualX, casillaActualY);
									}
									break;
								case 4:
									if((float)props.get("width")>20 && (float)props.get("height")>20) {
										nuevoEnemigo=new RedSlime((float)props.get("x"), (float)props.get("y"), this.mainStage, this, mapa, casillaActualX, casillaActualY);
									}else {
										nuevoEnemigo=new RedSlime((float)props.get("x")-(float)props.get("width")/2, (float)props.get("y")-(float)props.get("height")/2, this.mainStage, this, mapa, casillaActualX, casillaActualY);
									}
									break;
								default:
									break;
								}
								break;
							case 3:
								switch(this.generacionTiposEnemigos.nextInt(6)) {
								case 0:
									if((float)props.get("width")>20 && (float)props.get("height")>20) {
										nuevoEnemigo=new Bat((float)props.get("x"), (float)props.get("y"), this.mainStage, this, mapa, casillaActualX, casillaActualY);
									}else {
										nuevoEnemigo=new Bat((float)props.get("x")-(float)props.get("width")/2, (float)props.get("y")-(float)props.get("height")/2, this.mainStage, this, mapa, casillaActualX, casillaActualY);
									}
									break;
								case 1:
									if((float)props.get("width")>20 && (float)props.get("height")>20) {
										nuevoEnemigo=new Bee((float)props.get("x"), (float)props.get("y"), this.mainStage, this, mapa, casillaActualX, casillaActualY);
									}else {
										nuevoEnemigo=new Bee((float)props.get("x")-(float)props.get("width")/2, (float)props.get("y")-(float)props.get("height")/2, this.mainStage, this, mapa, casillaActualX, casillaActualY);
									}
									break;
								case 2:
									if((float)props.get("width")>20 && (float)props.get("height")>20) {
										nuevoEnemigo=new Wonwon((float)props.get("x"), (float)props.get("y"), this.mainStage, this, mapa, casillaActualX, casillaActualY);
									}else {
										nuevoEnemigo=new Wonwon((float)props.get("x")-(float)props.get("width")/2, (float)props.get("y")-(float)props.get("height")/2, this.mainStage, this, mapa, casillaActualX, casillaActualY);
									}
									break;
								case 3:
									if((float)props.get("width")>20 && (float)props.get("height")>20) {
										nuevoEnemigo=new GreenSlime((float)props.get("x"), (float)props.get("y"), this.mainStage, this, mapa, casillaActualX, casillaActualY);
									}else {
										nuevoEnemigo=new GreenSlime((float)props.get("x")-(float)props.get("width")/2, (float)props.get("y")-(float)props.get("height")/2, this.mainStage, this, mapa, casillaActualX, casillaActualY);
									}
									break;
								case 4:
									if((float)props.get("width")>20 && (float)props.get("height")>20) {
										nuevoEnemigo=new RedSlime((float)props.get("x"), (float)props.get("y"), this.mainStage, this, mapa, casillaActualX, casillaActualY);
									}else {
										nuevoEnemigo=new RedSlime((float)props.get("x")-(float)props.get("width")/2, (float)props.get("y")-(float)props.get("height")/2, this.mainStage, this, mapa, casillaActualX, casillaActualY);
									}
									break;
								case 5:
									if((float)props.get("width")>20 && (float)props.get("height")>20) {
										nuevoEnemigo=new Ratfolk((float)props.get("x"), (float)props.get("y"), this.mainStage, this, mapa, casillaActualX, casillaActualY);
									}else {
										nuevoEnemigo=new Ratfolk((float)props.get("x")-(float)props.get("width")/2, (float)props.get("y")-(float)props.get("height")/2, this.mainStage, this, mapa, casillaActualX, casillaActualY);
									}
									break;
								default:
									break;
								}
								break;
							}
							this.enemigos.add(nuevoEnemigo);
						}
						break;
				
				}
				
				
			}
		}
		aparicionRecurso = new Random();
		
		 switch (aparicionRecurso.nextInt(10)) {
		case 0:
		case 1:
		case 2:
		case 3:
			if(!casillaInicial){
			Corazon corazon = new Corazon(197, 152, mainStage, mapa, casillaActualX, casillaActualY);
			}
			break;
		}
		 
		

		this.player=new Player(this.inicioX,this.inicioY,this.mainStage,this, mapa, casillaActualX, casillaActualY);

		
		this.uiStage=new Stage();
		
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
		if(this.casillaInicial) {
			this.inicioX=180;
			this.inicioY=145;
		}else {
			//inicioX=190;
			//inicioY=155;
		}
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		this.mainStage.act();
		this.uiStage.act();
		colide();
		estadoSala();
	    
	    Parametros.jugadorx=player.getX();
	    Parametros.jugadory=player.getY();
	  
	    
	  
	    centrarCamara();
	    this.renderer.setView(this.camara);
	    this.renderer.render();
	    actualizarInterfaz();
	    this.mainStage.draw();
	    this.uiStage.draw();
	   
	    

	}
	
	protected void estadoSala() {
		if(!this.completada) {
			int contadorEnemigos = 0;
			for(Enemy listaEnemigos : this.enemigos) {
				if(listaEnemigos.getEnabled()) {
					contadorEnemigos++;
				}
			}
			if(contadorEnemigos<=0) {
				this.completada = true;
			}
		}else {
			for(Solid puerta : this.puertas) {
	    		puerta.setEnabled(false);
			}
		}
	    
	}

	public void colide() {
		
		for(Solid solid:this.paredes) {
			if(this.player.overlaps(solid)) {
				this.player.preventOverlap(solid);
				
			}
		}
		
		for(Solid solid:this.puertas) {
			if(solid.getEnabled() && this.player.overlaps(solid)) {
				this.player.preventOverlap(solid);
				
			}
		}

		for(int i=0;i<this.salidas.size;i++) {
			if(salidas.get(i)!=null && this.player.overlaps(this.salidas.get(i))) {
				this.player.velocity.x=0;
				this.player.velocity.y=0;
				switch (this.salidas.get(i).name) {
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
		
		for(int i=0;i<this.enemigos.size;i++) {
			for(int j=0;j<this.enemigos.size;j++) {
				if(this.enemigos.get(i)!=null && this.enemigos.get(j)!=null
						&& this.enemigos.get(i).getEnabled() && this.enemigos.get(j).getEnabled()
						&& this.enemigos.get(i)!=this.enemigos.get(j) && this.enemigos.get(i).overlaps(this.enemigos.get(j))) {
					this.enemigos.get(i).preventOverlap(this.enemigos.get(j));
					this.enemigos.get(j).preventOverlap(this.enemigos.get(i));
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
		this.camara.update();
		
	}
	
	
	public ArrayList<MapObject> getRectangleList(String propertyName){
		ArrayList<MapObject> list =new ArrayList<MapObject>();
		for(MapLayer layer: this.map.getLayers()) {
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
		for(MapLayer layer: this.map.getLayers()) {
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
		for(MapLayer layer: this.map.getLayers()) {
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
