package graham;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Stack;

import jdk.nashorn.internal.runtime.FindProperty;

public class Graham {
	
	public static double det(Point p, Point q, Point r) {
		return p.x * q.y + p.y * r.x + q.x * r.y - p.x * r.y - q.x * p.y - q.y * r.x;
	}
	
	public static void sort(LinkedList<Point> points, final Point reference) {
		Collections.sort(points, new Comparator<Point>() {
			@Override
			public int compare(Point point1, Point point2) {
				double det = det(point1, point2, reference);
				if (det > 0) return -1;
				else if(det < 0) return 1;
				else return 0;
				}
			});
	}
		
	public static Point findReferencePoint(LinkedList<Point> points) {
		Point referencePoint = points.getFirst();
		for (Point point : points) {
			if (point.y < referencePoint.y) {
				referencePoint = point;
			}
			else if (point.y == referencePoint.y && point.x < referencePoint.x) { //TODO epsilon on ==
				referencePoint = point;
			}
		}
		return referencePoint;
	}
	
	public static void removeDuplicates(LinkedList<Point> points) {
		int pointIndex = 0;
		while (pointIndex < points.size()) {
			int pointIndexToComapre = pointIndex + 1;
			while (pointIndexToComapre < points.size()) {
				int pointIndexTmp = pointIndex;
				if (Math.abs(points.get(pointIndexTmp).theta - points.get(pointIndexToComapre).theta) < 0.0000001) {
					if (points.get(pointIndexTmp).r > points.get(pointIndexToComapre).r) {
						points.remove(pointIndexToComapre);
					}
					else {
						points.remove(pointIndexTmp);
						pointIndexTmp = pointIndexToComapre;
					}
				}
				pointIndexToComapre++;
			}
			pointIndex++;
		}
	}
	
	public static LinkedList<Point> run(LinkedList<Point> points) {
		Point referencePoint = Graham.findReferencePoint(points);
		points.remove(referencePoint);
		Graham.sort(points, referencePoint);
		Graham.removeDuplicates(points);
		points.addFirst(referencePoint);
		Stack<Point> stack = new Stack<Point>();
		stack.push(referencePoint);
		stack.push(points.get(1));
		stack.push(points.get(2));
		for (int i = 3; i < points.size(); i++) {
			Point point1 = points.get(i);
			Point point2 = stack.get(stack.size() - 1);
			Point point3 = stack.get(stack.size() - 2);
			while(Graham.det(point1, point2, point3) > 0){
				stack.pop();
				point2 = stack.get(stack.size() - 1);
				point3 = stack.get(stack.size() - 2);
			}
			stack.push(points.get(i));
		}
		return null;
	}
	
	public static LinkedList<Point> readPointsCSV(String filePath) throws IOException {
		LinkedList<Point> points =  new LinkedList<Point>();
		BufferedReader reader = null;
		try {
			String line;
			String[] entry;
			reader = new BufferedReader(new FileReader(filePath));
			while ((line = reader.readLine()) != null) {
				entry = line.split(",");
				entry[0] = entry[0].trim();
				entry[1] = entry[1].trim();
				points.add(new Point(Double.valueOf(entry[0]), Double.valueOf(entry[1])));
			}
		}
		catch (IOException exception) {
			if (reader != null) reader.close();
			throw exception;
		}
		finally {
			if (reader != null) reader.close();
		}
		return points;
	}
	
}
