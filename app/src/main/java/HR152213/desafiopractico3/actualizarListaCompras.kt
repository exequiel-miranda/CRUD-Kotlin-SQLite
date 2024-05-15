package HR152213.desafiopractico3

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import HR152213.desafiopractivo3.R
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast

class actualizarListaCompras : AppCompatActivity() {
    lateinit var conexion: ConexionBD
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_lista_compras)
        conexion = ConexionBD(this)
        val idLista = intent.getIntExtra("idLista", -1)
        conexion.datosListaParaActualizar(this, idLista)

        val btnActualizarListaCompras = findViewById<Button>(R.id.btnActualizarListaCompras)
        val txtFechaEditar = findViewById<EditText>(R.id.txtFechaEditar)
        val txtTituloEditar = findViewById<EditText>(R.id.txtTituloEditar)
        val txtProductoEditar = findViewById<EditText>(R.id.txtProductoEditar)
        val imgAtras = findViewById<ImageView>(R.id.imgAtras)


        btnActualizarListaCompras.setOnClickListener {
            val nuevaFecha = txtFechaEditar.text.toString()
            val nuevoTitulo = txtTituloEditar.text.toString()
            val nuevosProductos = txtProductoEditar.text.toString()
            conexion.actualizarListaCompra(idLista, nuevaFecha, nuevoTitulo, nuevosProductos)
            Toast.makeText(this, "Lista actualizada", Toast.LENGTH_SHORT).show()
        }

        imgAtras.setOnClickListener {
            val pantallaMain = Intent(this, MainActivity::class.java)
            startActivity(pantallaMain)
        }



    }
}