package es.mbg.conference.trainschallenge;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.mbg.conference.exception.FileParameterException;

/**
 * Unit test for simple App.
 */
public class TrainsRouteTest {

	TrainsRouteManager trainsRouteManager;

	@Before
	public void setUp() {
		String routesStr = "AB5,  BC4,  CD8,  DC8,  DE6,  AD5,  CE2,  EB3,  AE7";
		trainsRouteManager = new TrainsRouteManager(routesStr);
	}

	@Test
	public void routeTownsReturnDistance() {

		Integer routeDistance = trainsRouteManager.getRouteDistanceBetweenNodes("A-B-C");
		Assert.assertEquals(new Integer(9), routeDistance);

		routeDistance = trainsRouteManager.getRouteDistanceBetweenNodes("A-D");
		Assert.assertEquals(new Integer(5), routeDistance);

		routeDistance = trainsRouteManager.getRouteDistanceBetweenNodes("A-D-C");
		Assert.assertEquals(new Integer(13), routeDistance);

		routeDistance = trainsRouteManager.getRouteDistanceBetweenNodes("A-E-B-C-D");
		Assert.assertEquals(new Integer(22), routeDistance);

		routeDistance = trainsRouteManager.getRouteDistanceBetweenNodes("A-E-D");
		Assert.assertEquals(new Integer(-1), routeDistance);
	}

	@Test
	public void routeStationsMaxStopsReturnCount() {

		Integer routeCount = trainsRouteManager.findRouteStops("C", "C", 3);

		Assert.assertEquals(new Integer(2), routeCount);
	}

	@Test
	public void routeStationsStopsReturnCount() {

		Integer routeCount = trainsRouteManager.findRouteCountForStops("A", "C", 4);

		Assert.assertEquals(new Integer(3), routeCount);

	}

	@Test
	public void routeStationsRouteReturnShortestDistance() {

		Integer shortestDistance = trainsRouteManager.findShortestDistanceBetweenTowns("A", "C");
		Assert.assertEquals(new Integer(9), shortestDistance);
		shortestDistance = trainsRouteManager.findShortestDistanceBetweenTowns("B", "B");
		Assert.assertEquals(new Integer(9), shortestDistance);
		shortestDistance = trainsRouteManager.findShortestDistanceBetweenTowns("C", "D");
		Assert.assertEquals(new Integer(8), shortestDistance);

	}

	@Test
	public void routeTownsValidRouteMaxDistanceReturnCount() {
		Integer routeCount = trainsRouteManager.findRoutesCountLessThanValue("C", "C", 30);

		Assert.assertEquals(new Integer(7), routeCount);

	}

	@Test(expected = FileParameterException.class)
	public void testNotInputFile() {
		TrainsMain.main(null);
	}

}
