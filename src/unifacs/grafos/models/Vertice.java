package unifacs.grafos.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Vertice {

	private String id;
	private List<Aresta> arestas;
	private Double distancia;

	public Vertice() {}
	
	public Vertice(String id) {
		this.id = id;		
	}
	
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

	@Override
	public String toString() {
		return "Vertice [id=" + id + "]";
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
		Vertice other = (Vertice) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
public int grau(){
		
		Set<String> arestas = new HashSet<String>();
		
		for(Aresta aresta : getArestas()){
			for(String id : aresta.getId().split("-")){
				try{
					if(!id.equals(getId())){
						arestas.add(id);
					}
				}catch(Exception e){ }
			}
		}
		
		int size = arestas.size();
		
		return size;
	}

public Double getDistancia() {
	return distancia;
}

public void setDistancia(Double distancia) {
	this.distancia = distancia;
}

}
