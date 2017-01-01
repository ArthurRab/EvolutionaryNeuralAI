package AI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import General.Config;

public class GeneIDGen {

	public static float chanceOfLinkMutation = 0.05f, chanceOfNodeMutation = 0.03f, chanceOfASexual=0.75f, chanceOfSexual = 0.25f;
	private int currID = 0;
	private int currNodeID = 0;
	private HashMap<Gene, Integer> map = new HashMap<Gene, Integer>();
	private HashMap<Integer, Gene> inverse = new HashMap<Integer, Gene>();
	private ArrayList<Integer> nodeOrdering = new ArrayList<Integer>();
	private int largestIn, smallestOut, largestOut, smallestIn, numOuts;
	private static HashMap<Integer, GeneIDGen> instances = new HashMap<Integer, GeneIDGen>();

	private GeneIDGen() {
		setNodeInfoSHH(Config.getDimensions()[0], Config.getDimensions()[1], Config.getDimensions()[3], Config.getDimensions()[3]);
	}

	public void overrideCurrentID(int id) {
		currID = id;
	}

	public int getID(Gene g) {
		Integer temp = map.getOrDefault(g, null);

		if (temp == null) {
			map.put(g, currID);
			inverse.put(currID, g);
		} else {
			return temp;
		}

		currID++;

		return currID - 1;
	}

	public ArrayList<Integer> getAllNodesBetween(int n1, int n2) {
		ArrayList<Integer> nodes = new ArrayList<Integer>();

		int i1 = Math.max(nodeOrdering.indexOf(n1), largestIn) + 1;
		int i2 = Math.min(nodeOrdering.indexOf(n2), nodeOrdering.size() - numOuts);

		for (int i : nodeOrdering.subList(i1, i2)) {
			nodes.add(i);
		}

		return nodes;
	}

	public int addNewNodeBetween(int n1, int n2) {
		int newNode = currNodeID;
		currNodeID++;

		Random rand = new Random();

		if (n1 <= largestIn) {
			n1 = largestIn;
		}

		if (smallestOut <= n2 && n2 <= largestOut) {
			n2 = smallestOut;
		}

		int n1Index = nodeOrdering.indexOf(n1);
		int n2Index = nodeOrdering.indexOf(n2);
		int r = rand.nextInt(n2Index - (n1Index)) + n1Index + 1;

		nodeOrdering.add(r, newNode);

		return newNode;
	}

	public ArrayList<Integer> getAllNodesAfter(int n) {
		int n1Index = nodeOrdering.get(n);

		if (n == -1) {
			System.out.println("GETALLNODESAFTER CALLED WITH INVALID N");
			return null;
		}

		return (ArrayList<Integer>) nodeOrdering.subList(n1Index + 1, nodeOrdering.size() - 1);
	}

	public ArrayList<Integer> getAllNodesBefore(int n) {
		int n1Index = nodeOrdering.get(n);

		if (n == -1) {
			System.out.println("GETALLNODESBEFORE CALLED WITH INVALID N");
			return null;
		}

		return (ArrayList<Integer>) nodeOrdering.subList(0, n1Index);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Integer> getNodeOrdering() {
		return (ArrayList<Integer>) nodeOrdering.clone();
	}

	public static void setNodeInfo(int inX, int inY, int outX, int outY) {
		setNodeInfo(0, inX, inY, outX, outY);
	}

	public static void setNodeInfo(int ID, int inX, int inY, int outX, int outY) {
		getInstance(ID).setNodeInfoSHH(inX, inY, outX, outY);
	}

	private void setNodeInfoSHH(int inX, int inY, int outX, int outY) {
		// Start the node ordering with input at beginning and output at end;
		numOuts = outX * outY;
		smallestIn = currNodeID;
		for (int i = 0; i < inX; i++) {
			for (int j = 0; j < inY; j++) {
				nodeOrdering.add(currNodeID);
				currNodeID++;
			}
		}

		// Bias Node (Counts as input node also)
		largestIn = currNodeID;
		nodeOrdering.add(currNodeID);
		currNodeID++;

		smallestOut = currNodeID;
		for (int i = 0; i < outX; i++) {
			for (int j = 0; j < outY; j++) {
				nodeOrdering.add(currNodeID);
				largestOut = currNodeID;
				currNodeID++;
			}
		}
	}

	public static GeneIDGen getInstance(int i) {
		if (instances.getOrDefault(i, null) == null) {
			instances.put(i, new GeneIDGen());
		}

		return instances.get(i);
	}

	public static GeneIDGen getInstance() {
		return getInstance(0);
	}

}
