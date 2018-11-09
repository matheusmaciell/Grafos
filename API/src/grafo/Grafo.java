package grafo;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JApplet;
import javax.swing.JFrame;

import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultWeightedEdge;

import org.jgrapht.graph.builder.GraphTypeBuilder;

import org.jgrapht.io.ImportException;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.swing.mxGraphComponent;


@SuppressWarnings("deprecation")
public class Grafo extends JApplet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static ArrayList<String> s = new ArrayList<String>();
	public static ArrayList<String> t = new ArrayList<String>();
	public static ArrayList<String> p = new ArrayList<String>();

	private static final Dimension DEFAULT_SIZE = new Dimension(800, 600);

	private JGraphXAdapter<Object, Edge> jgxAdapter;

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
		
		s.addAll(readFileTXT("source.txt"));
		t.addAll(readFileTXT("target.txt"));
		p.addAll(readFileTXT("weight.txt"));

		Grafo applet = new Grafo();
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
			Graph<Object, Edge> multiGraph = createGraph();
			
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
		} catch (ImportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
    private static Graph<Object, Edge> createGraph() throws ImportException {
    	Graph<Object, Edge> multiGraph =  GraphTypeBuilder.<Object, Edge>undirected().allowingMultipleEdges(true)
				.allowingSelfLoops(false).edgeClass(Edge.class).weighted(true).buildGraph();
    	
		for (int i = 0; i < s.size(); i++) {
			multiGraph.addVertex(s.get(i));
			multiGraph.addVertex(t.get(i));
			Edge edge1 = multiGraph.addEdge(s.get(i), t.get(i));
			double d = Double.valueOf(p.get(i));
			multiGraph.setEdgeWeight(edge1, d);
			
		}
        return multiGraph;
    }
}
