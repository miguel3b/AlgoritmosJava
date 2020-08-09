package com.example.studycalendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.Constants.Constants;
import com.example.dto.ActividadDiariaDTO;
import com.example.entity.EstudioDetalle;
import com.example.entity.TemaEstudio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static  final String TAG = "DatabaseHelper";
    private static  final String TEMA_ESTUDIO = "tema_estudio";
    private static  final String ESTUDIO_DETALLE= "estudio_detalle";
    private static  final String CATALOGO_SI_NO= "catalogo_sino";
    private static  final String TITLE = "title";
    private static  final String DESCRIPTION = "description";
    private static  final Integer VERSION = 15;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");


    public DatabaseHelper(Context context) {
        super(context, TEMA_ESTUDIO, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //TODO - como mejora se podrian marcar algunos campos como obligatorios
        String studyTable = "CREATE TABLE " + TEMA_ESTUDIO + "(id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TITLE + " text, "
                + DESCRIPTION + " text)";

        String catalogoSiNo = "CREATE TABLE " + CATALOGO_SI_NO + "(id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "descripcion TEXT)";

        String historialTable = "CREATE TABLE " + ESTUDIO_DETALLE + "(id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "id_tema_estudio INTEGER, "
                + "numero_repaso INTEGER, "
                + "fecha TEXT,"
                + "terminado INTEGER, "
                + "FOREIGN KEY(id_tema_estudio) REFERENCES tema_estudio(ID),"
                + "FOREIGN KEY(terminado) REFERENCES catalogo_sino(ID))";

        db.execSQL(studyTable);
        db.execSQL(catalogoSiNo);
        db.execSQL(historialTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS indexUniqueEstudioDetalle ON estudio_detalle (id_tema_estudio,numero_repaso)");
//        db.execSQL("DROP TABLE IF EXISTS " + TEMA_ESTUDIO);
//        db.execSQL("DROP TABLE IF EXISTS " + ESTUDIO_DETALLE);
//        db.execSQL("DROP TABLE IF EXISTS " + CATALOGO_SI_NO);
//        onCreate(db);
    }

    public long addData(TemaEstudio register){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DESCRIPTION,register.getDescription());
        contentValues.put(TITLE, register.getTitulo());

        Log.d(TAG, "registrarNuevoEstudio: Adding" + register.toString() + " to " + TEMA_ESTUDIO);

        long result = db.insert(TEMA_ESTUDIO,null,contentValues);
        db.close();
        return result;
    }

    public void deleteData(){

        SQLiteDatabase db = this.getWritableDatabase();
        int delete = db.delete(ESTUDIO_DETALLE, "ID IN(?,?,?,?,?)", new String[]{"4", "5", "6", "7", "8"});
        db.close();
    }

    public long registrarNuevoEstudio(EstudioDetalle estudioDetalle){


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_tema_estudio", estudioDetalle.getIdTemaEstudio());
        contentValues.put("numero_repaso", estudioDetalle.getNumeroRepaso());
        contentValues.put("fecha", dateFormat.format(estudioDetalle.getFechaEstudio()));
        contentValues.put("terminado", estudioDetalle.getTerminado());

        Log.d(TAG, "registrarNuevoEstudio: Adding " + estudioDetalle.toString() + " to " + ESTUDIO_DETALLE);

        long result = db.insert(ESTUDIO_DETALLE,null,contentValues);

        db.close();
        return result;
    }


    public List<TemaEstudio> getListContents() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM  " + TEMA_ESTUDIO + " order by id desc ", null);


        ArrayList<TemaEstudio> temaEstudioList = new ArrayList<>();
        if (data.getCount() > 0) {

            while (data.moveToNext()) {
                TemaEstudio temaEstudio = new TemaEstudio();
                temaEstudio.setId(data.getLong(0));
                temaEstudio.setTitulo(data.getString(1));
                temaEstudio.setDescription(data.getString(2));
                temaEstudioList.add(temaEstudio);
            }
        }

        data.close(); // todo - buenas practicas
        db.close();
        return temaEstudioList;
    }

    public ArrayList<ActividadDiariaDTO> getActivitiesByDate(Date fecha) {
        SQLiteDatabase db = this.getWritableDatabase();



        StringBuilder sentence = new StringBuilder("SELECT ed.id as idEstudioDetalle,* FROM tema_estudio er "+
                "inner join estudio_detalle ed on er.id = ed.id_tema_estudio " +
                "left join catalogo_sino csn on csn.id = ed.terminado ");

        if(fecha != null) {
            String format = dateFormat.format(fecha);
            sentence.append("where ed.fecha = '" + format + "'");
        }

        sentence.append(" order by ed.id_tema_estudio, ed.numero_repaso desc");

        Cursor data = db.rawQuery(sentence.toString(), null);

        ArrayList<ActividadDiariaDTO> studyTableList = new ArrayList<>();
        if (data.getCount() > 0) {

            while (data.moveToNext()) {
                ActividadDiariaDTO actividadDiariaDTO = new ActividadDiariaDTO();
                actividadDiariaDTO.setTitulo(data.getString(data.getColumnIndex("title")));
                actividadDiariaDTO.setDescription(data.getString(data.getColumnIndex("description")));
                actividadDiariaDTO.setTerminado(data.getInt(data.getColumnIndex("terminado")));

                try {
                    actividadDiariaDTO.setFechaEstudio(dateFormat.parse(data.getString(data.getColumnIndex("fecha"))));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                actividadDiariaDTO.setIdEstudio(data.getLong(data.getColumnIndex("idEstudioDetalle")));
                actividadDiariaDTO.setNumeroRepaso(data.getInt(data.getColumnIndex("numero_repaso")));
                actividadDiariaDTO.setTerminado(data.getInt(data.getColumnIndex("terminado")));
                studyTableList.add(actividadDiariaDTO);
            }
        }

        data.close(); // todo - buenas practicas
        db.close();
        return studyTableList;
    }


    public ArrayList<ActividadDiariaDTO> getRepasosNoRealizados() {
        SQLiteDatabase db = this.getWritableDatabase();


        StringBuilder sentence = new StringBuilder("SELECT ed.id as idEstudioDetalle,* FROM tema_estudio er " +
                "inner join estudio_detalle ed on er.id = ed.id_tema_estudio " +
                "left join catalogo_sino csn on csn.id = ed.terminado ");

        String format = dateFormat.format(new Date());
        sentence.append("where ed.fecha < '" + format + "' and ed.terminado = 2 ");

        sentence.append(" order by ed.id_tema_estudio, ed.numero_repaso desc");

        Cursor data = db.rawQuery(sentence.toString(), null);

        ArrayList<ActividadDiariaDTO> studyTableList = new ArrayList<>();
        if (data.getCount() > 0) {

            while (data.moveToNext()) {
                ActividadDiariaDTO actividadDiariaDTO = new ActividadDiariaDTO();
                actividadDiariaDTO.setTitulo(data.getString(data.getColumnIndex("title")));
                actividadDiariaDTO.setDescription(data.getString(data.getColumnIndex("description")));
                actividadDiariaDTO.setTerminado(data.getInt(data.getColumnIndex("terminado")));

                try {
                    actividadDiariaDTO.setFechaEstudio(dateFormat.parse(data.getString(data.getColumnIndex("fecha"))));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                actividadDiariaDTO.setIdEstudio(data.getLong(data.getColumnIndex("idEstudioDetalle")));
                actividadDiariaDTO.setNumeroRepaso(data.getInt(data.getColumnIndex("numero_repaso")));
                actividadDiariaDTO.setTerminado(data.getInt(data.getColumnIndex("terminado")));
                studyTableList.add(actividadDiariaDTO);
            }
        }

        data.close(); // todo - importante cerrar conexiones abiertas
        db.close();
        return studyTableList;
    }

    public ArrayList<EstudioDetalle> getDayActivities (){

        SQLiteDatabase db = this.getWritableDatabase();

        String currentDate = dateFormat.format(new Date());

        String query = "SELECT ed.id as idEstudioDetalle,* FROM  " + ESTUDIO_DETALLE  +" ed where fecha = '" + currentDate + "'" +
                " and terminado = " + Constants.ID_CATALOGO_SINO_NO;
        Cursor data = db.rawQuery(query , null);

        ArrayList<EstudioDetalle> listEstudioDetalle = new ArrayList<>();

        if(data.getCount() > 0){

            while (data.moveToNext()){

                EstudioDetalle estudioDetalle = new EstudioDetalle();
                estudioDetalle.setTerminado(data.getInt(data.getColumnIndex("terminado")));
                estudioDetalle.setId(data.getInt(data.getColumnIndex("idEstudioDetalle")));
                estudioDetalle.setIdTemaEstudio(data.getLong(data.getColumnIndex("id_tema_estudio")));

                try {
                    estudioDetalle.setFechaEstudio(dateFormat.parse(data.getString(data.getColumnIndex("fecha"))));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                estudioDetalle.setNumeroRepaso(data.getInt(data.getColumnIndex("numero_repaso")));
                listEstudioDetalle.add(estudioDetalle);
            }
        }

        data.close();
        db.close();
        return listEstudioDetalle;
    }

    public void actualizarEstadoActividadDia(Integer idCatalogoSiNo, Integer idEstudioDetalle){

        SQLiteDatabase db = this.getWritableDatabase();
        String sentencia = "update estudio_detalle set terminado = " + idCatalogoSiNo + " where id = " + idEstudioDetalle;
        db.execSQL(sentencia);
        db.close();

    }


    public long getCountByEstado(Integer estado){

        SQLiteDatabase db = this.getReadableDatabase();
        // TODO PENDIENTE VER SI EN EL CONTENT VALUES SE PUEDEN PASAR ARGUMENTOS (?)
        //ContentValues contentValues = new ContentValues();

        Calendar cal = Calendar.getInstance();
        String currentDate = dateFormat.format(cal.getTime());
        long numeroRegistros  = DatabaseUtils.queryNumEntries(db, ESTUDIO_DETALLE,
                "terminado=? and fecha <= '"+currentDate+"'", new String[] {estado.toString()});

        db.close();
        return numeroRegistros;
    }


    public ArrayList<EstudioDetalle> verificarTemasSinProximosRepasos(){

        SQLiteDatabase db = this.getWritableDatabase();
        Calendar cal = Calendar.getInstance();
        String currentDate = dateFormat.format(cal.getTime());

        String SQL = "SELECT id, terminado, fecha, max(numero_repaso) as numero_repaso, id_tema_estudio " +
                "FROM  estudio_detalle where id_tema_estudio not in(" +
                "select id_tema_estudio FROM estudio_detalle " +
                "where fecha >= '" + currentDate+"')" +
                "GROUP BY id_tema_estudio";

        Cursor data = db.rawQuery(SQL, null);


        ArrayList<EstudioDetalle> temaEstudioList = new ArrayList<>();
        if (data.getCount() > 0) {

            while (data.moveToNext()) {
                EstudioDetalle temaEstudio = new EstudioDetalle();
                temaEstudio.setId(data.getInt(data.getColumnIndex("id")));
                temaEstudio.setTerminado(data.getInt(data.getColumnIndex("terminado")));

                try {
                    temaEstudio.setFechaEstudio(dateFormat.parse(data.getString(data.getColumnIndex("fecha"))));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                temaEstudio.setNumeroRepaso(data.getInt(data.getColumnIndex("numero_repaso")));
                temaEstudio.setIdTemaEstudio(data.getLong(data.getColumnIndex("id_tema_estudio")));
                temaEstudioList.add(temaEstudio);
            }
        }

        data.close(); // todo - buenas practicas
        db.close();
        return temaEstudioList;




    }

    public void deleteDuplicated(){

        SQLiteDatabase db = this.getWritableDatabase();
        int delete = db.delete(ESTUDIO_DETALLE, "ID in (?)", new String[]{ "111"});
        db.close();
    }

    public void backUp() {
        try {
            File sd = Environment.getDataDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//com.example.studycalendar//databases//dbname.db";
                String backupDBPath = "dbname.db";

                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                Log.d("backupDB path", "" + backupDB.getAbsolutePath());

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }

                Log.d("RespaldoExitoso", "El respaldo se genero con exito:" + backupDB.getAbsolutePath());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
