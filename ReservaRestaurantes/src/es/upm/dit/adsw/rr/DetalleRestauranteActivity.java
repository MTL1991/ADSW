package es.upm.dit.adsw.rr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Clase necesaria par a la actividad Detalle Restaurante
 * 
 * @author Manuel Toro
 * @version 1.1
 * 
 */
public class DetalleRestauranteActivity extends Activity {

	private EditText cuadroRestaurante = null;
	private EditText cuadroDireccion = null;
	private EditText cuadroTelefono = null;
	private RadioGroup elegirComida = null;
	private Button botonGuardar = null;
	private Button botonCancelar = null;
	private ListaRestaurantesSingleton s;
	private static final String TRAZAGUARDAR = "guardado";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_actividad_detalle);
		cuadroRestaurante = (EditText) findViewById(R.id.editText1);
		cuadroDireccion = (EditText) findViewById(R.id.editText2);
		cuadroTelefono = (EditText) findViewById(R.id.editText3);
		elegirComida = (RadioGroup) findViewById(R.id.radioGroup1);
		botonGuardar = (Button) findViewById(R.id.button1);
		botonGuardar.setOnClickListener(new MyButtonSaveOnClickListener());
		botonCancelar = (Button) findViewById(R.id.button2);
		botonCancelar.setOnClickListener(new MyButtonCancelOnClickListener());
		s = ListaRestaurantesSingleton.getSingleton();
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			int posicion = extras.getInt("Posicion");
			Restaurante r = ListaRestaurantesSingleton.getSingleton()
					.getArrayRestaurante().get(posicion);
			cuadroRestaurante.setText(r.getNombre());
			Log.i(TRAZAGUARDAR, r.getNombre());
			cuadroDireccion.setText(r.getDireccion());
			Log.i(TRAZAGUARDAR, r.getDireccion());
			cuadroTelefono.setText(r.getTelefono());
			Log.i(TRAZAGUARDAR, r.getTelefono());
			String tipoComida = r.getTipoComida();
			if (tipoComida.compareTo(" Tradicional") == 0 || tipoComida.compareTo("Tradicional") == 0) {
				elegirComida.check(R.id.radio0);
				Log.i(TRAZAGUARDAR, tipoComida);
			}
			if (tipoComida.compareTo(" Internacional") == 0 || tipoComida.compareTo("Internacional") == 0) {
				elegirComida.check(R.id.radio1);
				Log.i(TRAZAGUARDAR, tipoComida);
			}
			if (tipoComida.compareTo(" Fast-food") == 0 || tipoComida.compareTo("Fast-food")==0 ) {
				elegirComida.check(R.id.radio2);
				Log.i(TRAZAGUARDAR, tipoComida);
			}

			Log.i(TRAZAGUARDAR, "tiene que salir todo");

		}

	}

	/**
	 * Clase privada con todos los pasos que seguira la aplicacion cuando se
	 * pulse el boton cancelar
	 * 
	 * @author Manuel
	 * 
	 */
	private class MyButtonCancelOnClickListener implements OnClickListener {
		public void onClick(View v) {
			cuadroRestaurante.setText("");
			cuadroTelefono.setText("");
			cuadroDireccion.setText("");
			elegirComida.check(-1);
			onBackPressed();
			Intent intent = new Intent(DetalleRestauranteActivity.this,
					ListaRestaurantesActivity.class);
			startActivity(intent);
		}
	}

	/**
	 * Clase privada con todos los pasos que seguira la aplicacion cuando se
	 * pulse el boton guardar.
	 * 
	 * @author Manuel
	 * 
	 */
	private class MyButtonSaveOnClickListener implements View.OnClickListener {
		public void onClick(View v) {
			String restaurante = cuadroRestaurante.getText().toString();
			Log.i(TRAZAGUARDAR, restaurante);
			String direccion = cuadroDireccion.getText().toString();
			Log.i(TRAZAGUARDAR, direccion);
			String telefono = cuadroTelefono.getText().toString();
			Log.i(TRAZAGUARDAR, telefono);
			int selectedId = elegirComida.getCheckedRadioButtonId();
			try {
				RadioButton tipo = (RadioButton) findViewById(selectedId);
				String tipoComida = tipo.getText().toString();
				Restaurante restauranteGuardar = new Restaurante(restaurante,
						direccion, telefono, tipoComida);
				Log.i(TRAZAGUARDAR, tipoComida);
				if (restaurante.equals("")) {
					Toast toast = Toast.makeText(getApplicationContext(),
							"Introduzca el nombre del restaurante",
							Toast.LENGTH_LONG);
					toast.show();
					Log.e(TRAZAGUARDAR, "Se interrumpio el guardado");
				} else {
					try {
						int posicion = getIntent().getExtras().getInt(
								"Posicion");
						s.modificarRestaurante(posicion, restauranteGuardar);
						Log.e(TRAZAGUARDAR, String.valueOf(posicion));

						for (Restaurante r : s.getArrayRestaurante()) {
							Log.e(TRAZAGUARDAR, "Restaurante:" + r.getNombre());
						}
						onBackPressed();
						Intent intent = new Intent(
								DetalleRestauranteActivity.this,
								ListaRestaurantesActivity.class);
						startActivity(intent);
					} catch (Exception e) {

						// listaRestaurantes.add(restauranteGuardar);
						s.introducirRestaurante(restauranteGuardar);

						Log.i(TRAZAGUARDAR, "se ha guardado con exito");
						for (Restaurante r : s.getArrayRestaurante()) {
							Log.i(TRAZAGUARDAR, "Restaurante:" + r.getNombre());
						}
						onBackPressed();
						Intent intent = new Intent(
								DetalleRestauranteActivity.this,
								ListaRestaurantesActivity.class);
						startActivity(intent);
					}

				}

			} catch (Exception e) {
				Toast toast = Toast.makeText(getApplicationContext(),
						"Elija un tipo de comida", Toast.LENGTH_LONG);
				toast.show();
				Log.e(TRAZAGUARDAR, "Se interrumpio el guardado");
			}
		}

	}
}
