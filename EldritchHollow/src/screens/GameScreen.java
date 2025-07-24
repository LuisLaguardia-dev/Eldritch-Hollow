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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;

import elements.Element;
import elements.Enemy;
import elements.Bat;
import elements.Player;
import elements.Solid;
import game.Demo;
import game.Parametros;
import managers.AudioManager;
import managers.ResourceManager;



public class GameScreen extends BScreen{
	
Stage mainStage;

public Array<Solid> suelo;

Solid end;

private ArrayList<ArrayList<Double>> distancias;
public OrthographicCamera camara;
private TiledMap map;
private OrthogonalTiledMapRenderer renderer;
private int tileWidth, tileHeight, mapWidthInTiles, mapHeightInTiles,
mapWidthInPixels, mapHeightInPixels;
private int[] pPlano=new int[] {3};


float inicioX;
float inicioY;

Random randomNumbers = new Random();
private byte level = 1;
public ArrayList<ArrayList<GenericStage>> mapaPiso;
private ArrayList<ArrayList<Integer>> esquema;
private final byte TAMANYO_MAZMORRA_X = 9;
private final byte TAMANYO_MAZMORRA_Y = 9;
private int cantidadHabitaciones = (int) (randomNumbers.nextInt(2) + 5 + level * 2.6);
private int casillaInicialX = randomNumbers.nextInt(9);
private int casillaInicialY = randomNumbers.nextInt(9);
public int casillaActualX = casillaInicialX;
public int casillaActualY = casillaInicialY;
	   

	public GameScreen(Demo game) {
	
		super(game);
		int cantidadHabitacionesPorGenerar = this.cantidadHabitaciones; 
		mapaPiso = new ArrayList<>();
		for(int i=0;i<TAMANYO_MAZMORRA_Y;i++) {
			ArrayList<GenericStage> fila = new ArrayList<>();
			for(int j=0;j<TAMANYO_MAZMORRA_X;j++) {
				fila.add(null);
			}
			mapaPiso.add(fila);
		}
		esquema = new ArrayList<>();
		for(int i=0;i<TAMANYO_MAZMORRA_Y;i++) {
			ArrayList<Integer> fila = new ArrayList<>();
			for(int j=0;j<TAMANYO_MAZMORRA_X;j++) {
				fila.add(null);
			}
			esquema.add(fila);
		}
		rellenarEsquema(casillaInicialX, casillaInicialY, "");
		
		generarMazmorra(cantidadHabitacionesPorGenerar);
	
		mainStage=new Stage();
		
		
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		super.render(delta);
		inicioNivel(casillaActualX, casillaActualY);

	}
	
	public void muerte() {
		this.game.setScreen(new DeathScreen(game));
	}
	
	public void inicioNivel(int casillaX, int casillaY) {
		this.casillaActualX=casillaX;
		this.casillaActualY=casillaY;
		game.setScreen(mapaPiso.get(casillaActualX).get(casillaActualY));
	}
	
