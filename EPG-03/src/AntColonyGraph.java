import org.jgrapht.Graph;
import org.jgrapht.alg.scoring.AlphaCentrality;
import org.jgrapht.alg.scoring.BetweennessCentrality;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.alg.scoring.ClusteringCoefficient;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.io.EdgeProvider;
import org.jgrapht.io.GmlImporter;
import org.jgrapht.io.ImportException;
import org.jgrapht.io.VertexProvider;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class AntColonyGraph {

    private static Graph<String, DefaultEdge> colony = new SimpleWeightedGraph<>(DefaultEdge.class);

    public static void main(String[] args) {

        //Importing graph ps.: rewrite your path to antcolony1000.gml
        String fileName = "./EPG-03/src/graph/antcolony1000gml.gml";
        colony = importDefaultGraphGML(colony, fileName);

        System.out.println("-----------PRIMEIRA QUESTAO-----------");
        showMostEfficientOnes();
        System.out.println("--------------------------------------");
        System.out.println("-----------SEGUNDA QUESTAO-----------");
        showMostInfluentialOnes();
        System.out.println("--------------------------------------");
        System.out.println("-----------TERCEIRA QUESTAO-----------");
        getClusteringCoefficient();
        System.out.println("--------------------------------------");
        System.out.println("-----------QUARTA QUESTAO-----------");
        assortativityCoefficient(colony);

    }

    /**
     * Este metodo mostra no console as 5 melhores formigas no quesito de conducao de informacoes.
     * O metodo usa um objeto da classe BetweenessCentrality que calcula a centralidade de cada vertice do grafo
     * assim pode-se resgatar as 5 primeiras formigas.
     *
     */
    private static void showMostEfficientOnes(){

        BetweennessCentrality<String, DefaultEdge> betweenessCentrality = new BetweennessCentrality<>(colony, true);
        Set<String> efficientAnts = new HashSet<String>();
        for(int i = 0; i < 5; i ++){

            String currentAnt = "1";
            for (String ant : betweenessCentrality.getScores().keySet()){

                if(betweenessCentrality.getVertexScore(ant) >= betweenessCentrality.getVertexScore(currentAnt)
                    && !efficientAnts.contains(ant))
                    currentAnt = ant;
            }
            efficientAnts.add(currentAnt);
        }

        int cont = 1;
        for(String ant : efficientAnts){
            System.out.println(cont++ + "ª Formiga mais eficiente na transmissao de informacao: " + ant);
        }
    }

    public static void assortativityCoefficient (Graph<String, DefaultEdge> graph) {
        // from: https://github.com/Infeligo/jgrapht-metrics/blob/master/src/main/java/org/jgrapht/metrics/AssortativityCoefficientMetric.java
    	double edgeCount = graph.edgeSet().size();
        double n1 = 0, n2 = 0, dn = 0;

        for (DefaultEdge e : graph.edgeSet()) {
            int d1 = graph.degreeOf(graph.getEdgeSource(e));
            int d2 = graph.degreeOf(graph.getEdgeTarget(e));

            n1 += d1 * d2;
            n2 += d1 + d2;
            dn += d1 * d1 + d2 * d2;
        }
        n1 /= edgeCount;
        n2 = (n2 / (2 * edgeCount)) * (n2 / (2 * edgeCount));
        dn /= (2 * edgeCount);
        
        double assortativity =  (n1 - n2) / (dn - n2);
        
        System.out.println("Grau de assortividade: " + assortativity);
        System.out.println("Devido ao grau de assortatividade negativo e próximo de -1, vemos que \n"
        		+ "o grafo é quase completamente não assortativo, ou seja, as formigas não se relacionam com outras \n"
        		+ "de grau semelhante."
        		);
        
    }
    
    private static void showMostInfluentialOnes(){
        AlphaCentrality<String, DefaultEdge> ac = new AlphaCentrality<>(colony, 0.001);

        Set<String> mostInfluential = new HashSet<>();
        for(int i = 0; i < 5; i ++){

            String currentAnt = "1";
            for(String ant : ac.getScores().keySet()){
                if(ac.getVertexScore(ant) >= ac.getVertexScore(currentAnt) && !mostInfluential.contains(ant)){
                    currentAnt = ant;
                }
            }
            mostInfluential.add(currentAnt);
        }

        int cont = 1;
        for(String ant : mostInfluential){
            System.out.println(cont++ + "ª Formiga mais influente: " + ant);
        }
    }
    
    private static void getClusteringCoefficient() {
    	ClusteringCoefficient<String, DefaultEdge> clusteringCoefficient = new ClusteringCoefficient<String, DefaultEdge>(colony);
    	System.out.println("Coeficiente de clustering médio: " + clusteringCoefficient.getAverageClusteringCoefficient());
    	System.out.println("Coeficiente de clustering global: " + clusteringCoefficient.getGlobalClusteringCoefficient());
    	System.out.println("Devido ao baixo coeficiente de clustering global, vemos que há uma baixa tendencia de formação de grupos isolados.");
    }

    /**
     *
     * O metodo retorna um grafo gerado a partir da importacao de um arquivo GML. Recebe como parametros o caminho do gml
     * e um grafo para que receba os vertices e as arestas.
     *
     * @param graph
     * @param filename
     * @return graph gerado a partir do arquivo
     */

    private static Graph<String, DefaultEdge> importDefaultGraphGML (Graph<String,DefaultEdge> graph, String filename) {
        VertexProvider<String> vp1 = (label, attributes) -> label;
        EdgeProvider<String, DefaultEdge> ep1 = (from, to, label, attributes) -> new DefaultEdge();
        GmlImporter<String, DefaultEdge> gmlImporter = new GmlImporter<>(vp1, ep1);
        try {
            gmlImporter.importGraph(graph, readFile(filename));
        } catch (ImportException e) {
            throw new RuntimeException(e);
        }
        return graph;
    }

    /**
     *  Le um arquivo gml dado seu caminho. Retorna um objeto String contendo o que ha no arquivo.
     *
     * @param filename
     * @return string object contendo os dados do arquivo gml
     */

    private static Reader readFile(String filename) {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringReader readergml = new StringReader(contentBuilder.toString());
        return readergml;
    }


}
