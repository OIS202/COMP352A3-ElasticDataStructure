public class ElasticSequence{
	private SequenceNode header;
	private SequenceNode trailer;
	private int size;
	
	public ElasticSequence() {
		header = new SequenceNode(-2,null, null, null);
		trailer = new SequenceNode(-1,null, header, null);
		header.setNext(trailer);
	}
	public int size() {
		return size;
	}
	public boolean isEmpty() {
		return size == 0;
	}
	public SequenceNode remove() {//remove first element
		if(size == 0)
			return null;
		SequenceNode temp = header.getNext();
		header.setNext(temp.getNext());
		temp.getNext().setPrev(header);
		temp.setNext(null);
		temp.setPrev(null);
		this.size--;
		return temp;
	}
	public boolean add(int key,String record) {
		if(size == 1000000000) {
			return false;
		}
		if((key >= 0)&&(key <= 99999999)) {
			if(header.getNext().getNext() != null) {
				SequenceNode current = header.getNext();
				while(current.getNext() != null) {//find if key exists and if it does return false
					int currentKey = current.getKey();
					if(currentKey == key) {
						System.out.println("key already exists");
						return false;
					}
					current = current.getNext();
				}
			}
		}
		SequenceNode newNode = new SequenceNode(key, record, header, header.getNext());//if not add it
		header.getNext().setPrev(newNode);
		header.setNext(newNode);
		size++;
		return true;
	}
	public boolean remove(int key) {
		boolean found = false;
		SequenceNode current = header.getNext();
		if((key >= 0)&&(key <= 99999999)) {//find if key exists 
			if(header.getNext().getNext() != null) {
				while(current.getNext() != null) {
					int currentKey = current.getKey();
					if(currentKey == key) {
						found = true;
						break;
					}
					current = current.getNext();
				}
			}
		}
		if(found == false)//if end of list encountered return false
			return false;
		SequenceNode pred = current.getPrev();//if key found remove it
		SequenceNode succ = current.getNext();
		pred.setNext(succ);
		succ.setPrev(pred);
		current.setNext(null);
		current.setPrev(null);
		size--;
		return true;
	}
	public String getValue(int key) {
		boolean found = false;
		SequenceNode current = header.getNext();
		if((key >= 0)&&(key <= 99999999)) {//find if key exists
			if(header.getNext().getNext() != null) {
				while(current.getNext() != null) {
					int currentKey = current.getKey();
					if(currentKey == key) {
						found = true;
						break;
					}
					current = current.getNext();
				}
			}
		}
		if(found == false)//if end of list encountered return "not found"
			return "not found";
		return current.getHospitalRecord();//if not return associated record
	}
	public int nextKey(int key) {
		boolean found = false;
		SequenceNode current = header.getNext();
		if((key >= 0)&&(key <= 99999999)) {//find if key exists
			if(header.getNext().getNext() != null) {
				while(current.getNext() != null) {
					int currentKey = current.getKey();
					if(currentKey == key) {
						found = true;
						break;
					}
					current = current.getNext();
				}
			}
		}
		if(found == false)//if end of list encountered return -100
			return -100;
		return current.getNext().getKey();//else return its successor
	}
	public int prevKey(int key) {
		boolean found = false;
		SequenceNode current = header.getNext();
		if((key >= 0)&&(key <= 99999999)) {//find if key exists with a previous 
			if(header.getNext().getNext() != null) {
				while(current.getNext() != null) {
					int currentKey = current.getKey();
					if(currentKey == key) {
						found = true;
						break;
					}
					current = current.getNext();
				}
			}
		}
		if(found == false)//if end of list encountered return -100
			return -100;
		return current.getPrev().getKey();//if not return its predecessor 
	}
	public int[] rangeKey(int key1, int key2) {
		boolean foundOne = false;
		boolean foundTwo = false;
		SequenceNode current = header.getNext();
		int[] returnArray;
		String rangeString = "";
		if(((key1 >= 0)&&(key1 <= 99999999))&&((key2 >= 0)&&(key2 <= 99999999))){
			if(header.getNext().getNext() != null) {
				while((current != null)&&(foundOne == false)&&(foundTwo == false)) {
					int currentKey = current.getKey();
					if(currentKey == key1) {//if we find key1 first 
						System.out.println("found"+key1+", "+currentKey);
						while(current.getNext() != null) {//keep traversing sequence and adding keys into integer array until key2 encountered
							if(current.getKey() == key2) {
								foundOne = true;
								break;
							}
							rangeString = rangeString+current.getKey();
							current = current.getNext();
						}
					}else if(currentKey == key2) {//else if key2 found first
						foundTwo = true;
						while(current.getNext() != null) {//keep traversing sequence and adding keys into integer array until key1 encountered
							if(current.getKey() == key1) {
								foundOne = true;
								break;
							}
							rangeString = rangeString+current.getKey();
							current = current.getNext();
						}
					}
					current = current.getNext();
				}
			}
		}
		if(((foundOne == false)&&(foundTwo == false))||((foundOne == false)&&(foundTwo == true))||((foundOne == true)&&(foundTwo == false))) {
			returnArray = new int[1];
			returnArray[0] = -100;
			//System.out.println("inside");
			return returnArray;
		}
		returnArray = new int[rangeString.length()];
		for (int i = 0; i < returnArray.length; i++) {
			returnArray[i] = Character.getNumericValue(rangeString.charAt(i));
		}
		return returnArray;
	}
	public ElasticSequence sort() {
		ElasticSequence sortedSequence = new ElasticSequence();
		SequenceNode current = null;
		SequenceNode nextCurrent = null;
		if(this.header.getNext().getNext() == null) {
			return sortedSequence;
		}
		current = this.header.getNext();
		while(current.getNext() != null) {
			sortedSequence.add(current.getKey(), current.getHospitalRecord());
			current = current.getNext();
		}
		int tempKey;
		String tempString;
		current = null;
		for(current = sortedSequence.header.getNext();current.getNext() != null;current = current.getNext()) {//classic selection sort
			for(nextCurrent = current.getNext();nextCurrent.getNext() != null;nextCurrent = nextCurrent.getNext()) {
				if(current.getKey() > nextCurrent.getKey()) {
					tempKey = current.getKey();
					tempString = current.getHospitalRecord();
					current.setKey(nextCurrent.getKey());
					current.setHospitalRecord(nextCurrent.getHospitalRecord());
					nextCurrent.setKey(tempKey);
					nextCurrent.setHospitalRecord(tempString);
				}
			}
		}
		return sortedSequence;
	}
	public int generate() {
		if(size >= 1000) {
			return -1;
		}if(header.getNext().getNext() == null) {//if sequence empty any key is unique 
			int key = (int )(Math.random() * 100000000 + 1);
			return key;
		}
		int key = -100;
		boolean unique = true;
		do {//keep generating keys and finding if they already exist in sequence until a unique one is generated 
			if(header.getNext().getNext() != null) {
				SequenceNode current = header.getNext();
				key = (int )(Math.random() * 100000000 + 1);
				unique = true;
				while(current.getNext() != null) {
					int currentKey = current.getKey();
					if(currentKey == key) {
						unique = false;
					}
					current = current.getNext();
				}
			}
		}while(unique == false);
		
		return key;
		
	}
	public void printSequence() {
		SequenceNode current = header.getNext();
		while(current.getNext() != null) {
			System.out.println(current.getKey()+", "+current.getHospitalRecord());
			current = current.getNext();
		}
	}
}
