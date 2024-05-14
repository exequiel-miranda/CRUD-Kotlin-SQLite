package HR152213.desafiopractivo3

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

       ////////////Agregar productos a la lista
       val txtFecha = findViewById<EditText>(R.id.txtFecha)
       val txtTitulo = findViewById<EditText>(R.id.txtTitulo)
        val txtProducto = findViewById<EditText>(R.id.txtProducto)
        val btnAgreagarProducto = findViewById<Button>(R.id.btnAgregarProducto)
       val btnCrearListaCompras = findViewById<Button>(R.id.btnCrearListaCompras)
        val lstProductos = findViewById<ListView>(R.id.lstProductos)

        btnAgreagarProducto.setOnClickListener {
           conexion.agregarProducto(txtProducto.text.toString(), 1)
           Toast.makeText(this, "Producto agregado", Toast.LENGTH_LONG).show()
            txtProducto.setText("")
           val listado = conexion.mostrarProductos()
           val miAdaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, listado)
           lstProductos.adapter = miAdaptador
       }
    }
}