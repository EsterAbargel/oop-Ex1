package Ex1;

public class Graph {
	public int Width;
	public int Height;
	public int resolution;
	public double[] rx = new double[2];
	public double[] ry=  new double[2];
	/**
	 * @param width
	 * @param height
	 * @param resolution
	 * @param range_X
	 * @param range_Y
	 */
	public Graph(int width, int height, int resolution, double[] rx, double[] ry) {
		this.Width = width;
		this.Height = height;
		this.resolution = resolution;
		this.rx = rx;
		this.ry = ry;
	}

}
