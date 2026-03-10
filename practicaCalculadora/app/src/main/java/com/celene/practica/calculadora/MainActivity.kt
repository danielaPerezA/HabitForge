package com.celene.practica.calculadora

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    var operacionSeleccionada: Int = 0
    var primerNumero: Double = 0.0
    lateinit var textoPrevioPantalla: TextView
    lateinit var textoActualPantalla: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        textoActualPantalla = findViewById(R.id.txtActual)
        textoPrevioPantalla = findViewById(R.id.txtAnterior)

        val botonBorrar: Button = findViewById(R.id.btnBorrar)
        val botonIgual: Button = findViewById(R.id.btnIgual)

        botonIgual.setOnClickListener {
            val segundoNumero: Double = textoActualPantalla.text.toString().toDouble()
            var resultadoOperacion: Double = 0.0

            if (operacionSeleccionada != 0) {
                when (operacionSeleccionada) {
                    1 -> resultadoOperacion = primerNumero + segundoNumero
                    2 -> resultadoOperacion = primerNumero - segundoNumero
                    3 -> resultadoOperacion = primerNumero * segundoNumero
                    4 -> resultadoOperacion = primerNumero / segundoNumero
                }

                textoActualPantalla.text = resultadoOperacion.toString()
                textoPrevioPantalla.text = ""
            }
        }

        botonBorrar.setOnClickListener {
            textoPrevioPantalla.text = ""
            textoActualPantalla.text = ""
            primerNumero = 0.0
            operacionSeleccionada = 0
        }
    }

    fun presionarDigito(view: View) {
        val digitosPrevios: String = textoActualPantalla.text.toString()

        when (view.id) {
            R.id.btnCero -> textoActualPantalla.text = digitosPrevios + "0"
            R.id.btnUno -> textoActualPantalla.text = digitosPrevios + "1"
            R.id.btnDos -> textoActualPantalla.text = digitosPrevios + "2"
            R.id.btnTres -> textoActualPantalla.text = digitosPrevios + "3"
            R.id.btnCuatro -> textoActualPantalla.text = digitosPrevios + "4"
            R.id.btnCInco -> textoActualPantalla.text = digitosPrevios + "5"
            R.id.btnSeis -> textoActualPantalla.text = digitosPrevios + "6"
            R.id.btnSiete -> textoActualPantalla.text = digitosPrevios + "7"
            R.id.btnOcho -> textoActualPantalla.text = digitosPrevios + "8"
            R.id.btnNueve -> textoActualPantalla.text = digitosPrevios + "9"
            R.id.btnPunto -> textoActualPantalla.text = digitosPrevios + "."
        }
    }

    fun clickOperacion(view: View) {
        primerNumero = textoActualPantalla.text.toString().toDouble()
        val textoPrimerNumero: String = textoActualPantalla.text.toString()
        textoActualPantalla.setText("")

        when (view.id) {
            R.id.btnSumar -> {
                textoPrevioPantalla.setText(textoPrimerNumero + "+")
                operacionSeleccionada = 1
            }
            R.id.btnMenos -> {
                textoPrevioPantalla.setText(textoPrimerNumero + "-")
                operacionSeleccionada = 2
            }
            R.id.btnMultiplicacion -> {
                textoPrevioPantalla.setText(textoPrimerNumero + "x")
                operacionSeleccionada = 3
            }
            R.id.btnDivision -> {
                textoPrevioPantalla.setText(textoPrimerNumero + "/")
                operacionSeleccionada = 4
            }
        }
    }
}