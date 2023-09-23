
public class HashNode {
	private int key;
	private String record;
	private HashNode next;

	public HashNode(int key, String record) {
		this.key = key;
		this.record = record;
	}
	
	public int getKey() {
		return key;
	}
	public String getRecord() {
		return record;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public void setRecord(String record) {
		this.record = record;
	}
	public HashNode getNext() {
		return next;
	}
	public void setNext(HashNode next) {
		this.next = next;
	}
}
