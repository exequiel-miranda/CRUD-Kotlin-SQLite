package HR152213.desafiopractico3

import HR152213.desafiopractivo3.R
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class ConexionBD(context: Context): SQLiteOpenHelper(context, nombreBD, factory, version) {

    companion object{
        internal val nombreBD = "dbCompras"
        internal val factory = null
        internal val version = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE lista_compra( id_lista INTEGER PRIMARY KEY AUTOINCREMENT, fecha varchar(50), titulo varchar(50))")
        db?.execSQL("CREATE TABLE productos_lista( id_producto_lista INTEGER PRIMARY KEY AUTOINCREMENT, id_lista INTEGER NOT NULL, producto varchar(50), FOREIGN KEY (id_lista) REFERENCES lista_compra(id_lista))")
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun CrearLista(fecha: String, titulo: String){
        val baseDatos: SQLiteDatabase = writableDatabase
        val valores: ContentValues = ContentValues()

        valores.put("fecha", fecha)
        valores.put("titulo", titulo)

        baseDatos.insert("lista_compra", null, valores)
        baseDatos.close()
    }

    fun agregarProducto(producto: String, id_lista:Int){
        val baseDatos: SQLiteDatabase = writableDatabase
        val valores: ContentValues = ContentValues()

        valores.put("id_lista", id_lista)
        valores.put("producto", producto)

        baseDatos.insert("productos_lista", null, valores)
        baseDatos.close()
    }

    fun mostrarListas(): List<String>{
        val baseDatos: SQLiteDatabase = writableDatabase
        val cursor = baseDatos.rawQuery("SELECT * FROM lista_compra", null)

        val miLista = mutableListOf<String>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val fecha = cursor.getString(1)
            val titulo = cursor.getString(2)

            val datos = "$id. $titulo - $fecha"
            miLista.add(datos)
        }
        cursor.close()
        baseDatos.close()
        return miLista
    }

    fun mostrarProductos(ultimoID: Int): List<String>{
        val baseDatos: SQLiteDatabase = writableDatabase
        val cursor = baseDatos.rawQuery("SELECT * FROM productos_lista WHERE id_lista = $ultimoID", null)

        val miLista = mutableListOf<String>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val id_lista = cursor.getString(1)
            val producto = cursor.getString(2)

            val datos = "$producto"
            miLista.add(datos)
        }
        cursor.close()
        baseDatos.close()
        return miLista
    }

    @SuppressLint("Range")
    fun idUltimaLista(): Int {
        val db = this.readableDatabase
        var id = 0
        val query = "SELECT MAX(id_lista) AS max_id FROM lista_compra"
        val cursor: Cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex("max_id"))
        }
        cursor.close()
        db.close()
        return id
    }

    fun borrarLista(idLista: Any): Boolean {
        val db = this.writableDatabase
        return try {
            val query = "DELETE FROM lista_compra WHERE id_lista = $idLista"
            db.execSQL(query)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        } finally {
            db.close()
        }
    }

    fun borrarProducto(idProducto: Int) {
        val baseDatos: SQLiteDatabase = writableDatabase
        // Eliminar el producto de la tabla productos_lista
        baseDatos.delete("productos_lista", "id_producto_lista = ?", arrayOf(idProducto.toString()))
    }

        @SuppressLint("Range")
    fun datosDeLaListaParaActualizar(activity: AppCompatActivity, idLista: Any): Boolean {
        val baseDatos: SQLiteDatabase = writableDatabase
        val cursor = baseDatos.rawQuery(
            "SELECT pl.id_producto_lista, lc.fecha, lc.titulo, pl.producto FROM lista_compra lc INNER JOIN productos_lista pl ON lc.id_lista = pl.id_lista WHERE lc.id_lista = $idLista",
            null
        )
        var success = false
        if (cursor.moveToFirst()) {
            val fecha = cursor.getString(cursor.getColumnIndex("fecha"))
            val titulo = cursor.getString(cursor.getColumnIndex("titulo"))
            val editTextFecha = activity.findViewById<EditText>(R.id.txtFechaEditar)
            val editTextTitulo = activity.findViewById<EditText>(R.id.txtTituloEditar)

            editTextFecha.setText(fecha)
            editTextTitulo.setText(titulo)
            success = true
        }

        val productos = mutableListOf<String>()
        while (cursor.moveToNext()) {
            val idProducto = cursor.getInt(cursor.getColumnIndex("id_producto_lista"))
            val producto = cursor.getString(cursor.getColumnIndex("producto"))
            productos.add("$idProducto. $producto")
        }
        // Mostrar los productos en el ListView
        val listViewProductos = activity.findViewById<ListView>(R.id.lstProductosEditar)
        val adaptador = ArrayAdapter(activity, android.R.layout.simple_list_item_1, productos)
        listViewProductos.adapter = adaptador


        cursor.close()
        baseDatos.close()
        return success
    }

    fun mostrarProductosActualizar(idListaL: Int): List<String>{
        val baseDatos: SQLiteDatabase = writableDatabase
        val cursor = baseDatos.rawQuery("SELECT * FROM productos_lista WHERE id_lista = $idListaL", null)

        val miLista = mutableListOf<String>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val id_lista = cursor.getString(1)
            val producto = cursor.getString(2)

            val datos = "$id. $producto"
            miLista.add(datos)
        }
        cursor.close()
        baseDatos.close()
        return miLista
    }

    fun actualizarListaCompra(idLista: Int, nuevaFecha: String, nuevoTitulo: String) {
        val baseDatos: SQLiteDatabase = writableDatabase

        val contentValues = ContentValues()
        contentValues.put("fecha", nuevaFecha)
        contentValues.put("titulo", nuevoTitulo)
        baseDatos.update("lista_compra", contentValues, "id_lista = ?", arrayOf(idLista.toString()))

        baseDatos.close()
    }

    fun actualizarNombreProducto(idProducto: Int, nuevoNombre: String) {
        val baseDatos: SQLiteDatabase = writableDatabase
        val contentValues = ContentValues()
        contentValues.put("producto", nuevoNombre)
        baseDatos.update("productos_lista", contentValues, "id_producto_lista = ?", arrayOf(idProducto.toString()))
        baseDatos.close()
    }


}