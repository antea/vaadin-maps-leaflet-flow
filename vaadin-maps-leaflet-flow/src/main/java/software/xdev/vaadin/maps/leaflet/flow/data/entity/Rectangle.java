package software.xdev.vaadin.maps.leaflet.flow.data.entity;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import software.xdev.vaadin.maps.leaflet.flow.data.Converter.LPointConverter;
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
	
	public LPoint getNwPoint() { return this.nwPoint; }
	public void setNwPoint(LPoint point) { this.nwPoint = point; }
	
	
	public LPoint getSePoint() { return this.sePoint; }
	public void setSePoint(LPoint point) { this.sePoint = point; }
}
