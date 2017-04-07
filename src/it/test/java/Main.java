package it.test.java;

import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws IOException {
		String scelta = "";
		Scanner scan = new Scanner(System.in);
		FileManager file = new FileManager();
		
		//Mostro all'utente la possibilit� di inserire il percorso
		//della cartella e il nome del file sul quale lavorare
		file.chooseDirPath(scan);
		file.chooseFileName(scan);
		
		//Mostro il men� delle scelte possibili
		printMsg();
		
		//Implemento la possibilit� di scegliere un'opzione oppure un'altra
		//Termino l'esecuzione alla pressione del carattere "q"
		while(!"q".equals(scelta = scan.nextLine())){
			switch(scelta){
				case "1":
					file.numeroUtenti(scan);
					printMsg();
					break;
				case "2":
					file.numeroEmailErrate(scan);
					printMsg();
					break;
				case "3":
					file.listaEmailErrate(scan);
					printMsg();
					break;
				case "4":
					file.nameOccurrences(scan);
					printMsg();
					break;
				case "5":
					file.prefixOccurrences(scan);
					printMsg();
					break;
				case "6":
					file.over18year(scan);
					printMsg();
					break;
				default:
					System.out.println("---------------------------------------------------------------");
					System.out.println("Comando non riconosciuto, scegli uno dei seguenti comandi");
					printMsg();
					break;
			}
		}
		System.out.println("---------------------------------------------------------------");
		System.out.println("Esecuzione Terminata!!!");
		System.out.println("---------------------------------------------------------------");
		scan.close();
	}
	
	public static void printMsg(){
		System.out.println("---------------------------------------------------------------");
		System.out.println("Scegli un'azione tra le seguenti: ");
		System.out.println("  1 -> Visualizza il numero degli utenti presenti sul file");
		System.out.println("  2 -> Numero degli utenti con e-mail errata");
		System.out.println("  3 -> Lista delle e-mail errate");
		System.out.println("  4 -> Lista degli utenti con nome ricorrente all'interno del file");
		System.out.println("  5 -> Lista dei prefissi telefonici ripetuti pi� volte");
		System.out.println("  6 -> Visualizza il numero degli utenti maggiorenni");
		System.out.println("  q -> Termina il programma");
	}
}
