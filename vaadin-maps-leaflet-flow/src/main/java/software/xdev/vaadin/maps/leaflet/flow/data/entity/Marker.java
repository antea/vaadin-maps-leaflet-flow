package software.xdev.vaadin.maps.leaflet.flow.data.entity;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;


@Entity
public class Marker extends AbstractEntity
{
	
	@ElementCollection
	private List<Double> point;
	
	public void setPoint(List<Double> point) { this.point = point; }
	public List<Double> getPoint() { return this.point; }
}
