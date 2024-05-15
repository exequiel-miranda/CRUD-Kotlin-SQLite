package HR152213.desafiopractico3

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import HR152213.desafiopractivo3.R
import android.content.Intent
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class actualizarListaCompras : AppCompatActivity() {
    lateinit var conexion: ConexionBD
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_lista_compras)

        conexion = ConexionBD(this)
        val idListaL = intent.getIntExtra("idLista", -1)
        conexion.datosDeLaListaParaActualizar(this, idListaL)

        val btnActualizarListaCompras = findViewById<Button>(R.id.btnActualizarListaCompras)
        val txtFechaEditar = findViewById<EditText>(R.id.txtFechaEditar)
        val txtTituloEditar = findViewById<EditText>(R.id.txtTituloEditar)
        val imgAtras = findViewById<ImageView>(R.id.imgAtras)
        val lstProductosEditar = findViewById<ListView>(R.id.lstProductosEditar)


        btnActualizarListaCompras.setOnClickListener {
            val nuevaFecha = txtFechaEditar.text.toString()
            val nuevoTitulo = txtTituloEditar.text.toString()

            conexion.actualizarListaCompra(idListaL, nuevaFecha, nuevoTitulo)
            Toast.makeText(this, "Lista actualizada", Toast.LENGTH_SHORT).show()
        }

       fun actualizarProducto(Producto: String) {
           val builder = AlertDialog.Builder(this)
           builder.setTitle("Actualizar producto")

           val nombreProducto = EditText(this)
           nombreProducto.hint = "Producto"
           val idProducto = Producto.split(".")
           nombreProducto.setText(idProducto[0].substring(1))
           nombreProducto.setText(Producto)

           val layout = LinearLayout(this)
           layout.orientation = LinearLayout.VERTICAL
           layout.addView(nombreProducto)
           builder.setView(layout)

           builder.setPositiveButton("Actualizar") { dialog, which ->
               val nuevoNumeroProducto = nombreProducto.text.toString()
               val idProducto = idProducto[0].toInt()
               conexion.actualizarNombreProducto(idProducto, nuevoNumeroProducto)
               builder.setNegativeButton("Cancelar", null)
               builder.show()
           }
           builder.show()
       }
        
            lstProductosEditar.setOnItemClickListener { parent, view, position, id ->
                val listaSeleccionada = parent.getItemAtPosition(position) as String
                val idLista = listaSeleccionada.split(".")[0].toInt()
                val builder = AlertDialog.Builder(this)
                builder.setTitle("¿Que deseas realizar?")
                builder.setMessage("Selecciona una opción")
                builder.setPositiveButton("Actualizar") { dialog, which ->
                    actualizarProducto(listaSeleccionada)
                }
                builder.setNegativeButton("Borrar") { dialog, which ->
                    conexion.borrarProducto(idLista)
                    conexion.datosDeLaListaParaActualizar(this, idListaL)
                    val listado = conexion.mostrarProductosActualizar(idListaL)
                    val miAdaptador =
                        ArrayAdapter(this, android.R.layout.simple_list_item_1, listado)
                    lstProductosEditar.adapter = miAdaptador
                }
                val dialog = builder.create()
                dialog.show()
            }


            imgAtras.setOnClickListener {
                val pantallaMain = Intent(this, MainActivity::class.java)
                startActivity(pantallaMain)
            }


        }
    }
