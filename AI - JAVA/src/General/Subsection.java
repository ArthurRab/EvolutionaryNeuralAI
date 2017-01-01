package General;

public class Subsection<T> {

	T[][] fullThing;
	public int x,y,w,h;
	
	
	public Subsection(T[][] full, int startX, int startY, int width, int height){
		fullThing=full;
		x=startX;
		y=startY;
		w=width;
		h=height;
	}
	
	public T get(int x,int y){

	int indexX=x-this.x, indexY=y-this.y;
	
	
	if(indexX<0||indexX>=this.x+w || indexY<0 || indexY>= this.y+h){
		 throw new IndexOutOfBoundsException("Recieved: ("+x+","+y+"), Should be between 0 and ("+w+","+h+")");
	}
	
	return fullThing[indexX][indexY];
	}
}
