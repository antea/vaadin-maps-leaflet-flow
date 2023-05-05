package software.xdev.vaadin.maps.leaflet.flow.data;

import java.util.Collection;


public class LFeatureGroup extends LLayerGroup
{
	
	public LFeatureGroup(final LComponent... lComponents)
	{
		super(lComponents);
	}
	
	public LFeatureGroup(final Collection<LComponent> lComponents)
	{
		super(lComponents);
	}
	@Override
	protected String getLeafletComponentName()
	{
		return "featureGroup";
	}
}


