package software.xdev.vaadin.maps.leaflet.flow.data.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;


@Entity
public class Polyline extends AbstractEntity
{
	

	// private List<LPoint> points = new ArrayList<>();
	@ElementCollection
	private List<Double> points = new ArrayList<>();
	
	public List<Double> getPoints() { return this.points; }
	public void setPoints(List<Double> points) { this.points = points; }
}
