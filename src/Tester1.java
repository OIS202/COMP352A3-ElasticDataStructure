import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Tester1 {

	public static void main(String[] args) {
		Scanner readFile = null;
		ElasticERL data = new ElasticERL();
		data.setInThreshold(500);
		System.out.println(data.generate());
		try {
			readFile = new Scanner(new FileInputStream("EHITS_test_file1.txt"));
			while(readFile.hasNextInt()) {
				int key = readFile.nextInt();
				String record = "some record";
				data.add(key, record);
			}
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
		}
		//data.printData();
		System.out.println(data.getValue(33212));
		System.out.println(data.nextKey(33211));
		System.out.println(data.prevKey(33212));
		int[] range = data.rangeKey(33211, 157256);
		for (int i = 0; i < range.length; i++) {
			if(range[i] != -100)//since i set the null value to -100
				System.out.println(range[i]);
		}
		data.remove(157256);
		
	}
}
