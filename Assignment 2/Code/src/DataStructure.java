import java.lang.Math;
public class DataStructure implements DT {
	
	private Link xFirst;
	private Link xLast;
	private Link yFirst;
	private Link yLast;
	private int size;
	private int xMin;
	private int yMin;
	private int xMax;
	private int yMax;

	
	
	public void print(){
		Link pointer=xFirst;
		while (pointer!=null){
			System.out.println(pointer.toString());
			pointer=pointer.getXNext();
		}
	}
	//////////////// DON'T DELETE THIS CONSTRUCTOR ////////////////
	public DataStructure(){
		yFirst = null;
		xFirst = null;
		xLast = null;
		yLast = null;
		size = 0;
		xMin=Integer.MAX_VALUE;//represent infinity or NaN for empty list
		xMax=Integer.MIN_VALUE;//represent negative infinity or NaN for empty list
		yMin=Integer.MAX_VALUE;//represent infinity or NaN for empty list
		yMax=Integer.MIN_VALUE;//represent negative infinity or NaN for empty list
	}

	//add to the data structure point, making sure its organized by Y and X axis.
	//assuming given point is not exist in the data structure.
	public void addPoint(Point point) {
		Container con = new Container(point);
		if(size==0){//in case that the data structure is empty.
			Link tmp=new Link(con);
			xFirst = tmp;
			yFirst = tmp;
			xLast = tmp;
			yLast = tmp;
			xMin = point.getX();
			xMax = point.getX();
			yMin = point.getY();
			yMax = point.getY();
		}
		else {//data structure is not empty.
		   Link newLink = new Link(con);
		   if(point.getX()<xMin){//if it is the smallest value of x we make it the first X link
			   newLink.setXNext(xFirst);
			   xFirst.setXPrev(newLink);
			   xFirst = newLink;
			   xMin=point.getX();
		   }
		   else{//not the smallest value
			   Link current = xFirst;
			   boolean found=false;
			   while(current.getXNext()!=null && !found){//to find the Link we need to connect to the new Link.
				   int xVal=(((current.getXNext()).getData()).getData()).getX();
				   if(point.getX()<=xVal){
					   found=true;
				   }else{
					   current=current.getXNext();
				   }
			   }
			   newLink.setXNext(current.getXNext());
			   newLink.setXPrev(current);
			   if(found==false){//it is the biggest value of x therefore we make it the last X link
				   xMax=point.getX();
				   xLast=newLink;
			   }else{
				   (current.getXNext()).setXPrev(newLink);
			   }
			   current.setXNext(newLink);
		   }
		   if(point.getY()<yMin){//if it is the smallest value of y we make it the first Y link
			   newLink.setYNext(yFirst);
			   yFirst.setYPrev(newLink);
			   yFirst = newLink;
			   yMin=point.getY();
		   }
		   else{//not the smallest value
			   Link current = yFirst;
			   boolean found=false;
			   while(current.getYNext()!=null && !found){//to find the Link we need to connect to the new Link.
				   int yVal=(((current.getYNext()).getData()).getData()).getY();
				   if(point.getY()<=yVal){
					   found=true;
				   }else{
					   current=current.getYNext();
				   }
			   }
			   newLink.setYNext(current.getYNext());
			   newLink.setYPrev(current);
			   if(found==false){//it is the biggest value of y therefore we make it the last Y link
				   yMax=point.getY();
				   yLast=newLink;
			   }else{
				   (current.getYNext()).setYPrev(newLink);
			   }
			   current.setYNext(newLink);
		   }
		}
		size=size+1;
	}
	
//////////////////////////////////////////////////////////////////////////////////////
	@Override

