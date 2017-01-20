

public class Point3d {
	public float x,y,z;
	public Point3d(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z=z;
	}
	public Point3d( Point3d p){
		x =p.x;
		y=p.y;
		z =p.z;
	}
	public Point3d(Vector p){
		x =p.x;
		y=p.y;
		z =p.z;
	}
	public Point3d add(Point3d p){
		return new Point3d(x+p.x,y+p.y,z+p.z);
	}
	public Point3d sub( Point3d p){
		return new Point3d(x-p.x,y-p.y,z-p.z);
	}
	public Point3d sub( Vector p){
		return new Point3d(x-p.x,y-p.y,z-p.z);
	}
	public float dot( Vector v){
		return ((x*v.x)+(y*v.y)+(z*v.z));
	}
	public float dot( Point3d v){
		return ((x*v.x)+(y*v.y)+(z*v.z));
	}
	public float dot( Normal v){
		return ((x*v.x)+(y*v.y)+(z*v.z));
	}


}