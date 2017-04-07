package it.test.java.persona;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Persona {
	private String nome, cognome, email;
	private String data_nascita, telefono, indirizzo;
	private static final Pattern EMAIL_REGEX = 
			Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}", Pattern.CASE_INSENSITIVE);
	
	public Persona(){
		nome = cognome = email = "";
		data_nascita = telefono = indirizzo = "";
	}
	
	public Persona(String nome, String cognome, String email, String data_nascita, String telefono, String indirizzo){
		this.nome 		  = nome;
		this.cognome 	  = cognome;
		this.email 		  = email;
		this.data_nascita = data_nascita;
		this.telefono 	  = telefono;
		this.indirizzo    = indirizzo;
	}
	
	//Restituisce TRUE se la mail rispetta il Pattern definito sopra come Regular Expression
	public boolean isValidEmail(String email){
		Matcher match = EMAIL_REGEX.matcher(email);
		return match.matches();
	}
	
	//Restituisce TRUE se l'età della persona controllata è maggiore di 18 anni
	public boolean isMaggiorenne(){
		LocalDate today = LocalDate.now();
		String[] data_nascita = this.data_nascita.split("/");
		LocalDate nascita = LocalDate.of(
				Integer.valueOf(data_nascita[2]), 
				Integer.valueOf(data_nascita[1]), 
				Integer.valueOf(data_nascita[0]));
		
		if(Period.between(nascita, today).getYears() >= 18)
			return true;
		
		return false;
	}
	
	//Inizio metodi GET, rendo disponibile la classe in sola lettura
	public String getNome() {
		return nome;
	}
	
	public String getCognome() {
		return cognome;
	}

	public String getEmail() {
		return email;
	}

	public String getData_nascita() {
		return data_nascita;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getIndirizzo() {
		return indirizzo;
	}
}
