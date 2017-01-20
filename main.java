import extra.*;
import geo.GeoObject;
import geo.Plane;
import geo.Sphere;

import java.awt.List;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Random;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

import javax.swing.JFrame;

public class Part3 extends JFrame implements GLEventListener {
	private static final long serialVersionUID = 1L;
	final static String name = "[name]";
	float u, v;

	final int width = 512;
	final int height = 512;
	final int max_Depth = 2;
	Buffer scene;

	public Part3() {
		super(name + "'s Raytracer");
		this.scene = renderScene();
		GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities caps = new GLCapabilities(profile);
		GLCanvas canvas = new GLCanvas(caps);
		canvas.addGLEventListener(this);
		this.setName(name + "'s Raytracer");
		this.getContentPane().add(canvas);
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		canvas.requestFocusInWindow();
	}

	public Buffer renderScene() {
		float[] data = new float[width * height * 3];
		float l = -.1F;
		float r = .1F;
		float b = -.1F;
		float t = .1F;
		float d = .1F;
		float shift = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int i = (y * height) + x;
				i *= 3;
				u = (float) (l + (r - l) * (x + .5) / 512);
				v = (float) (b + ((t - b) * (y + .5) / 512));

				Ray ray = new Ray(new Point3d(0.0F, 0.0F, 0.0F), new Vector(u,
						v, -.1F));
				int depth = 0;
				Vector L = shade(ray, depth);
				data[i + 0] = (float) Math.pow(L.x, 1 / 2.2); // red
				data[i + 1] = (float) Math.pow(L.y, 1 / 2.2); // green
				data[i + 2] = (float) Math.pow(L.z, 1 / 2.2); // blue
			} // second for loop
		}
		return FloatBuffer.wrap(data);
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glDrawPixels(width, height, GL2.GL_RGB, GL2.GL_FLOAT, this.scene);
		gl.glFlush();
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {

	}

	@Override
	public void init(GLAutoDrawable arg0) {
	}

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3,
			int arg4) {
	}

	public static void main(String[] args) {
		Part3 game = new Part3();
	}

	public Vector shade(Ray ray, int depth) {
		Sphere blue = new Sphere(new Point3d(-4.0F, 0.0F, -7.0F), 1.0F, 0);
		Sphere green = new Sphere(new Point3d(0.0F, 0.0F, -7.0F), 2.0F, 0);
		Sphere red = new Sphere(new Point3d(4.0F, 0.0F, -7.0F), 1.0F, .8F);
		Plane plane = new Plane(new Point3d(0.0F, -2.0F, 0.0F), new Normal(
				0.0F, -2.0F, 0.0F), .5F);
		Vector L = new Vector();

		ArrayList<GeoObject> list = new ArrayList<GeoObject>();
		list.add(red);
		list.add(green);
		list.add(blue);
		list.add(plane);

		Vector Ls = new Vector();
		Vector La = new Vector();
		Vector Ld = new Vector();
		for (GeoObject k : list) {
		 if (green.hit(ray)) {
				Vector ka = new Vector(0.0F, 0.2F, 0.0F);
				Vector kd = new Vector(0.0F, 0.5F, 0.0F);
				Vector ks = new Vector(0.5F, 0.5F, .5F);
				Vector n = ray.direction.scale(green.getT()).add(
						new Vector(ray.origin.x, ray.origin.y, ray.origin.z));
				Vector norm = n.sub(new Vector(green.center.x, green.center.y,
						green.center.z));
				norm.normalize();
				Vector light = new Vector(-4.0F, 4.0F, -3.0F).sub(n);
				light.normalize();
				Ld = kd.scale(Math.max(0, norm.dot(light)));
				ray.normalize();
				Vector temp = ray.direction.sub(light);
				Vector h = temp.scale(1 / temp.mag);
				Ls = ks.scale((float) Math.pow(norm.dot(h), 32));
				La = ka;
				L = La.add(Ls).add(Ld);

			}
		 else if (red.hit(ray)) {
				Vector ka = new Vector(0.0F, 0.0F, 0.2F);
				Vector kd = new Vector(0.0F, 0.0F, 1.0F);
				Vector ks = new Vector(0.0F, 0.0F, 0.0F);
				Vector n = ray.direction.scale(red.t).add(new Vector(ray.origin.x,ray.origin.y,ray.origin.z));
				
																				// Vector(ray.origin.x,ray.origin.y,ray.origin.z));
				Vector norm = n.sub(new Vector(red.center.x, red.center.y,
						red.center.z));
				norm.normalize();

				Vector light = new Vector(-4.0F, 4.0F, -3.0F).sub(n);
				light.normalize();
				Ld = kd.scale(Math.max(0, norm.dot(light)));
				ray.normalize();
				Vector temp = ray.direction.add(light);
				Vector h = temp.scale(1 / temp.mag);
				Ls = ks.scale((float) Math.pow(norm.dot(h), 0));
				La = ka;
				L = La.add(Ls).add(Ld);

				Ray shadow = new Ray(new Point3d(n.x, n.y, n.z), light);
				if (green.hit(shadow) == true) { // created a shadow ray with
													// point of intersection and
													// light vector
					L = ka;
				}
				Ray reflect = new Ray(new Point3d(n),
						((norm.scale(-2 * norm.dot(ray.direction)))).add(ray.direction));
				
				if(depth < 3){
					return L.scale(.2f).add(shade(reflect,depth+1).scale(.8f));
				}

			} 

			else if (blue.hit(ray)) {
				Vector ka = new Vector(0.2F, 0.0F, 0.0F);
				Vector kd = new Vector(1.0F, 0.0F, 0.0F);
				Vector ks = new Vector(0.0F, 0.0F, 0.0F);
				Vector n = ray.direction.scale(blue.t).add(
						new Vector(ray.origin.x, ray.origin.y, ray.origin.z));
				Vector norm = n.sub(new Vector(blue.center.x, blue.center.y,
						blue.center.z));

				Vector light = new Vector(-4.0F, 4.0F, -3.0F).sub(n);
				light.normalize();
				norm.normalize();
				Ld = kd.scale(Math.max(0, norm.dot(light)));
				ray.normalize();
				Vector h = ray.direction.add(light);
				Ls = ks.scale((float) Math.pow(norm.dot(h), 0));
				La = ka;
				L = La.add(Ls).add(Ld);

			}

			else if (plane.hit(ray)) {
				Vector ka = new Vector(.2F, .2F, .2F);
				Vector kd = new Vector(1.0F, 1.0F, 1.0F);
				Vector ks = new Vector(0, 0, 0);
				Vector n = ray.direction.scale(plane.d).add(new Vector(ray.origin.x, ray.origin.y, ray.origin.z));
				Vector norm = new Vector(0, 1, 0);

				Vector light = new Vector(-4.0F, 4.0F, -3.0F).sub(n);
				light.normalize();
				Ld = kd.scale(Math.max(0, norm.dot(light)));
				ray.normalize();
				Vector h = ray.direction.add(light);
				Ls = ks.scale((float) Math.pow(norm.dot(h), 0));
				La = ka;

				Ray reflect = new Ray(new Point3d(n.x, n.y, n.z),
						ray.direction.sub(norm.scale(2 * norm
								.dot(ray.direction))));

				L = La.add(Ls).add(Ld);
				Ray shadow = new Ray(new Point3d(n.x, n.y, n.z), light);
				if (green.hit(shadow) == true) {
					L = ka;
				} else if (red.hit(shadow) == true)
					L = ka;
				else if (blue.hit(shadow) == true)
					L = ka;
     if( depth < 3){
    	 return L.scale(.5f).add(shade(reflect,depth+1).scale(.5f));
     }
			}

			
		}
		return L;
	}

}
