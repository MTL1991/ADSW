package es.upm.dit.adsw.pract1;

/**
 * Calcula el mapa de distancias. Para cada celda del laberinto, calcula a
 * cuantos saltos esta del jugador.
 * 
 * @author Jose A. Manas
 * @version 24/3/2012
 */
public class MapaDistancias {

	/**
	 * Calcula el mapa de distancias.
	 * 
	 * @param laberinto
	 *            el laberinto de trabajo.
	 * @param celda
	 *            donde esta el jugador.
	 * @return mapa de distancias.
	 */
	public static int[][] getDistancias(Laberinto laberinto, Celda celda) {
		int lado = laberinto.getLado();
		int[][] distancias = new int[lado][lado];
		for (int y = lado - 1; y >= 0; y--) {
			for (int x = 0; x < lado; x++) {
				distancias[x][y] = Integer.MAX_VALUE;
			}
		}
		set(laberinto, distancias, celda, 0);
		print(lado, distancias);
		return distancias;
	}

	/**
	 * Propaga la informacion de distancia a una nueva celda.
	 * 
	 * @param laberinto
	 *            el laberinto de trabajo.
	 * @param distancias
	 *            mapa de distancias que estamos rellenando.
	 * @param celda
	 *            celda cuya distancia acabamos de conocer y vamos a propagar.
	 * @param d
	 *            distancia de la celda d al jugador.
	 */
	private static void set(Laberinto laberinto, int[][] distancias,
			Celda celda, int d) {
		int x = celda.getPunto().getX();
		int y = celda.getPunto().getY();
		if (distancias[x][y] > d) {
			distancias[x][y] = d;
			if (laberinto.getConexion(celda, Direccion.NORTE) != null) {
				set(laberinto, distancias,
						laberinto.getConexion(celda, Direccion.NORTE), d + 1);
			}
			if (laberinto.getConexion(celda, Direccion.SUR) != null) {
				set(laberinto, distancias,
						laberinto.getConexion(celda, Direccion.SUR), d + 1);
			}
			if (laberinto.getConexion(celda, Direccion.ESTE) != null) {
				set(laberinto, distancias,
						laberinto.getConexion(celda, Direccion.ESTE), d + 1);
			}
			if (laberinto.getConexion(celda, Direccion.OESTE) != null) {
				set(laberinto, distancias,
						laberinto.getConexion(celda, Direccion.OESTE), d + 1);
			}

		} else {
			return;
		}

	}

	public static void print(int lado, int[][] distancias) {

		for (int y = lado - 1; y >= 0; y--) {
			for (int x = 0; x < lado; x++) {
				System.out.printf("%3d", distancias[x][y]);
			}
			System.out.println();
		}
		System.out.println();
	}
}
