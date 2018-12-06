package grafo2;

import org.jgrapht.graph.DefaultWeightedEdge;

public class Edge2 extends DefaultWeightedEdge{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	  @Override
	  public String toString() {
	    return "Weight: " + this.getWeight();
	  }

}