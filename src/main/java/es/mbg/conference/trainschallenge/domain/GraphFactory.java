package es.mbg.conference.trainschallenge.domain;

public class GraphFactory {

	public static Graph createGraph(String graphString) {
		
		String[] arrayGraph = graphString.split(",");
		Graph graph = new Graph();
		for(int i = 0;i< arrayGraph.length;i++) {
			graph.getConnections().add(createConnection(arrayGraph[i]));
			
		}
		
		return graph;
		
	}

	private static Connection createConnection(String connection) {

		Node node1 = new Node();
		node1.setName(connection.substring(0,1));
		
		Node node2 = new Node();
		node2.setName(connection.substring(1,2));
		
		Connection con = new Connection(node1,node2,Double.parseDouble(connection.substring(2,3)));
		
		return con;
	}
}
