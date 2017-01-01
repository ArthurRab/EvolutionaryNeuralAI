package AI;

import java.util.ArrayList;

public class Neuron {
	
	private float value=0;
	private ArrayList<NeuralLink> nlOut = new ArrayList<NeuralLink>();
	
	public Neuron(float startValue){
		overrideValueAndUpdate(startValue);
	}
	
	public Neuron(){
		this(0);
	}
	
	public void update(float dv){
		value+=dv;
		for(NeuralLink i:nlOut){
			i.update();
		}
	}
	
	public float getValue(){
		if(value>0){
			return 1f;
		}else if(value<0){
			return -1f;
		}
		return 0f;
	}
	
	public void addOutputNL(NeuralLink nl){
		nlOut.add(nl);
	}
	
	public void overrideValueAndUpdate(float newValue){
		value = newValue;
		update(0);
	}
}
