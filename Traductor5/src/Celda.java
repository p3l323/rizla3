import java.util.Iterator;
import java.util.Set;

public class Celda {
	private String terminal;
	private String noTerminal;
	private Produccion prod;
	
	public Celda(String noTerminal,String terminal, Produccion prod) {
		super();
		this.terminal = terminal;
		this.noTerminal = noTerminal;
		this.prod = prod;
	}

	
	

}
