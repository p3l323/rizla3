import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;





public class AnSintLL {
	
	private static String archivoEntrada = "src/salida/tokens.txt";
	private static String archivoParse = "src/salida/parse.txt";
	private static String archivoParseHuman = "src/salida/human_parse.txt";
	private static String archivoError = "src/salida/errores.txt";
	private static String tabla = "src/tabla.txt";
	private static String gramatica = "src/sintactico6.txt";
	private static String gram = "src/GramASint.txt";
	private static String linea;
	private static String cima;
	private static String siguiente;
	private static ArrayList<String> pila= new ArrayList<String>();
	private static ArrayList<String> entrada= new ArrayList<String>();
	
	
	
	public AnSintLL(String archivo){
	}
	
	
	private static void printBuffer(Queue<Token> buffer){
		Token elemento;
		System.out.println("Contenido del Buffer");
		boolean p;
		for (int i=0;i<buffer.size();i++){
			elemento=buffer.remove();
			System.out.println(elemento);
			buffer.add(elemento);
		}
	}
	
	private static void createError(){
		
	}
		
	/* PROGRAMA PRINCIPAL */
	public static void main(String[] args) throws Exception{
		boolean error;
		
		String[] terminales=null;
		String[] noTerminales=null;
		int contador=0;
		
		System.out.println("UBICACIÓN DEL ARCHIVO DE TOKENS: "+archivoEntrada);
		System.out.println("UBICACIÓN DEL ARCHIVO DE PARSE: "+archivoParse);
		
		FileReader f = new FileReader(gramatica);
		BufferedReader b = new BufferedReader(f);
		while((cadena = b.readLine())!=null) {
			if(cadena.length()!=0 && !cadena.startsWith("//")){
				temporal = cadena.split(" ");
				if(cadena.startsWith("Terminales = {") && cadena.endsWith("}")){
					
				}
				else if(cadena.startsWith("NoTerminales = {") && cadena.endsWith("}")){				
					noTerminales = new String[temporal.length - 3];
					for(int i=3;i<temporal.length-1;i++)
						noTerminales[i-3]=temporal[i];
				}
				else if(cadena.startsWith("Axioma = ")){				
					}
					
				else if(cadena.startsWith("Axioma ="))
					axioma = temporal[2];
				
			}
		}
		
		//COMPROBAR GRAMATICA
		System.out.println("Símbolos Terminales\n"+producciones.getTerminales());
		System.out.println("\nSímbolos No Terminales\n"+producciones.getNoterminales());
	
		tablaDecision.print();
		
		//ANÁLISIS
		pila = new Stack<String>();
		buffer = new LinkedList<Token>();
		
		
		
		
		printBuffer(buffer);
		String hola;
		pila.add("$");
		pila.add(producciones.axioma);
		
		error = false;
		
		lambda.add("lambda");
		
		while(!pila.isEmpty() && !error){
			cabeza=pila.pop();
			System.out.println("\nCabeza: "+cabeza);
			if(cabeza.equals(token.getCodigo()))
				token=buffer.remove();
			else{
				prod=tablaDecision.getStackSimbols(cabeza,token.getCodigo());
				parse.add(prod);
				consec= 	pila.addAll(consec);
				}
				else if(consec.equals(null)){
					error=true;
				}
			}
		}
		if(!error && buffer.isEmpty())
			System.out.println("\nANÁLISIS SINTÁCTICO CORRECTO");
		else{
			System.out.println("\nERROR: Análisis Sintáctico");
		}
	}
			
}
