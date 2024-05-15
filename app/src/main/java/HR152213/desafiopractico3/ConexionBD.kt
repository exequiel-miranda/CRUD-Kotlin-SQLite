package HR152213.desafiopractico3

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

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




}