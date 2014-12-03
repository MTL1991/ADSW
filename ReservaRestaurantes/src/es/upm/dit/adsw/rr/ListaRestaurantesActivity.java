package es.upm.dit.adsw.rr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import android.app.ListActivity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Clase usada para la actividad principal que contiene todos los metodos
 * relativos al apartado 2 y en gran medida al 3
 * 
 * @author Manuel Toro
 * @version 1.1
 * 
 */
public class ListaRestaurantesActivity extends ListActivity {
	public static final String CARGANDO = "Cargando:";
	private static final String ORDENAR = "Ordenando:";
	private ListView lv;
	private ArrayAdapter<Restaurante> aA;
	private ListaRestaurantesSingleton s;
	private int MNU_OPC1 = 1;
	private int MNU_OPC2 = 2;
	private int MNU_OPC3 = 3;
	private ActualizaListaTask tareaAsyncTask;
	private ProgressBar progressBar;
	private TextView textView;
	private Boolean cargandoLista;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		progressBar.setVisibility(View.INVISIBLE);
		lv = (ListView) findViewById(android.R.id.list);
		textView = (TextView) findViewById(R.id.listaVacia);
		cargandoLista = false;
		s = ListaRestaurantesSingleton.getSingleton();
		if (!s.getArrayRestaurante().isEmpty()) {
			textView.setText("");
		}

		aA = new ArrayAdapter<Restaurante>(this,
				android.R.layout.simple_list_item_1, s.getArrayRestaurante());
		lv.setAdapter(aA);

