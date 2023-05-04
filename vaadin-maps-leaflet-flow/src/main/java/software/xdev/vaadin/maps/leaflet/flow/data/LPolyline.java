package software.xdev.vaadin.maps.leaflet.flow.data;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * LPolyline needs pretty much the same options as LPolygon.
 * The only difference is that it uses L.polyline under the hood.
 */
public class LPolyline extends LPolygon
{
	
	public LPolyline(final LPoint... points)
	{
		super(points);
	}
	
	public LPolyline(final List<LPoint> points)
	{
		super(points);
	}
	@Override
	public String buildClientJSItems() throws JsonProcessingException
	{
		final ObjectMapper mapper = new ObjectMapper();
		return "let item = L.polyline("
			+ mapper.writeValueAsString(getGeometry().getCoordinates()) + ","
			+ mapper.writeValueAsString(getProperties())
			+ ");";
	}
	
}
