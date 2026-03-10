package com.daniela.calculadora

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    //0-> nada, 1-> suma, 2, resta 3-> mult, 4->división
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

        val btnBorrar : Button = findViewById(R.id.btnBorrar)
        val btnIgual: Button = findViewById(R.id.btnIgual)

        btnIgual.setOnClickListener {
            var numeroDos: Double = txt_actual.text.toString().toDouble()
            var respuesta: Double = 0.0

            if (oper != 0){
                when(oper){
                    1 -> respuesta = numero + numeroDos
                    2 -> respuesta = numero - numeroDos
                    3 -> respuesta = numero * numeroDos
                    4 -> respuesta = numero / numeroDos
                }

                txt_actual.text = respuesta.toString()
                txt_anterior.text = ""
            }
        }

        btnBorrar.setOnClickListener {
            txt_anterior.text = ""
            txt_actual.text = ""
            numero = 0.0
            oper = 0
        }
    }

    fun presionarDigito(view: View){
   //     val txt_actual: TextView = findViewById(R.id.txtActual) esto se comenta en el video debudo a la creación de la variable global
        var numeroActual: String = txt_actual.text.toString().toString()

        when(view.id){
            R.id.btnCero -> txt_actual.text = numeroActual + "0"
            R.id.btnUno -> txt_actual.text = numeroActual + "1"
            R.id.btnDos -> txt_actual.text = numeroActual + "2"
            R.id.btnTres -> txt_actual.text = numeroActual + "3"
            R.id.btnCuatro -> txt_actual.text = numeroActual + "4"
            R.id.btnCInco -> txt_actual.text = numeroActual + "5"
            R.id.btnSeis -> txt_actual.text = numeroActual + "6"
            R.id.btnSiete -> txt_actual.text = numeroActual + "7"
            R.id.btnOcho -> txt_actual.text = numeroActual + "8"
            R.id.btnNueve -> txt_actual.text = numeroActual + "9"
            R.id.btnPunto -> txt_actual.text = numeroActual + "."
        }
    }

    fun clickOperacion(view: View){
        numero = txt_actual.text.toString().toDouble()
        var numeroDos: String = txt_actual.text.toString()
        txt_actual.setText("")
        when(view.id){
            R.id.btnSumar -> {
                txt_anterior.setText(numeroDos + "+")
                oper = 1
            }
            R.id.btnMenos -> {
                txt_anterior.setText(numeroDos + "-")
                oper = 2
            }
            R.id.btnMultiplicacion -> {
                txt_anterior.setText(numeroDos + "x")
                oper = 3
            }
            R.id.btnDivision ->{
                txt_anterior.setText(numeroDos + "/")
                oper = 4
            }
        }
    }
}