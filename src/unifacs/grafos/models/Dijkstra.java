package unifacs.grafos.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Dijkstra{

	  //private final List<Vertice> vertices;
	  private final List<Aresta> arestas;
	  private Set<Vertice> settledNodes;
	  private Set<Vertice> unSettledNodes;
	  private Map<Vertice, Vertice> predecessors;
	  private Map<Vertice, Integer> distancia;

	  public Dijkstra(Grafo grafo) {
	    // create a copy of the array so that we can operate on this array
	    //this.vertices = new ArrayList<Vertice>(grafo.getVertices());
	    this.arestas = new ArrayList<Aresta>(grafo.getArestas());
	  }

	  public void Execute(Vertice v) {
	    settledNodes = new HashSet<Vertice>();
	    unSettledNodes = new HashSet<Vertice>();
	    distancia = new HashMap<Vertice, Integer>();
	    predecessors = new HashMap<Vertice, Vertice>();
	        
	    distancia.put(v, 0);
	    unSettledNodes.add(v);
	    
	    while (unSettledNodes.size() > 0) {
	      Vertice vertice = getMinimo(unSettledNodes);
	      settledNodes.add(vertice);
	      unSettledNodes.remove(vertice);
	      EncontrarDistanciaMinima(vertice);
	    }
	  }

	  private void EncontrarDistanciaMinima(Vertice v) {
	    List<Vertice> verticesAdjacentes = getVizinhos(v);
	    for (Vertice verticeAdjacente : verticesAdjacentes) {
	      if (getMenorDistancia(verticeAdjacente) > getMenorDistancia(v)+ getDistancia(v, verticeAdjacente)) {
	        distancia.put(verticeAdjacente, getMenorDistancia(v) + getDistancia(v, verticeAdjacente));
	        predecessors.put(verticeAdjacente, v);
	        unSettledNodes.add(verticeAdjacente);
	      }
	    }

	  }

	  private int getDistancia(Vertice v, Vertice destino) {
	    for (Aresta aresta : arestas) {
	      if (aresta.getVerticeEntrada().equals(v) && aresta.getVerticeSaida().equals(destino)) {
	        return aresta.getPeso();
	      }
	    }
	    throw new RuntimeException("Should not happen");
	  }

	  private List<Vertice> getVizinhos(Vertice v) {
	    List<Vertice> vizinhos = new ArrayList<Vertice>();
	    for (Aresta aresta : arestas) {
	      if (aresta.getVerticeEntrada().equals(v) && !isSettled(aresta.getVerticeSaida())) {
	        vizinhos.add(aresta.getVerticeSaida());
	      }
	    }
	    return vizinhos;
	  }

	  private Vertice getMinimo(Set<Vertice> vertices) {
		  Vertice minimo = null;
	    for (Vertice v : vertices) {
	      if (minimo == null) {
	    	  minimo = v;
	      } else {
	        if (getMenorDistancia(v) < getMenorDistancia(minimo)) {
	        	minimo = v;
	        }
	      }
	    }
	    return minimo;
	  }

	  private boolean isSettled(Vertice vertice) {
	    return settledNodes.contains(vertice);
	  }

	  private int getMenorDistancia(Vertice destino) {
	    Integer d = distancia.get(destino);
	    if (d == null) {
	      return Integer.MAX_VALUE;
	    } else {
	      return d;
	    }
	  }

	  public LinkedList<Vertice> getCaminho(Vertice alvo) {
	    LinkedList<Vertice> caminho = new LinkedList<Vertice>();
	    Vertice passo = alvo;

	    if (predecessors.get(passo) == null) {
	      return null;
	    }
	    caminho.add(passo);
	    while (predecessors.get(passo) != null) {
	    	passo = predecessors.get(passo);
	    	caminho.add(passo);
	    }
	    
	    Collections.reverse(caminho);
	    return caminho;
	  }

	} 
