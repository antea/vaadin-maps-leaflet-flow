/*
 * Copyright © 2019 XDEV Software (https://xdev.software)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package software.xdev.vaadin.maps.leaflet.flow.data;

import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import software.xdev.vaadin.maps.leaflet.flow.LMap;


public class LMarker implements LComponent
{

	private static final String MARKER_TYPE = "Point";
	private LMarkerGeometry geometry;
	private LMarkerOptions properties;
	/**
	 * Tag for custom meta-data
	 */
	private String tag;
	private UUID id = UUID.randomUUID();



	/**
	 * Creates a new Marker at the latitude, longitude
	 *
	 * @param lat
	 * @param lon
	 */
	public LMarker(final double lat, final double lon)
	{
		this(lat, lon, null);
	}
	
	public LMarker(final LPoint point)
	{
		this(point.getLat(), point.getLon());
	}

	public LMarker(final double lat, final double lon, final String tag)
	{
		this.geometry = new LMarkerGeometry(MARKER_TYPE, lat, lon);
		this.properties = new LMarkerOptions();
		this.tag = tag;
	}
	
	public LMarker(UUID id, final double lat, final double longitude) {
		this(lat, longitude);
		this.id = id;
	}
	
	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public LIcon getIcon()
	{
		return this.properties.getIcon();
	}

	public void setDivIcon(final LDivIcon icon)
	{
		this.properties.setIcon(icon);
	}

	public LIcon getDivIcon()
	{
		return this.properties.getIcon();
	}

	public void setIcon(final LIcon icon)
	{
		this.properties.setIcon(icon);
	}

	public LMarkerGeometry getGeometry()
	{
		return this.geometry;
	}

	public void setGeometry(final LMarkerGeometry geometry)
	{
		this.geometry = geometry;
	}

	public LMarkerOptions getProperties()
	{
		return this.properties;
	}

	public void setProperties(final LMarkerOptions properties)
	{
		this.properties = properties;
	}
	
	public LPoint getPoint()
	{
		return new LPoint(this.getLat(),this.getLon());
	}
	
	public double getLat()
	{
		return this.geometry.getCoordinates().get(0);
	}
	
	public void setLat(final double lat)
	{
		this.geometry.getCoordinates().set(0, lat);
	}
	
	public double getLon()
	{
		return this.geometry.getCoordinates().get(1);
	}
	
	public void setLon(final double lon)
	{
		this.geometry.getCoordinates().set(1, lon);
	}
	
	@Override
	public String getPopup()
	{
		return this.properties.getPopup();
	}
	
	/**
	 * Sets a Pop-up to the Marker
	 *
	 * @param popup
	 */
	public void setPopup(final String popup)
	{
		this.properties.setPopup(popup);
	}
	
	public String getTag()
	{
		return this.tag;
	}
	
	public void setTag(final String tag)
	{
		this.tag = tag;
	}
	
	@Override
	public String buildClientJSItems() throws JsonProcessingException
	{
		final ObjectMapper mapper = new ObjectMapper();
		return "let item = L.marker("
			+ mapper.writeValueAsString(this.geometry.getCoordinates()) + ","
			+ "{icon: "
			+ "new L."
			+ (this.properties.getIcon() instanceof LDivIcon ? "divIcon" : "Icon")
			+ "(" + mapper.writeValueAsString(this.properties.getIcon()) + ")"
			+ " }"
			+ ");"
			+ (this.getTag() != null && !this.getTag().isBlank()
			? ("\nvar vaadinServer = this.$server;"
			+ "\nitem.on('click', function (e) { vaadinServer.onMarkerClick('" + this.tag + "') });\n")
			: "");
			// + "item.on('pm:edit', (e) => {\n"
			// + "  "+ LMap.CLIENT_ENABLE_MAP_DRAGGING_FUNCTION+"();\n" // (declared in the constructor of LMap) I made this method is because: for some reason when you drag a marker you cannot drag the map after
			// + "});\n"
			// + "item.on('pm:remove', (e) => {\n"
			// + "   "+LMap.CLIENT_REMOVE_MARKER_GLOBAL_MCG+"(e.layer);\n"
			// + "});";
		
	}
}
