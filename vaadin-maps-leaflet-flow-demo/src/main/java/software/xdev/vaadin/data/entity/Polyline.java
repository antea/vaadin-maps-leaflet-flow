package software.xdev.vaadin.data.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import software.xdev.vaadin.maps.leaflet.flow.data.LPoint;


@Entity
public class Polyline extends AbstractEntity
{
	
	@NotBlank
	@Convert(converter = LPointConverter.class)
	private List<LPoint> points = new ArrayList<>();
	
	public List<LPoint> getPoints() { return this.points; }
	public void setPoints(List<LPoint> points) { this.points = points; }
}
