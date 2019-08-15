package es.mbg.conference.business;

import es.mbg.conference.trainschallenge.domain.Graph;

public interface GraphI {

	void fillMapNodeAdjacents(Graph graph);

	int getRouteDistanceBetweenNodes(String townsIn);

	int findRouteStops(String townOrigin, String townEnd, int maxStop);

	int findRouteCountForStops(String startTown, String endTown, int stopsRequired);

	int findShortestDistanceBetweenTowns(String startTown, String endTown);

	int findRoutesCountLessThanValue(String start, String end, int maxDistance);

}
