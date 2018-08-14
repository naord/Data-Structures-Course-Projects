// SUBMIT
public class BTree implements BTreeInterface {

	// ///////////////////BEGIN DO NOT CHANGE ///////////////////
	// ///////////////////BEGIN DO NOT CHANGE ///////////////////
	// ///////////////////BEGIN DO NOT CHANGE ///////////////////
	private BNode root;
	private final int t;

	/**
	 * Construct an empty tree.
	 */
	public BTree(int t) { //
		this.t = t;
		this.root = null;
	}

	// For testing purposes.
	public BTree(int t, BNode root) {
		this.t = t;
		this.root = root;
	}

	@Override
	public BNode getRoot() {
		return root;
	}

	@Override
	public int getT() {
		return t;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((root == null) ? 0 : root.hashCode());
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
		BTree other = (BTree) obj;
		if (root == null) {
			if (other.root != null)
				return false;
		} else if (!root.equals(other.root))
			return false;
		if (t != other.t)
			return false;
		return true;
	}
	
	// ///////////////////DO NOT CHANGE END///////////////////
	// ///////////////////DO NOT CHANGE END///////////////////
	// ///////////////////DO NOT CHANGE END///////////////////


	//Search a block by its key, return the block if found, null otherwise.
	public Block search(int key) {
		if(root!=null){
			return root.search(key);
		}
		return null;
	}

	//Insert a new block
	public void insert(Block b) {
		if(root!=null){
			if(root.isFull()){
				this.splitRoot();
			}
			root.insert(b);
			return;
		}else{	//in case the tree is empty creating node with a single block.
			this.root=new BNode(t,b);
		}
	}

	//Splits the root into 3 nodes
	public void splitRoot(){
		int i=((root.getNumOfBlocks())/2);
		BNode Nroot=new BNode(t,false,1);//create the new root (t, not leaf, one block)
		(Nroot.getBlocksList()).add(root.getBlockAt(i));// add the middle block from root to new root
		//create left and right child
		BNode Rchild=new BNode(t,root.isLeaf(),t-1);
		BNode Lchild=new BNode(t,root.isLeaf(),t-1);
		for(int j=0;j<i;j++){	// add the left half of the blocks from root
			(Lchild.getBlocksList()).add(root.getBlockAt(j));
		}	
		if(!Lchild.isLeaf()){
			for(int j=0;j<=i;j++){	// add the left half of the children from root
				(Lchild.getChildrenList()).add(root.getChildAt(j));
			}
		}
		for(int j=i+1;j<(root.getNumOfBlocks());j++){	// add the right half of the blocks from root
			(Rchild.getBlocksList()).add(root.getBlockAt(j));
		}
		if(!Rchild.isLeaf()){
			for(int j=i+1;j<=(root.getNumOfBlocks());j++){	// add the right half of the children from root
				(Rchild.getChildrenList()).add(root.getChildAt(j));
			}
		}
		Nroot.getChildrenList().add(Lchild); //Appends the specified element to the end of this list
		Nroot.getChildrenList().add(Rchild); //Appends the specified element to the end of this list
		this.root=Nroot;
		

	}
	
	@Override
	public void delete(int key) {
		root.delete(key);
		if(root.getBlocksList().size()==0){
			if(root.getChildrenList().size()==0){
				return;
			}
				
			root=root.getChildrenList().get(0);
		}
	}

	@Override
	public MerkleBNode createMBT() {
		 return root.createHashNode();
	
		
	}

	//-----------print for test only--------
	public void print() {
		root.print(0);
		System.out.println("--------");
		System.out.println();
	}

}
