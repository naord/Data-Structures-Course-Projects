
public class Link {

	private Container data;
	private Link xNext;
	private Link xPrev;
	private Link yNext;
	private Link yPrev;
	
	public Link(Container data, Link xNext, Link xPrev, Link yNext, Link yPrev){
	    this.data = data;
	    this.xNext = xNext;
	    this.xPrev = xPrev;
	    this.yNext = yNext;
	    this.yPrev = yPrev;
	}
	public Link(Container data){
	    this(data, null, null, null, null);
	}
	
	public Container getData(){
		return data;
	}
	
	public void setData(Container data){
		this.data = data; 
	}
	
	public Link getXNext(){
		return xNext;
	}
	
	public Link getYNext(){
		return yNext;
	}
	
	public Link getYPrev(){
		return yPrev;
	}
	
	public Link getXPrev(){
		return xPrev;
	}
	
	public void setXNext(Link next){
		xNext = next;
	}
	
	public void setYNext(Link next){
		yNext = next;
	}
	
	public void setXPrev(Link prev){
		xPrev = prev;
	}
	
	public void setYPrev(Link prev){
		yPrev = prev;
	}
	
	
	
	public String toString(){ 
		return data.toString();
	}
	
   	public boolean equals(Object other){
   		return data.equals(((Link)other).getData());
   	}

}

