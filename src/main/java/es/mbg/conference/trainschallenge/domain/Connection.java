package es.mbg.conference.trainschallenge.domain;

public class Connection {

	private Node start;
	private Node end;
	private double weight;

	public Connection(Node start, Node end, double d) {
		this.start = start;
		this.end = end;
		this.weight = d;
	}

	public Node getStart() {
		return start;
	}

	public void setStart(Node start) {
		this.start = start;
	}

	public Node getEnd() {
		return end;
	}

	public void setEnd(Node end) {
		this.end = end;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	
}
