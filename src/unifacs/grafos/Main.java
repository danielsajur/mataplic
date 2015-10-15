package unifacs.grafos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import unifacs.grafos.models.Aresta;
import unifacs.grafos.models.Grafo;
import unifacs.grafos.models.Vertice;
import unifacs.grafos.service.GrafoService;

public class Main {

	private static List<String> MENU;
	
	static{
		MENU = new ArrayList<String>();
		MENU.add(" Adicionar Vertice");
		MENU.add(" Remover Vertice");
		MENU.add(" Adicionar Aresta (x-y)");
		MENU.add(" Remover Aresta (x-y)");
		MENU.add(" Obter grau mínimo, máximo e médio");
		MENU.add(" Obter vertices Adjacentes");
		MENU.add(" Gerar Matriz de Adjacências");
		MENU.add(" Verificar Grafo Conexo");
		MENU.add(" Verificar Grafo Euleriano");
		MENU.add("S a i r");
	}
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {

		GrafoService service = new GrafoService();
		Grafo grafo = service.criar();


		Scanner scanner = new Scanner(System.in);
		int itemMenu = 0;

		while(itemMenu < MENU.size()){
			
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
				printGrau(grafo);
				break;
			case 6:
				printVerticesAdjacentes(service, grafo);
				break;
			case 7:
				print(service, grafo);
				break;
			case 8:
				printGrafoConexo(grafo);
				break;
			case 9:
				printGrafoEuleriano(grafo);
				break;
			}
			
			if(itemMenu < MENU.size()){
				grafo = service.atualizar(grafo);
			}
		}
		
	}

	private static void printGrafoEuleriano(Grafo grafo) {

		if(grafo.temCaminhoEuleriano()){
			System.out.println("\n  Existe um caminho de Euler.");
		}else{
			System.out.println("\n  Não existe um caminho de Euler.");
		}
	}

	private static void printGrafoConexo(Grafo grafo) {
		
		if(grafo.isConexo()){
			System.out.println("\n  Este Grafo é conexo.");
		}else{
			System.out.println("\n  Este Grafo não é conexo.");
		}
		
	}

	private static void printVerticesAdjacentes(final GrafoService service, final Grafo grafo) {
		
		Vertice verticeTestado = selecionarVertice(service, grafo, null);
		List<Vertice> verticesAdjacentes = service.getVerticesAdjacentes(verticeTestado);
		System.out.println("  Vertices adjacentes de " + verticeTestado.getId());
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
		int indice = 0;
		for (Aresta aresta : grafo.getArestas()) {
			System.out.println("  " + (++indice) + " - Vertice " + aresta.getId());
		}
		System.out.print(" 	Selecione um vertice:");
		
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
		
		if(texto == null || texto.trim().isEmpty()){
			texto = "Selecione um vertice: ";
		}
		
		System.out.print(" 	" + texto);
		Scanner scanner = new Scanner(System.in);
		int indiceEscolhido = scanner.nextInt();
		
		Vertice verticeEscolhido = grafo.getVertices().get(indiceEscolhido-1);
		System.out.println(" 	\nO vertice escolhido: " + verticeEscolhido.getId());
		return verticeEscolhido;
	}

	private static void printGrau(final Grafo grafo) {
		System.out.println("  Grau minimo: " + grafo.grauMinimo());
		System.out.println("  Grau maximo: " + grafo.grauMaximo());
		System.out.println("  Grau medio: " + grafo.grauMedio());
	}

	private static void printMenu() {
		
		System.out.println("\n\n  M E N U\n");
		int i = 0;
		for(String itemMenu : MENU){
			System.out.println((++i) + " - " + itemMenu);
		}
		
	}

	private static void print(GrafoService service, Grafo grafo) {

		int[][] matriz = service.atualizar(grafo).matrizAdjacencias();
		
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
