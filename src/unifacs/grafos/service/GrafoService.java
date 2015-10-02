package unifacs.grafos.service;

import java.util.ArrayList;
import java.util.List;

import unifacs.grafos.dao.GrafoDao;
import unifacs.grafos.models.Aresta;
import unifacs.grafos.models.Grafo;
import unifacs.grafos.models.Vertice;

public class GrafoService {

	private List<Vertice> vertices;
	private List<Aresta> arestas;
	
	public Grafo createGrafo() {
		
		createVertices(GrafoDao.getValue("vertices"));
		createArestas(GrafoDao.getValue("arestas"));
		populateVerticesWithAresatas();

		Grafo grafo = new Grafo();
		grafo.setArestas(arestas);
		grafo.setVertices(vertices);
		
		return grafo;
	}
	
	private void createVertices(String values) {
		
		if(vertices == null)
			vertices = new ArrayList<Vertice>();
		
		for (String str : values.split(",")) {
			
			Vertice item = new Vertice();
			item.setId(str);
			addVertice(vertices, item);
		}
	}
	
	private void createArestas(String values) {
		
		if(arestas == null)
			arestas = new ArrayList<Aresta>();
		
		for (String str : values.split(",")) {
			
			Vertice verticeEntrada = findVerticeById(str.split("-")[0]); 
			Vertice verticeSaida = findVerticeById(str.split("-")[1]); 

			Aresta aresta = new Aresta();
			aresta.setId(str);
			aresta.setVerticeEntrada(verticeEntrada);
			aresta.setVerticeSaida(verticeSaida);
			addAresta(arestas, aresta);
		}
	}
	
	private void populateVerticesWithAresatas() {
		
		for (Vertice vertice : vertices) {
			findArestasByVertice(vertice);
		}
	}
	
	private void findArestasByVertice(Vertice vertice) {
		
		vertice.setArestas(new ArrayList<Aresta>());
		
		for (Aresta aresta : arestas) {
			if(aresta.getVerticeEntrada().getId().equals(vertice.getId()) 
					|| aresta.getVerticeSaida().getId().equals(vertice.getId()))
				vertice.getArestas().add(aresta);
		}
	}
	
	private Vertice findVerticeById(String value) {
		
		for (Vertice vertice : vertices) {
			if(vertice.getId().equals(value))
				return vertice;
		}
		
		return null;
		
	}

	public List<Vertice> getVertices() {
		return vertices;
	}

	public void setVertices(List<Vertice> vertices) {
		this.vertices = vertices;
	}

	public List<Aresta> getArestas() {
		return arestas;
	}

	public void setArestas(List<Aresta> arestas) {
		this.arestas = arestas;
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
		
		return temArestaParalela(aresta, arestas) ? false : arestas.add(aresta);
	}
	
	public boolean addVertice(List<Vertice> vertices, Vertice vertice) {
		return vertices.add(vertice);
	}
	
	private boolean temArestaParalela(Aresta aresta, List<Aresta> arestas) {
		return arestas.contains(aresta);
	}
	
	public int obterGrau(Vertice vertice) {
		return vertice.getArestas().size() - 2;
	}
	
	public boolean removerAresta(Aresta aresta, List<Aresta> arestas){
		return arestas.remove(aresta);
	}
	
	public boolean removerVertice(Grafo grafo, Vertice vertice){
		
		for(Aresta aresta : vertice.getArestas()) {
			removerAresta(aresta, grafo.getArestas());
		}
		
		return vertices.remove(vertice);
	}
	
	public int ObterGrauMinimo(Grafo grafo){
		
		int grauMinimo = 9999;
		
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
	
}
