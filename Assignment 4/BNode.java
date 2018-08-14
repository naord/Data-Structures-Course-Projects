import java.util.ArrayList;

//SUBMIT
public class BNode implements BNodeInterface {
	
	// ///////////////////BEGIN DO NOT CHANGE ///////////////////
	// ///////////////////BEGIN DO NOT CHANGE ///////////////////
	// ///////////////////BEGIN DO NOT CHANGE ///////////////////
	private final int t;
	private int numOfBlocks;
	private boolean isLeaf;
	private ArrayList<Block> blocksList;
	private ArrayList<BNode> childrenList;

	/**
	 * Constructor for creating a node with a single child.<br>
	 * Useful for creating a new root.
	 */
	public BNode(int t, BNode firstChild) {
		this(t, false, 0);
		this.childrenList.add(firstChild);
	}

	/**
	 * Constructor for creating a <b>leaf</b> node with a single block.
	 */
	public BNode(int t, Block firstBlock) {
		this(t, true, 1);
		this.blocksList.add(firstBlock);
	}

	public BNode(int t, boolean isLeaf, int numOfBlocks) {
		this.t = t;
		this.isLeaf = isLeaf;
		this.numOfBlocks = numOfBlocks;
		this.blocksList = new ArrayList<Block>();
		this.childrenList = new ArrayList<BNode>();
	}

	// For testing purposes.
	public BNode(int t, int numOfBlocks, boolean isLeaf,
			ArrayList<Block> blocksList, ArrayList<BNode> childrenList) {
		this.t = t;
		this.numOfBlocks = numOfBlocks;
		this.isLeaf = isLeaf;
		this.blocksList = blocksList;
		this.childrenList = childrenList;
	}

	@Override
	public int getT() {
		return t;
	}

	@Override
	public int getNumOfBlocks() {
		return numOfBlocks;
	}

	@Override
	public boolean isLeaf() {
		return isLeaf;
	}

	@Override
	public ArrayList<Block> getBlocksList() {
		return blocksList;
	}

	@Override
	public ArrayList<BNode> getChildrenList() {
		return childrenList;
	}

	@Override
	public boolean isFull() {
		return numOfBlocks == 2 * t - 1;
	}

	@Override
	public boolean isMinSize() {
		return numOfBlocks == t - 1;
	}
	
	@Override
	public boolean isEmpty() {
		return numOfBlocks == 0;
	}
	
	@Override
	public int getBlockKeyAt(int indx) {
		return blocksList.get(indx).getKey();
	}
	
	@Override
	public Block getBlockAt(int indx) {
		return blocksList.get(indx);
	}

	@Override
	public BNode getChildAt(int indx) {
		return childrenList.get(indx);
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((blocksList == null) ? 0 : blocksList.hashCode());
		result = prime * result
				+ ((childrenList == null) ? 0 : childrenList.hashCode());
		result = prime * result + (isLeaf ? 1231 : 1237);
		result = prime * result + numOfBlocks;
		result = prime * result + t;
		return result;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BNode other = (BNode) obj;
		if (blocksList == null) {
			if (other.blocksList != null)
				return false;
		} else if (!blocksList.equals(other.blocksList))
			return false;
		if (childrenList == null) {
			if (other.childrenList != null)
				return false;
		} else if (!childrenList.equals(other.childrenList))
			return false;
		if (isLeaf != other.isLeaf)
			return false;
		if (numOfBlocks != other.numOfBlocks)
			return false;
		if (t != other.t)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "BNode [t=" + t + ", numOfBlocks=" + numOfBlocks + ", isLeaf="
				+ isLeaf + ", blocksList=" + blocksList + ", childrenList="
				+ childrenList + "]";
	}

	// ///////////////////DO NOT CHANGE END///////////////////
	// ///////////////////DO NOT CHANGE END///////////////////
	// ///////////////////DO NOT CHANGE END///////////////////
	
		

	//Search a block by its key and return the block if found, null otherwise.
	public Block search(int key) {
		int i=0;
		while(i<numOfBlocks && key>=(blocksList.get(i)).getKey()){	//TODO need to check block list!=null?
			if(key==(blocksList.get(i)).getKey()){	//if we found the block
				return blocksList.get(i);
			}
			i=i+1;
		}
		if (isLeaf==true){	//if its a leaf we did not found the block
			return null;
		}
		else{	//in case we stop because the key is bigger, or there are no more blocks in the node.
			return (childrenList.get(i)).search(key);
		}
	}

