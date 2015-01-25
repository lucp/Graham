package graham;

public class Point {
		
	public double x;
	public double y;
	public double r;
	public double theta;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
		this.r = Math.sqrt(x * x + y * y);
		this.theta = Math.atan2(y, x);
	}
	
//		@Override
//		public boolean equals(Object point) {
//			if ()
//		}
	
}
