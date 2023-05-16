package software.xdev.vaadin.data.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import software.xdev.vaadin.maps.leaflet.flow.data.LPoint;


@Entity
public class Polyline extends AbstractEntity
{
	@ElementCollection
	private List<Double> points = new ArrayList<>();
	
	// default constructor
	public Polyline() {}
	public Polyline(UUID id, List<Double> points) {
		super(id);
		this.points = points;
	}
	
	public List<Double> getPoints() { return this.points; }
	public void setPoints(List<Double> points) { this.points = points; }
	
}