	//Insert a new block
	//assuming this node is not full
	public void insert(Block b){
		if(isLeaf()){
			insertNonFull(b);
			return;
		}else{
			int i=getNumOfBlocks()-1;	//index to insert
			while(i>=0 && getBlockAt(i).getKey()>b.getKey()){//check which child we need to go, start from the end of the list.
				i--;
			}
			i++;
			if((getChildAt(i)).isFull()){	//if the node is full we need to split
				splitChild(i);
				i=getNumOfBlocks()-1;	//index to insert
				while(i>=0 && getBlockAt(i).getKey()>b.getKey()){//check which child we need to go, start from the end of the list.
					i--;
				}
				i++;
			}
			(getChildAt(i)).insert(b);
			return; 
		}
	}
	
	//Insert a new block to leaf
	//assuming this node is not full
	public void insertNonFull(Block b) {
		/*if(!isLeaf()){	//we insert only to a leaf
			insert(b);
		}*/
		int i=getNumOfBlocks()-1;	//index to insert
		while(i>=0 && getBlockAt(i).getKey()>b.getKey()){//check in which index we need to insert, start from the end of the list.
			i--;
		}
		i++;
		blocksList.add(i,b);
		numOfBlocks++;
	}
	
	//Splits the child node at childIndex into 2 nodes
	public void splitChild(int childIndex){

		BNode toSplit=getChildAt(childIndex);	//the node we want to split
		BNode Rchild=new BNode(t,toSplit.isLeaf(),t-1);	//the right half of the node we want to split
		BNode Lchild=new BNode(t,toSplit.isLeaf(),t-1);	//the left half of the node we want to split
		int i=toSplit.getNumOfBlocks()/2;	//index to insert
		(Rchild.getBlocksList()).addAll((toSplit.getBlocksList()).subList(i+1,(i*2)));	// add the right half of the blocks from the node we want to split
		(Rchild.getBlocksList()).add((toSplit.getBlocksList()).get((i*2))); //because subList(inclusive,exclusive)
		if(!Rchild.isLeaf()){
			(Rchild.getChildrenList()).addAll((toSplit.getChildrenList()).subList(i+1,i*2+1));// add the right half of the children from the node we want to split
			(Rchild.getChildrenList()).add((toSplit.getChildrenList()).get(2*i+1));//because subList(inclusive,exclusive)
		}
		(Lchild.getBlocksList()).addAll((toSplit.getBlocksList()).subList(0,i));	// add the left half of the blocks from the node we want to split
		if(!Lchild.isLeaf()){
			(Lchild.getChildrenList()).addAll((toSplit.getChildrenList()).subList(0,i+1));// add the left half of the children from the node we want to split
		}
		Block toInsert=(toSplit.getBlocksList()).get(i);	//the block we want to insert up
		int j=getNumOfBlocks()-1;
		while(j>=0 && getBlockKeyAt(j)>toInsert.getKey()){//check in which index we need to insert the middle block
			j--;
		}
		j++;
		getBlocksList().add(j,toInsert);	//insert the middle block from child
		numOfBlocks++;
		getChildrenList().remove(childIndex);	//remove the child we split
		getChildrenList().add(j,Lchild);	//insert left child
		getChildrenList().add(j+1,Rchild);	//insert right child
	}
	
	public void mergeChildern(BNode left,BNode right ,Block median,BNode father){
		//merge node with its children
		left.addBlockToEnd(median);// add blocks
		left.addAllBlocks(right.getBlocksList());//add blocks
		left.getChildrenList().addAll(right.getChildrenList());//add children
		father.getChildrenList().remove(right);// delete the right child from father
		father.removeBlock(median);//delete the taken block from father
	}
	
	//True if the child node at childIndx-1 exists and has more than t-1 blocks
	private boolean childHasNonMinimalLeftSibling(int childIndx){
	    boolean ans=false;
	    if(childrenList.size()>=childIndx&childIndx!=0){// check if the child exist
	    	ans=(childrenList.get(childIndx-1).getNumOfBlocks()>t-1);//check if the child has enoguh children
	    }
		return ans;
	}
	
