
import java.awt.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.SingleSourcePaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.WeightedPseudograph;


public class VendaImoveis {
	
	Graph <String,DefaultWeightedEdge> distrito = new WeightedPseudograph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	
	VendaImoveis (String fileName) {
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String sCurrentLine = br.readLine();
			while ((sCurrentLine = br.readLine()) != null) {
				String [] attributes = sCurrentLine.split(",");
				distrito.addVertex(attributes[0]);
				distrito.addVertex(attributes[1]);
				DefaultWeightedEdge e = new DefaultWeightedEdge();
				distrito.addEdge(attributes[0], attributes[1], e);
				distrito.setEdgeWeight(e, Double.parseDouble(attributes[2]));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String localizaImovel (String pontodeInteresse, Set <String> imoveis) {
		if(!distrito.containsVertex(pontodeInteresse) || null == imoveis) {
			return null;
		}
	
		DijkstraShortestPath<String, DefaultWeightedEdge> grafoDijkstra = new DijkstraShortestPath<String, DefaultWeightedEdge>(distrito);
		// grafo com os caminhos ate o ponto de interesse
		SingleSourcePaths<String, DefaultWeightedEdge> ssp = grafoDijkstra.getPaths(pontodeInteresse);
		
		//laco que verifica os pesos dos caminhos ate o peso de interesse e a saida 
		//eh o imovel com o menor caminho
		double menorDistancia = Double.POSITIVE_INFINITY;
		String saida = null;
		for(String imovel : imoveis) {
			if(ssp.getWeight(imovel) < menorDistancia) {
				menorDistancia = ssp.getWeight(imovel);
				saida = imovel;
			}
			
		}
		return saida;
	}
	

}
