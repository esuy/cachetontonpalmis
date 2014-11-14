package com.cache.tontonpalmis.profil;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;

import com.cache.tontonpalmis.MainActivity;

public class Profil implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1341091161980577977L;
	private long id;
	private String email;
	private String password;
	private String name;
	private String tel;
	private String zone;
	private String photo;
	private String status;
	private String pin;
	private String operator;
	private String sexe;
	private String date_naiss;
	private boolean active;
	private String lasttime;
	private boolean friends;
	private boolean waitting;
	public boolean isFriends() {
		return friends;
	}
	public boolean isWaitting() {
		return waitting;
	}
	public void setWaitting(boolean waitting) {
		this.waitting = waitting;
	}
	public void setFriends(boolean friends) {
		this.friends = friends;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getSexe() {
		return sexe;
	}
	public void setSexe(String sexe) {
		this.sexe = sexe;
	}
	public String getDate_naiss() {
		return date_naiss;
	}
	public void setDate_naiss(String date_naiss) {
		this.date_naiss = date_naiss;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getLasttime() {
		return lasttime;
	}
	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}
	public static Profil parseJson(JSONObject data){
		Profil profil = new Profil();
		if(!data.isNull("id_profil")){
			try {
				if(!data.isNull("id_profil")){
					profil.setId(Long.parseLong(data.getString("id_profil")));
				}
				if(!data.isNull("nom")){
					profil.setName(data.getString("nom"));
				}
				if(!data.isNull("numero")){
					profil.setTel(data.getString("numero"));
				}
				if(!data.isNull("lieu")){
					profil.setZone(data.getString("lieu"));
				}
				if(!data.isNull("operateur")){
					profil.setOperator(data.getString("operateur"));
				}
				if(!data.isNull("pin")){
					profil.setPin(data.getString("pin"));
				}
				if(!data.isNull("photo")){
					profil.setPhoto(data.getString("photo"));
				}
				if(!data.isNull("active")){
					//data.getBoolean("active")
					profil.setActive(true);
				}
				if(!data.isNull("status")){
					profil.setStatus(data.getString("status"));
				}else{
					profil.setStatus("-----");
				}
				if(!data.isNull("authenticationtime")){
					profil.setLasttime(data.getString("authenticationtime"));
				}
				/*if(!data.isNull("nom")){
					profil.setName(data.getString("nom"));
				}*/
				if(!data.isNull("sexe")){
					profil.setSexe(data.getString("sexe"));
				}
				if(!data.isNull("age")){
					profil.setDate_naiss(data.getString("age"));
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return profil;
	}
	
}
