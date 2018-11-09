package atg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;

public class Grafo {
	public static ArrayList<String> s = new ArrayList<String>();
	public static ArrayList<String> t = new ArrayList<String>();
	public static ArrayList<String> p = new ArrayList<String>();
	
	public static ArrayList<String> readFileTXT(String path) throws IOException{
		ArrayList<String> lines = new ArrayList<String>();
		File file = new File(path);
		
		if(!file.exists()) {
			throw new IOException("Arquivo n�o existe.");
		}
		
		FileReader fileReader = new FileReader(path);
		BufferedReader bufferReader = new BufferedReader(fileReader);
		while(bufferReader.ready()) {
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
		
		org.jgrapht.Graph<Object, DefaultWeightedEdge> multiGraph = GraphTypeBuilder
				.<Object, DefaultWeightedEdge> undirected().allowingMultipleEdges(true)
				.allowingSelfLoops(false).edgeClass(DefaultWeightedEdge.class).weighted(true).buildGraph();
		
		for (int i = 0; i < s.size(); i++) {
			multiGraph.addVertex(s.get(i));
			multiGraph.addVertex(t.get(i));
			DefaultWeightedEdge edge1 = multiGraph.addEdge(s.get(i), t.get(i));
			double d = Double.valueOf(p.get(i));
			multiGraph.setEdgeWeight(edge1,d);
			System.out.println(multiGraph.getEdgeWeight(edge1));
		}
	}
}
