package AI;

import java.util.HashMap;

public class Brain {

	private HashMap<Integer, Neuron> nodes = new HashMap<Integer, Neuron>();
	private Neuron[][] input;
	private Neuron[][] output;
	private HashMap<Integer, NeuralLink> links = new HashMap<Integer, NeuralLink>();
	private Neuron bias = new Neuron(1);

	public Brain(int inX, int inY, int outX, int outY, Genome genome) {
		int tempNodeCounter = 0;

		input = new Neuron[inX][inY];
		output = new Neuron[outX][outY];

		for (int i = 0; i < input.length; i++) {
			for (int j = 0; j < input[i].length; j++) {
				Neuron tempN = new Neuron();
				nodes.put(tempNodeCounter, tempN);
				input[i][j] = tempN;
				tempNodeCounter++;
			}
		}
		
		nodes.put(tempNodeCounter, bias);
		tempNodeCounter++;

		for (int i = 0; i < output.length; i++) {
			for (int j = 0; j < output[i].length; j++) {
				Neuron tempN = new Neuron();
				nodes.put(tempNodeCounter, tempN);
				output[i][j] = tempN;
				tempNodeCounter++;
			}
		}

		for (Gene g : genome.genes.values()) {
			if (g.getEnabled()) {
				Neuron inNode = nodes.getOrDefault(g.in, null);
				Neuron outNode = nodes.getOrDefault(g.out, null);

				if (inNode == null) {
					inNode = new Neuron();
					nodes.put(g.in, inNode);
				}

				if (outNode == null) {
					outNode = new Neuron();
					nodes.put(g.out, outNode);
				}

				NeuralLink nl = new NeuralLink(inNode, outNode, g.strength);
				links.put(g.ID, nl);
				inNode.addOutputNL(nl);
			}
		}
		
		bias.update(0);

	}
	
	public int[][] getNewOutput(float[][] in){
		int[][] out = new int[output.length][output[0].length];
		
		
		for(int i=0;i<in.length;i++){
			for(int j=0;j<in[i].length;j++){
				input[i][j].overrideValueAndUpdate(in[i][j]);
			}
		}
		bias.update(0);
		
		for(int i=0;i<out.length;i++){
			for(int j=0;j<out[i].length;j++){
				if(output[i][j].getValue()>0){
					out[i][j]=1;
				}else{
					out[i][j]=0;
				}
			}
		}
		
		return out;
		
	}

	public int[][] getNewOutput(int[][] state) {
		float[][] temp = new float[state.length][state[0].length];
		
		for(int i=0;i<state.length;i++){
			for(int j=0;j<state[i].length;j++){
				temp[i][j]=(float)state[i][j];
			}
		}
		
		return getNewOutput(temp);
	}

}
