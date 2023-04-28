package software.xdev.vaadin.maps.leaflet.flow.data;

import java.util.Arrays;
import java.util.Collection;


public class LMarkerClusterGroup extends LLayerGroup
{
	
	public LMarkerClusterGroup(final LComponent... lComponents)
	{
		super(lComponents);
	}
	
	public LMarkerClusterGroup(final Collection<LComponent> lComponents)
	{
		super(lComponents);
	}
	@Override
	protected String getLeafletComponentName()
	{
		return "markerClusterGroup";
	}
	
	
}
