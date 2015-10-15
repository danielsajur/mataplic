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