	public void cambioNivel(int casillaX, int casillaY, String name) {
		float tpX;
		float tpY;
		this.casillaActualX=casillaX;
		this.casillaActualY=casillaY;
		
		if(mapaPiso.get(casillaActualX).get(casillaActualY) instanceof CurrentStage) {
			CurrentStage casilla = (CurrentStage)mapaPiso.get(casillaActualX).get(casillaActualY);
			AudioManager.playMusic("assets/audio/music/musicaGeneral.mp3");
			switch (name) {
			case "E":
				tpX = casilla.entradas.get(1).getX()-casilla.entradas.get(1).getWidth()/2;
				tpY = casilla.entradas.get(1).getY()-casilla.entradas.get(1).getHeight()/2;
				casilla.player.setPosition(tpX, tpY);
				break;
			case "O":
				tpX = casilla.entradas.get(0).getX()-casilla.entradas.get(0).getWidth()/2;
				tpY = casilla.entradas.get(0).getY()-casilla.entradas.get(0).getHeight()/2;
				casilla.player.setPosition(tpX, tpY);
				break;
			case "N":
				tpX = casilla.entradas.get(3).getX()-casilla.entradas.get(3).getWidth()/2;
				tpY = casilla.entradas.get(3).getY()-casilla.entradas.get(3).getHeight()/2;
				casilla.player.setPosition(tpX, tpY);
				break;
			case "S":
				tpX = casilla.entradas.get(2).getX()-casilla.entradas.get(2).getWidth()/2;
				tpY = casilla.entradas.get(2).getY()-casilla.entradas.get(2).getHeight()/2;
				casilla.player.setPosition(tpX, tpY);
				break;
			default:
				break;
			}
		}else if(mapaPiso.get(casillaActualX).get(casillaActualY) instanceof BossStage){
			BossStage casilla = (BossStage)mapaPiso.get(casillaActualX).get(casillaActualY);
			AudioManager.playMusic("assets/audio/music/musicaJefe.mp3");
			switch (name) {
			case "E":
				tpX = casilla.entradas.get(1).getX()-casilla.entradas.get(1).getWidth()/2;
				tpY = casilla.entradas.get(1).getY()-casilla.entradas.get(1).getHeight()/2;
				casilla.player.setPosition(tpX, tpY);
				break;
			case "O":
				tpX = casilla.entradas.get(0).getX()-casilla.entradas.get(0).getWidth()/2;
				tpY = casilla.entradas.get(0).getY()-casilla.entradas.get(0).getHeight()/2;
				casilla.player.setPosition(tpX, tpY);
				break;
			case "N":
				tpX = casilla.entradas.get(3).getX()-casilla.entradas.get(3).getWidth()/2;
				tpY = casilla.entradas.get(3).getY()-casilla.entradas.get(3).getHeight()/2;
				casilla.player.setPosition(tpX, tpY);
				break;
			case "S":
				tpX = casilla.entradas.get(2).getX()-casilla.entradas.get(2).getWidth()/2;
				tpY = casilla.entradas.get(2).getY()-casilla.entradas.get(2).getHeight()/2;
				casilla.player.setPosition(tpX, tpY);
				break;
			default:
				break;
			}
		}
		else if(mapaPiso.get(casillaActualX).get(casillaActualY) instanceof TreasureStage){
			TreasureStage casilla = (TreasureStage)mapaPiso.get(casillaActualX).get(casillaActualY);
			AudioManager.playMusic("assets/audio/music/musicaGeneral.mp3");
			switch (name) {
			case "E":
				tpX = casilla.entradas.get(1).getX()-casilla.entradas.get(1).getWidth()/2;
				tpY = casilla.entradas.get(1).getY()-casilla.entradas.get(1).getHeight()/2;
				casilla.player.setPosition(tpX, tpY);
				break;
			case "O":
				tpX = casilla.entradas.get(0).getX()-casilla.entradas.get(0).getWidth()/2;
				tpY = casilla.entradas.get(0).getY()-casilla.entradas.get(0).getHeight()/2;
				casilla.player.setPosition(tpX, tpY);
				break;
			case "N":
				tpX = casilla.entradas.get(3).getX()-casilla.entradas.get(3).getWidth()/2;
				tpY = casilla.entradas.get(3).getY()-casilla.entradas.get(3).getHeight()/2;
				casilla.player.setPosition(tpX, tpY);
				break;
			case "S":
				tpX = casilla.entradas.get(2).getX()-casilla.entradas.get(2).getWidth()/2;
				tpY = casilla.entradas.get(2).getY()-casilla.entradas.get(2).getHeight()/2;
				casilla.player.setPosition(tpX, tpY);
				break;
			default:
				break;
			}
		}
		
		game.setScreen(mapaPiso.get(casillaActualX).get(casillaActualY));
	}
	
