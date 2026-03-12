package com.daniela.calculadora

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var oper: Int = 0
    var numero: Double = 0.0
    lateinit var txt_anterior: TextView
    lateinit var txt_actual: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        txt_actual = findViewById(R.id.txtActual)
        txt_anterior = findViewById(R.id.txtAnterior)

        val btnBorrar: Button = findViewById(R.id.btnBorrar)
        val btnIgual: Button = findViewById(R.id.btnIgual)

        btnIgual.setOnClickListener {
            val numeroDos = txt_actual.text.toString()
                .replace(",", "")
                .toDoubleOrNull() ?: return@setOnClickListener
            var respuesta = 0.0

            if (oper != 0) {
                when (oper) {
                    1 -> respuesta = numero + numeroDos
                    2 -> respuesta = numero - numeroDos
                    3 -> respuesta = numero * numeroDos
                    4 -> {
                        if (numeroDos == 0.0) {
                            txt_actual.text = "Error"
                            txt_anterior.text = ""
                            oper = 0
                            return@setOnClickListener
                        }
                        respuesta = numero / numeroDos
                    }
                }

                txt_actual.text = formatearResultado(respuesta)
                txt_anterior.text = ""
                oper = 0
                numero = 0.0
            }
        }

        btnBorrar.setOnClickListener {
            txt_anterior.text = ""
            txt_actual.text = ""
            numero = 0.0
            oper = 0
        }
    }

    private fun formatearResultado(valor: Double): String {
        val resultado = if (valor == valor.toLong().toDouble()) {
            valor.toLong().toString()
        } else {
            valor.toString()
        }
        return formatearConPuntos(resultado)
    }

    fun presionarDigito(view: View) {
        val numeroActual: String = txt_actual.text.toString().replace(",", "")

        when (view.id) {
            R.id.btnCero -> txt_actual.text = formatearConPuntos(numeroActual + "0")
            R.id.btnUno -> txt_actual.text = formatearConPuntos(numeroActual + "1")
            R.id.btnDos -> txt_actual.text = formatearConPuntos(numeroActual + "2")
            R.id.btnTres -> txt_actual.text = formatearConPuntos(numeroActual + "3")
            R.id.btnCuatro -> txt_actual.text = formatearConPuntos(numeroActual + "4")
            R.id.btnCInco -> txt_actual.text = formatearConPuntos(numeroActual + "5")
            R.id.btnSeis -> txt_actual.text = formatearConPuntos(numeroActual + "6")
            R.id.btnSiete -> txt_actual.text = formatearConPuntos(numeroActual + "7")
            R.id.btnOcho -> txt_actual.text = formatearConPuntos(numeroActual + "8")
            R.id.btnNueve -> txt_actual.text = formatearConPuntos(numeroActual + "9")
            R.id.btnPunto -> {
                if (!numeroActual.contains(".")) {
                    txt_actual.text = if (numeroActual.isEmpty()) "0." else "$numeroActual."
                }
            }
        }
    }

    fun clickOperacion(view: View) {
        val numeroDos = txt_actual.text.toString()
            .replace(",", "").toDoubleOrNull() ?: return
        numero = numeroDos
        val numeroTexto = txt_actual.text.toString()
        txt_actual.setText("")

        when (view.id) {
            R.id.btnSumar -> { txt_anterior.setText("$numeroTexto +"); oper = 1 }
            R.id.btnMenos -> { txt_anterior.setText("$numeroTexto -"); oper = 2 }
            R.id.btnMultiplicacion -> { txt_anterior.setText("$numeroTexto x"); oper = 3 }
            R.id.btnDivision -> { txt_anterior.setText("$numeroTexto /"); oper = 4 }
        }
    }

    private fun formatearConPuntos(numero: String): String {
        if (numero.contains(".")) return numero
        return numero.toLongOrNull()?.let {
            "%,d".format(it)
        } ?: numero
    }
}