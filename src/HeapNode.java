
public class HeapNode {
	private int key;
	private String hospitalRecord;
	
	public HeapNode(int key, String record) {
		this.key = key;
		this.hospitalRecord = record;
	}
	
	public String getHospitalRecord() {
		return hospitalRecord;
	}
	public int getKey() {
		return key;
	}
	public void setHospitalRecord(String hospitalRecord) {
		this.hospitalRecord = hospitalRecord;
	}
	public void setKey(int key) {
		this.key = key;
	}
}