	//Añade las casillas al mapa
	private void generarMazmorra(int cantidad) {
		while(cantidad>0) {
			int generaCasillaX = randomNumbers.nextInt(9);
			int generaCasillaY = randomNumbers.nextInt(9);
			if(seHaGeneradoCasilla(generaCasillaX, generaCasillaY, "default")) {
				cantidad--;
			}
			
		}
		
		for(int i=0;i<esquema.size();i++) {
			for(int j=0;j<esquema.get(i).size();j++) {
				if(esquema.get(i).get(j)==null) {
					System.out.print("- ");
				}else if(i==casillaInicialX && j==casillaInicialY) {
					System.out.print("I ");
				}else{
					System.out.print("* ");
				}
			}
			System.out.println();
		}
		
		generarHabitacionesEspeciales();
		
		cuentaAdyacentes();
		
		for(int i=0;i<mapaPiso.size();i++) {
			for(int j=0;j<mapaPiso.get(i).size();j++) {
				if(mapaPiso.get(i).get(j)==null) {
					System.out.print("- ");
				}else if(i==casillaInicialX && j==casillaInicialY) {
					System.out.print("I ");
				}else if(mapaPiso.get(i).get(j).getClass().getSimpleName().equals("CurrentStage")) {
					System.out.print("* ");
				}else if(mapaPiso.get(i).get(j).getClass().getSimpleName().equals("BossStage")){
					System.out.print("B ");
				}else {
					System.out.print("T ");
				}
			}
			System.out.println();
		}
	}
	
	private void generarHabitacionesEspeciales() {
		int contador=0;
		boolean jefeGenerado = false;
		boolean tesoroGenerado  = false;
		boolean tiendaGenerado  = false;
		while(!jefeGenerado) {
			if(contador==0) {
				if(seHaGeneradoBoss("boss", true)) {
					jefeGenerado=true;
				}
			}else {
				if(seHaGeneradoBoss("boss", false)) {
					jefeGenerado=true;
				}
			}
			contador++;
			
		}
		while(!tesoroGenerado) {
			if(seHaGeneradoTesoro("tesoro")) {
				tesoroGenerado=true;
			}
		}
		/*
		 * while(!tiendaGenerada){
		 * 		if(seHaGeneradoTienda("tienda")) {
					tiendaGenerado=true;
				}
		 * }
		 */
	}
	
	private boolean seHaGeneradoTienda(String tipoSala) {
		boolean encontrado = false;
		while(!encontrado) {
			int generaCasillaX = randomNumbers.nextInt(9);
			int generaCasillaY = randomNumbers.nextInt(9);

			if (existeCasilla(generaCasillaX, generaCasillaY) &&
			    esquema.get(generaCasillaX).get(generaCasillaY) != null &&
			    esquema.get(generaCasillaX).get(generaCasillaY) == 1) {

			    int[][] direcciones = {
			        {1, 0},   // Abajo
			        {-1, 0},  // Arriba
			        {0, 1},   // Derecha
			        {0, -1}   // Izquierda
			    };

			    for (int[] direccion : direcciones) {
			        int vecinoX = generaCasillaX + direccion[0];
			        int vecinoY = generaCasillaY + direccion[1];

			        if (existeCasilla(vecinoX, vecinoY) && esquema.get(vecinoX).get(vecinoY) == null) {
			            // Verifica si el vecino tiene solo una casilla adyacente libre
			            int contadorAdyacentesLibres = 0;
			            for (int[] otraDireccion : direcciones) {
			                int otraVecinoX = vecinoX + otraDireccion[0];
			                int otraVecinoY = vecinoY + otraDireccion[1];
			                if (existeCasilla(otraVecinoX, otraVecinoY) && esquema.get(otraVecinoX).get(otraVecinoY) == null) {
			                    contadorAdyacentesLibres++;
			                }
			            }

			            if (contadorAdyacentesLibres == 3) {
			                System.out.println("Adyacente libre en dirección: " + direccion[0] + ", " + direccion[1]);
			                System.out.println("Coordenadas: " + vecinoX + ", " + vecinoY);
			                rellenarEsquema(vecinoX, vecinoY, tipoSala);
			                encontrado = true;
			                return true;
			            }
			        }
			    }
			}
		}
		
		return false;
	}

