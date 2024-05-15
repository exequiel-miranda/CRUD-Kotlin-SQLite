package HR152213.desafiopractico3

import HR152213.desafiopractivo3.R
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class agregarProductos : AppCompatActivity() {
    lateinit var conexion: ConexionBD
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_productos)
        conexion = ConexionBD(this)

        val txtProductos = findViewById<EditText>(R.id.txtProducto)
        val btnAgregarProducto = findViewById<Button>(R.id.btnAgregarProducto)
        val lstProductos = findViewById<ListView>(R.id.lstProductos)
        val btnCrearListaCompra = findViewById<Button>(R.id.btnCrearListaCompra)

        btnAgregarProducto.setOnClickListener {
            val ultimoID = conexion.idUltimaLista()
            conexion.agregarProducto(txtProductos.text.toString(), ultimoID)
            Toast.makeText(this, "Producto agregado", Toast.LENGTH_LONG).show()
            txtProductos.setText("")
            val listado = conexion.mostrarProductos(ultimoID)
            val miAdaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, listado)
            lstProductos.adapter = miAdaptador
        }

        btnCrearListaCompra.setOnClickListener {
            Toast.makeText(this, "Lista agregada", Toast.LENGTH_LONG).show()
            val pantallaMain = Intent(this, MainActivity::class.java)
            startActivity(pantallaMain)
        }

    }
}