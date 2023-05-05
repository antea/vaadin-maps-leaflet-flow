package software.xdev.vaadin.maps.leaflet.flow.data;

import static org.apache.commons.text.StringEscapeUtils.escapeEcmaScript;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Leaflet LayerGroup under the hood
 */
public class LLayerGroup
{
	private final List<LComponent> components = new ArrayList<>();
	private String buildClientJSVarName = "item";
	
	
	public LLayerGroup(final LComponent... lComponents)
	{
		this.components.addAll(Arrays.asList(lComponents));
	}
	
	public LLayerGroup(final Collection<LComponent> lComponents)
	{
		this.components.addAll(lComponents);
	}
	
	public List<LComponent> getComponents()
	{
		return this.components;
	}
	
	protected String getLeafletComponentName()
	{
		return "layerGroup";
	}
	
	public String buildClientJSItems() throws JsonProcessingException
	{
		
		/*
			// Under the hood, this is what is happening bellow
			let layerGroup = L.layerGroup();
			layerGroup.addLayer(L.marker(getRandomLatLng(map)));
			... Add more layers (ie components) ...
			map.addLayer(markers);
		*/
		
		// check LMap.addLComponent to understand why we do let item
		StringBuilder jsCode = new StringBuilder("let "+ getBuildClientJSVarName() + " = L." + getLeafletComponentName() + "()");
		for(int i = 0; i < this.components.size(); i++)
		{
			// this is to avoid the item variable being instantiated with the same variable name
			String buildClientJSItems = this.components.get(i).buildClientJSItems().replaceAll("item", "item" + i);
			
			jsCode
				.append("\n")
				.append(buildClientJSItems)
				.append("\n")
				.append(this.components.get(i).getPopup() != null
					? "item" + i + ".bindPopup('" + escapeEcmaScript(this.components.get(i).getPopup()) + "');\n"
					: "")
				.append("\n")
				.append(getBuildClientJSVarName() + ".addLayer(item" + i + ")");
			
		}
		
		return jsCode.toString();
	}
	
	public String getBuildClientJSVarName()
	{
		return buildClientJSVarName;
	}
	
	public void setBuildClientJSVarName(final String buildClientJSVarName)
	{
		this.buildClientJSVarName = buildClientJSVarName;
	}
}
