package unifacs.grafos;

import unifacs.grafos.models.Grafo;
import unifacs.grafos.service.GrafoService;


public class Main {

	public static void main(String[] args) {
		
		GrafoService service = new GrafoService();
		Grafo grafo = service.createGrafo();
		int[][] matriz = service.generateMatrizAdjacencia(grafo);
		
		print(matriz, grafo);
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
