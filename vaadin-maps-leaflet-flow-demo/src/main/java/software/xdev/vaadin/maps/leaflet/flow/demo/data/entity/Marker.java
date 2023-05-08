package software.xdev.vaadin.maps.leaflet.flow.demo.data.entity;

import java.util.List;


import jakarta.persistence.Entity;

import software.xdev.vaadin.maps.leaflet.flow.data.LPoint;


@Entity
public class Marker extends AbstractEntity {
	private LPoint point;
	
	public void setPoint(LPoint point) { this.point = point; }
	public LPoint getPoint() { return this.point; }
}
