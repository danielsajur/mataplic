package unifacs.grafos;

import java.util.List;
import java.util.Scanner;

import unifacs.grafos.models.Aresta;
import unifacs.grafos.models.Grafo;
import unifacs.grafos.models.Vertice;
import unifacs.grafos.service.GrafoService;

public class Main {

	@SuppressWarnings("resource")
	public static void main(String[] args) {

		GrafoService service = new GrafoService();
		Grafo grafo = service.criar();


		Scanner scanner = new Scanner(System.in);
		int itemMenu = 0;

		while(itemMenu < 8){
			
			printMenu();
			System.out.print("\nInforme sua opção: ");
			itemMenu = scanner.nextInt();
			
			switch (itemMenu) {
			case 1:
				adicionarVertice(service, grafo);
				break;
			case 2:
				removerVertice(service, grafo);
				break;
			case 3:
				adicionarAresta(service, grafo);
				break;
			case 4:
				removerAresta(service, grafo);
				break;
			case 5:
				printGrau(service, grafo);
				break;
			case 6:
				printVerticesAdjacentes(service, grafo);
				break;
			case 7:
				print(service, grafo);
				break;
			default:
				print(service, grafo);
				break;
			}
			
			grafo = service.atualizar(grafo);
		}
		
	}

	private static void printVerticesAdjacentes(final GrafoService service, final Grafo grafo) {
		
		Vertice verticeTestado = selecionarVertice(service, grafo, null);
		List<Vertice> verticesAdjacentes = service.getVerticesAdjacentes(verticeTestado);
		System.out.println("Vertices adjacentes de " + verticeTestado.getId());
		for (Vertice vertice : verticesAdjacentes) {
			System.out.println(vertice.getId());
		}		
	}

	private static void removerAresta(final GrafoService service, Grafo grafo) {
		Aresta aresta = selecionarAresta(service, grafo);
		service.removerAresta(aresta, grafo.getArestas());
	}


	private static void adicionarAresta(final GrafoService service, Grafo grafo) {
		
		Vertice verticeSaida = selecionarVertice(service, grafo, "Selecione o Vértice de saída: ");
		Vertice verticeEntrada = selecionarVertice(service, grafo, "Selecione o Vértice de entrada: ");
		String id = verticeEntrada.getId()+"-"+verticeSaida.getId();
		
		Aresta aresta = new Aresta();
		aresta.setId(id);
		aresta.setVerticeEntrada(verticeEntrada);
		aresta.setVerticeSaida(verticeSaida);
		service.addAresta(grafo.getArestas(), aresta);
		
	}

	@SuppressWarnings("resource")
	private static Aresta selecionarAresta(GrafoService service, Grafo grafo) {

		Scanner scanner = new Scanner(System.in);
		System.out.println(" 	Selecione um vertice:");
		int indice = 0;
		for (Aresta aresta : grafo.getArestas()) {
			System.out.println("  " + (++indice) + " - Vertice " + aresta.getId());
		}
		
		int indiceEscolhido = scanner.nextInt()-1;
		return grafo.getArestas().get(indiceEscolhido);
		
	}
	
	private static void removerVertice(final GrafoService service, final Grafo grafo) {
		
		Vertice vertice = selecionarVertice(service, grafo, null);
		service.removerVertice(grafo, vertice);
	}
	
	@SuppressWarnings("resource")
	private static void adicionarVertice(final GrafoService service, final Grafo grafo) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("	Informe o novo Vertice: ");
		String novoVertice = scanner.next();
		service.addVertice(grafo.getVertices(), new Vertice(novoVertice));
	}
	
	@SuppressWarnings("resource")
	private static Vertice selecionarVertice(GrafoService service, Grafo grafo, String texto){
		
		int indice = 1;
		for (Vertice vertice : grafo.getVertices()) {
			System.out.println("  " + (indice++) + " - Vertice " + vertice.getId());
		}
		
		if(texto != null && !texto.trim().isEmpty()){
			System.out.println(" 	" + texto);
		}else{
			System.out.print(" 	Selecione um vertice: ");
		}
		
		Scanner scanner = new Scanner(System.in);
		int indiceEscolhido = scanner.nextInt();
		Vertice verticeEscolhido = grafo.getVertices().get(indiceEscolhido-1);
		System.out.println(" 	\nO vertice escolhido: " + verticeEscolhido.getId());
		return verticeEscolhido;
	}

	private static void printGrau(final GrafoService service, final Grafo grafo) {
		System.out.println("Grau minimo: " + service.ObterGrauMinimo(grafo));
		System.out.println("Grau maximo: " + service.ObterGrauMaximo(grafo));
		System.out.println("Grau medio: " + service.ObterGrauMedio(grafo));
	}

	private static void printMenu() {
		
		System.out.println("\n\n M E N U\n");
		System.out.println(" 1 - Novo Vertice");
		System.out.println(" 2 - Remover Vertice");
		System.out.println(" 3 - Nova Aresta (x-y)");
		System.out.println(" 4 - Remover Aresta (x-y)");
		System.out.println(" 5 - Grau mínimo, máximo e médio");
		System.out.println(" 6 - Vertices Adjacentes");
		System.out.println(" 7 - Matriz de Adjacências");
		System.out.println(" 8 - S a i r");
		
	}

	private static void print(GrafoService service, Grafo grafo) {

		grafo = service.atualizar(grafo);
		int[][] matriz = service.generateMatrizAdjacencia(grafo);
		
		System.out.print("  ");
		for (int i = 0; i < grafo.getVertices().size(); i++) {
			System.out.print(grafo.getVertices().get(i).getId() + " ");
		}

		System.out.println("");

		for (int i = 0; i < grafo.getVertices().size(); i++) {

			System.out.print(grafo.getVertices().get(i).getId() + " ");

			for (int j = 0; j < grafo.getVertices().size(); j++) {
				try{
					System.out.print(matriz[i][j] + " ");
				}catch(Exception e){
					System.out.print( "0 ");
				}
			}

			System.out.println("");

		}

	}

}
