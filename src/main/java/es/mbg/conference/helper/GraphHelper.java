//package es.mbg.conference.helper;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import es.mbg.conference.trainschallenge.domain.Connection;
//import es.mbg.conference.trainschallenge.domain.Graph;
//import es.mbg.conference.trainschallenge.domain.Node;
//
//public class GraphHelper {
//
//	public static void fillMapNodeAdjacents(Graph graph, Map<String, Map<String, Double>> mapNodeAdjacentes) {
//		Node startNode = null;
//		Map<String, Double> mapNodesAdj;
//		for (Connection con : graph.getConnections()) {
//			startNode = con.getStart();
//			if (!mapNodeAdjacentes.containsKey(startNode.name)) {
//				mapNodesAdj = new HashMap<>();
//				mapNodesAdj.put(con.getEnd().getName(), con.getWeight());
//				mapNodeAdjacentes.put(startNode.getName(), mapNodesAdj);
//			} else {
//				mapNodesAdj = mapNodeAdjacentes.get(con.getStart().getName());
//				mapNodesAdj.put(con.getEnd().getName(), con.getWeight());
//				mapNodeAdjacentes.put(startNode.getName(), mapNodesAdj);
//			}
//		}
//
//	}
//
//	public static int getDistance(String town1, String town2, Map<String, Map<String, Double>> mapNodeAdjacentes) {
//		int valueResult = 0;
//		Map<String, Double> nodesAdj = mapNodeAdjacentes.get(town1);
//		if (nodesAdj.containsKey(town2)) {
//			valueResult += nodesAdj.get(town2);
//		} else {
//			valueResult = -1;
//		}
//		return valueResult;
//	}
//
//}
