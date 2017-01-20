
import extra.Normal;
import extra.Point3d;
import extra.Ray;

public class Plane extends GeoObject {
	public Point3d p;
	public float d;
	public float a ;
	Normal n;
   public Plane(Point3d p, Normal n, float b){
	   this.p = new Point3d(p);
	   this.n = new Normal(n);
	   a =b;
   }
	@Override
	public boolean hit(Ray ray) {
		 d = p.sub(ray.origin).dot(n)/ray.direction.dot(n);
		if( d > 0.00001F){
			return true;
		}
		return false;
	}
 public boolean isSphere(){
	 return false;
 }
	

}
