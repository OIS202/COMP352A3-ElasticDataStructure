import java.util.Arrays;
import java.util.NoSuchElementException;

public class MinHeap {

  private int capacity = 1000000000;
  private int size = 0;//current size
  private HeapNode[] heap;

  public MinHeap() {
    this.heap = new HeapNode[capacity];
  }

  public int getSize() {
	return size;
  }

  private int getLeftChildIndex(int parentIndex) {
    return 2 * parentIndex + 1;
  }

  private int getRightChildIndex(int parentIndex) {
    return 2 * parentIndex + 2;
  }

  private int getParentIndex(int childIndex) {
    return (childIndex - 1) / 2;
  }

  private boolean hasLeftChild(int index) {
    return getLeftChildIndex(index) < size;
  }

  private boolean hasRightChild(int index) {
    return getRightChildIndex(index) < size;
  }

  private boolean hasParent(int index) {
    return getParentIndex(index) >= 0;
  }

  private int leftChild(int parentIndex) {
    return heap[getLeftChildIndex(parentIndex)].getKey();
  }

  private int rightChild(int parentIndex) {
    return heap[getRightChildIndex(parentIndex)].getKey();
  }

  private int parent(int childIndex) {
    return heap[getParentIndex(childIndex)].getKey();
  }

  private void swap(int index1, int index2) {
    HeapNode element = heap[index1];
    heap[index1] = heap[index2];
    heap[index2] = element;
  }

  
  public HeapNode poll() {//this is the removeMin method 
    if (size == 0) {
      throw new NoSuchElementException();
    }

    HeapNode element = heap[0];//remove first element

    heap[0] = heap[size - 1];
    size--;
    heapifyDown();//down-heap to preserve heap order property
    return element;
  }

  
  public void add(int key,String record) {
	for (int i = 0; i < size; i++) {
		if(heap[i].getKey() == key) {//check if key exists
			System.out.println("key already exists");
			return;
		}
	}
	HeapNode node = new HeapNode(key,record);//if not add it
    heap[size] = node;
    size++;
    heapifyUp();//up heap to preserve heap order property
  }

  private void heapifyUp() {
    int index = size - 1;

    while (hasParent(index) && parent(index) > heap[index].getKey()) {//check if the parent is larger than the child
      swap(getParentIndex(index), index);//if so swap them
      index = getParentIndex(index);
    }
  }

  private void heapifyDown() {
    int index = 0;

    while (hasLeftChild(index)) {
      int smallestChildIndex = getLeftChildIndex(index);

      if (hasRightChild(index) && rightChild(index) < leftChild(index)) {//check which child is smaller
        smallestChildIndex = getRightChildIndex(index);
      }

      if (heap[index].getKey() < heap[smallestChildIndex].getKey()) {//if the current key is smaller than its smallest child then heap order is preserved and break
        break;
      } else {
        swap(index, smallestChildIndex);//if not send parent down and bring up child
      }
      index = smallestChildIndex;
    }
  }

  public void printHeap() {
    for (int i = 0; i < size; i++) {
      System.out.print(heap[i].getKey() + " ");
    }
  }
  public int generate() {
	  if(this.size == this.capacity)
		  return -100;
	  if(this.heap[0] == null) {//any key is unique when heap empty
		  int key = (int )(Math.random() * 100000000 + 1);
		  return key;
	  }
	  int key = -1;
	  boolean unique = false;
	  while(unique == false) {//keep generating a key and finding if its existing until unique key found
		  key = (int )(Math.random() * 100000000+ + 1);
		  unique = true;
		  for (int i = 0; i < size; i++) {
			  if(heap[i].getKey() == key) {
				  unique = false;
			  }
		  }
	  }
	  return key;
  }
  public ElasticSequence allKeys() {
	  HeapNode[] copy = Arrays.copyOf(this.heap, this.size);//make copy of heap as we will empty original to make sure that we are getting the minimum key each time
	  ElasticSequence sortedSeq = new ElasticSequence();
	  int count = this.getSize();
	  for (int i = 0; i < count; i++) {//empty original heap into sequence
		  HeapNode removed = this.poll();
		  sortedSeq.add(removed.getKey(), removed.getHospitalRecord());
	  }
	  for (int i = 0; i < copy.length; i++) {//refill original heap
		this.add(copy[i].getKey(), copy[i].getHospitalRecord());;
	  }
	  return sortedSeq;
  }
  public String getValue(int key) {
	  String value = null;
	  int count = this.getSize();
	  for (int i = 0; i < count; i++) {//find if key exists 
		  if(this.heap[i].getKey() == key) {
			  value = this.heap[i].getHospitalRecord();//if it does return its value
			  return value;
		  }
	  }
	  return value;//if not return null
  }
  public int nextKey(int key) {
	  int next = -100;
	  int count = this.getSize();
	  for (int i = 0; i < count-1; i++) {//find if key exists then return its successor
		  if(this.heap[i].getKey() == key) {
			  next = this.heap[i+1].getKey();
			  return next;
		  }
	  }
	  return next;//else return -100
  }
  public int prevKey(int key) {
	  int prev = -100;
	  int count = this.getSize();
	  for (int i = 1; i < count; i++) {//find if key exists then return its predecessor
		  if(this.heap[i].getKey() == key) {
			  prev = this.heap[i-1].getKey();
			  return prev;
		  }
	  }
	  return prev;//else return -100
  }
  public int[] rangeKeys(int key1, int key2) {
	  int count = this.getSize();
	  int[] inRange = new int[count];
	  for (int i = 0; i < inRange.length; i++) {
		  inRange[i] = -100;
	  }
	  for (int i = 0; i < inRange.length; i++) {
		  if(this.heap[i].getKey() == key1) {//traverse heap until we find first key
			  int counter = 0;
			  for(int j = i + 1;j<inRange.length;j++) {//then keep adding keys until second key encountered
				  if(this.heap[j].getKey() == key2) {
					  return inRange;
				  }
				  inRange[counter++] = this.heap[j].getKey();
			  }
		  }else if(this.heap[i].getKey() == key1) {//traverse heap until we find second key
			  int counter = 0;
			  for(int j = i + 1;j<inRange.length;j++) {//then keep adding keys until first key encountered
				  if(this.heap[j].getKey() == key1) {
					  return inRange;
				  }
				  inRange[counter++] = this.heap[j].getKey();
			  }
		  }
	}
	return inRange;
  }
}