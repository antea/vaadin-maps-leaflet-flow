package software.xdev.vaadin.data.entity;



import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import software.xdev.vaadin.maps.leaflet.flow.data.LPoint;


@Entity
public class Rectangle extends AbstractEntity
{
	
	// North West Point
	private double nwLat;
	private double nwLong;
	
	private double seLat;
	private double seLong;
	
	public double getNwLat()
	{
		return nwLat;
	}
	
	public void setNwLat(final double nwLat)
	{
		this.nwLat = nwLat;
	}
	
	public double getNwLong()
	{
		return nwLong;
	}
	
	public void setNwLong(final double nwLong)
	{
		this.nwLong = nwLong;
	}
	
	public double getSeLat()
	{
		return seLat;
	}
	
	public void setSeLat(final double seLat)
	{
		this.seLat = seLat;
	}
	
	public double getSeLong()
	{
		return seLong;
	}
	
	public void setSeLong(final double seLong)
	{
		this.seLong = seLong;
	}
}
