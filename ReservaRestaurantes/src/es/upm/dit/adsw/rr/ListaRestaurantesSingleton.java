package es.upm.dit.adsw.rr;

import java.util.ArrayList;

/**
 * Clase tipo Singleton
 * 
 * @author Manuel Toro
 * @version 1.1
 */

public class ListaRestaurantesSingleton {

	private static ListaRestaurantesSingleton singleton = null;
	private ArrayList<Restaurante> listaRestaurantes;
	private boolean listaCargada;

	/**
	 * Constructor
	 */
	private ListaRestaurantesSingleton() {
		listaRestaurantes = new ArrayList<Restaurante>();
		listaCargada = false;
	}

	/**
	 * Metodo que crea el objeto Singleton si no exite
	 * 
	 * @return objeto de la clase
	 */
	public static synchronized ListaRestaurantesSingleton getSingleton() {

		if (singleton == null) {
			singleton = new ListaRestaurantesSingleton();
		}
		return singleton;
	}

	/**
	 * Metodo que devuelve la lista de restaurantes
	 * 
	 * @return listaRestaurantes lista de restaurantes.
	 */

	public ArrayList<Restaurante> getArrayRestaurante() {

		return listaRestaurantes;
	}

	/**
	 * Metodo que permite añadir nuevos restaurantes
	 * 
	 * @param r
	 *            restaurante a añadir
	 */
	public void introducirRestaurante(Restaurante r) {
		listaRestaurantes.add(r);
	}

	/**
	 * Metodo que modifica el restaurante r en la posicion especificada
	 * 
	 * @param posicion
	 *            posicion en la lista
	 * @param r
	 *            restaurante a añadir
	 */
	public void modificarRestaurante(int posicion, Restaurante r) {
		listaRestaurantes.set(posicion, r);
	}

	/**
	 * metodo que modifica el valor de listaCargada
	 * 
	 * @param b
	 *            veracidad o no de la lectura de la lista
	 */
	public void setListaCargada(boolean b) {
		listaCargada = b;

	}

	/**
	 * metodo que devuelve el valor de listaCargada
	 * 
	 * @return listaCargada
	 */
	public boolean getListaCargada() {
		return listaCargada;

	}

}
