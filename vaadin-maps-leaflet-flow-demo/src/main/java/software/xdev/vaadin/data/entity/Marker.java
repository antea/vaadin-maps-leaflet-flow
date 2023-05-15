package software.xdev.vaadin.data.entity;


import javax.persistence.Entity;

import jakarta.persistence.Convert;

import software.xdev.vaadin.maps.leaflet.flow.data.LPoint;


@Entity
public class Marker extends AbstractEntity
{
	
	@Convert(converter = LPointConverter.class)
	private LPoint point;
	
	public void setPoint(LPoint point) { this.point = point; }
	public LPoint getPoint() { return this.point; }
}
