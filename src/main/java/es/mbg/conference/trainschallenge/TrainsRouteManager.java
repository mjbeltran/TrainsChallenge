package es.mbg.conference.trainschallenge;

import es.mbg.conference.business.GraphI;
import es.mbg.conference.business.impl.GraphImpl;
import es.mbg.conference.trainschallenge.domain.Graph;
import es.mbg.conference.trainschallenge.domain.GraphFactory;

/**
 * 
 * @author mjbeltran
 *
 */
public class TrainsRouteManager {

	private Graph graph;
	private GraphI graphBusiness;

	public TrainsRouteManager(String strGraph) {
		graph = GraphFactory.createGraph(strGraph.toString().replace(" ", "").replace("[", "").replace("]", ""));
		graphBusiness = GraphImpl.getInstance();
		graphBusiness.fillMapNodeAdjacents(graph);
	}

	public int getRouteDistanceBetweenNodes(String townsIn) {

		return graphBusiness.getRouteDistanceBetweenNodes(townsIn);
	}

	public int findRouteStops(String townOrigin, String townEnd, int maxStop) {

		return graphBusiness.findRouteStops(townOrigin, townEnd, maxStop);
	}

	public int findRouteCountForStops(String startTown, String endTown, int stopsRequired) {

		return graphBusiness.findRouteCountForStops(startTown, endTown, stopsRequired);
	}

	public int findShortestDistanceBetweenTowns(String startTown, String endTown) {
		return graphBusiness.findShortestDistanceBetweenTowns(startTown, endTown);
	}

	public int findRoutesCountLessThanValue(String start, String end, int maxDistance) {
		return graphBusiness.findRoutesCountLessThanValue(start, end, maxDistance);

	}

}
