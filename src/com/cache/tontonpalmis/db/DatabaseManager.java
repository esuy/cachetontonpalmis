package com.cache.tontonpalmis.db;

import java.util.ArrayList;
import java.util.List;

import com.cache.tontonpalmis.profil.Profil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseManager extends SQLiteOpenHelper {
	SQLiteDatabase database;

	public DatabaseManager(Context context) {
		super(context, "tontonpalmis.db", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.i("sqlite version", db.getVersion() + "");
		String phonemanager = "CREATE TABLE IF NOT EXISTS serial_number(phonemanager_id integer NOT NULL PRIMARY KEY,sim_serial varchar(30) NOT NULL,numero varchar(30) NOT NULL);";
		String profil = "CREATE TABLE IF NOT EXISTS profile (numero varchar(12) NOT NULL, id_profil bigint NOT NULL,lieu varchar(50) NOT NULL,operateur varchar(21) NOT NULL,pin varchar(30),photo text,active boolean DEFAULT true,status varchar(30) NOT NULL,authenticationtime  datetime,nom varchar(30) NOT NULL,sexe varchar(6) NOT NULL,age date NOT NULL, friends boolean DEFAULT false NOT NULL,waiting boolean DEFAULT false NOT NULL);";
		String msgChat = "CREATE TABLE messagechat (id_message bigint NOT NULL,useersent bigint NOT NULL,userrecieve bigint NOT NULL,sentdt datetime NOT NULL,\"read\" boolean DEFAULT false NOT NULL,readdt datetime,\"type\" varchar(30) NOT NULL,processed boolean DEFAULT false NOT NULL,message text NOT NULL);";
		db.execSQL(phonemanager);
		db.execSQL(profil);
		db.execSQL(msgChat);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		onCreate(db);

	}

	public void openInWritable() {
		if (!(this.database == this.getWritableDatabase()))
			this.database = this.getWritableDatabase();
	}

	public void openInReadable() {
		if (!(this.database == this.getReadableDatabase()))
			this.database = this.getReadableDatabase();
	}

	public void close() {
		try {
			this.database.close();
		} catch (Exception ex) {

		}
	}

	public boolean existingRecord(String selectQuery) {
		Cursor cursor = this.database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			cursor.close();
			return true;
		}
		return false;
	}

	public boolean isRequest(long id) {
		Cursor cursor = this.database.rawQuery(
				"SELECT  * FROM profile where id_profil=" + id, null);
		if (cursor.moveToFirst()) {
			boolean test = false;
			if (!cursor.isNull(cursor.getColumnIndex("waiting")))
				test = Boolean.parseBoolean(cursor.getString(cursor
						.getColumnIndex("waiting")));
			cursor.close();
			return test;

		}
		return false;
	}

	public void insertSerial_number(String serial, String numero) {
		this.openInWritable();
		ContentValues values = new ContentValues();
		values.put("phonemanager_id", 1);
		values.put("sim_serial", serial);
		values.put("numero", numero);
		this.database.insert("serial_number", null, values);
		this.close();
	}

	public String getNumero(String serial) {
		Log.i("processing", "Reading numero...");
		String numero = "";
		String selectQuery = "SELECT  * FROM serial_number WHERE sim_serial=\""
				+ serial + "\"";
		this.openInReadable();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				numero = cursor.getString(cursor.getColumnIndex("numero"));
				Log.i("numero", "Reading numero...");
			} while (cursor.moveToNext());
		}
		cursor.close();
		this.close();
		return numero;
	}

	public void insertProfil(Profil profil) {
		this.openInWritable();
		Log.i("profil...", profil.getStatus() + "---");
		ContentValues values = new ContentValues();
		values.put("numero", profil.getTel());
		values.put("id_profil", profil.getId());
		values.put("lieu", profil.getZone());
		values.put("operateur", profil.getOperator());
		if (profil.getPin() != null || profil.getPin() != "")
			values.put("pin", profil.getPin());
		if (profil.getPhoto() != null || profil.getPhoto() != "")
			values.put("photo", profil.getPhoto());
		values.put("active", profil.isActive());
		values.put("status", profil.getStatus());
		values.put("authenticationtime", profil.getLasttime());
		values.put("nom", profil.getName());
		values.put("sexe", profil.getSexe());
		values.put("age", profil.getDate_naiss());
		values.put("friends", profil.isFriends());
		values.put("waiting", profil.isWaitting());
		if (this.existingRecord("SELECT  * FROM profile where id_profil="
				+ profil.getId())) {
			this.database.update("profile", values,
					"id_profil = " + profil.getId(), null);
		} else {
			this.database.insert("profile", null, values);
		}
	}

	public Profil getProfilByTel(String tel) {
		Profil profil = null;

		String selectQuery = "SELECT  * FROM profile WHERE numero=\"" + tel
				+ "\"";
		this.openInReadable();
		Cursor cursor = database.rawQuery(selectQuery, null);
		for (int i = 0; i < cursor.getColumnCount(); i++)
			Log.i("column " + i, cursor.getColumnName(i));

		Log.i("size", cursor.getCount() + "..." + tel + "...");
		if (cursor.moveToFirst()) {
			profil = new Profil();
			do {
				profil.setFriends(true);
				// numero =
				// cursor.getString(cursor.getColumnIndex("id_profil"));
				if (!cursor.isNull(cursor.getColumnIndex("id_profil"))) {
					Log.i("size",
							"..."
									+ cursor.getLong(cursor
											.getColumnIndex("id_profil"))
									+ "...");
					profil.setId(cursor.getLong(cursor
							.getColumnIndex("id_profil")));
				}
				if (!cursor.isNull(cursor.getColumnIndex("nom"))) {
					profil.setName(cursor.getString(cursor
							.getColumnIndex("nom")));
				}
				if (!cursor.isNull(cursor.getColumnIndex("numero"))) {
					profil.setTel(cursor.getString(cursor
							.getColumnIndex("numero")));
				}
				if (!cursor.isNull(cursor.getColumnIndex("lieu"))) {
					profil.setZone(cursor.getString(cursor
							.getColumnIndex("lieu")));
				}
				if (!cursor.isNull(cursor.getColumnIndex("operateur"))) {
					profil.setOperator(cursor.getString(cursor
							.getColumnIndex("operateur")));
				}
				if (!cursor.isNull(cursor.getColumnIndex("pin"))) {
					profil.setPin(cursor.getString(cursor.getColumnIndex("pin")));
				}
				if (!cursor.isNull(cursor.getColumnIndex("photo"))) {
					profil.setPhoto(cursor.getString(cursor
							.getColumnIndex("photo")));
				}
				if (!cursor.isNull(cursor.getColumnIndex("active"))) {
					profil.setActive(Boolean.parseBoolean(cursor
							.getString(cursor.getColumnIndex("active"))));
				}
				if (!cursor.isNull(cursor.getColumnIndex("status"))) {
					profil.setStatus(cursor.getString(cursor
							.getColumnIndex("status")));
				}
				if (!cursor.isNull(cursor.getColumnIndex("authenticationtime"))) {
					profil.setLasttime(cursor.getString(cursor
							.getColumnIndex("authenticationtime")));
				}
				if (!cursor.isNull(cursor.getColumnIndex("sexe"))) {
					profil.setSexe(cursor.getString(cursor
							.getColumnIndex("sexe")));
				}
				if (!cursor.isNull(cursor.getColumnIndex("age"))) {
					profil.setDate_naiss(cursor.getString(cursor
							.getColumnIndex("age")));
				}
			} while (cursor.moveToNext());
		}
		cursor.close();
		this.close();

		return profil;
	}

	public List<Profil> getProfils(long id) {
		List<Profil> profils = new ArrayList<Profil>();
		String selectQuery = "SELECT  * FROM profile WHERE id_profil <> " + id;
		this.openInReadable();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				Profil profil = new Profil();
				profil.setFriends(true);
				// numero =
				// cursor.getString(cursor.getColumnIndex("id_profil"));
				if (!cursor.isNull(cursor.getColumnIndex("id_profil"))) {
					profil.setId(cursor.getLong(cursor
							.getColumnIndex("id_profil")));
				}
				if (!cursor.isNull(cursor.getColumnIndex("nom"))) {
					profil.setName(cursor.getString(cursor
							.getColumnIndex("nom")));
				}
				if (!cursor.isNull(cursor.getColumnIndex("numero"))) {
					profil.setTel(cursor.getString(cursor
							.getColumnIndex("numero")));
				}
				if (!cursor.isNull(cursor.getColumnIndex("lieu"))) {
					profil.setZone(cursor.getString(cursor
							.getColumnIndex("lieu")));
				}
				if (!cursor.isNull(cursor.getColumnIndex("operateur"))) {
					profil.setOperator(cursor.getString(cursor
							.getColumnIndex("operateur")));
				}
				if (!cursor.isNull(cursor.getColumnIndex("pin"))) {
					profil.setPin(cursor.getString(cursor.getColumnIndex("pin")));
				}
				if (!cursor.isNull(cursor.getColumnIndex("photo"))) {
					profil.setPhoto(cursor.getString(cursor
							.getColumnIndex("photo")));
				}
				if (!cursor.isNull(cursor.getColumnIndex("active"))) {
					profil.setActive(Boolean.parseBoolean(cursor
							.getString(cursor.getColumnIndex("active"))));
				}
				if (!cursor.isNull(cursor.getColumnIndex("status"))) {
					profil.setStatus(cursor.getString(cursor
							.getColumnIndex("status")));
				}
				if (!cursor.isNull(cursor.getColumnIndex("authenticationtime"))) {
					profil.setLasttime(cursor.getString(cursor
							.getColumnIndex("authenticationtime")));
				}
				if (!cursor.isNull(cursor.getColumnIndex("sexe"))) {
					profil.setSexe(cursor.getString(cursor
							.getColumnIndex("sexe")));
				}
				if (!cursor.isNull(cursor.getColumnIndex("age"))) {
					profil.setDate_naiss(cursor.getString(cursor
							.getColumnIndex("age")));
				}
				profils.add(profil);
			} while (cursor.moveToNext());
		}
		cursor.close();
		this.close();

		return profils;
	}

}
