

import extra.Normal;
import extra.Point3d;

public class Vector {
	public float x,y,z;
	public float mag;
	public  Vector( float x, float y, float z){
		this.x =x;
		this.y = y;
		this.z=z;
		mag = (float) Math.sqrt(x*x+y*y+z*z);
	}
	public Vector( Vector v){
		mag = (float) Math.sqrt(x*x+y*y+z*z);
		x= v.x;
		y = v.y;
		z= v.z;
	}
	public Vector(){
		x=0;
		y=0;
		z=0;
	}
	public Vector add(Vector v){
		return new Vector( x+v.x,y+v.y,z+v.z);
	}
	public Vector scale(float t){
		return new Vector( t*x,t*y,t*z);
	}
	
	public Vector sub(Vector v){
		return new Vector( x-v.x, y-v.y,z-v.z);
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
	public void normalize(){
		   
		   x/=mag;
		   y/=mag;
		   z/=mag;
	   }
	public float getMag(){
		return mag;
	}

}
