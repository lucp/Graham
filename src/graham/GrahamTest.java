package graham;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

import org.junit.Test;

public class GrahamTest {

	@Test
	public void grahamTest() throws IOException {
		Stack<Point> stack = Graham.run(Graham.readPointsCSV("res/punktyPrzykladowe.csv"));
		String output = new String();
		while (!stack.empty()) {
			output += stack.pop().toString() + "\n";
		}
		try {
			File file = new File("res/convexHull.csv");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(output);
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
