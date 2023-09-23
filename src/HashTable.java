
public class HashTable {

	private HashNode[] table;
	private int arrayCapacity;
	private int arraySize;//current size
	
	public HashTable() {
		this.table = new HashNode[1000000000];
		arrayCapacity = 1000000000;
		arraySize = 0;
	}
	
	public int getArraySize() {
		return arraySize;
	}
	public boolean isEmpty() {
		return (arraySize == 0);
	}
	public final int hashCode(int key) {//compression function
		return (((13*key)+17)%33)%10000;
	}
	public void add(int key, String record) {
		int index = this.hashCode(key);
		HashNode head = this.table[index];
		while(head != null) {//check if key already exists
			if((head.getKey() == key)) {
				head.setRecord(record);//if it does update its record
				return;
			}
			head = head.getNext();
		}
		this.arraySize++;
		head = this.table[index];
		HashNode newest = new HashNode(key, record);
		newest.setNext(head);
		this.table[index] = newest;
	}
	public String getValue(int key) {//get record for given key
		int index = this.hashCode(key);
		HashNode head = this.table[index];
		while(head != null) {//search for key until found or reach end of list
			if(head.getKey() == key) {
				return head.getRecord();
			}
			head = head.getNext();
		}
		return null;//if not found return null
	}
	public HashNode remove(int key) {//remove specific entry
		int index = this.hashCode(key);
		HashNode head = this.table[index];
		HashNode prev = null;
		while(head != null) {//find it 
			if(head.getKey() == key)
				break;
			prev = head;
			head = head.getNext();
		}

		if(head == null)//if end of list reached return null
			return null;
		this.arraySize--;
		
		if(prev != null)
			prev.setNext(head.getNext());
		else
			this.table[index] = head.getNext();
		return head;
	}
	public int generate() {//generate unique key
		if(this.arraySize == 0) {//if table empty any key would be unique
			int key = (int )(Math.random() * 100000000 + 1);
			return key;
		}
		int key = -100;
		boolean unique = false;
		while(unique == false) {//keep generating and look for if the generated key exists if it does repeat loop
			key = (int )(Math.random() * 100000000 + 1);
			unique = true;
			int index = this.hashCode(key);
			HashNode head = this.table[index];
			while(head != null) {
				if((head.getKey() == key)) {
					unique = false;
					break;
				}
				head = head.getNext();
			}
		}
		return key;
	}
	public ElasticSequence allKeys() {//return all entries as a sorted sequence
		ElasticSequence seq;
		MinHeap heap = new MinHeap();
		for (int i = 0; i < this.table.length; i++) {//empty list into heap
			if(this.table[i] == null)
				continue;
			HashNode head = this.table[i];
			while(head != null) {
				heap.add(head.getKey(), head.getRecord());
				head = head.getNext();
			}
		}
		seq = heap.allKeys();//use heap method
		return seq;
	}
	public int nextKey(int key) {//return next key
		int index = this.hashCode(key);
		HashNode head = this.table[index];
		int next = -100;
		while(head != null) {//find key
			if(head.getKey() == key)
				break;
		}
		if((head == null)) {//if end of list reached return -100
			return next;
		}
		if(head.getNext() == null)
			return next;
		next = head.getNext().getKey();
		return next;
	}
	public int prevKey(int key) {//same as nextKey but for the previous key
		int index = this.hashCode(key);
		HashNode previous = this.table[index];
		int prev = -100;
		while(previous != null) {
			if(previous.getNext() != null) {
				if(previous.getNext().getKey() == key)
					break;
			}
			previous = previous.getNext();
		}
		if((previous == null)) {
			return prev;
		}
		prev = previous.getKey();
		return prev;
	}
	public int[] rangeKey(int key1, int key2) {//returns an integer array which contains either all -100 values indicating there is no keys between the two that are given
		int[] range = new int[this.arraySize];//or return array filled with keys in between
		for (int i = 0; i < range.length; i++) {
			range[i] = -100;
		}
		int index1 = this.hashCode(key1);
		int index2 = this.hashCode(key2);
		int smaller = 0;
		int larger =0;
		if(index1 < index2) {//find which compressed hash is smaller so we can begin the search there
			smaller = key1;
			larger = key2;
		}
		if(index2 < index1) {
			smaller = index2;
			larger = index1;
		}
		int start = Math.min(index1, index2);
		int end = Math.max(index1, index2);
		HashNode head = this.table[start];
		if(head == null)//if list empty return null
			return null;
		head = this.table[end];
		if(head == null)
			return null;
		int counter = 0;
		for (int i = start; i <= end; i++) {
			head = this.table[i];
			if(i == start) {
				while(head != null) {//find key found in the smaller index
					if(head.getKey() == smaller)
						break;
					head = head.getNext();
				}
				while(head != null) {//add the keys remaining in the chain of the smaller index if any exist
					 range[counter++] = head.getKey();
					head = head.getNext();
				}
			}else if(i == end) {//if we reached the larger index add every single key in the chain until we reach the second key
				while(head != null) {
					if(head.getKey() == larger)
						break;
					range[counter++] = head.getKey();
					head = head.getNext();
				}
			}else {//if we are neither at the start nor the end index add all the keys in every chain in every index in between
				while(head != null) {
					range[counter++] = head.getKey();
					head = head.getNext();
				}
			}
		}
		return range;
	}
}
