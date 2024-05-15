package HR152213.desafiopractico3

import HR152213.desafiopractivo3.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

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

        ///Darle clic a un elemento de la lista y poder borrarlo
        listView.setOnItemClickListener { parent, view, position, id ->
            val listaSeleccionada = parent.getItemAtPosition(position) as String
            val idLista = listaSeleccionada.split(".")[0].toInt()
            val builder = AlertDialog.Builder(this)
            builder.setTitle("¿Que deseas realizar?")
            builder.setMessage("Selecciona una opción")
            builder.setPositiveButton("Actualizar") { dialog, which ->

                val intent = Intent(this, actualizarListaCompras::class.java)
                intent.putExtra("idLista", idLista)
                startActivity(intent)
            }
            builder.setNegativeButton("Borrar") { dialog, which ->
                conexion.borrarLista(idLista)
                Toast.makeText(this, "Producto borrado", Toast.LENGTH_SHORT).show()
                val listado = conexion.mostrarListas()
                val miAdaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, listado)
                listView.adapter = miAdaptador
            }
            val dialog = builder.create()
            dialog.show()
        }




    }



}