package unifacs.grafos.models;



public class Aresta {

	private String id;
	private Vertice verticeEntrada;
	private Vertice verticeSaida;

	public Vertice getVerticeEntrada() {
		return verticeEntrada;
	}

	public void setVerticeEntrada(Vertice verticeEntrada) {
		this.verticeEntrada = verticeEntrada;
	}

	public Vertice getVerticeSaida() {
		return verticeSaida;
	}

	public void setVerticeSaida(Vertice verticeSaida) {
		this.verticeSaida = verticeSaida;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
	