
//Don't change the class name
public class Container{
	private Link link;
	private Point data;//Don't delete or change this field;
	
	
	public Container(Point point){
		data=point;
	}
	//Don't delete or change this function
	public Point getData()
	{
		return data;
	}
	public Link getLink(){
		return link;
	}
	public void setLink(Link link){
		this.link=link;
	}
	public String toString(){
		return data.toString();
		
	}
}