	//True if the child node at childIndx+1 exists and has more than t-1 blocks.
	private boolean childHasNonMinimalRightSibling(int childIndx){
		boolean ans=false;
		    if(childrenList.size()>=childIndx+2){// check if the child exist
		    	ans=(childrenList.get(childIndx+1).getNumOfBlocks()>t-1);//check if the child has enoguh children
		    }
		return ans;
	}

	private boolean childNonMinimal(int childIndx){
		boolean ans=false;
		if(childrenList.size()>=childIndx+1){
			ans=(childrenList.get(childIndx).getBlocksList().size()>t-1);
		}
		return ans;
	}
	
	public void removeBlock(Block b){
		blocksList.remove(b);
		numOfBlocks--;
	}
	
	public void removeBlock(int i){
		blocksList.remove(i);
		numOfBlocks--;
	}
	
public void addBlock(int i,Block b){
		blocksList.add(i, b);
	numOfBlocks++;

	}

public void addBlockToEnd(Block b){
	blocksList.add( b);
numOfBlocks++;

}

public void addAllBlocks(ArrayList<Block> b){
	blocksList.addAll(b);
numOfBlocks+=b.size();

}

public Block max(){
if(isLeaf){
	return this.getBlockAt(numOfBlocks-1);
}
else return this.getChildAt(childrenList.size()-1).max();	
}

public Block min(){
if(isLeaf){
	return this.getBlockAt(0);
}
else return this.getChildAt(0).min();	
}


	
	
	
	@Override
	public void delete(int key) {
		int j=0;
		for( j=0;j<blocksList.size()-1&&blocksList.get(j).getKey()<key;j++){//search for the key in the current node 
		}//for search for k in current node 
		
			if(blocksList.get(j).getKey()==key){//if k was found in current node	
				if(isLeaf){//if k was found in the current node and the current node is a leaf
					blocksList.remove(j);//remove the current object that we need to 
					numOfBlocks--;
			return;//finish the proccess
				}//if k was found and current node is a leaf
			
				else{//if current node is not leaf and k is in current node 
					if(childNonMinimal(j)){//left child isnt minimal
						ArrayList<Block> tempList=	childrenList.get(j).getBlocksList();
						Block maximumInLeft=getChildAt(j).max();//***************find maximum in left subtree
						getChildAt(j).delete(maximumInLeft.getKey());//delete maximum from left sub tree
						blocksList.set(j, maximumInLeft);//put maximum in left sub tree in father
						
						
						return;
						
					}
					
					else if(childNonMinimal(j+1)){//right child isnt minimal
						ArrayList<Block> tempList=	childrenList.get(j+1).getBlocksList();
						Block minimumInRight=getChildAt(j+1).min();//***************find minimum in right subtree
						getChildAt(j+1).delete(minimumInRight.getKey());//delete minimum from right sub tree
						blocksList.set(j, minimumInRight);//put minimum in right sub tree  in father
						
						return;
						
					}
					
					else{//both child are minimal
						if(childrenList.size()>=j+1)//if both children exist
						mergeChildern( childrenList.get(j),childrenList.get(j+1) ,blocksList.get(j),this);//merge both children with key
						childrenList.get(j).delete(key);//continue to the new node
						return;
					}
					
				}//else if current node is not a leaf
				
			}// if k found in current node	

		
			
			
			
			
			
			
			
			else{///////////////////////////from here is what happens if k was not found in the current node 
			
			
		if(isLeaf)return;//if k is not in current node and current node is a leaf finish process ***************can be a nug if j+1 doesnt exist
		
		//find child index
			if(blocksList.get(j).getKey()<key){
				j++;
			}
			//now index of child is for sure j
			
			if(childrenList.size()<=j){//if child doesnt exist finish the process
				return;
			}
			
			
				if(childNonMinimal(j)){//if children is not minimal move on
					childrenList.get(j).delete(key);
					return;
				}
				
				else if(childHasNonMinimalLeftSibling(j)){//if left sibling is not minimal
					//borrow from left
				BNode left=childrenList.get(j-1);
				BNode right=childrenList.get(j);
				right.addBlock(0, blocksList.get(j-1));//take from father add to right
				this.removeBlock(j-1);//remove from father
				this.addBlock(j-1, left.getBlocksList().get(left.getBlocksList().size()-1));//father borrow from left sibling
				left.removeBlock(left.getBlocksList().size()-1);//remove from left sibling
				
				if(!left.isLeaf){
		    	BNode BiggestLeftChild=	left.getChildAt(left.getChildrenList().size()-1);// biggest left child saved here
				right.getChildrenList().add(0, BiggestLeftChild);//put biggest left child to be smallest right child
				left.getChildrenList().remove(left.getChildrenList().size()-1);
				}
				
					right.delete(key);
					return;
				}//borrow from left
				
				else if(childHasNonMinimalRightSibling(j)){//if right sibling is not minimal
					//borrow from right
					BNode left=childrenList.get(j);
					BNode right=childrenList.get(j+1);
					
					left.addBlockToEnd(blocksList.get(j));//take from father
					this.removeBlock(j);//remove from father
					this.addBlock(j, right.getBlocksList().get(0));//father borrow from right sibling
					right.removeBlock(0);//remove from right sibling
					
					if(!right.isLeaf){
					BNode smallestRightChild=right.getChildAt(0);// smallest right child saved here
					left.getChildrenList().add( smallestRightChild);//put smallest right child to be biggest left child
					right.getChildrenList().remove(0);
					}
					
					left.delete(key);
					return;	
				}//borrow from right
				
				else{
					//merge
					if(childrenList.size()-1>=j+1){//if right sibling exist
					//merge with right	
						mergeChildern(childrenList.get(j),childrenList.get(j+1) ,blocksList.get(j),this);
						childrenList.get(j).delete(key);
						return;
					}//merge with right
					
					else if(childrenList.size()-1>=j-1){//if left sibling exist
				//merge with left
						mergeChildern(childrenList.get(j-1),childrenList.get(j) ,blocksList.get(j-1),this);
						childrenList.get(j-1).delete(key);
						return;
						
					}//merge with left
				}//else merge
			
			}//else k not found in current node
		
	}//delete
		

