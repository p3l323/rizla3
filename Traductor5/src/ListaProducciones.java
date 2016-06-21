import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ListaProducciones {
	private HashMap<String,List<Produccion>> producciones;
	private HashMap<String,ArrayListMod<String>[]> firstandfollow;
	String axioma;
	private ArrayListMod<String> terminales;
	private ArrayListMod<String> noterminales;
	private TablaDecision tabla;


	
	public ListaProducciones(String axioma) {
		super();
		this.axioma = axioma;
		this.producciones = new HashMap<String,List<Produccion>>();
		this.firstandfollow = new HashMap<String,ArrayListMod<String>[]>();
		this.terminales = new ArrayListMod<String>();
		this.noterminales = new ArrayListMod<String>();
	}

	public void addProduccion(Produccion prod){
		List<Produccion> listaReglas;
		Iterator<?> it;
		String simbolo;
		
		//AÑADIR PRODUCCION
		if (producciones.get(prod.getAntecedente()) == null){
			listaReglas = new ArrayListMod<Produccion>();
			listaReglas.add(prod);
			producciones.put(prod.getAntecedente(),listaReglas);
		}
		else{
			producciones.get(prod.getAntecedente()).add(prod);
		}
		
		//UPDATE DE TERMINALES Y NO TERMINALES
		this.noterminales.add(prod.getAntecedente());
		it = prod.getConsecuente().iterator();
		while(it.hasNext()){
			simbolo = (String) it.next();
			if(this.isNoTerminal(simbolo))
				this.noterminales.add(simbolo);
			else
				this.terminales.add(simbolo);			
		}
	}
	
	
	public TablaDecision buildTable() throws Exception{
		tabla = new TablaDecision();
		Map.Entry listaFirsts;
		Iterator<String> itProducciones;
		List<Produccion> firsts;
		Iterator<Produccion> itActual;
		ArrayListMod<String> follow;
		Produccion prodActual;
		Produccion prodAux;
		Celda aux;
		String antecedente;
		Iterator<String> itFirsts;
		String noTerminal;
		Iterator<String> simbolosFollow;
		ArrayList<String> consLambda=new ArrayList<String>();
		consLambda.add("lambda");
		String simbolo;
		
		
		itProducciones = this.producciones.keySet().iterator();
		while(itProducciones.hasNext()){
			antecedente=itProducciones.next();
			firsts=this.producciones.get(antecedente);
			itActual = firsts.iterator();
			while(itActual.hasNext()){
				prodActual = itActual.next();
				itFirsts = prodActual.getFirst().iterator();
				while(itFirsts.hasNext()){
					noTerminal=itFirsts.next();
					if(noTerminal.equals("lambda")){
						follow=this.firstandfollow.get(antecedente)[1];
						simbolosFollow=follow.iterator();
						while(simbolosFollow.hasNext()){
							prodAux=new Produccion(antecedente,consLambda);
							simbolo = simbolosFollow.next();
							tabla.addCelda(new Celda(antecedente,simbolo,prodAux));
						}
					}	
					else
						tabla.addCelda(new Celda(antecedente,noTerminal,prodActual));
				}
			}
		}
		return tabla;
	}
	
	
	
	public void compruebaGramatica(){
		ArrayListMod<String>[] lista;
		String cadena="";
		Iterator itmap = firstandfollow.entrySet().iterator();
		Iterator itProducciones = this.producciones.entrySet().iterator();
		Map.Entry listaFirsts;
		List<Produccion> firsts;
		String elemento;
		
		Iterator itActual;
		Produccion actual;
		Produccion corredor;
		boolean flagFirsts;
		boolean flagFollow;
		int i=1;
		int j;

		
		while (itProducciones.hasNext()){
			
			//COMPROBACIÓN DE CONFLICTOS ENTRE LOS FIRST DEL MISMO NO TERMINAL
			flagFirsts = false;
			flagFollow = false;
			i=1;
			listaFirsts = (Entry) itProducciones.next();
			firsts = (List<Produccion>) listaFirsts.getValue();
			itActual = firsts.iterator();
			while(itActual.hasNext() && !flagFirsts){
				actual = (Produccion) itActual.next();
				if(firsts.size()>i){
					j=i;
					while(firsts.size()>j && !flagFirsts){
						corredor = firsts.get(j);
						if(actual.getFirst().containsAny(corredor.getFirst()))
							flagFirsts=true;
						j++;
					}
					i++;
				}
			}
			if(flagFirsts)
				cadena = cadena + "\n\nError entre firsts\n";
			
			//COMPROBACIÓN DE CONFLICTOS ENTRE FIRST Y FOLLOW
			elemento = (String)listaFirsts.getKey();
			lista = this.firstandfollow.get(elemento);
			if(lista[0].contains("lambda")&&lista[0].containsAny(lista[1])){
				cadena = cadena + "\n\nError follow\n";
				flagFollow=true;
			}
				
			if (flagFollow || flagFirsts){
				cadena = cadena + elemento+": "+this.producciones.get(elemento).toString()+"\n";
				cadena = cadena + "             FIRST:  "+this.firstandfollow.get(elemento)[0].toString()+"\n";
				cadena = cadena + "             FOLLOW: "+this.firstandfollow.get(elemento)[1].toString()+"\n\n";
			}
		}

		if(cadena.equals(""))
			System.out.println("Gramatica correcta");
		else
			System.out.println("Gramatica INCORRECTA\nSe encontraron los siguientes errores\n\n"+cadena);
	}

	//FIRST DE UN SIMBOLO
	public ArrayListMod<String> getElementFirst(String elemento){
		ArrayListMod<String> listaFirst;
		if (this.isNoTerminal(elemento))
			return this.getProdFirst(elemento);
		else{
			listaFirst = new ArrayListMod<String>();
			listaFirst.add(elemento);
			return listaFirst;
		}
	}
	
	//FIRST DE UNA PRODUCCION
	public ArrayListMod<String> getProdFirst(String antecedente){
		ArrayListMod<String> first= new ArrayListMod<String>();
		Iterator<Produccion> itproducciones;
		List<Produccion> producciones = this.producciones.get(antecedente);
		//System.out.println(antecedente);
		itproducciones = producciones.iterator();
		while (itproducciones.hasNext())
			first.addAll(this.getFirst(itproducciones.next().getConsecuente()));
		return first;
	}
	
	//FIRST DE UNA COLECCION DE SIMBOLOS
	public ArrayListMod<String> getFirst(ArrayList<String> derecha){
		ArrayListMod<String> first= new ArrayListMod<String>();
		ArrayListMod<String> temporal;
		ArrayListMod<String> firstProduccion;
		int contaLambdas=0;
		boolean lambda=true;
		String simbolo;
		Iterator itproducciones;
		Iterator itDerecha;
		Produccion prod;

		itDerecha = derecha.iterator();
		firstProduccion = new ArrayListMod<String>();
		//ELEMENTO
		while (itDerecha.hasNext() && lambda){
			lambda=false;
			simbolo = (String) itDerecha.next();
			temporal = this.getElementFirst(simbolo);
			if(temporal.contains("lambda")){
				temporal.remove("lambda");
				contaLambdas++;
				lambda=true;
			}
			else
				lambda=false;
			first.addAll(temporal);
		}
		if (derecha.size() == contaLambdas)
			first.add("lambda");
		return first;
	}

	private void insertFollow2(ArrayList<String> derecha,String antecedente){
		ArrayList<String> derecha2;
		ArrayList<String> derechaSigPaso;
		String simboloSig;
		Iterator<String> itproduccion;
		ArrayListMod<String> temporal;
		ArrayListMod<String>[] fandf;
		
		//System.out.println(derecha+" "+antecedente);
		itproduccion = derecha.iterator();
		while (itproduccion.hasNext()){
			simboloSig = itproduccion.next();
			if(simboloSig.equals(antecedente)){
				derecha2=new ArrayList<String>();
				while (itproduccion.hasNext())
					derecha2.add(itproduccion.next());
				derechaSigPaso=(ArrayList<String>) derecha2.clone();
				this.insertFollow2(derechaSigPaso, antecedente);
				temporal=this.getFirst(derecha2);
				temporal.remove("lambda");
				fandf=this.firstandfollow.get(antecedente);
				fandf[1].addAll(temporal);
				this.firstandfollow.put(antecedente,fandf);
			}
		}
	}
	
	
	private void followPaso2(){
		ArrayListMod<String>[] fandf;
		ArrayListMod<String> temporal;
		ArrayList<String> derecha;
		ArrayList<String> consecuente;
		String simboloSig;
		
		Iterator<Produccion> itproducciones;
		Iterator<?> itproduccion;
		Produccion prod;
		List<Produccion> producciones;
		
		Iterator<String> itmap;
		Iterator<String> itmap2;
		
		String antecedente1;
		String antecedente2;

		//SELECCIONA LA LISTA DE PRODUCCIONES DE A
		itmap = this.noterminales.iterator();
		while (itmap.hasNext()){
			antecedente1 = itmap.next();
			itmap2 = this.noterminales.iterator();
			while (itmap2.hasNext()){
				antecedente2 = itmap2.next();
				producciones = this.producciones.get(antecedente2);
				itproducciones = producciones.iterator();
				while (itproducciones.hasNext()){
					consecuente = itproducciones.next().getConsecuente();
					if(consecuente.contains(antecedente1))
						this.insertFollow2(consecuente, antecedente1);
				}
			}
		}
	}
	
	public void followPaso3(){
		String actual;
		ArrayListMod<String>[] fandf;
		ArrayListMod<String> temporal;
		String simbolo="";
		String simboloSig;
		String antecedente;
		Iterator itproducciones;
		Iterator itproduccion;
		Produccion prod;
		List<Produccion> producciones;
		boolean encontrado=false;
		
		ArrayListMod<String> lista;
		Iterator itmap = this.producciones.entrySet().iterator();
		Iterator itmap2;
		Map.Entry elemento;
		Map.Entry elemento2;
		String cadena="";
		
		//SELECCIONA LA LISTA DE PRODUCCIONES DE A
		while (itmap.hasNext()){
			itmap2 = this.producciones.entrySet().iterator();
			elemento = (Entry) itmap.next();
			actual = (String) elemento.getKey();
			while (itmap2.hasNext()){
				elemento2 = (Entry) itmap2.next();
				antecedente = (String) elemento2.getKey();
				producciones = this.producciones.get(antecedente);
				itproducciones = producciones.iterator();
				while (itproducciones.hasNext()){
					prod = (Produccion) itproducciones.next();
					itproduccion = prod.getConsecuente().iterator();
					//ELEMENTO
					encontrado = false;
					while (itproduccion.hasNext()){
						simboloSig = (String) itproduccion.next();
						if(encontrado){
							encontrado=false;
							temporal=this.getElementFirst(simboloSig);
							if(temporal.contains("lambda")){
								temporal=this.firstandfollow.get(prod.getAntecedente())[1];
								fandf=this.firstandfollow.get(actual);
								fandf[1].addAll(temporal);
								this.firstandfollow.put(actual,fandf);
							}	
						}	
						if(simboloSig.equals(actual))
							encontrado=true;
					}
					if(encontrado){
						temporal=this.firstandfollow.get(prod.getAntecedente())[1];
						fandf=this.firstandfollow.get(actual);
						fandf[1].addAll(temporal);
						this.firstandfollow.put(actual,fandf);
					}
				}
			}
		}
	}
	
	public void updateFirstandFollow(boolean mostrar){
		
		ArrayListMod<String>[] lista;
		ArrayListMod<String> follow;
		Iterator<String> itmap;
		String antecedente;
		String elemento;
		Iterator<Produccion> itListaProd;
		Produccion prod;

		//UPDATE FIRST
		itmap = this.noterminales.iterator();
		while (itmap.hasNext()){
			lista=new ArrayListMod[2];
			antecedente = itmap.next();
			lista[0] = this.getProdFirst(antecedente);
			itListaProd=this.producciones.get(antecedente).iterator();
			//ACTUALIZA LOS FIRST DE CADA UNA DE LAS PRODUCCIONES
			while(itListaProd.hasNext()){
				prod = itListaProd.next();
				prod.setFirst(this.getFirst(prod.getConsecuente()));
			}
			this.firstandfollow.put(antecedente,lista);
		}
		
		//UPDATE FOLLOW
		//PASO 1 (Crear listas de los follows y añadir $ al axioma)
		itmap = this.noterminales.iterator();
		while (itmap.hasNext()){
			antecedente = itmap.next();
			lista = this.firstandfollow.get(antecedente);
			lista[1] = new ArrayListMod<String>();
			if(axioma.equals(antecedente))
				lista[1].add("$");
			this.firstandfollow.put(antecedente, lista);
		}
		if(mostrar)
			printPasosMessage("PASO 1");
		
		//PASO 2
		followPaso2();
		if(mostrar)
			printPasosMessage("PASO 2");		

		//PASO 3
		for(int i=0;i<5;i++){
			followPaso3();
		}
		if(mostrar)
			printPasosMessage("PASO 3");		

	}
	
	private void printPasosMessage(String message){
		System.out.println("\n\n\n"+message+"\n");
		System.out.println(this.toString());
	}
	
	public String getAxioma() {
		return axioma;
	}

	private boolean isNoTerminal(String elem){
		char primera=elem.charAt(0);
		return primera>=65 && elem.charAt(0)<=90; 
	}
	
	public void print(){
		Iterator it = (this.producciones.entrySet().iterator());
		while(it.hasNext())
			System.out.println(it.next());
	}
	
	public void print(Produccion prod){
		System.out.println(this.producciones.get(prod.getAntecedente()));
	}
	
	@Override
	public String toString(){
		
		ArrayListMod<String>[] lista=new  ArrayListMod[2];
		Iterator itmap = producciones.entrySet().iterator();
		Map.Entry elemento;
		String cadena="";
		
		
		while (itmap.hasNext()){
			elemento = (Entry) itmap.next();
			cadena = cadena + elemento.getKey()+": "+this.producciones.get(elemento.getKey()).toString()+"\n";
			cadena = cadena + "             FIRST:  "+this.firstandfollow.get(elemento.getKey())[0].toString()+"\n";
			cadena = cadena + "             FOLLOW: "+this.firstandfollow.get(elemento.getKey())[1].toString()+"\n\n";
		}
		return cadena;
	}
	

	
	
	public Set<String> getAntecedentes(){
		return producciones.keySet();
	}

	public List<Produccion> getConsecuentes(String antecedente){
		return producciones.get(antecedente);
	}
	
	public ArrayListMod<String> getFirstTabla(String produccion){
		return firstandfollow.get(produccion)[0];
	}
	
	public ArrayListMod<String> getFollowTabla(String produccion){
		return firstandfollow.get(produccion)[1];
	} 
	
	public ArrayListMod<String> getTerminales() {
		return terminales;
	}

	public ArrayListMod<String> getNoterminales() {
		return noterminales;
	}
}
