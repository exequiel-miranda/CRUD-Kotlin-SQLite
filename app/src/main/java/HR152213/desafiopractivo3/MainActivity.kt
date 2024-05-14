package HR152213.desafiopractivo3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast

class MainActivity : AppCompatActivity() {



    lateinit var conexion: ConexionBD
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txtFecha = findViewById<EditText>(R.id.txtFecha)
        val txtTitulo = findViewById<EditText>(R.id.txtTitulo)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        conexion = ConexionBD(this)

        //////////Mostrar datos
        val listado = conexion.mostrarLista()
        val listView = findViewById<ListView>(R.id.lstCompras)
        val miAdaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, listado)
        listView.adapter = miAdaptador

        ////////////Agregar datos
        btnGuardar.setOnClickListener {
            conexion.CrearLista(txtFecha.text.toString(), txtTitulo.text.toString())
            Toast.makeText(this, "Lista de compras guardados", Toast.LENGTH_LONG).show()
            val listado = conexion.mostrarLista()
            val miAdaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, listado)
            listView.adapter = miAdaptador
        }


    }



}