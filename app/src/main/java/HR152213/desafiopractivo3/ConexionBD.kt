package HR152213.desafiopractivo3

import android.content.ContentValues
import android.content.Context
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

}