		aA.notifyDataSetChanged();

	}

	/**
	 * Metodo que se preocupa de comprobar si se ha pulsado un restaurante de la
	 * lista y en caso afirmativo iniciar la actividad DetalleRestaurante
	 */
	@Override
	public void onListItemClick(ListView l, View v, int posicion, long id) {
		super.onListItemClick(l, v, posicion, id);
		if (cargandoLista) {
			return;
		}
		Intent intent = new Intent(getApplication(),
				DetalleRestauranteActivity.class);
		intent.putExtra("Posicion", posicion);
		startActivity(intent);

	}

	/**
	 * Metodo que crea los botones que saldran al pulsar el boton menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// MenuInflater inflater = getMenuInflater();
		// inflater.inflate(R.menu.menu, menu);
		menu.add(Menu.NONE, MNU_OPC1, Menu.NONE, "Añadir").setIcon(
				R.drawable.ic_menu_tick);
		menu.add(Menu.NONE, MNU_OPC2, Menu.NONE, "Cargar").setIcon(
				R.drawable.ic_menu_tick);
		menu.add(Menu.NONE, MNU_OPC3, Menu.NONE, "Ordenar").setIcon(
				R.drawable.ic_menu_tick);
		return true;
	}

	/**
	 * Metodo que le da la implementacion a cada caso de los botones que genera
	 * onCreateOptionsMenu
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case 1:
			Intent intent = new Intent(ListaRestaurantesActivity.this,
					DetalleRestauranteActivity.class);
			startActivity(intent);
			return true;
		case 2:
			if (!s.getListaCargada()) {

				tareaAsyncTask = new ActualizaListaTask();
				tareaAsyncTask.execute();
				s.setListaCargada(true);
			} else {
				Toast toast = Toast.makeText(getApplicationContext(),
						"La lista ya estaba cargada", Toast.LENGTH_LONG);
				toast.show();
			}
			return true;

		case 3:
			Log.i(ORDENAR,"Empiezo con la ordenacion");

			ordenar();
			Log.i(ORDENAR, "He acabado con la ordenacion");
			for (Restaurante r : s.getArrayRestaurante()) {
				Log.i("despuesdeOrdenar", "Restaurante:" + r.getNombre());
			}
			return true;
		default:

			return super.onOptionsItemSelected(item);
		}

	}

	/**
	 * Metodo para ordenar la lista de restaurantes alfabeticamente.
	 */
	public void ordenar() {
		if (s.getArrayRestaurante().isEmpty()) {
			Toast toast = Toast.makeText(getApplicationContext(),
					"No hay nada que ordenar", Toast.LENGTH_LONG);
			toast.show();
			return;
		}
		for (Restaurante r : s.getArrayRestaurante()) {
			Log.i(ORDENAR, "Restaurante:" + r.getNombre());
		}

		quicksort(s.getArrayRestaurante(), 0, s.getArrayRestaurante().size());

	}

	/**
	 * Metodo auxiliar que es llamado para ordenar por el algoritmo quick sort
	 * 
	 * @param listaRestaurantes
	 *            lista a ordenar
	 * @param a
	 *            posicion inferior desde la que ordenamos
	 * @param z
	 *            posicion superior desde la que ordenamos
	 */
	private void quicksort(ArrayList<Restaurante> listaRestaurantes, int a,
			int z) {
		int media = (int) (a + z) / 2;
		Restaurante pivoteR = listaRestaurantes.get(media);
		Log.i(ORDENAR, "alla vamos1");
		int inf1 = a;
		int sup = z;
		while (inf1 < sup) {
			Log.i(ORDENAR, "alla vamos2");
			while (listaRestaurantes.get(inf1).compareTo(pivoteR) < 0) {
				Log.i(ORDENAR, "entro aqui1");
				inf1++;

			}
			while (pivoteR.compareTo(listaRestaurantes.get(sup - 1)) < 0) {
				Log.i(ORDENAR, "entro aqui2");
				sup--;

			}
			if (inf1 < sup) {
				Log.i(ORDENAR, "voy a cambiar1");
				Restaurante rTemporal = listaRestaurantes.get(inf1);
				listaRestaurantes.set(inf1, listaRestaurantes.get(sup - 1));
				listaRestaurantes.set(sup - 1, rTemporal);

				inf1++;
				sup--;
				onResume();
			}
		}
		if (a < sup) {
			quicksort(listaRestaurantes, a, sup);
			Log.i(ORDENAR, "empiezo1");
		}
		if (inf1 < z) {
			quicksort(listaRestaurantes, inf1, z);
			Log.i(ORDENAR, "empiezo2");
		}
	}

	/**
	 * Clase privada que ejecuta todos los metodos necesarios para obtener la
	 * informacion desde un archivo de texto, guardarlos en la lista de
	 * restaurantes y posteriormente mostrarlos por pantalla
	 * 
	 * @author Manuel
	 * 
	 */
	private class ActualizaListaTask extends AsyncTask<Void, Integer, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressBar.setVisibility(View.VISIBLE);
			cargandoLista = true;

		}

		@Override
		protected Void doInBackground(Void... unused) {
			try {
				ArrayList<Restaurante> restaurantes = cargarRestaurantes();
				int progreso = 100 / restaurantes.size();
				;
				for (Restaurante rActual : restaurantes) {
					ListaRestaurantesSingleton.getSingleton()
							.introducirRestaurante(rActual);
					publishProgress(progreso);
					Thread.sleep(2000);
				}
			} catch (InterruptedException e) {
				Log.i(CARGANDO, "Interrumpido");
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
			super.onProgressUpdate(progress);
			aA.notifyDataSetChanged();
			progressBar.incrementProgressBy(progress[0]);

		}

		@Override
		protected void onPostExecute(Void unused) {
			progressBar.setVisibility(View.INVISIBLE);
			textView.setVisibility(View.INVISIBLE);
			cargandoLista = false;
		}

		private ArrayList<Restaurante> cargarRestaurantes() {
			ArrayList<Restaurante> restaurantes = new ArrayList<Restaurante>();
			AssetManager am = getResources().getAssets();
			InputStream entrada = null;
			try {
				entrada = am.open("restaurantes.txt");
				InputStreamReader ir = new InputStreamReader(entrada);
				BufferedReader bf = new BufferedReader(ir);

				String linea = bf.readLine();
				while (linea != null) {
					try {
						String[] datos = linea.split(";");
						restaurantes.add(new Restaurante(datos[0], datos[1],
								datos[2], datos[3]));
						Log.i(CARGANDO,datos[0]+""+datos[1]+""
								+datos[2]+""+ datos[3]);
						Thread.sleep(2000);
						linea = bf.readLine();

					} catch (Exception e) {
						linea = bf.readLine();
					}
				}
				entrada.close();

			} catch (IOException e) {
				Log.i(CARGANDO, "Imposible abrir el fichero");
			}
			return restaurantes;
		}

	}

	@Override
	public void onResume() {
		super.onResume();
		aA.notifyDataSetChanged();
	}
}
