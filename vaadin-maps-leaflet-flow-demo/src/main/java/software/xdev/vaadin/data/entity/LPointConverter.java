<<<<<<<< HEAD:vaadin-maps-leaflet-flow/src/main/java/software/xdev/vaadin/maps/leaflet/flow/data/Converter/LPointConverter.java
package software.xdev.vaadin.maps.leaflet.flow.data.Converter;
========
package software.xdev.vaadin.data.entity;
>>>>>>>> 81751c640c88110697f824a9d129807c911a05e5:vaadin-maps-leaflet-flow-demo/src/main/java/software/xdev/vaadin/data/entity/LPointConverter.java

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import software.xdev.vaadin.maps.leaflet.flow.data.LPoint;


@Converter
public class LPointConverter implements AttributeConverter<LPoint, List<Double>>
{
	
	@Override
	public List<Double> convertToDatabaseColumn(final LPoint lPoint)
	{
		if (lPoint == null || lPoint.getCoords().isEmpty()) {
			return null;
		}
		
		else {
			return lPoint.getCoords();
		}
	}
	
	@Override
	public LPoint convertToEntityAttribute(final List<Double> doubleList)
	{
		// return null if both coords not present
		if ( doubleList == null || doubleList.isEmpty() || doubleList.size() < 2) {
			return null;
		}
		
		else {
			// return LPoint from extracted from list
			return new LPoint(doubleList.get(0), doubleList.get(1));
		}
	}
}
