package unifacs.grafos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
			vertices.add(item);
		}
	}
	
	private void createArestas(String values) {
		
		if(arestas == null)
			arestas = new ArrayList<Aresta>();
		
		for (String str : values.split(",")) {
			
			Vertice verticeEntrada = findVerticeById(str.split("-")[0]); 
			Vertice verticeSaida = findVerticeById(str.split("-")[1]); 

			Aresta aresta = new Aresta();
			aresta.setId(str + "_" + getRandomNumberAsString());
			aresta.setVerticeEntrada(verticeEntrada);
			aresta.setVerticeSaida(verticeSaida);
			arestas.add(aresta);
		}
	}
	
	private String getRandomNumberAsString() {
		return String.valueOf(((new Random().nextFloat() + 100000) * 900000));
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
	
}
