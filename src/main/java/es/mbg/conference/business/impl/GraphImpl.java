package es.mbg.conference.business.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import es.mbg.conference.business.GraphI;
import es.mbg.conference.trainschallenge.domain.Connection;
import es.mbg.conference.trainschallenge.domain.Graph;
import es.mbg.conference.trainschallenge.domain.Node;

/**
 * 
 * @author mjbeltran
 *
 */
public class GraphImpl implements GraphI {

	private static GraphImpl instance;

	private Map<String, Map<String, Double>> mapNodeAdjacentes = new HashMap<String, Map<String, Double>>();

	public static GraphImpl getInstance() {
		if (instance == null) {
			instance = new GraphImpl();
		}
		return instance;
	}

	@Override
	public void fillMapNodeAdjacents(Graph graph) {
		Node startNode = null;
		Map<String, Double> mapNodesAdj;
		for (Connection con : graph.getConnections()) {
			startNode = con.getStart();
			if (!mapNodeAdjacentes.containsKey(startNode.name)) {
				mapNodesAdj = new HashMap<>();
				mapNodesAdj.put(con.getEnd().getName(), con.getWeight());
				mapNodeAdjacentes.put(startNode.getName(), mapNodesAdj);
			} else {
				mapNodesAdj = mapNodeAdjacentes.get(con.getStart().getName());
				mapNodesAdj.put(con.getEnd().getName(), con.getWeight());
				mapNodeAdjacentes.put(startNode.getName(), mapNodesAdj);
			}
		}

	}

	@Override
	public int getRouteDistanceBetweenNodes(String townsIn) {
		int result = 0;
		int resultAux = 0;
		String[] towns = townsIn.split("-");
		for (int i = 0; i < towns.length; i++) {
			resultAux = 0;
			if (i + 1 < towns.length) {
				resultAux = getDistance(towns[i], towns[i + 1], mapNodeAdjacentes);
			}
			if (resultAux == -1) {
				return -1;
			}
			result += resultAux;
		}

		return result;
	}

	public static int getDistance(String town1, String town2, Map<String, Map<String, Double>> mapNodeAdjacentes) {
		int valueResult = 0;
		Map<String, Double> nodesAdj = mapNodeAdjacentes.get(town1);
		if (nodesAdj.containsKey(town2)) {
			valueResult += nodesAdj.get(town2);
		} else {
			valueResult = -1;
		}
		return valueResult;
	}

	@Override
	public int findRouteStops(String townOrigin, String townEnd, int maxStop) {
		int stops = 0;
		Map<String, Double> nodesAdjs;
		int trips = 0;
		while (stops < maxStop) {
			nodesAdjs = mapNodeAdjacentes.get(townOrigin);
			for (Map.Entry<String, Double> nodeAd : nodesAdjs.entrySet()) {
				if (!nodeAd.getKey().equals(townEnd)) {
					townOrigin = nodeAd.getKey();
					stops++;
					break;
				} else {
					trips++;
					townOrigin = nodeAd.getKey();
					break;

				}
			}
		}
		return trips;
	}

	@Override
	public int findRouteCountForStops(String startTown, String endTown, int stopsRequired) {
		Set<String> foundRoutes = new HashSet<>();
		Integer stopsLeft = stopsRequired - 1; // 0 Based
		for (String nextTown : mapNodeAdjacentes.get(startTown).keySet()) {
			List<String> validRoutes = buildStopsBaseRouteForStops(nextTown, endTown, stopsLeft);
			for (String route : validRoutes) {
				foundRoutes.add(startTown + route);
			}
		}

		return foundRoutes.size();
	}

	private List<String> buildStopsBaseRouteForStops(String currentTown, String endTown, Integer stopsRequired) {
		List<String> routesAvailable = new ArrayList<>();
		if (currentTown.equals(endTown) && stopsRequired == 0) {
			routesAvailable.add(endTown.toString());
			return routesAvailable;
		}
		Integer stopsLeft = stopsRequired - 1;
		if (stopsLeft >= 0) {
			for (String nextStation : mapNodeAdjacentes.get(currentTown).keySet()) {
				List<String> validRoutes = buildStopsBaseRouteForStops(nextStation, endTown, stopsLeft);
				for (String route : validRoutes) {
					routesAvailable.add(currentTown + route);
				}
			}
		}

		return routesAvailable;
	}

