package software.xdev.vaadin.maps.leaflet.flow.demo.data.entity;

import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;

import software.xdev.vaadin.maps.leaflet.flow.data.LPoint;

@Entity
public class Rectangle extends AbstractEntity {
	
	@ElementCollection
	private List<Double> points = new ArrayList<>();
	
	public List<Double> getPoints() { return this.points; }
	public void setPoints(List<Double> points) { this.points = points; }
}
