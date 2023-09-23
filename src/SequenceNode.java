public class SequenceNode {
	private String hospitalRecord;
	private int key;
	private SequenceNode prev;
	private SequenceNode next;
	
	public SequenceNode(int key, String record, SequenceNode prev, SequenceNode next){
		this.key = key;
		hospitalRecord = record;
		this.prev = prev;
		this.next = next;
	}
	public String getHospitalRecord() {
		return hospitalRecord;
	}
	public int getKey() {
		return key;
	}
	public SequenceNode getNext() {
		return next;
	}
	public SequenceNode getPrev() {
		return prev;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public void setHospitalRecord(String hospitalRecord) {
		this.hospitalRecord = hospitalRecord;
	}
	public void setPrev(SequenceNode prev) {
		this.prev = prev;
	}
	public void setNext(SequenceNode next) {
		this.next = next;
	}
}
