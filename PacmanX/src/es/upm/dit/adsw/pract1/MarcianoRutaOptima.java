package es.upm.dit.adsw.pract1;

/**
 * Marcianito que analiza la forma mas corta de llegar al jugador y empieza a
 * recorrerla.
 * 
 * @author Manuel Toro Legaz
 * @version 07/4/2012
 */
public class MarcianoRutaOptima extends Marciano {

	/**
	 * Constructor.
	 * 
	 * @param laberinto
	 *            el laberinto en el que se mueve.
	 * @param celda
	 *            posicion inicial.
	 * @param dt
	 *            delta de tiempo para irse moviendo.
	 */
	public MarcianoRutaOptima(Laberinto laberinto, Celda celda, int dt) {
		super(laberinto, celda, dt);
		setImagen("Anibal.png");
	}

	/**
	 * Determina en que direccion vamos a movernos. Calcula la ruta optima entre
	 * el marciano y la posicion actual del jugador y da un primer paso en esa
	 * ruta. Hay que recalcular continuamente porque el jugador puede moverse de
	 * sitio.
	 * 
	 * @return direccion del proximo movimiento.
	 */
	protected Direccion seleccionDireccion() {
		Laberinto l = getLaberinto();
		Jugador j = l.getJugador();
		Celda celdaJugador = j.getCelda();
		int[][] distancias = MapaDistancias.getDistancias(l, celdaJugador);
		Celda celdaMarciano = getCelda();
		int mX = celdaMarciano.getPunto().getX();
		int mY = celdaMarciano.getPunto().getY();

		Direccion optima = null;
		int minima = distancias[mX][mY];
		/*
		 * System.out.println(minima);
		 * System.out.println(distancias[mX+1][mY]);//este
		 * System.out.println(distancias[mX-1][mY]);//oeste
		 * System.out.println(distancias[mX][mY+1]);//norte
		 * System.out.println(distancias[mX][mY-1]);//sur
		 */if (l.getConexion(celdaMarciano, Direccion.ESTE) != null
				&& distancias[mX + 1][mY] == (minima - 1)) {
			optima = Direccion.ESTE;
			minima = distancias[mX + 1][mY];

		}
		if (l.getConexion(celdaMarciano, Direccion.OESTE) != null
				&& distancias[mX - 1][mY] == (minima - 1)) {
			optima = Direccion.OESTE;
			minima = distancias[mX - 1][mY];

		}
		if (l.getConexion(celdaMarciano, Direccion.NORTE) != null
				&& distancias[mX][mY + 1] == (minima - 1)) {
			optima = Direccion.NORTE;
			minima = distancias[mX][mY + 1];

		}
		if (l.getConexion(celdaMarciano, Direccion.SUR) != null
				&& distancias[mX][mY - 1] == (minima - 1)) {
			optima = Direccion.SUR;
			minima = distancias[mX][mY - 1];

		}

		return optima;
	}

}
