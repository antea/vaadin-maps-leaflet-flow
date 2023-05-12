package software.xdev.vaadin.data.entity;

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
