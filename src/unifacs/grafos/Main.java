package unifacs.grafos;

import java.util.List;

import unifacs.grafos.models.Grafo;
import unifacs.grafos.models.Vertice;
import unifacs.grafos.service.GrafoService;


public class Main {

	public static void main(String[] args) {
		
		GrafoService service = new GrafoService();
		Grafo grafo = service.createGrafo();
		int[][] matriz = service.generateMatrizAdjacencia(grafo);
		
		print(matriz, grafo);
		
		System.out.println("");
		
		System.out.println("Grau minimo: "+ service.ObterGrauMinimo(grafo));
		System.out.println("Grau maximo: "+ service.ObterGrauMaximo(grafo));
		System.out.println("Grau medio: "+ service.ObterGrauMedio(grafo));
		
		Vertice verticeTestado = grafo.getVertices().get(2);
		
		List<Vertice> verticesAdjacentes = service.getVerticesAdjacentes(verticeTestado);
		
		System.out.println("Vertices adjacentes de " + verticeTestado.getId());
		
		for (Vertice vertice : verticesAdjacentes) {
			System.out.println(vertice.getId());
		}
		
	}

	private static void print(int[][] matriz, Grafo grafo) {

		System.out.print("  ");
		for (int i = 0; i < grafo.getVertices().size(); i++) {
			System.out.print(grafo.getVertices().get(i).getId() + " ");	
		}
		
		System.out.println("");
		
		for (int i = 0; i < grafo.getVertices().size(); i++) {
			
			System.out.print(grafo.getVertices().get(i).getId() + " ");
			
			for (int j = 0; j < grafo.getVertices().size(); j++) {
				System.out.print(matriz[i][j] + " ");
			}
			
			System.out.println("");
			
		}

		
	}

}
