package nfl.season.scorestrip;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="ss")
public class Ss {

	@Element
	private Gms gms;
	
	public Ss() {
		
	}
	
	public Gms getGms() {
		return gms;
	}

	public void setGms(Gms gms) {
		this.gms = gms;
	}
	
}
