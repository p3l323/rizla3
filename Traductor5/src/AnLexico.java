import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class AnLexico {
	private static final String [] PalRes={"var","function","return","if","for","write","int","bool","chars","prompt"};
	private static int tokens = 0;
	private static String linea;
	private static String archivoEntrada = "src/codigo fuente/correcto1.txt";
	private static String archivoSalida = "src/salida/tokens.txt";
	private static int numLin=1;
	private static char c;
	private static char csig;
	private static String lexema;
	private static Token token;
	private static int intchar;
	private static BufferedReader b;
	private static boolean initiated = false;
	
	
	public static void init() throws IOException{
		b = new BufferedReader(new FileReader(archivoEntrada));	
		
		csig=(char) intchar;	
		initiated = true;
	}
	
	
	
	private static boolean EOF(){
		return intchar==-1;
	}
	
	public static boolean finArchivo(){
		return intchar==-1;
	}
	
	

	public static Token leerArchivo() throws FileNotFoundException, IOException {
		
		token = null;
		if(EOF())
			return null;
		
		
			}
		}
		
		if(Character.compare(c, '\n')==0){
			token = genToken("br",null);
			numLin++;
		}
		
		else if(Character.isDigit(c)){
			System.out.println("Token Digito");
			while(	!EOF() && Character.isDigit(csig)){
				leeCaracter();
			}
			try{
					(Character.isLetter(csig) 
							|| Character.isDigit(csig) 
							|| Character.compare(csig, '_')==0)){
				leeCaracter();
			}
			boolean encontrado=false;
			for(int j=0;!encontrado && j<PalRes.length;j++){
				if(lexema.equals(PalRes[j])){
					encontrado=true;
					token = genToken(lexema,null);
				}
			}
			if(!encontrado){
				if(lexema.compareTo("true")==0||lexema.compareTo("false")==0)
					
			}
			if(!EOF()){
				token =  genToken("conschars",lexema);
				leeCaracter();				
			}else
				System.err.println("Lexico: Faltan o sobran comillas en la linea: "+numLin);
		}

		/* SUMA, RESTA, MULTIPLICACION O DIVISION CON O SIN ASIGNACION,
				   ASIGNACION, COMPARACIONES O NEGACION */
		else if(isOperator(c) || Character.compare(c, '=')==0 ||
				Character.compare(c, '>')==0||Character.compare(c, '<')==0||Character.compare(c, '!')==0){
			if(!EOF() && Character.compare(csig, '=')==0){
				leeCaracter();
			}
			token = genToken(lexema,null);
			System.out.println("Token +=");
		}
		/* && O || */
		else if(Character.compare(c, '&')==0||Character.compare(c, '|')==0){
			if(!EOF() && Character.compare(csig, c)==0){
				leeCaracter();
				token = genT.compare(c, '?')==0){
			token = genToken(Character.toString(c),null);
			System.out.println("Token {");
		}
		lexema="";
		leeCaracter();
		return token;
			}
			/* ESPACIOS(IGNORADOS) Y TOKEN NO VALIDO */
			/*else if(Character.compare(c, ' ')!=0 && Character.compare(c, '	')!=0){
			String lexema=Character.toString(c);

			while(!EOF() && Character.compare(c, ' ')!=0 && Character.compare(c, '=')!=0 
					&& Character.compare(c, ')')!=0 && Character.compare(c, '(')!=0
					&& Character.compare(c, '}')!=0 && Character.compare(c, '{')!=0
					&& Character.compare(c, ',')!=0 && Character.compare(c, ';')!=0
					&& Character.compare(c, '+')!=0 && Character.compare(c, '-')!=0
					&& Character.compare(c, ':')!=0 && Character.compare(c, '?')!=0){
				i++;
				c=linea.charAt(i);
				lexema+=c;
			}
			System.err.println("Lexico: Linea "+numLin+" lexema "+lexema+" no valido ");
		}*/
			e.printStackTrace();
		}
		finally {
			try {
				if (null != fichero)
					fichero.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
		}
		return new Token(codigo,atributo,numLin);
	}
}
