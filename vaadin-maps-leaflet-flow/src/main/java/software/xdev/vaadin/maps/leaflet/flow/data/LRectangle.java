package software.xdev.vaadin.maps.leaflet.flow.data;

import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class LRectangle implements LComponent
{
	private final LPoint nwPoint;
	private final LPoint sePoint;
	private final LPolygonOptions properties;
	
	private UUID id = UUID.randomUUID();
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	
	public LRectangle(final LPoint nwPoint, final LPoint sePoint)
	{
		this.nwPoint = nwPoint;
		this.sePoint = sePoint;
		this.properties = new LPolygonOptions();
		this.properties.setFill(true);
	}
	
	public LRectangle(UUID id, final LPoint nwPoint, final LPoint sePoint) {
		this(nwPoint, sePoint);
		this.id = id;
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
