package unifacs.grafos.service;

import java.util.ArrayList;
import java.util.List;

import unifacs.grafos.dao.GrafoDao;
import unifacs.grafos.models.Aresta;
import unifacs.grafos.models.Grafo;
import unifacs.grafos.models.Vertice;

public class GrafoService {

	private List<String> listaVertices; 
	private List<String> listaArestas;
	
	public GrafoService() {
		listaVertices =  new ArrayList<String>();
		listaArestas =  new ArrayList<String>();
	}
	
	public Grafo criar() {
		
		for(String v : GrafoDao.getValue("vertices").split(",")){
			listaVertices.add(v);
		}

		for(String a : GrafoDao.getValue("arestas").split(",")){
			listaArestas.add(a);
		}
		
		return criar(listaVertices, listaArestas);
	}
	
	public Grafo criar(List<String> arrVertices, List<String> arrArestas) {
		
		List<Vertice> vertices = createVertices(arrVertices);
		List<Aresta> arestas = createArestas(arrArestas, vertices);
		return gerar(vertices, arestas);
		
	}
	
	private Grafo gerar(final List<Vertice> vertices, final List<Aresta> arestas) {
		populateVerticesWithAresatas(vertices, arestas);
		Grafo grafo = new Grafo();
		grafo.setArestas(arestas);
		grafo.setVertices(vertices);
		return grafo;
	}

	private List<Vertice> createVertices(List<String> strVertices) {
		
		List<Vertice> vertices = new ArrayList<Vertice>();
		for (String strVertice : strVertices) {
			Vertice item = new Vertice();
			item.setId(strVertice);
			addVertice(vertices, item);
		}
		
		return vertices;
		
	}
	
	private List<Aresta> createArestas(List<String> strArestas, List<Vertice> vertices) {
		
		List<Aresta> arestas = new ArrayList<Aresta>();
		
		for (String strAresta : strArestas) {
			
			Vertice verticeEntrada = findVerticeById(strAresta.split("-")[0], vertices); 
			Vertice verticeSaida = findVerticeById(strAresta.split("-")[1], vertices); 

			Aresta aresta = new Aresta();
			aresta.setId(strAresta);
			aresta.setVerticeEntrada(verticeEntrada);
			aresta.setVerticeSaida(verticeSaida);
			addAresta(arestas, aresta);
		}
		
		return arestas;
	}
	
	private void populateVerticesWithAresatas(final List<Vertice> vertices, final List<Aresta> arestas) {
		
		for (Vertice vertice : vertices) {
			findArestasByVertice(vertice, arestas);
		}
	}
	
	private void findArestasByVertice(final Vertice vertice, final List<Aresta> arestas) {
		
		vertice.setArestas(new ArrayList<Aresta>());
		
		for (Aresta aresta : arestas) {
			if(aresta.getVerticeEntrada().equals(vertice) 
					|| aresta.getVerticeSaida().equals(vertice))
				vertice.getArestas().add(aresta);
		}
	}
	
	private Vertice findVerticeById(String value, List<Vertice> vertices) {
		
		for (Vertice vertice : vertices) {
			if(vertice.getId().equals(value))
				return vertice;
		}
		
		return null;
		
	}

	public int[][] generateMatrizAdjacencia(Grafo grafo) {

		int numeroVertices = grafo.getVertices().size();
		
		int[][] matriz = new int[numeroVertices][numeroVertices];
		
		for (int i = 0; i < numeroVertices; i++) {
			
			Vertice verticeEntrada = grafo.getVertices().get(i);
			
			for (int j = 0; j < numeroVertices; j++) {
				
				Vertice verticeSaida = grafo.getVertices().get(j);
				
				for(Aresta aresta : grafo.getArestas()){
					
					matriz[i][j] = 0;
					
					if(verticeEntrada.equals(aresta.getVerticeEntrada()) 
							&& verticeSaida.equals(aresta.getVerticeSaida())){
						
						matriz[i][j] = 1;
						break;
					}
					
				}
				
			}
		}
		
		return matriz;
	}
	
	public boolean addAresta(List<Aresta> arestas, Aresta aresta) {
		if(temArestaParalela(aresta, arestas)){
			return false;
		}else{
			arestas.add(aresta);
			return true;
		}
	}
	
	public boolean addVertice(final List<Vertice> vertices, Vertice vertice) {
		return vertices.add(vertice);
	}
	
	private boolean temArestaParalela(Aresta aresta, List<Aresta> arestas) {
		return arestas.contains(aresta);
	}
	
	public int obterGrau(Vertice vertice) {
		return vertice.getArestas().size() - 2;
	}
	
	public boolean removerAresta(Aresta aresta, List<Aresta> arestas){
		boolean removido = arestas.remove(aresta);
		listaArestas.remove(aresta.getId());
		return removido;
	}
	
	public boolean removerVertice(final Grafo grafo, Vertice vertice){
		
		for(Aresta aresta : vertice.getArestas()) {
			removerAresta(aresta, grafo.getArestas());
		}
		
		boolean removido = grafo.getVertices().remove(vertice);
		listaVertices.remove(vertice.getId());
		return removido;
	}
	
	public int ObterGrauMinimo(Grafo grafo){
		
		int grauMinimo = 99999;
		
		for(Vertice vertice : grafo.getVertices()){
			int grau = obterGrau(vertice);
			
			if(grau < grauMinimo){
				grauMinimo = grau;
			}
		}
		
		return grauMinimo;
	}

	public int ObterGrauMaximo(Grafo grafo){
		
		int grauMaximo = 0;
		
		for(Vertice vertice : grafo.getVertices()){
			int grau = obterGrau(vertice);
			
			if(grau > grauMaximo){
				grauMaximo = grau;
			}
		}
		
		return grauMaximo;
	}
	
	public int ObterGrauMedio(Grafo grafo){
		
		int somaGraus = 0;
		
		for(Vertice vertice : grafo.getVertices()){
			somaGraus += obterGrau(vertice);
		}
		
		return somaGraus/grafo.getVertices().size();
	}
	
	public List<Vertice> getVerticesAdjacentes(Vertice vertice){
		
		List<Vertice> adjacentes = new ArrayList<Vertice>();
		
		 for (Aresta aresta : vertice.getArestas()){
			 if(!aresta.getVerticeSaida().equals(vertice)){
				 adjacentes.add(aresta.getVerticeSaida());
			 }
		 }
		 
		return adjacentes;
	}

	public Grafo atualizar(Grafo grafo) {
		return gerar(grafo.getVertices(), grafo.getArestas());
	}

}
