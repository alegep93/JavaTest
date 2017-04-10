package it.test.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import it.test.java.persona.Persona;

public class FileManager {
	private String dirPath, fileName, filePath;

	public FileManager(){
		this.dirPath = "";
		this.fileName = "";
		this.filePath = "";
	}

	//Consente l'inserimento del percorso della cartella da tastiera
	public void chooseDirPath(Scanner scan){
		System.out.println("Inserisci il percorso della cartella: ");
		this.dirPath = scan.nextLine();
		File dirPath = new File(this.dirPath);
		if(!dirPath.exists()){
			System.out.println("Cartella non presente, inserisci un percorso valido: ");
			chooseDirPath(scan);
		}
	}

	public void mostraFile(){
		File dir = new File(this.dirPath);
		if(dir != null && dir.exists()){
			String[] dirList = dir.list();
			if(dirList != null){
				for(int i=0; i<dirList.length; i++){
					File f = new File(this.dirPath, dirList[i]);
					if(f.exists() && f.isFile()){
						System.out.println("  - " + dirList[i]);
					}
				}
			}
		}
	}

	//Consente l'inserimento del percorso della cartella da tastiera
	public void chooseFileName(Scanner scan){
		System.out.println("Inserisci il nome di uno dei seguenti file: ");
		mostraFile();
		this.fileName = scan.nextLine();
		File filePath = new File(this.fileName);

		if(!filePath.exists()){
			System.out.println("File non presente, inserisci un nome valido: ");
			chooseFileName(scan);
		}

		this.filePath = this.dirPath + "\\" + this.fileName;
	}

	//Istanzia una nuova "Persona" passandogli un Array contenente lo split di una riga del file
	public Persona createPersona(String[] datiArr){
		Persona p = new Persona(datiArr[0], datiArr[1], datiArr[2], datiArr[3], datiArr[4], datiArr[5]);
		return p;
	}

