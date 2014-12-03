package es.upm.dit.adsw.IMC;

import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class CalculadoraIMCActivity<DataPicker> extends Activity {
	private EditText cuadroAltura = null;
	private EditText cuadroPeso = null;
	private TextView mostrarIMC = null;
	private TextView gradoIMC = null;
	private DatePicker fechaNacimiento = null;
	private TextView mostrarIGCE = null;
	private RadioGroup sexoBoton = null;
	private static String TRAZA = "traza";

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		cuadroAltura = (EditText) findViewById(R.id.introducirAltura);
		cuadroPeso = (EditText) findViewById(R.id.introducirPeso);
		mostrarIMC = (TextView) findViewById(R.id.muestraIMC);
		gradoIMC = (TextView) findViewById(R.id.gradoIMC);
		fechaNacimiento = (DatePicker) findViewById(R.id.datePicker1);
		mostrarIGCE = (TextView) findViewById(R.id.mostrarIGCE);
		sexoBoton = (RadioGroup) findViewById(R.id.radioGroup1);

	}

	public void calculaIMC(View v) {
		double altura = 0.0;
		double peso = 0.0;

		try {
			altura = Double.parseDouble(cuadroAltura.getText().toString());

		} catch (NumberFormatException e) {
			Toast toast = Toast.makeText(this, R.string.altura,
					Toast.LENGTH_LONG);
			
			toast.show();
		}
		try {
			peso = Double.parseDouble(cuadroPeso.getText().toString());

		} catch (NumberFormatException e) {
			Toast toast = Toast
					.makeText(this, R.string.peso, Toast.LENGTH_LONG);
			toast.show();
		}
		double iMC = peso / ((altura / 100) * (altura / 100));
		mostrarIMC.setText(String.valueOf(iMC));
		
		if (iMC < 18.5) {
			gradoIMC.setText("infrapeso");
		}
		if (18.5 <= iMC && iMC < 25) {
			gradoIMC.setText("normal");
		}
		if (iMC >= 25 && iMC < 30) {
			gradoIMC.setText("sobrepeso");
		}
		if (iMC >= 30) {
			gradoIMC.setText("obeso");
		}

		

	}

	private int calculaEdad(int anio, int mes, int dia) {
		Calendar c = Calendar.getInstance();
		c.set(anio, mes, dia);
		
		int diferencia=(Calendar.getInstance().get(Calendar.YEAR) - c
				.get(Calendar.YEAR));
		Log.i(TRAZA,"diferenccia"+diferencia);
		return diferencia;

	}

	public void calculaIGCE(View v) {

		int anio = fechaNacimiento.getYear();
		int mes = fechaNacimiento.getMonth();
		int dia = fechaNacimiento.getDayOfMonth();
		int edad = calculaEdad(anio, mes, dia);
		double sexo = 0;

		int selectedId = sexoBoton.getCheckedRadioButtonId();
		RadioButton sexoElegido = (RadioButton) findViewById(selectedId);
		String generoStr = sexoElegido.getText().toString();
		String mujer = getString(R.string.mujer);

		Log.e(TRAZA, generoStr);
		if (generoStr.equals(mujer)) {
			sexo = 0;
		} else {
			sexo = 1;
		}
		try {
			double iMC = Double.parseDouble(mostrarIMC.getText().toString());
			if (edad <= 15) {

				mostrarIGCE.setText(String.valueOf((1.51 * iMC) - (0.70 * edad)
						- (3.6 * sexo) + 1.4));
			}
			if (edad > 15) {

				mostrarIGCE.setText(String.valueOf((1.20 * iMC) + (0.23 * edad)
						- (10.8 * sexo) + 5.4));
			}
		} catch (NumberFormatException e) {
			Toast toast = Toast.makeText(this, R.string.calculoDeIMC,
					Toast.LENGTH_LONG);
			toast.show();
		}

	}
}
