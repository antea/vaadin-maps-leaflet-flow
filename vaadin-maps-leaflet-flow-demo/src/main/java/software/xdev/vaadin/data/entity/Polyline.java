package software.xdev.vaadin.data.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
<<<<<<< HEAD:vaadin-maps-leaflet-flow/src/main/java/software/xdev/vaadin/maps/leaflet/flow/data/entity/Polyline.java
import software.xdev.vaadin.maps.leaflet.flow.data.Converter.LPointConverter;
=======
>>>>>>> 81751c640c88110697f824a9d129807c911a05e5:vaadin-maps-leaflet-flow-demo/src/main/java/software/xdev/vaadin/data/entity/Polyline.java
import software.xdev.vaadin.maps.leaflet.flow.data.LPoint;


@Entity
public class Polyline extends AbstractEntity
{
	
	@Convert(converter = LPointConverter.class)
	private List<LPoint> points = new ArrayList<>();
	
	public List<LPoint> getPoints() { return this.points; }
	public void setPoints(List<LPoint> points) { this.points = points; }
}
