package cdx.opencdx.evrete.domain;

public class BloodPressure {
	
	private int systolic;
	private String status;
	
	public int getSystolic() {
		return systolic;
	}

	public void setSystolic(int systolic) {
		this.systolic = systolic;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

}
