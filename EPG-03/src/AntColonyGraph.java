import org.jgrapht.Graph;
import org.jgrapht.alg.scoring.AlphaCentrality;
import org.jgrapht.alg.scoring.BetweennessCentrality;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
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
        String fileName = "/home/eduardo/graph/antcolony1000gml.gml";
        colony = importDefaultGraphGML(colony, fileName);

        System.out.println("-----------PRIMEIRA QUESTAO-----------");
        showMostEfficientOnes();
        System.out.println("--------------------------------------");
        System.out.println("-----------SEGUNDA QUESTAO-----------");
        showMostInfluentialOnes();
        System.out.println("--------------------------------------");



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
