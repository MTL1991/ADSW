package es.upm.dit.adsw.rr;

/**
 * Clase que define la estructura de los objetos Restaurante y que permite
 * modificar los parametros de los mismos
 * 
 * @author Manuel Toro
 * @version 1.1
 */
public class Restaurante implements Comparable<Restaurante> {

	private String nombre;
	private String direccion;
	private String telefono;
	private String tipoComida;

	/**
	 * 
	 * @param nombre
	 *            nombre del restaurante
	 * @param direccion
	 *            direccion del restaurante
	 * @param telefono
	 *            telefono del restaurante
	 * @param tipoComida
	 *            tipo de comida que ser sirve en el restaurante
	 */
	public Restaurante(String nombre, String direccion, String telefono,
			String tipoComida) {
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
		this.tipoComida = tipoComida;
	}

	/**
	 * getter
	 * 
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * getter
	 * 
	 * @return direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * getter
	 * 
	 * @return telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * getter
	 * 
	 * @return tipoComida
	 */
	public String getTipoComida() {
		return tipoComida;
	}

	/**
	 * Metodo que devuelve la cadena con los datos a aparecer por pantalla
	 * 
	 * @return cadena con el nombre y la direccion del restaurante
	 */
	public String toString() {
		return "Restaurante:" + getNombre() + ". Direccion:" + getDireccion();
	}

	/**
	 * Metodo para comparar restaurantes return entero valor 0,>0 o <0 en
	 * funcion de lo obtenido en la comparacion
	 */
	@Override
	public int compareTo(Restaurante r) {
		return this.toString().compareTo(r.toString());
	}

}
