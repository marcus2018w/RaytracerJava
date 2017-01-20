
public class Sphere extends GeoObject{
	float radius;
	public Point3d center;
	public float t;
	public float a;
public Sphere(Point3d center, float radius, float  b){
	this.radius = radius;
	this.center = new Point3d(center);
	a = b;
}
@Override
public boolean hit(Ray ray) {
	float a,b,c;
	a =ray.direction.dot(ray.direction);
	b= 2*ray.origin.sub(center).dot(ray.direction);
	c=   (ray.origin.sub(center).dot(ray.origin.sub(center)) - radius*radius);
	float discrim = b*b - 4*a*c;
	float one;
	one = (float) ((-b-Math.sqrt(discrim ))/(2*a));
  
	if(one> .00001f){
		t =one;
	}

	if( discrim >= 0){
	return true;
	
}else{
	return false;

}


}

public float getT(){
	return t;
}
@Override
public boolean isSphere() {
	// TODO Auto-generated method stub
	return true;
}
}