	private boolean seHaGeneradoTesoro(String tipoSala) {
		boolean encontrado = false;
		while(!encontrado) {
			int generaCasillaX = randomNumbers.nextInt(9);
			int generaCasillaY = randomNumbers.nextInt(9);

			if (existeCasilla(generaCasillaX, generaCasillaY) &&
			    esquema.get(generaCasillaX).get(generaCasillaY) != null &&
			    esquema.get(generaCasillaX).get(generaCasillaY) == 1) {

			    int[][] direcciones = {
			        {1, 0},   // Abajo
			        {-1, 0},  // Arriba
			        {0, 1},   // Derecha
			        {0, -1}   // Izquierda
			    };

			    for (int[] direccion : direcciones) {
			        int vecinoX = generaCasillaX + direccion[0];
			        int vecinoY = generaCasillaY + direccion[1];

			        if (existeCasilla(vecinoX, vecinoY) && esquema.get(vecinoX).get(vecinoY) == null) {
			            // Verifica si el vecino tiene solo una casilla adyacente libre
			            int contadorAdyacentesLibres = 0;
			            for (int[] otraDireccion : direcciones) {
			                int otraVecinoX = vecinoX + otraDireccion[0];
			                int otraVecinoY = vecinoY + otraDireccion[1];
			                if (existeCasilla(otraVecinoX, otraVecinoY) && esquema.get(otraVecinoX).get(otraVecinoY) == null) {
			                    contadorAdyacentesLibres++;
			                }
			            }

			            if (contadorAdyacentesLibres == 3) {
			                System.out.println("Adyacente libre en dirección: " + direccion[0] + ", " + direccion[1]);
			                System.out.println("Coordenadas: " + vecinoX + ", " + vecinoY);
			                rellenarEsquema(vecinoX, vecinoY, tipoSala);
			                encontrado = true;
			                return true;
			            }
			        }
			    }
			}
		}
		
		return false;
	}

	private boolean seHaGeneradoBoss(String tipoSala, boolean primeraVez) {
		ArrayList<ArrayList<Double>> distancias;
		double maxDistancia = 0;
        int filaMax = 0;
        int columnaMax = 0;
        if(primeraVez) {
        	distancias = new ArrayList<>();
            for(int i=0;i<TAMANYO_MAZMORRA_Y;i++) {
    			ArrayList<Double> fila = new ArrayList<>();
    			for(int j=0;j<TAMANYO_MAZMORRA_X;j++) {
    				fila.add(null);
    			}
    			distancias.add(fila);
    		}
            this.distancias = distancias;
            for (int i = 0; i < esquema.size(); i++) {
                for (int j = 0; j < esquema.get(i).size(); j++) {
                    double distancia = Math.sqrt(Math.pow(i - this.casillaInicialX, 2) + Math.pow(j - this.casillaInicialY, 2));
                    this.distancias.get(i).set(j, distancia);
                    
                }
            }
        }
        
        for (int i = 0; i < esquema.size(); i++) {
            for (int j = 0; j < esquema.get(i).size(); j++) {
                if (esquema.get(i).get(j)!=null && this.distancias.get(i).get(j) > maxDistancia) {
                    maxDistancia = this.distancias.get(i).get(j);
                    filaMax = i;
                    columnaMax = j;
                }
            }
        }

        int cantidadAdyacentes = 0;
        if(existeCasilla(filaMax+1, columnaMax) && esquema.get(filaMax+1).get(columnaMax)!=null) {
        	cantidadAdyacentes++;
        }
        if(existeCasilla(filaMax-1, columnaMax) && esquema.get(filaMax-1).get(columnaMax)!=null) {
        	cantidadAdyacentes++;
        }
        if(existeCasilla(filaMax, columnaMax+1) && esquema.get(filaMax).get(columnaMax+1)!=null) {
        	cantidadAdyacentes++;
        }
        if(existeCasilla(filaMax, columnaMax-1) && esquema.get(filaMax).get(columnaMax-1)!=null) {
        	cantidadAdyacentes++;
        }
        if(cantidadAdyacentes>1) {
        	if(existeCasilla(filaMax+1, columnaMax) && esquema.get(filaMax+1).get(columnaMax)==null) {
        		rellenarEsquema(filaMax+1, columnaMax, tipoSala);
                return true;
            }
            if(existeCasilla(filaMax-1, columnaMax) && esquema.get(filaMax-1).get(columnaMax)==null) {
            	rellenarEsquema(filaMax-1, columnaMax, tipoSala);
                return true;
            }
            if(existeCasilla(filaMax, columnaMax+1) && esquema.get(filaMax).get(columnaMax+1)==null) {
            	rellenarEsquema(filaMax, columnaMax+1, tipoSala);
                return true;
            }
            if(existeCasilla(filaMax, columnaMax-1) && esquema.get(filaMax).get(columnaMax-1)==null) {
            	rellenarEsquema(filaMax, columnaMax-1, tipoSala);
                return true;
            }
            this.distancias.get(filaMax).set(columnaMax, 0.0);
            return false;
        }else {
        	rellenarEsquema(filaMax, columnaMax, tipoSala);
            return true;
        }
        
	}

