package unifacs.grafos.models;

public class Aresta {

	private String id;
	private Vertice verticeEntrada;
	private Vertice verticeSaida;
	private int Peso;
	
	public int getPeso(){
		return Peso;
	}
	
	public void setPeso(int peso){
		this.Peso = peso;		
	}
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aresta other = (Aresta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Aresta [" + verticeEntrada.getId() + "-" + verticeSaida.getId() + "]";
	}

}
	