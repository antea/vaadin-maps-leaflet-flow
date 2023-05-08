package software.xdev.vaadin.maps.leaflet.flow.data;

import java.util.Arrays;
import java.util.Collection;

/**
 * Uses the leaflet.markercluster plugin under the hood
 * It needs the same methods as layerGroup so that's why it inherits from it
 */
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
	public void setClusterRadius(int clusterRadius) {
		this.options = "{maxClusterRadius: " + clusterRadius + "}";
	}
	
	
}
