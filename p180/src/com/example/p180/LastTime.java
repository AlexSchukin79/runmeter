package com.example.p180;

import java.util.Date;

public class LastTime {
int meter = 17;
Date timesDate = new Date();

public long last() {
	Date timDate = new Date();
	long last = (timDate.getTime() - timesDate.getTime()) * 17 / 1000;
	timesDate.setTime(timDate.getTime());
	return last;
}
}
