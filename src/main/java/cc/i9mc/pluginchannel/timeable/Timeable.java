package cc.i9mc.pluginchannel.timeable;


public abstract class Timeable {
	
	long startDate;
	
	long effectiveDate;
	
	public Timeable(long effective) {
		startDate = System.currentTimeMillis();
		effectiveDate = System.currentTimeMillis() + effective;
	}
	
	public boolean isTimeLess() {
		return System.currentTimeMillis() > effectiveDate;
	}

	public long getStartDate() {
		return this.startDate;
	}

	public long getEffectiveDate() {
		return this.effectiveDate;
	}

	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}

	public void setEffectiveDate(long effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
}
