package nfl.season.scorestrip;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name="g")
public class G {

	@Attribute
	private String eid;
	
	@Attribute
	private int gsis;
	
	@Attribute
	private String d;
	
	@Attribute
	private String t;
	
	@Attribute
	private String q;
	
	@Attribute
	private String k;
	
	@Attribute
	private String h;
	
	@Attribute
	private String hnn;
	
	@Attribute
	private String hs;
	
	@Attribute
	private String v;
	
	@Attribute
	private String vnn;
	
	@Attribute
	private String vs;
	
	@Attribute
	private String p;
	
	@Attribute
	private String rz;
	
	@Attribute
	private String ga;
	
	@Attribute
	private String gt;
	
	public G() {
		
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public int getGsis() {
		return gsis;
	}

	public void setGsis(int gsis) {
		this.gsis = gsis;
	}

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public String getK() {
		return k;
	}

	public void setK(String k) {
		this.k = k;
	}

	public String getH() {
		return h;
	}

	public void setH(String h) {
		this.h = h;
	}

	public String getHnn() {
		return hnn;
	}

	public void setHnn(String hnn) {
		this.hnn = hnn;
	}

	public String getHs() {
		return hs;
	}

	public void setHs(String hs) {
		this.hs = hs;
	}

	public String getV() {
		return v;
	}

	public void setV(String v) {
		this.v = v;
	}

	public String getVnn() {
		return vnn;
	}

	public void setVnn(String vnn) {
		this.vnn = vnn;
	}

	public String getVs() {
		return vs;
	}

	public void setVs(String vs) {
		this.vs = vs;
	}

	public String getP() {
		return p;
	}

	public void setP(String p) {
		this.p = p;
	}

	public String getRz() {
		return rz;
	}

	public void setRz(String rz) {
		this.rz = rz;
	}

	public String getGa() {
		return ga;
	}

	public void setGa(String ga) {
		this.ga = ga;
	}

	public String getGt() {
		return gt;
	}

	public void setGt(String gt) {
		this.gt = gt;
	}
	
}
