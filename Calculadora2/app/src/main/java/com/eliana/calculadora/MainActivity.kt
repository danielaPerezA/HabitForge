package com.eliana.calculadora

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
    }

    fun presionarDigito(view: View){
        val txt_actual: TextView = findViewById(R.id.txtActual)
        var numeroActual: String = txt_actual.text.toString().toString()

        when(view.id){
            R.id.btnCero -> txt_actual.setText(numeroActual + "0")
            R.id.btnUno -> txt_actual.setText(numeroActual + "1")
            R.id.btnDos -> txt_actual.setText(numeroActual + "2")
            R.id.btnTres -> txt_actual.setText(numeroActual + "3")
            R.id.btnCuatro -> txt_actual.setText(numeroActual + "4")
            R.id.btnCInco -> txt_actual.setText(numeroActual + "5")
            R.id.btnSeis -> txt_actual.setText(numeroActual + "6")
            R.id.btnSiete -> txt_actual.setText(numeroActual + "7")
            R.id.btnOcho -> txt_actual.setText(numeroActual + "8")
            R.id.btnNueve -> txt_actual.setText(numeroActual + "9")
            R.id.btnPunto -> txt_actual.setText(numeroActual + ".")
        }


    }
}