	@Override
	public MerkleBNode createHashNode() {
		ArrayList<byte[]> dataList=new ArrayList<byte[]>();
		for(int i=0;i<numOfBlocks;i++){
			dataList.add(blocksList.get(i).getData());//create list of data
		}//for
if(isLeaf){//if leaf
	byte[] hush=HashUtils.sha1Hash(dataList);//create hush value 
	MerkleBNode ans=new MerkleBNode(hush);//create new mbt node and return it
	return ans;

}//if leaf
else{//not leaf
	ArrayList<MerkleBNode> mbtList=new ArrayList<MerkleBNode>();
	for(int i=0;i<childrenList.size();i++){
		mbtList.add(childrenList.get(i).createHashNode());
		}//for recursivly create all the children mbt nodes
	for(int i=0 , j=0;i<mbtList.size();i++,j+=2){
		dataList.add(j, mbtList.get(i).getHashValue());// add all the children values to data list
		
	}
	byte[] hush=HashUtils.sha1Hash(dataList);//create hush value 
	MerkleBNode ans=new MerkleBNode(hush,mbtList);//create new mbt node and return it
	return ans;
	
}//else not leaf	
}



		
	
	
	
	
	
	//-----------print for test only----------------
	public void print(int spaces) {
		String spacing = "";
		for(int i=0; i<spaces; i++) {
			spacing = spacing.concat("-");
		}
		spacing = spacing.concat(" ");
		
		System.out.println(spacing + "Has " + numOfBlocks + " Blocks, " + childrenList.size() + " Children.");
		System.out.print(spacing + "Blocks: {");
		if(numOfBlocks == 0) System.out.println("}");
		for(int i=0; i<numOfBlocks; i++) {
			System.out.print(blocksList.get(i).getKey());
			if(i<=numOfBlocks-2) System.out.print(", ");
			else System.out.println("}");
		}
		System.out.println(spacing + "Children:");
		for(int i=0; i<childrenList.size(); i++) {
			System.out.print(spacing);
			childrenList.get(i).print(spaces + 1);
		}
	}
	

}
