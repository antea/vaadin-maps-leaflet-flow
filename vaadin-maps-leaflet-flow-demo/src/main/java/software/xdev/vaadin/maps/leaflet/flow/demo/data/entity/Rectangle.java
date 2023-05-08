package software.xdev.vaadin.maps.leaflet.flow.demo.data.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Nullable;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import software.xdev.vaadin.maps.leaflet.flow.data.LPoint;

@Entity
public class Rectangle extends AbstractEntity {
	
	private List<LPoint> points = new ArrayList<>();
	
	public List<LPoint> getPoints() { return this.points; }
	public void setPoints(List<LPoint> points) { this.points = points; }
}