	//Legge ciò che c'è scritto nel file
	public List<Persona> leggiContenutoFile(Scanner scan) throws IOException{
		String linea = "";
		List<Persona> usersList = new ArrayList<>();
		FileReader fr = null;
		BufferedReader br = null;
		try{
			fr = new FileReader(this.filePath);
			br = new BufferedReader(fr);

			linea = br.readLine();

			while(linea != null){
				String[] datiArr = linea.split("\\|");
				if(datiArr.length == 6){
					Persona p = createPersona(datiArr);
					usersList.add(p);
					linea = br.readLine();
				}else{
					System.out.println("---------------------------------------------------------------");
					System.out.println("ERRORE: ci sono dei record che non hanno 6 colonne");
					break;
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(fr != null)
				fr.close();
		}
		return usersList;
	}

	//Calcola il numero di record presenti nel file
	public void numeroUtenti(Scanner scan) throws IOException{
		int counter = 0;
		List<Persona> usersList = new ArrayList<>();
		usersList = leggiContenutoFile(scan);

		for(int i=0; i<usersList.size(); i++)
			counter++;

		System.out.println("---------------------------------------------------------------");
		System.out.println("Numero utenti letti: " + counter);
	}

	//Crea una lista contenente le email che non corrispondono al pattern specificato
	public List<String> UtentiConEmailErrata(Scanner scan) throws IOException{
		List<Persona> usersList = new ArrayList<>();
		List<String> emailList = new ArrayList<>();
		usersList = leggiContenutoFile(scan);

		for(Persona user : usersList){
			String email = user.getEmail();
			if(!user.isValidEmail(email)){
				emailList.add(email);
			}
		}
		return emailList;
	}

	//Conta i record che hanno una mail con una sintassi non corretta
	public void numeroEmailErrate(Scanner scan) throws IOException{
		int counter = 0;
		List<String> emailList = new ArrayList<>();
		emailList = UtentiConEmailErrata(scan);

		for (int i = 0; i < emailList.size(); i++)
			counter++;

		System.out.println("---------------------------------------------------------------");
		System.out.println("Numero di utenti con email errata: " + counter);
	}

	//Mostra su console la lista delle mail non corrette
	public void listaEmailErrate(Scanner scan) throws IOException{
		int i = 1;
		List<String> emailList = new ArrayList<>();
		emailList = UtentiConEmailErrata(scan);

		System.out.println("---------------------------------------------------------------");
		System.out.println("Lista e-mail errate: ");
		for(String e : emailList){
			System.out.println("  " + i + " -> " + e);
			i++;
		}	
	}

	//Mostra a video il nome che ricorre più volte all'interno del file
	public void nameOccurrences(Scanner scan){
		Map<String, Integer> myMap = new HashMap<String, Integer>();
		List<Persona> persList = new ArrayList<>();

		try{
			persList = leggiContenutoFile(scan);
			System.out.println("---------------------------------------------------------------");

			for (Persona pers : persList) {
				String nome = pers.getNome();
				Integer freq = myMap.get(nome);

				if(freq == null)
					freq = 1;
				else
					freq++;

				myMap.put(nome, freq);
			}

			for (String key : myMap.keySet()) {
				Integer val = myMap.get(key);
				if(val == Collections.max(myMap.values()))
					System.out.println("Il nome con più occorrenze è " + key + " il quale si ripete " + val + " volte");
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	//Mostra a video i prefissi che si ripetono più di una volta all'interno del file
	public void prefixOccurrences(Scanner scan){
		Map<String, Integer> myMap = new HashMap<String, Integer>();
		List<Persona> persList = new ArrayList<>();

		try{
			persList = leggiContenutoFile(scan);
			System.out.println("---------------------------------------------------------------");

			for (Persona p : persList) {
				String prefix = p.getTelefono().split(" ")[0];
				Integer freq = myMap.get(prefix);

				if(freq == null)
					freq = 1;
				else
					freq++;

				myMap.put(prefix, freq);
			}
			System.out.println("Prefissi ripetuti: ");
			for (String key : myMap.keySet()) {
				Integer val = myMap.get(key);

				if(val > 1)
					System.out.println("  " + key + " - " + val + " occorrenze");
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	//Conta gli utenti che hanno più di 18 anni
	public void over18year(Scanner scan){
		List<Persona> persList = new ArrayList<>();
		int counter = 0;

		try{
			persList = leggiContenutoFile(scan);
			System.out.println("---------------------------------------------------------------");

			for (Persona p : persList) {
				if(p.isMaggiorenne())
					counter++;
			}
		}catch(IOException e){
			e.printStackTrace();
		}

		System.out.println("Numero di persone con età maggiore di 18 anni: " + counter);
	}

	//Mostra nomi e cognomi degli utenti che hanno più di 18 anni
	public void over18yearName(Scanner scan){
		List<Persona> persList = new ArrayList<>();
		int i = 1;
		try{
			persList = leggiContenutoFile(scan);
			System.out.println("---------------------------------------------------------------");

			System.out.println("Lista delle persone maggiorenni: ");
			for(Persona p : persList) {
				String nome = p.getNome();
				String cognome = p.getCognome();
				if(p.isMaggiorenne()){
					System.out.format("%2s %s %s",i," -> ",nome + " " + cognome);
					System.out.println();
					i++;
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	//Mostra gli indirizzi associati ad ogni Persona
	public void showAddress(Scanner scan){
		List<Persona> persList = new ArrayList<>();
		
		try{
			persList = leggiContenutoFile(scan);
			for(Persona p : persList){
				System.out.println("---------------------------------------------------------------");
				String address = p.getIndirizzo();
				String nome = p.getNome() + " " + p.getCognome();
				System.out.format("%-20s %3s %s",nome, " -> ", address);
				System.out.println();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	//Mostra tutti i record con il testo formattato, per una corretta lettura delle informazioni
	public void showPrettyPerson(Scanner scan){
		List<Persona> persList = new ArrayList<>();
		
		try{
			persList = leggiContenutoFile(scan);
			for(Persona p : persList){
				System.out.println("---------------------------------------------------------------");
				String nome = p.getNome() + " " + p.getCognome();
				String email = "-> " + p.getEmail();
				String dataNascita = "-> " + p.getData_nascita();
				String telefono = "-> " + p.getTelefono();
				String indirizzo = "-> " + p.getIndirizzo();
				System.out.println(nome + ": ");
				System.out.format("%-17s %s \r\n","  E-mail", email);
				System.out.format("%-17s %s \r\n","  Data di nascita", dataNascita);
				System.out.format("%-17s %s \r\n","  Telefono", telefono);
				System.out.format("%-17s %s \r\n","  Indirizzo", indirizzo);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
