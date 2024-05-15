package HR152213.desafiopractico3

import HR152213.desafiopractivo3.R
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AgregarListasDeCompras : AppCompatActivity() {
    lateinit var conexion: ConexionBD
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_listas_de_compras)
        conexion = ConexionBD(this)

        //Crear una lista de compras nueva
       val txtFecha = findViewById<EditText>(R.id.txtFecha)
       val txtTitulo = findViewById<EditText>(R.id.txtTitulo)
       val btnCrearListaCompras = findViewById<Button>(R.id.btnCrearListaCompras)


        btnCrearListaCompras.setOnClickListener {
           conexion.CrearLista(txtFecha.text.toString(), txtTitulo.text.toString())
            val pantallaAgregarProductos = Intent(this, agregarProductos::class.java)
            startActivity(pantallaAgregarProductos)
       }
    }
}