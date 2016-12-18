package logic.algorithm;

import java.util.ArrayList;
import java.util.Collections;

import logic.graph.Connection;
import logic.graph.Graph;
import logic.graph.Node;
import logic.algorithm.DijkstraNode;

public class Dijkstra {
	private Node start;
	private Node end;
	private ArrayList<Node> allNodes;
	private ArrayList<DijkstraNode> allNodesAsDN;

	public Dijkstra(Graph graph) {
		this.start = graph.getRoot();
		this.end = graph.getEnd();

		allNodes = (ArrayList<Node>) graph.getNodes().clone();
		allNodes.add(start);
		allNodes.add(end);
		allNodesAsDN = new ArrayList<>();
		for (Node node : allNodes) {
			DijkstraNode dnode = new DijkstraNode(node);
			if (node == start) {
				dnode.setLength(0);
			}
			allNodesAsDN.add(dnode);
		}
	}

	public Path getShortestWaysPath() {
		DijkstraNode activeNode = findNodeWithShortestLength();
		while (activeNode.getMe() != end) {
//			System.out.println("buf" + allNodesAsDN.indexOf(activeNode) + activeNode.isChecked());
			activeNode = findNodeWithShortestLength();
			activeNode.setChecked();
			for (Connection conn : activeNode.getMe().getConnections()) {
//				System.out.println(conn.getEnd().getPoint().getX() + ":" + conn.getEnd().getPoint().getY() + " -- "
//						+ conn.getStart().getPoint().getX() + ":" + conn.getStart().getPoint().getY());
				if (!getDnodeFromNode(getTargetNode(conn, activeNode.getMe())).isChecked()) {
					if (getDnodeFromNode(getTargetNode(conn, activeNode.getMe())).getLength() > activeNode.getLength()
							+ conn.getLength()) {
						getDnodeFromNode(getTargetNode(conn, activeNode.getMe()))
								.setLength(activeNode.getLength() + conn.getLength());
						getDnodeFromNode(getTargetNode(conn, activeNode.getMe())).setPrevious(activeNode.getMe());
					}
				}
			}
		}

		return getPathAsArrayList();
	}

	private DijkstraNode findNodeWithShortestLength() {
		DijkstraNode shortest = new DijkstraNode(new Node(null));

		for (DijkstraNode dnode : allNodesAsDN) {
			if (!dnode.isChecked()) {
				if (dnode.getLength() < shortest.getLength()) {
					shortest = dnode;
				}
			}
		}
		return shortest;
	}

	private DijkstraNode getDnodeFromNode(Node node) {
		if (allNodesAsDN.get(allNodes.indexOf(node)).getMe() == node) {
			return allNodesAsDN.get(allNodes.indexOf(node));
		}
		for (DijkstraNode dnode : allNodesAsDN) {
			if (dnode.getMe() == node) {
				return dnode;
			}
		}
		return null;
	}

	private Node getTargetNode(Connection conn, Node node) {
		if (conn.getEnd() == node) {
			return conn.getStart();
		}
		return conn.getEnd();
	}

	private Path getPathAsArrayList() {
		ArrayList<Node> out = new ArrayList<>();
		DijkstraNode activeNode = getDnodeFromNode(end);
		out.add(activeNode.getMe());

		while (activeNode.getMe() != start) {
			out.add(activeNode.getPrevious());
			activeNode = getDnodeFromNode(activeNode.getPrevious());
		}

		Collections.reverse(out);
		return new Path(out);
	}

}
