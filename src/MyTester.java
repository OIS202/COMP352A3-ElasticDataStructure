import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * i am adding this file because the posted test files are very big and need a lot of time for inserting since you have to search the whole structure to check if that 
 * key exists since keys have to be unique
 * so i extracted a 5000 line piece from the first test file to create a test file called MyTest.txt to test these functionalities quickly
 */
public class MyTester {

	public static void main(String[] args) {
		Scanner readFile = null;
		ElasticERL data = new ElasticERL();
		data.setInThreshold(500);
		System.out.println(data.generate());
		try {
			readFile = new Scanner(new FileInputStream("MyTest.txt"));
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
		ElasticSequence seq = data.allKeys();
		seq.printSequence();
	}

}
