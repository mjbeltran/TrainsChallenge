package es.mbg.conference.trainschallenge;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import es.mbg.conference.exception.FileParameterException;

/**
 * 
 * @author mjbeltran
 *
 */
public class TrainsMain {

	public static void main(String[] args) {
		if (args == null || args.length < 1) {
			throw new FileParameterException("Input file is necessary to execute this programme");
		}

		String inputFile = args[0].toString();

		StringBuilder stbGraph = new StringBuilder();
		try (Stream<String> lines = Files.lines(Paths.get(inputFile))) {
			List<List<String>> values = lines.map(line -> Arrays.asList(line.split(","))).collect(Collectors.toList());
			values.forEach(value -> stbGraph.append(value.toString().replace("Graph:", "")));
			TrainsRouteManager trainsRouteManager = new TrainsRouteManager(stbGraph.toString());

			print(trainsRouteManager);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void print(TrainsRouteManager trainsRouteManager) {
		System.out.println("Output #1 : " + parseRouteDistance(trainsRouteManager.getRouteDistanceBetweenNodes("A-B-C")));
		System.out.println("Output #2 : " + parseRouteDistance(trainsRouteManager.getRouteDistanceBetweenNodes("A-D")));
		System.out.println("Output #3 : " + parseRouteDistance(trainsRouteManager.getRouteDistanceBetweenNodes("A-D-C")));
		System.out.println("Output #4 : " + parseRouteDistance(trainsRouteManager.getRouteDistanceBetweenNodes("A-E-B-C-D")));
		System.out.println("Output #5 : " + parseRouteDistance(trainsRouteManager.getRouteDistanceBetweenNodes("A-E-D")));
		System.out.println("Output #6 : " + trainsRouteManager.findRouteStops("C", "C", 3));
		System.out.println("Output #7 : " + trainsRouteManager.findRouteCountForStops("A", "C", 4));
		System.out.println("Output #8 : " + trainsRouteManager.findShortestDistanceBetweenTowns("A", "C"));
		System.out.println("Output #9 : " + parseRouteDistance(trainsRouteManager.findShortestDistanceBetweenTowns("B", "B")));
		System.out.println("Output #9 : " + trainsRouteManager.findRoutesCountLessThanValue("C", "C", 30));

	}

	private static String parseRouteDistance(Integer distance) {
		if (distance == -1) {
			return "NO SUCH ROUTE";
		}
		return Integer.toString(distance);
	}
}
