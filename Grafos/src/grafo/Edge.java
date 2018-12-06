package grafo;

import org.jgrapht.graph.DefaultWeightedEdge;

public class Edge extends DefaultWeightedEdge{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	  @Override
	  public String toString() {
	    return "Weight: " + this.getWeight();
	  }

}