package General;

public class Vector2 {

	public int x,y;
	public Vector2(){
		this(0,0);
	}
	public Vector2(int x,int y){
		this.x=x;
		this.y=y;
	}
	
	public Vector2 plus(Vector2 other){
		return new Vector2(x+other.x,y+other.y);
	}
	public void add(Vector2 other){
		x+=other.x;
		y+=other.y;
	}
	
	public Vector2 cpy(){
		return new Vector2(x,y);
	}
	
	public void set(Vector2 other){
		x=other.x;
		y=other.y;
	}
	
	public void set(int x,int y){
		this.x=x;
		this.y=y;
	}
	
	public boolean equals(Vector2 other){
		return x==other.x && y==other.y;
	}
	public boolean equals(int x,int y){
		return x==this.x && y==this.y;
	}
	
	public Vector2 times(int t){
		return new Vector2(t*x,t*y);
	}
}
