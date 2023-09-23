

public class HashTester {

	public static void main(String[] args) {
		HashTable table = new HashTable();
		table.add(0, "gklj;alskdfgj;lskargj");
		table.add(616545, "1256186514941981");
		table.add(61655, "ajlgha");
		table.add(6164445, "ajlgha");
		table.add(61545, "ajlgha");
		table.rangeKey(0, 616545);
		System.out.println(table.remove(616545).getRecord());
	}

}