	private void cuentaAdyacentes() {
		String direccion = "";
		for(int i=0;i<mapaPiso.size();i++) {
			for(int j=0;j<mapaPiso.get(i).size();j++) {
				if(esquema.get(i).get(j) != null) {
					if(i==casillaInicialX && j==casillaInicialY) {
						if(existeCasilla(i+1, j) && esquema.get(i+1).get(j) != null) {
							direccion += "D";
						}
						if(existeCasilla(i-1, j) && esquema.get(i-1).get(j) != null) {
							direccion += "U";
						}
						if(existeCasilla(i, j+1) && esquema.get(i).get(j+1) != null) {
							direccion += "R";
						}
						if(existeCasilla(i, j-1) && esquema.get(i).get(j-1) != null) {
							direccion += "L";
						}
						rellenarCasilla(i, j, direccion, true, false, false, false);
						direccion = "";
					}else {
						if(esquema.get(i).get(j)==2) {
							if(existeCasilla(i+1, j) && esquema.get(i+1).get(j) != null) {
								direccion += "bD";
							}else if(existeCasilla(i-1, j) && esquema.get(i-1).get(j) != null) {
								direccion += "bU";
							}else if(existeCasilla(i, j+1) && esquema.get(i).get(j+1) != null) {
								direccion += "bR";
							}else if(existeCasilla(i, j-1) && esquema.get(i).get(j-1) != null) {
								direccion += "bL";
							}
							rellenarCasilla(i, j, direccion, false, true, false, false);
						}else if(esquema.get(i).get(j)==3) {
							if(existeCasilla(i+1, j) && esquema.get(i+1).get(j) != null) {
								direccion += "tD";
							}else if(existeCasilla(i-1, j) && esquema.get(i-1).get(j) != null) {
								direccion += "tU";
							}else if(existeCasilla(i, j+1) && esquema.get(i).get(j+1) != null) {
								direccion += "tR";
							}else if(existeCasilla(i, j-1) && esquema.get(i).get(j-1) != null) {
								direccion += "tL";
							}
							rellenarCasilla(i, j, direccion, false, false, true, false);
						}else if(esquema.get(i).get(j)==4) {
							if(existeCasilla(i+1, j) && esquema.get(i+1).get(j) != null) {
								direccion += "sD";
							}else if(existeCasilla(i-1, j) && esquema.get(i-1).get(j) != null) {
								direccion += "sU";
							}else if(existeCasilla(i, j+1) && esquema.get(i).get(j+1) != null) {
								direccion += "sR";
							}else if(existeCasilla(i, j-1) && esquema.get(i).get(j-1) != null) {
								direccion += "sL";
							}
							rellenarCasilla(i, j, direccion, false, false, false, true);
						}else{
							if(existeCasilla(i+1, j) && esquema.get(i+1).get(j) != null) {
								direccion += "D";
							}
							if(existeCasilla(i-1, j) && esquema.get(i-1).get(j) != null) {
								direccion += "U";
							}
							if(existeCasilla(i, j+1) && esquema.get(i).get(j+1) != null) {
								direccion += "R";
							}
							if(existeCasilla(i, j-1) && esquema.get(i).get(j-1) != null) {
								direccion += "L";
							}
							if(existeCasilla(i+1, j) && esquema.get(i+1).get(j) != null && esquema.get(i+1).get(j) == 2 && existeCasilla(i, j) && esquema.get(i).get(j) == 1) {
								direccion+="-Db";
							}else if(existeCasilla(i-1, j) && esquema.get(i-1).get(j) != null && esquema.get(i-1).get(j) == 2 && existeCasilla(i, j) && esquema.get(i).get(j) == 1) {
								direccion+="-Ub";
							}else if(existeCasilla(i, j+1) && esquema.get(i).get(j+1) != null && esquema.get(i).get(j+1) == 2 && existeCasilla(i, j) && esquema.get(i).get(j) == 1) {
								direccion+="-Rb";
							}else if(existeCasilla(i, j-1) && esquema.get(i).get(j-1) != null && esquema.get(i).get(j-1) == 2 && existeCasilla(i, j) && esquema.get(i).get(j) == 1) {
								direccion+="-Lb";
							}
							rellenarCasilla(i, j, direccion, false, false, false, false);
						}
						
						direccion = "";
					}
					
			
				}
			}
			
		}
		
	}
	//rellena una casilla
	private void rellenarCasilla(int x, int y, String direccion, boolean inicial, boolean jefe, boolean tesoro, boolean tienda) {
		if(jefe) {
			System.out.println(direccion + ", " + x + ", " + y);
			mapaPiso.get(x).set(y, new BossStage(game, direccion, this, x, y));
		}else if(tesoro) {
			mapaPiso.get(x).set(y, new TreasureStage(game, direccion, this, x, y));
		}else if(tienda) {
			//mapaPiso.get(x).set(y, new ShopStage(game, direccion, this, x, y));
		}else{
			mapaPiso.get(x).set(y, new CurrentStage(game, direccion, inicial, this, x, y));
		}
		
	}