	//return array of points sorted by axis
public Point[] getPointsInRangeRegAxis(int min, int max, Boolean axis) {
		// TODO Auto-g  enerated method stub
		Point[] ans1=new Point[size];//biggest array we may need
		int pointsCounter=0;
		Link pointer=xFirst;
		
		
		if(axis==true){//if x
		
		
		for(int i=0;(i<size);i++){
			
		Point current=pointer.getData().getData();//get a pointer  to look at the next point in DT
		if(current.getX()>max)break;//if its not in range we can stop 
		if((current.getX()>=min)&(current.getX()<=max)){//if in range add it 
		ans1[pointsCounter]=current;
		pointsCounter+=1;
		}
		
		pointer=pointer.getXNext();
		
		
		}//for 
		
		}//if x
		
		if(axis==false){//if y
		pointer=yFirst;
			for(int i=0;(i<size);i++){
		Point current=pointer.getData().getData();// exactly the same as if x
		if(current.getY()>max)break;
		if((current.getY()>=min)&(current.getY()<=max)){
		ans1[pointsCounter]=current;
		pointsCounter+=1;
		}
	
		
		
		pointer=pointer.getYNext();
		}//for 
		
		
		
		}//if y
		Point[] ans=new Point[pointsCounter];
		for(int i=0;i<pointsCounter;i++){
		ans[i]=ans1[i];
		}
		return ans;
				
		
	}
////////////////////////////////////////////////////////////////////////////////////////
	@Override
	//return array of points sorted by the opposite axis
	public Point[] getPointsInRangeOppAxis(int min, int max, Boolean axis) {
		// TODO Auto-generated method stub
				Point[] ans1=new Point[size];
				int pointsCounter=0;
				Link pointer=xFirst;
				
				
				if(axis==true){//if x
				pointer=yFirst;
				
				for(int i=0;(i<size);i++){
					
				Point current=pointer.getData().getData();//look at next point in DT
				
				if((current.getX()>=min)&(current.getX()<=max)){
				ans1[pointsCounter]=current;//if in range add it 
				pointsCounter+=1;
				}
				pointer=pointer.getYNext();//look at next point on y
				
				
				
				}//for 
				
				}//if x
				
				if(axis==false){
				
					for(int i=0;(i<size);i++){
				Point current=pointer.getData().getData();
				
				if((current.getY()>=min)&(current.getY()<=max)){
				ans1[pointsCounter]=current;
				pointsCounter+=1;
				}
				
				pointer=pointer.getXNext();
				
				
				}//for 
				
				
				
				}//if y
				Point[] ans=new Point[pointsCounter];
				for(int i=0;i<pointsCounter;i++){
				ans[i]=ans1[i];
				}
				return ans;
						
	}
///////////////////////////////////////////////////////////////////////////////
	@Override
	//returns the density of the points
	//assuming there is more than one point.
	public double getDensity() {
		int xLength=xMax-xMin;
		int yLength=yMax-yMin;
		if(xLength==0 | yLength==0){
			throw new RuntimeException("xLength==0 | yLength==0");
		}
		return (size)/(xLength*yLength);//calculating density according to the givven formula
	}

	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	//delete from the data structure all the points who is not in the range of min<X<max by given axis.
	public void narrowRange(int min, int max, Boolean axis) {
		// TODO Auto-generated method stub
		//initiate max link and min link
		Link maxLink=xLast;
		Link minLink=xFirst;
		if(!axis){
	         maxLink=yLast;
			 minLink=yFirst;
		} 	
		if(axis==true){// if x
			//find the new max
			int numberToCheck=maxLink.getData().getData().getX();//the next numbe
			while((numberToCheck>max)&&(maxLink!=null)){//will run until in range or until has nothing to run on 
				this.removeChain(maxLink);// will remove the chain from the y axis and will sort out any situation with handalin the last in y axis
				size-=1;
				maxLink=maxLink.getXPrev();
				xLast=maxLink;
				if(maxLink!=null){
				numberToCheck=maxLink.getData().getData().getX();
				}
			}//while end
			//maxLink.setXNext(null);//deatach the unwanted links
		
			//find the new min
			numberToCheck=minLink.getData().getData().getX();//the next number
			while((numberToCheck<min)&&(minLink!=null)){//will run until in range or until has nothing to run on
				this.removeChain(minLink);// will remove the chain from the y axis and will sort out any situation with handaling the last in y axis
				size-=1;
				minLink=minLink.getXNext();
				xFirst=minLink;
				if(minLink!=null){
					
				numberToCheck=minLink.getData().getData().getX();
				}
			}//while end
			//minLink.setXPrev(null);//deatach the unwanted links
		}//if x 
	
		
		
		if(axis==false){//if y
			//find the new max
			int numberToCheck=maxLink.getData().getData().getY();
			while((numberToCheck>max)&&(maxLink!=null)){//***********************
				this.removeChain(maxLink);// will remove the chain from the y axis and will sort out any situation with handalin the last in x axis
				size-=1;
				maxLink=maxLink.getYPrev();
				yLast=maxLink;
				if(maxLink!=null){
				numberToCheck=maxLink.getData().getData().getY();
				}
			}//while end
			//maxLink.setYNext(null);//deatach the unwanted links
		 
			//find the new min
			numberToCheck=minLink.getData().getData().getY();
			while((numberToCheck<min)&&(minLink!=null)){
				this.removeChain(minLink);// will remove the chain from the x axis and will sort out any situation with handalin the last in x axis
				size-=1;
				minLink=minLink.getYNext();
				yFirst=minLink;
				if(minLink!=null){
				numberToCheck=minLink.getData().getData().getY();
				}
			}//while end
			//minLink.setYPrev(null);//deatach the unwanted links
			
		
		
		
		}//if y		
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// will remove the the given Link from DataStructure
	public  void removeChain(Link l){
		if(l.getXPrev()==null&l.getXNext()==null){
			yFirst = null;
			xFirst = null;
			//size = 0;
			xMin=Integer.MAX_VALUE;//represent infinity or NaN for empty list
			xMax=Integer.MIN_VALUE;//represent negative infinity or NaN for empty list
			yMin=Integer.MAX_VALUE;//represent infinity or NaN for empty list
			yMax=Integer.MIN_VALUE;//represent negative infinity or NaN for empty list
			return;
				}//if it was the only link in the chain************************************************************************
		
		
		
			
	if(l.getXPrev()==null){//if first 
		xFirst=l.getXNext();
		xFirst.setXPrev(null);
	}//if it was first
	else if(l.getXNext()==null){
		xLast=l.getXPrev();
		xLast.setXNext(null);
		}//if it was last
	else{
		l.getXPrev().setXNext(l.getXNext());
		l.getXNext().setXPrev(l.getXPrev());
	}//it wasn't first and wasn't last
		
		
		
			if(l.getYPrev()==null){
				yFirst=l.getYNext();
				yFirst.setYPrev(null);
			}//if it was first
			else if(l.getYNext()==null){
				yLast=l.getYPrev();
				yLast.setYNext(null);
				}//if it was last
			else{
				l.getYPrev().setYNext(l.getYNext());
				l.getYNext().setYPrev(l.getYPrev());
			}//it wasn't first and wasn't last
		

	}//end of method
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	@Override
	//return true if xMax-xMin>yMax-yMin (X length is bigger than Y length).
	public Boolean getLargestAxis() {
		return (xMax-xMin>yMax-yMin);
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	//return container with the median point value by given axis.
	public Container getMedian(Boolean axis) {
		// TODO Auto-generated method stub
		Link pointer;
		if(axis){ pointer=xFirst;
		
		for(int i=1;i<=size/2;i++){
			pointer=pointer.getXNext();
			
		}
		}//if x 
		else {pointer =yFirst;
		for(int i=1;i<=size/2;i++){
			pointer=pointer.getYNext();
			
		}
		}//if y
		pointer.getData().setLink(pointer);
		return pointer.getData();
		
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	//return array that contain the two closest points in the given strip
	public Point[] nearestPairInStrip(Container container, double width,Boolean axis) {
		Point[] ans=nearestPairInInnerStrip( container,  width, axis);//will check only opoints that one is on the left side and other is on right side
			
		if(ans==null||ans.length==0){// if have no good answer create two arrays
			Point[] pointsX;
			Point[] pointsY;
			int left;
			int right;
			if(axis){//if x
	   left=(int)width/2-container.getData().getX();
	   right=(int)width/2+container.getData().getX();
	   pointsX=getPointsInRangeRegAxis(left, right, axis);
	   pointsY=getPointsInRangeRegAxis(left, right, !axis);
			}
			else{
		    left=(int)width/2-container.getData().getY();
		    right=(int)width/2+container.getData().getY();
		    pointsX=getPointsInRangeRegAxis(left, right, !axis);
			   pointsY=getPointsInRangeRegAxis(left, right, axis);
	   }
			ans=nearestPair(pointsX,pointsY,axis);// do the normal recursive method
	  
		
		}
		return ans;
	}// should be o(n)
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	//return array of the nearest pair in the DataStructure.
	public Point[] nearestPair() {//
		boolean axis=getLargestAxis();// we want to divide and conquer on the largest axis
		
		
		Point[]	 pointsX=getPointsInRangeRegAxis( xMin,  xMax, true);//sorted by x
		Point[]	pointsY=getPointsInRangeRegAxis( yMin,  yMax, false);//sorted by y
		
	
		return nearestPair(pointsX,pointsY,axis);// will call the recursive method
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// gets two arrays of left side and of right side and it should return the shortest pair 
	public Point[] nearestPair(Point[] pointsX,Point[] pointsY,boolean Axis){
		Point[] ans=new Point[2];
		if(pointsX.length<=1) return ans;// if not even one pair return empty array
if(pointsX.length<=4) return bruteForce(pointsX);//if less then 3 pairs do bruteForce //because there is no more then 4 points the brute force method will be O(1)
//if its not smaller then 4...

if(Axis==true){//if x is the bigger axis
	int median=pointsX.length/2;
	//split arrays
	Point[]	leftX=getPointsInRangeRegAxis(pointsX[0].getX(),pointsX[median-1].getX(),Axis);
	Point[] rightX=getPointsInRangeRegAxis(pointsX[median].getX(),pointsX[pointsX.length-1].getX(),Axis);
	Point[]	leftY=getPointsInRangeOppAxis(pointsX[0].getX(),pointsX[median-1].getX(),Axis);
	Point[] rightY=getPointsInRangeOppAxis(pointsX[median].getX(),pointsX[pointsX.length-1].getX(),Axis);
	Point[] ansL=nearestPair(leftX,leftY,Axis);//recursive find answer on left sid and answer on right side
	Point[] ansR=nearestPair(rightX,rightY,Axis);
	  double delta;
	
	  int temp=0;
	  
	  ///////  will check if have answers and how many
	  
	  if(ansL[0]!=null){
		  temp++;
	  }
	  if(ansL[1]!=null){
		  temp++;
	  }
	  if(ansR[0]!=null){
		  temp++;
	  }
	  if(ansR[1]!=null){
		  temp++;
	  }
	if(temp==0||temp==1)return ans;
	
	
	 ///////////
	  
    if(ansL==null||ansL.length==0||ansL[0]==null||ansL[1]==null){//if no answer on right
    
    	ans= ansR;
    	delta=this.distance(ansR[0], ansR[1]);
    
    }
    else if(ansR==null||ansR.length==0||ansR[0]==null||ansR[1]==null){//*if no answer on left
    
    	ans= ansL;
    	delta=this.distance(ansL[0], ansL[1]);
    	  
    	  
    }
    else{
  double deltaL=this.distance(ansL[0], ansL[1]);//get minimum between left and right
   double deltaR=this.distance(ansR[0], ansR[1]);
 
   if(deltaL<deltaR){
	   ans=ansL;
	   delta=deltaL;
   }
   else{
	   ans=ansR;
	   delta=deltaR;
   }
    }//else
   //
    //check strip between
   Container container=new Container(pointsX[pointsX.length/2]);
   int d=(int)delta+2;
   Point[] ansS=nearestPairInInnerStrip(container,d,Axis);
   if(ansS==null||ansS.length==0||ansS[0]==null||ansS[1]==null)return ans;
   double deltaS=this.distance(ansS[0], ansS[1]);
   
   if(deltaS<delta)ans=ansS;	
}//if x 



if(Axis==false){//if y is the bigger axis do the same as in x only opposite axis
	int median=pointsY.length/2;
	Point[]	leftX=getPointsInRangeOppAxis(pointsY[0].getY(),pointsY[median-1].getY(),Axis);
	Point[] rightX=getPointsInRangeOppAxis(pointsY[median].getY(),pointsY[pointsY.length-1].getY(),Axis);
	Point[]	leftY=getPointsInRangeRegAxis(pointsY[0].getY(),pointsY[median-1].getY(),Axis);
	Point[] rightY=getPointsInRangeRegAxis(pointsY[median].getY(),pointsY[pointsY.length-1].getY(),Axis);
	Point[] ansL=nearestPair(leftX,leftY,Axis);
	Point[] ansR=nearestPair(rightX,rightY,Axis);
	  double delta;
	  
	  
	  
	 
	  
  int temp=0;
	  
	  ///////  
	  
	  if(ansL[0]!=null){
		  temp++;
	  }
	  if(ansL[1]!=null){
		  temp++;
	  }
	  if(ansR[0]!=null){
		  temp++;
	  }
	  if(ansR[1]!=null){
		  temp++;
	  }
	if(temp==0||temp==1)return ans;
	
	
	 ///////////
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
    if(ansL==null||ansL.length==0||ansL[0]==null||ansL[1]==null){//*
    	ans= ansR;
    	delta=this.distance(ansR[0], ansR[1]);
    }
    else if(ansR==null||ansR.length==0||ansR[0]==null||ansR[1]==null){//*
    	ans= ansL;
    	delta=this.distance(ansL[0], ansL[1]);
    }
    else{
   double deltaL=this.distance(ansL[0], ansL[1]);
   double deltaR=this.distance(ansR[0], ansR[1]);
 
   if(deltaL<deltaR){
	   ans=ansL;
	   delta=deltaL;
   }
   else{
	   ans=ansR;
	   delta=deltaR;
   }
    }//else
   //
    
   Container container=new Container(pointsY[pointsY.length/2]);
   int d=(int)delta+2;
   Point[] ansS=nearestPairInInnerStrip(container,d,Axis);
   if(ansS==null||ansS.length==0||ansS[0]==null||ansS[1]==null)return ans;
   double deltaS=this.distance(ansS[0], ansS[1]);
   
   if(deltaS<delta)ans=ansS;	
}//if y	
		
		
		return ans;
		
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//method to find if there is two points with distance of less then width
public Point[] nearestPairInInnerStrip(Container container, double width,Boolean axis){
	Point[] ans=new Point[2];
	Point middle=container.getData();
	double delta=width;
	double minDistance=width;

	//
	double low=middle.getX()-delta;
	double high=middle.getX()+delta;
	if(!axis){
		low=middle.getY()-delta;
		high=middle.getY()+delta;
	}
	//
	Point[] strip=getPointsInRangeOppAxis((int)low,(int)high,axis);

	//it looks like O(n^2) but prooved geomethrically that it is O(n)
  for(int i=0;i<strip.length;i++){
	  for(int j=i+1;j<strip.length&(j-i)<=6;j++){
		  double distance=this.distance(strip[i],strip[j]);
		  if(distance<minDistance){
			  minDistance=distance;
			  ans[0]=strip[i];
			  ans[1]=strip[j];
		  }//if
		  
	  }//inner for will not run more then 6 times
  }//outer for
	
	
	
	return ans;
	
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//return the closest points by comparing distance between each point to all of the other points. 
public Point[] bruteForce(Point[] points){
	if(points.length<=1)return null;
	if(points.length==2)return points;
	Point[] ans=new Point[2];
	
	ans[0]=points[0];
	ans[1]=points[1];
	
	double minDistance=this.distance(points[0], points[1]);
	for(int i=0;i<points.length;i++){
		for(int j=i+1;j<points.length;j++){
			double distance=this.distance(points[i], points[j]);
			if(distance<minDistance){
				minDistance=distance;
				ans[0]=points[i];
				ans[1]=points[j]; 
			}
		}
			
	}	
	
	return ans;
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//calculate the distance between two points by given formula.
public  double distance(Point a,Point b){
	return Math.sqrt((a.getX()-b.getX())*(a.getX()-b.getX())+(a.getY()-b.getY())*(a.getY()-b.getY()));	
}
}

