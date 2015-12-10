package unifacs.grafos.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import unifacs.grafos.service.GrafoService;


public class BellmanFord {

	private Map<String, Double> distanciaAtual = new HashMap<String, Double>();
	
	
	public List<Vertice> verificaMenorCaminho(Grafo grafo, Vertice verticeInicial) {
		
		inicializarDistancias(grafo.getVertices(), verticeInicial);
		
		while (true) {
			
			preecherDistanciaAtual(grafo);
			iteracaoBellmanFord(grafo);
			
			for (Map.Entry<String, Double> distancia : distanciaAtual.entrySet()) {
				System.out.println(distancia.getKey() + " => " + distancia.getValue());
			}
			
			if(caminhosMapeados(grafo))
				break;
		}
		

		return null;
	}
	
	
	private void preecherDistanciaAtual(Grafo grafo) {
		
		Map<String, Double> distancias = new HashMap<String, Double>();
		
		for (Vertice vertice : grafo.getVertices()) {
			distancias.put(vertice.getId(), vertice.getDistancia());
		}
		distanciaAtual = distancias;
	}


	private boolean caminhosMapeados(Grafo grafo) {
		
		Map<String, Double> distancias = new HashMap<String, Double>();
		
		for (Vertice vertice : grafo.getVertices()) {
			distancias.put(vertice.getId(), vertice.getDistancia());
		}
		
		return distanciaAtual.equals(distancias);
	}


	public void iteracaoBellmanFord(Grafo grafo) {
		
		for (Vertice vertice : grafo.getVertices()) {
			
			for (Aresta aresta : vertice.getArestas()) {
				if (aresta.getVerticeSaida().getId().equals(vertice.getId())) {
					Double distancia = aresta.getVerticeEntrada().getDistancia() + aresta.getPeso();
					if (distancia < vertice.getDistancia()) {
						vertice.setDistancia(distancia);
					}
				}
			}
			
		}
	}
	
	public void inicializarDistancias(List<Vertice> vertices, Vertice verticeInicial) {
		for (Vertice vertice : vertices) {
			if(vertice.getId().equals(verticeInicial.getId())) {
				vertice.setDistancia(0.0);
				distanciaAtual.put(vertice.getId(), 0.0);
			} else {
				vertice.setDistancia(Double.POSITIVE_INFINITY);
				distanciaAtual.put(vertice.getId(), Double.POSITIVE_INFINITY);
			}
		}
	}
	
	public static void main(String[] args) {
		
		Grafo grafo = new GrafoService().criar();
		new BellmanFord().verificaMenorCaminho(grafo, grafo.getVertice("a")); 
	}

}
