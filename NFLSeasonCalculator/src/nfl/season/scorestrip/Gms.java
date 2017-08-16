package nfl.season.scorestrip;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name="gms")
public class Gms {

	@ElementList(inline = true, type = G.class, entry = "g")
	private List<G> g;
	
	@Attribute
	private int gd;
	
	@Attribute
	private int w;
	
	@Attribute
	private String y;
	
	@Attribute
	private String t;
	
	public Gms() {
		
	}

	public List<G> getG() {
		return g;
	}

	public void setG(List<G> g) {
		this.g = g;
	}

	public int getGd() {
		return gd;
	}

	public void setGd(int gd) {
		this.gd = gd;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}
	
}
