package constants;

public enum ExecutionSpeed {
	FAST("fast"), MEDIUM("medium"),SLOW("slow");
	
	int seconds;
	String type;
	
	ExecutionSpeed(String type){
		this.type = type;
	}
	
	public int getSeconds() {
		switch(this) {
		case FAST: seconds = 10;
		break;
		case MEDIUM: seconds = 120;
		break;
		case SLOW: seconds = 600;
		break;
		default: seconds = 120;
	}
		return this.seconds;
	}
	
}
