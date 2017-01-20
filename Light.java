import java.awt.Color;

public class Light {
	Point3d origin;
	Vector direction;
	Color color;

	public Light(Point3d origin, Vector direction, Color color) {
		this.origin = origin;
		this.direction = direction;
		this.color = color;
		// TODO Auto-generated constructor stub
	}
	public Light(Point3d origin, Vector direction){
		this.origin = origin;
		this.direction = direction;
		color = new Color(1.0F,1.0F,1.0F);
	}

}