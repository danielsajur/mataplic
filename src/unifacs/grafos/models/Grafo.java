package unifacs.grafos.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Grafo {

	private String id;
	private String name;
	private String type;
	private List<Vertice> vertices;
	private List<Aresta> arestas;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public int grauMinimo(){
		int grauMinimo = 99999;
		
		for(Vertice vertice : getVertices()){
			int grau = grau(vertice);
			
			if(grau < grauMinimo){
				grauMinimo = grau;
			}
		}
		
		return grauMinimo;
	}
	
	public int grauMaximo(){
		
		int grauMaximo = 0;
		
		for(Vertice vertice : getVertices()){
			int grau = grau(vertice);
			
			if(grau > grauMaximo){
				grauMaximo = grau;
			}
		}
		
		return grauMaximo;
	}
	
	public int grauMedio(){
		
		int somaGraus = 0;
		
		for(Vertice vertice : getVertices()){
			somaGraus += grau(vertice);
		}
		
		return somaGraus/getVertices().size();
	}

	public int grau(Vertice vertice){
		
		Set<String> arestas = new HashSet<String>();
		
		for(Aresta aresta : vertice.getArestas()){
			for(String id : aresta.getId().split("-")){
				try{
					if(!id.equals(vertice.getId())){
						arestas.add(id);
					}
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
			}
		}
		
		return arestas.size();
	}
	
	public boolean isConexo() {

		int[][] matriz = matrizAdjacencias();
		
		if (matriz.length > 0) {
			
			for (int i = 0; i < matriz.length - 1; i++) {
				
				int x = 0;
				
				for (int j = 0; j < matriz.length - 1; j++) {

					if (i != j) {
						
						if (matriz[i][j] == 0 && matriz[j][i] == 0) {
							x++;
						}
					}
				}
				
				if (x == matriz.length - 2) {
					return false;
				}
				
			}
			
			return true;
		}
		
		return false;
	}
	
	public int[][] matrizAdjacencias() {

		int numeroVertices = getVertices().size();
		
		int[][] matriz = new int[numeroVertices][numeroVertices];
		
		for (int i = 0; i < numeroVertices; i++) {
			
			Vertice verticeEntrada = getVertices().get(i);
			
			for (int j = 0; j < numeroVertices; j++) {
				
				Vertice verticeSaida = getVertices().get(j);
				
				for(Aresta aresta : getArestas()){
					
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
