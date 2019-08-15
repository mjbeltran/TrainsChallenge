package es.mbg.conference.trainschallenge.domain;

import java.util.ArrayList;
import java.util.List;

public class Graph {

	private List<Connection> connections;

	public Graph() {
		this.setConnections(new ArrayList<>());
	}

	public List<Connection> getConnections() {
		return connections;
	}

	public void setConnections(List<Connection> connections) {
		this.connections = connections;
	}
}
