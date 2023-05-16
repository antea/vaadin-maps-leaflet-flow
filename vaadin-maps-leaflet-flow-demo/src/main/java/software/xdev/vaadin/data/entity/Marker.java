package software.xdev.vaadin.data.entity;


import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Type;

import software.xdev.vaadin.maps.leaflet.flow.data.LPoint;


@Entity
public class Marker extends AbstractEntity
{
	private double latitude;
	private double longitude;
	
	public double getLong() { return longitude; }
	
	public void setLongitude(final double longitude) {this.longitude = longitude;}
	
	public void setLat(double lat) { this.latitude = lat; }
	public double getLat() { return this.latitude; }
}
