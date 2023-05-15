package software.xdev.vaadin.data.entity;



import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import software.xdev.vaadin.maps.leaflet.flow.data.LPoint;


@Entity
public class Rectangle extends AbstractEntity
{
	
	@NotBlank
	@Convert(converter = LPointConverter.class)
	// North West Point
	private LPoint nwPoint;
	
	@NotBlank
	@Convert(converter = LPointConverter.class)
	// South East Point
	private LPoint sePoint;
	
	public LPoint getNwPoint() { return this.nwPoint; }
	public void setNwPoint(LPoint point) { this.nwPoint = point; }
	
	
	public LPoint getSePoint() { return this.sePoint; }
	public void setSePoint(LPoint point) { this.sePoint = point; }
}
