
public class ElasticERL {
	private ElasticSequence sequence;// three data structures to represent the three categories of data sizes
	private MinHeap heap;
	private HashTable table;
	private int capacity;
	private int size;//current size
	private int active;//which data structure is currently in use
	
	public void setInThreshold(int capacity) {//set the capacity and instantiate the appropriate data structure
		if(capacity<=1000) {
			sequence = new ElasticSequence();
			this.active = 1;
			this.size = 0;
			this.capacity = capacity;
			System.out.println(active);
		}else if(capacity <= 100000) {
			heap = new MinHeap();
			this.active = 2;
			this.size = 0;
			this.capacity = capacity;
		}else{
			table = new HashTable();
			this.active = 3;
			this.size = 0;
			this.capacity = capacity;
		}
	}
	public int generate() {//generate unique key
		if(active == 1) {
			int generated = sequence.generate();
			return generated;
		}else if(active == 2) {
			int generated = heap.generate();
			return generated;
		}else if(active == 3) {
			int generated = table.generate();
			return generated;
		}else {
			return -100;
		}
	}
	public void add(int key, String record) {
		if(active == 1) {
			this.sequence.add(key, record);
			this.size++;
			if((this.size >= 1000) && (this.size <= 100000)) {//if the size of the sequence after adding the last element is more than 100
				this.heap = new MinHeap();//then instantiate the heap and put all the entries that were in the sequence into the heap
				SequenceNode popped = this.sequence.remove();
				while(popped != null) {//check if sequence is empty
					System.out.println(popped.getKey()+", "+popped.getHospitalRecord());
					this.heap.add(popped.getKey(), popped.getHospitalRecord());
					popped = this.sequence.remove();
				}
				this.capacity = 100000;
				this.active = 2;//change currently used structure
			}
		}else if(active == 2) {
			this.heap.add(key, record);
			this.size++;
			if((this.size >= 100000)) {//if after last added element the size was more than 100000 then instantiate a hash table and place all entries
				this.table = new HashTable();//that were in heap into the sequence then into hash table
				ElasticSequence heapSeq = this.heap.allKeys();//get heap entries into sequence
				SequenceNode popped = heapSeq.remove();
				while(popped != null) {//check if sequence empty
					System.out.println(popped.getKey()+", "+popped.getHospitalRecord());
					this.heap.add(popped.getKey(), popped.getHospitalRecord());
					popped = heapSeq.remove();
				}
				this.capacity = 1000000;
				this.active = 3;//change currently used structure
			}
		}else if(active == 3) {
			this.table.add(key, record);
		}
	}
	public void remove(int key) {
		if(active == 1) {
			this.sequence.remove(key);
			this.size--;
		}else if(active == 2) {
			this.heap.poll();
			this.size--;
			if(this.size <= 1000) {//if after removing last entry the size of the heap is less than or equal to 1000 then put all entries of heap into sequence
				this.sequence = this.heap.allKeys();
				this.capacity = 1000;
				this.active = 1;
			}
		}else if(active == 3) {//if after removing last entry the size of the hash table is less than or equal to 1000000 
			this.table.remove(key);//then put all entries of hash table into sequence then into heap
			this.size--;
			if(this.size <= 100000) {
				ElasticSequence tableSeq = this.table.allKeys();
				this.heap = new MinHeap();
				SequenceNode popped = tableSeq.remove();
				while(popped != null) {
					System.out.println(popped.getKey()+", "+popped.getHospitalRecord());
					this.heap.add(popped.getKey(), popped.getHospitalRecord());
					popped = tableSeq.remove();
				}
				this.capacity = 100000;
				this.active = 2;
			}
		}
	}
	public ElasticSequence allKeys() {//return all entries as a sorted sequence
		ElasticSequence all = null;
		if(active == 1) {
			all = this.sequence.sort();
			return all;
		}else if(active == 2) {
			all = this.heap.allKeys();
			return all;
		}else if(active == 3) {
			all = this.table.allKeys();
			return all;
		}
		return all;
	}
	public int nextKey(int key) {//get next key 
		int next = -100;
		if(active == 1) {
			next = this.sequence.nextKey(key);
			return next;
		}else if(active == 2) {
			next = this.heap.nextKey(key);
			return next;
		}else if(active == 3) {
			next = this.table.nextKey(key);
			return next;
		}
		return next;
	}
	public int prevKey(int key) {//get previous key
		int prev = -100;
		if(active == 1) {
			prev = this.sequence.prevKey(key);
			return prev;
		}else if(active == 2) {
			prev = this.heap.prevKey(key);
			return prev;
		}else if(active == 3) {
			prev = this.table.prevKey(key);
			return prev;
		}
		return prev;
	}
	public String getValue(int key) {//get record of the given key
		String record = null;
		if(active == 1) {
			record = this.sequence.getValue(key);
			return record;
		}else if(active == 2) {
			record = this.heap.getValue(key);
			return record;
		}else if(active == 3) {
			record = this.table.getValue(key);
			return record;
		}
		return record;
	}
	public int[] rangeKey(int key1, int key2) {//get the keys between the 2 given keys as an array of integers
		int[] rangeArr = null;
		if(active == 1) {
			rangeArr = this.sequence.rangeKey(key1, key2);
			return rangeArr;
		}else if(active == 2) {
			rangeArr = this.heap.rangeKeys(key1, key2);
			return rangeArr;
		}else if(active == 3) {
			rangeArr = this.table.rangeKey(key1, key2);
			return rangeArr;
		}
		return rangeArr;
	}
	public void printData() {
		if(active == 1) {
			this.sequence.printSequence();
			System.out.println(this.sequence.getClass());
		}else if(active == 2) {
			this.heap.printHeap();
			System.out.println(this.heap.getClass());
		}else if(active == 3) {
			System.out.println(this.table.getClass());
		}
	}
}