	//Rellena una casilla del esquema
	private void rellenarEsquema(int x, int y, String tipoSala) {
			switch(tipoSala) {
			case "boss":
				esquema.get(x).set(y, 2);
				break;
			case "tesoro":
				esquema.get(x).set(y, 3);
				break;
			case "tienda":
				esquema.get(x).set(y, 4);
			default:
				esquema.get(x).set(y, 1);
				break;
			}
		
	}
	
	//Devuelve si existe esa casilla
	private boolean existeCasilla(int x, int y) {
		return x>=0&&x<mapaPiso.size()&&y>=0&&y<mapaPiso.get(x).size();
	}
	
	//Devuelve si se ha rellenado alguna casilla
	private boolean seHaGeneradoCasilla(int x, int y, String tipoSala) {
		if(existeCasilla(x+1, y) && esquema.get(x+1).get(y)==null && esquema.get(x).get(y)!=null){
			rellenarEsquema(x+1, y, tipoSala);
			return true;
		}
		if(existeCasilla(x-1, y) && esquema.get(x-1).get(y)==null && esquema.get(x).get(y)!=null) {
			rellenarEsquema(x-1, y, tipoSala);
			return true;
		}
		if(existeCasilla(x, y+1) && esquema.get(x).get(y+1)==null && esquema.get(x).get(y)!=null) {
			rellenarEsquema(x, y+1, tipoSala);
			return true;
		}
		if(existeCasilla(x, y-1) && esquema.get(x).get(y-1)==null && esquema.get(x).get(y)!=null) {
			rellenarEsquema(x, y-1, tipoSala);
			return true;
		}
		return false;
	}
	
}