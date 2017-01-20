
public class Normal {
 float x,y,z;
	public Normal( float x, float y, float z){
		this.x = x;
		this.y =y;
		this.z =z;
	}
   public Normal( Normal n){
	   x=n.x;
	   y = n.y;
	   z = n.z;
   }
   public void normalize(){
	   double mag = Math.sqrt(x*x+y*y+z*z);
	   x/=mag;
	   y/=mag;
	   z/=mag;
   }
}
