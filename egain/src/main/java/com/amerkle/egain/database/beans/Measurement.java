package com.amerkle.egain.database.beans;

import java.util.Date;

public class Measurement {
	private Date m_date = null;
	private Double m_temperature = null;
	private Double m_humidity = null;

	public Measurement() {

	}

	public Measurement(Date d, Double t, Double h) {
		m_date = d;
		m_temperature = t;
		m_humidity = h;
	}

	public Date getM_date() {
		return m_date;
	}

	public void setM_date(Date m_date) {
		this.m_date = m_date;
	}

	public Double getM_temperature() {
		return m_temperature;
	}

	public void setM_temperature(Double m_temperature) {
		this.m_temperature = m_temperature;
	}

	public Double getM_humidity() {
		return m_humidity;
	}

	public void setM_humidity(Double m_humidity) {
		this.m_humidity = m_humidity;
	}
}
