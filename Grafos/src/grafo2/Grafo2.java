package grafo2;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JApplet;
import javax.swing.JFrame;

import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;

import org.jgrapht.graph.builder.GraphTypeBuilder;

import org.jgrapht.io.ImportException;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.swing.mxGraphComponent;

@SuppressWarnings("deprecation")
public class Grafo2 extends JApplet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static ArrayList<String> s = new ArrayList<String>();
	public static ArrayList<String> t = new ArrayList<String>();
	public static ArrayList<String> p = new ArrayList<String>();

	private static final Dimension DEFAULT_SIZE = new Dimension(800, 600);

	private JGraphXAdapter<Object, Edge2> jgxAdapter;

	public static ArrayList<String> readFileTXT(String path) throws IOException {
		ArrayList<String> lines = new ArrayList<String>();
		File file = new File(path);

		if (!file.exists()) {
			throw new IOException("Arquivo não existe.");
		}

		FileReader fileReader = new FileReader(path);
		BufferedReader bufferReader = new BufferedReader(fileReader);
		while (bufferReader.ready()) {
			String line = bufferReader.readLine();
			lines.add(line);
		}

		fileReader.close();
		bufferReader.close();

		return lines;
	}

	public static void main(String[] args) throws IOException {

		s.addAll(readFileTXT("source2.txt"));
		t.addAll(readFileTXT("target2.txt"));
		p.addAll(readFileTXT("weight2.txt"));

		Grafo2 applet = new Grafo2();
		applet.init();

		JFrame frame = new JFrame();
		frame.getContentPane().add(applet);
		frame.setTitle("ATG");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}

	public void init() {
		// create a JGraphT graph
		try {
			Graph<Object, Edge2> multiGraph = createGraph();

			jgxAdapter = new JGraphXAdapter<>(multiGraph);

			setPreferredSize(DEFAULT_SIZE);
			mxGraphComponent component = new mxGraphComponent(jgxAdapter);
			component.setConnectable(false);
			component.getGraph().setAllowDanglingEdges(false);
			getContentPane().add(component);
			resize(DEFAULT_SIZE);

			mxCircleLayout layout = new mxCircleLayout(jgxAdapter);

			// center the circle
			int radius = 100;
			layout.setX0((DEFAULT_SIZE.width / 2.0) - radius);
			layout.setY0((DEFAULT_SIZE.height / 2.0) - radius);
			layout.setRadius(radius);
			layout.setMoveCircle(true);

			layout.execute(jgxAdapter.getDefaultParent());
			questao2(multiGraph);
			//System.out.println("Resultado questao 3");
			//questao3(multiGraph);

		} catch (ImportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void questao2(Graph<Object, Edge2> multiGraph) {
		
		Set<Object> vertices = multiGraph.vertexSet();

		for (Object vertice : vertices) {
			Set<Edge2> arestasQueSaem = multiGraph.outgoingEdgesOf(vertice);

			Double max = -1.0;
			Object arestaFinal = null;

			if (arestasQueSaem.size() != 1) {
				for (Edge2 arestaSaida : arestasQueSaem) {
					Double aux = multiGraph.getEdgeWeight(arestaSaida);
					if (aux > max) {
						arestaFinal = multiGraph.getEdgeTarget(arestaSaida);
						max = aux;
					}
				}
				System.out.println(arestaFinal);
			}
		}
	}

	private static void questao3(Graph<Object, Edge2> multiGraph) {
		Set<Object> vertices = multiGraph.vertexSet();

		Object proximo = null;

		for (Object vertice : vertices) {
			proximo = findTheNextOne(vertice, multiGraph);
			while(findTheNextOne(proximo, multiGraph) != null){
				proximo = findTheNextOne(proximo, multiGraph);
			}
			break;
		}

		System.out.println(proximo);


	}

	private static Object findTheNextOne(Object verticeAtual, Graph<Object, Edge2> multiGraph){
		Set<Edge2> arestasQueSaem = multiGraph.outgoingEdgesOf(verticeAtual);

		Double maximo = -1.0;
		Object proximoVertice = null;

		if (arestasQueSaem.size() >= 1) {
			for (Edge2 arestaSaida : arestasQueSaem) {
				Double candidato = multiGraph.getEdgeWeight(arestaSaida);
				if (candidato > maximo) {
					proximoVertice = multiGraph.getEdgeTarget(arestaSaida);
					maximo = candidato;
				}
			}
			return proximoVertice;
		}
		return proximoVertice;

	}


	private static Graph<Object, Edge2> createGraph() throws ImportException {
		Graph<Object, Edge2> multiGraph = GraphTypeBuilder.<Object, Edge2>undirected().allowingMultipleEdges(true)
				.allowingSelfLoops(false).edgeClass(Edge2.class).weighted(true).buildGraph();

		for (int i = 0; i < s.size(); i++) {
			multiGraph.addVertex(s.get(i));
			multiGraph.addVertex(t.get(i));
			Edge2 edge1 = multiGraph.addEdge(s.get(i), t.get(i));
			double d = Double.valueOf(p.get(i));
			multiGraph.setEdgeWeight(edge1, d);

		}
		// multiGraph.edgesOf(multiGraph);
		return multiGraph;
	}
}