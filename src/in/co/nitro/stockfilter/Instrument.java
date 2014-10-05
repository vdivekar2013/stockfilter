package in.co.nitro.stockfilter;

public class Instrument {
	private String code;
	private String name;
	private char group;
	private char type;
	private float open;
	private float close;
	private float high;
	private float low;
	private float previousclose;
	private float lastTrade;
	private long numberTrades;
	private long numberShares;
	private float turnover;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public char getGroup() {
		return group;
	}
	public void setGroup(char group) {
		this.group = group;
	}
	public char getType() {
		return type;
	}
	public void setType(char type) {
		this.type = type;
	}
	public float getOpen() {
		return open;
	}
	public void setOpen(float open) {
		this.open = open;
	}
	public float getClose() {
		return close;
	}
	public void setClose(float close) {
		this.close = close;
	}
	public float getHigh() {
		return high;
	}
	public void setHigh(float high) {
		this.high = high;
	}
	public float getLow() {
		return low;
	}
	public void setLow(float low) {
		this.low = low;
	}
	public float getPreviousclose() {
		return previousclose;
	}
	public void setPreviousclose(float previousclose) {
		this.previousclose = previousclose;
	}
	public float getLastTrade() {
		return lastTrade;
	}
	public void setLastTrade(float lastTrade) {
		this.lastTrade = lastTrade;
	}
	public long getNumberTrades() {
		return numberTrades;
	}
	public void setNumberTrades(long numberTrades) {
		this.numberTrades = numberTrades;
	}
	public long getNumberShares() {
		return numberShares;
	}
	public void setNumberShares(long numberShares) {
		this.numberShares = numberShares;
	}
	public float getTurnover() {
		return turnover;
	}
	public void setTurnover(float turnover) {
		this.turnover = turnover;
	}
	
}