	@Override
	public int findShortestDistanceBetweenTowns(String startTown, String endTown) {
		List<String> routesValides = buildRoutes(startTown, endTown);

		return calculateMinValueDistanceRoute(routesValides);
	}

	private List<String> buildRoutes(String startTown, String endtTown) {
		List<String> knownRoutes = new ArrayList<>();
		for (String nextStation : mapNodeAdjacentes.get(startTown).keySet()) {
			List<String> validRoutes = buildRouteFromStartToEnd(nextStation, endtTown, new HashSet<>());
			for (String route : validRoutes) {
				knownRoutes.add(startTown + route);
			}
		}
		return knownRoutes;
	}

	private List<String> buildRouteFromStartToEnd(String currentTown, String endTown, Set<String> townsVisited) {

		List<String> routesAvailable = new ArrayList<>();

		if (currentTown.equals(endTown)) {
			routesAvailable.add(endTown.toString());
			return routesAvailable;
		}

		for (String nextTown : mapNodeAdjacentes.get(currentTown).keySet()) {
			Set<String> visitedSnapshot = new HashSet<>();
			visitedSnapshot.addAll(townsVisited);
			if (!townsVisited.contains(nextTown)) {
				visitedSnapshot.add(currentTown);
				List<String> validRoutes = buildRouteFromStartToEnd(nextTown, endTown, visitedSnapshot);
				for (String route : validRoutes) {
					routesAvailable.add(currentTown + route);
				}
			}
		}

		return routesAvailable;
	}

	private int calculateMinValueDistanceRoute(List<String> routesValides) {

		Double minValueRoute = Double.MAX_VALUE;
		Double valueRoute = 0.0;
		for (String route : routesValides) {
			valueRoute = calculateValueRoute(route);
			if (valueRoute < minValueRoute) {
				minValueRoute = valueRoute;
			}
		}

		return minValueRoute.intValue();
	}

	private double calculateValueRoute(String route) {

		char[] routeChar = route.toCharArray();
		Double value = 0.0;
		for (int i = 0; i < routeChar.length - 1; i++) {
			String node = Character.toString(routeChar[i]);
			Map<String, Double> adj = mapNodeAdjacentes.get(node);
			node = Character.toString(routeChar[i + 1]);
			value += adj.get(node);
		}

		return value;

	}

	@Override
	public int findRoutesCountLessThanValue(String start, String end, int maxDistance) {
		int routeCount = 0;

		Map<String, Double> routesDistanceMap = getRouteDistanceMapMaxDistance(start, maxDistance);
		for (String route : routesDistanceMap.keySet()) {
			if (route.endsWith(end) && !route.equals(start)) {
				routeCount++;
			}
		}
		return routeCount;
	}

	private Map<String, Double> getRouteDistanceMapMaxDistance(String start, int maxDistance) {
		Map<String, Double> routesDistanceMap = new LinkedHashMap<>();

		List<String> currentRoutes = new ArrayList<>();
		List<String> nextRoutes;
		routesDistanceMap.put(start, 0.0);
		currentRoutes.add(start);

		while (currentRoutes.size() > 0) {
			nextRoutes = new ArrayList<>();
			for (String currentRoute : currentRoutes) {
				String lastStop = currentRoute.substring(currentRoute.length() - 1);
				Map<String, Double> adjacentNodes = mapNodeAdjacentes.get(lastStop);
				if (adjacentNodes != null) {
					for (String adjacentKey : adjacentNodes.keySet()) {
						Double currentDistance = routesDistanceMap.get(currentRoute);
						String newRoute = currentRoute + adjacentKey;
						Double newDistance = currentDistance + adjacentNodes.get(adjacentKey);
						if (newDistance < maxDistance) {
							nextRoutes.add(newRoute);
							routesDistanceMap.put(newRoute, newDistance);
						}
					}
				}
			}
			currentRoutes.clear();
			currentRoutes.addAll(nextRoutes);
		}
		return routesDistanceMap;
	}
}
