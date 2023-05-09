package software.xdev.vaadin.maps.leaflet.flow.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class LRectangle implements LComponent
{
	private final LPoint nwPoint;
	private final LPoint sePoint;
	private final LPolygonOptions properties;
	
	
	public LRectangle(final LPoint nwPoint, final LPoint sePoint)
	{
		this.nwPoint = nwPoint;
		this.sePoint = sePoint;
		this.properties = new LPolygonOptions();
		this.properties.setFill(true);
	}
	
	@Override
	public String buildClientJSItems() throws JsonProcessingException
	{
		final ObjectMapper mapper = new ObjectMapper();
		return "let item = L.rectangle("
			+ "["
			+ "[" + this.nwPoint.getLat() + ", " + this.nwPoint.getLon() + "],"
			+ "[" + this.sePoint.getLat() + ", " + this.sePoint.getLon() + "]"
			+ "],"
			+ mapper.writeValueAsString(this.properties)
			+ ");";
		
	}
	
	@Override
	public String getPopup()
	{
		return null;
	}
	
	public LPoint getNwPoint()
	{
		return nwPoint;
	}
	
	public LPoint getSePoin()
	{
		return sePoint;
	}
	
	public LPolygonOptions getProperties()
	{
		return properties;
	}
}
