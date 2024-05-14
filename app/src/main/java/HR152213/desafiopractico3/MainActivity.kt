package HR152213.desafiopractico3

import HR152213.desafiopractivo3.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    lateinit var conexion: ConexionBD
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        conexion = ConexionBD(this)

        //////////Mostrar las listas de compras
        val listado = conexion.mostrarListas()
        val listView = findViewById<ListView>(R.id.lstCompras)
        val miAdaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, listado)
        listView.adapter = miAdaptador


        val btnAgregarListado = findViewById<Button>(R.id.btnAgregarListado)
        btnAgregarListado.setOnClickListener {
            val agregarListadoCompras = Intent(this, AgregarListasDeCompras::class.java)
            startActivity(agregarListadoCompras)
        }

    }



}