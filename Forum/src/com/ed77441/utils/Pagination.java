package com.ed77441.utils;

public class Pagination {
	private int last;
	private int start, end, current;
	
	public Pagination (int current, int last) {
		this.current = current;
		this.last = last;
		
		int expectedStart = current - 2;
		int expectedEnd = current + 2;
		
		if (expectedStart < 1) {
			start = 1;
			end = Math.min(last, 5);
		}
		else if (expectedEnd > last){
			start = Math.max(last - 5 + 1, 1);
			end = last;
		}
		else {
			start = expectedStart;
			end = expectedEnd;
		}		
	}
	
	public int getLast() {
		return last;
	}

	public void setLast(int last) {
		this.last = last;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}
	
	public static int calculatePageCount (int entries, int base) {
		return (entries - 1) / base + 1;
	}
}
