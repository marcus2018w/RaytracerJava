
public class Ray {
public Point3d origin;
public Vector direction;
 public Ray(Point3d origin, Vector direction){
	 this.origin = new Point3d(origin);
	 this.direction = new Vector(direction);
	 
 }
 public Ray(){
	 origin = new Point3d(0,0,0);
	 direction = new Vector(0,0,0);
 }
 public void normalize(){
	   double mag = Math.sqrt(direction.x*direction.x+direction.y*direction.y+direction.z*direction.z);
	   direction.x/=mag;
	  direction.y/=mag;
	   direction.z/=mag;
 }
}
