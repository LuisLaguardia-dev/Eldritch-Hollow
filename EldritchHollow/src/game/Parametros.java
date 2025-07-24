package game;

public class Parametros {

//Screen
 //private static int anchoPantalla=1200;
 //private static int altoPantalla=900;
 
 private static int anchoPantalla=800;
 private static int altoPantalla=600;
 
 public static boolean debug=false;
 
 //Audio;
 public static float musicVolume=0.5f;
 public static float soundVolume=0.5f;
 
 
// public static float zoom=0.24f;
 public static float zoom=0.5f;
 
 //variables de juego
 
 public static int nivel=1;
 
 public static float jugadorx=0;
 public static float jugadory=0;
 public static int danyo=1;
 public static float velocidad = 50;
 public static int vida=6;
 public static int maxVida=6;
 public static int maxTotalVida=24;
 public static boolean contenedorCorazonRecogido = false;
 public static boolean EspadaRecogido = false;
 public static boolean BotasRecogido = false;
 

 
 
 
 



public static int getAnchoPantalla() {
	return anchoPantalla;
}

public static void setAnchoPantalla(int anchoPantalla) {
	Parametros.anchoPantalla = anchoPantalla;
}

public static int getAltoPantalla() {
	return altoPantalla;
}

public static void setAltoPantalla(int altoPantalla) {
	Parametros.altoPantalla = altoPantalla;
}




 
}
