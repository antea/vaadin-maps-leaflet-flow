<<<<<<< HEAD:vaadin-maps-leaflet-flow/src/main/java/software/xdev/vaadin/maps/leaflet/flow/data/entity/Marker.java
package software.xdev.vaadin.maps.leaflet.flow.data.entity;
=======
package software.xdev.vaadin.data.entity;
>>>>>>> 81751c640c88110697f824a9d129807c911a05e5:vaadin-maps-leaflet-flow-demo/src/main/java/software/xdev/vaadin/data/entity/Marker.java

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import software.xdev.vaadin.maps.leaflet.flow.data.Converter.LPointConverter;
import software.xdev.vaadin.maps.leaflet.flow.data.LPoint;


@Entity
public class Marker extends AbstractEntity
{
	
	@Convert(converter = LPointConverter.class)
	private LPoint point;
	
	public void setPoint(LPoint point) { this.point = point; }
	public LPoint getPoint() { return this.point; }
}
