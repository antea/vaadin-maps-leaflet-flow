package software.xdev.vaadin.data.entity;


import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import software.xdev.vaadin.data.entity.AbstractEntity;
import software.xdev.vaadin.maps.leaflet.flow.data.LPoint;


@Entity
public class Marker extends AbstractEntity
{
	
	@Convert(converter = LPointConverter.class)
	private LPoint point;
	
	public void setPoint(LPoint point) { this.point = point; }
	public LPoint getPoint() { return this.point; }
}
