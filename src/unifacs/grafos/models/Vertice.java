package unifacs.grafos.models;

import java.util.List;

public class Vertice {

	private String id;
	private List<Aresta> arestas;

	public List<Aresta> getArestas() {
		return arestas;
	}

	public void setArestas(List<Aresta> arestas) {
		this.arestas = arestas;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
