package software.xdev.vaadin.maps.leaflet.flow.data.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import software.xdev.vaadin.maps.leaflet.flow.data.LPoint;


@Entity
public class Rectangle extends AbstractEntity
{
	
	@Convert(converter = LPointConverter.class)
	// North West Point
	private LPoint nwPoint;
	
	@Convert(converter = LPointConverter.class)
	// South East Point
	private LPoint sePoint;
	
	public LPoint getNoPoint() { return this.nwPoint; }
	public void setNoPoint(LPoint point) { this.nwPoint = point; }
	
	
	public LPoint getSePoint() { return this.sePoint; }
	public void setSePoint(LPoint point) { this.sePoint = point; }
}
