
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.SingleSourcePaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedPseudograph;

/**
 *  
 * @author Eduardo
 * @author Yanka
 * @author Jader
 * @auuthor Caio Jose
 *
 */
public class VendaImoveis {
	
	Graph <String,DefaultWeightedEdge> distrito = new WeightedPseudograph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	
	/**
	 * Construtor da classe venda imoveis. Le um arquivo no formato csv
	 * e a partir dele gera um grafo com peso. 
	 * 
	 * @param fileName nome do arquivo com seu respectivo caminho
	 */
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
	
	/**
	 * Este metodo procura o caminho mais curto a partir de um conjunto de imoveis dado
	 * e o ponto de interesse. Caso o ponto de interesse nao pertenca ao
	 * grafo ou o conjunto de imoveis seja null, o retorno eh null. Para encontrar o caminho
	 * mais curto, o metodo usa o grafo da classe DijkstraShortestPath, passando como parametro o 
	 * grafo gerado pela leitura do arquivo csv, e um grafo da classe SingleSourcePath capaz de retornar
	 * o peso dos caminhos ate o ponto de interesse obtido pelo caminho de dijkstra.
	 * 
	 * 
	 * 
	 * @param pontodeInteresse ponto de interesse
	 * @param imoveis lista de imoveis
	 * @return String do imovel cujo caminho ate o ponto de interesse eh o mais curto
	 */
	public String localizaImovel (String pontodeInteresse, Set <String> imoveis) {
		if(!distrito.containsVertex(pontodeInteresse) || null == imoveis) {
			return null;
		}
	
		DijkstraShortestPath<String, DefaultWeightedEdge> grafoDijkstra = new DijkstraShortestPath<String, DefaultWeightedEdge>(distrito);
		SingleSourcePaths<String, DefaultWeightedEdge> ssp = grafoDijkstra.getPaths(pontodeInteresse);
		
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
