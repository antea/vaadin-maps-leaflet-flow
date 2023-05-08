package software.xdev.vaadin.maps.leaflet.flow.demo.data.entity;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import jakarta.persistence.Entity;
import software.xdev.vaadin.maps.leaflet.flow.data.LPoint;

@Entity
public class Polyline extends AbstractEntity {
	
	@NElem
	private List<LPoint> points = new ArrayList<>();
	
	public List<LPoint> getPoints() { return this.points; }
	public void setPoints(List<LPoint> points) { this.points = points; }
}
