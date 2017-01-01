package AI;

public class NeuralLink {

	Neuron in,out;
	float strength;
	float oldValue;
	public NeuralLink(Neuron n1, Neuron n2, float s){
		in=n1;
		out=n2;
		strength=s;
		oldValue=0;
	}
	
	public void update(){
		float changeInSignal = (in.getValue()-oldValue)*strength;
		oldValue=in.getValue();
		
		out.update(changeInSignal);
	}
}
