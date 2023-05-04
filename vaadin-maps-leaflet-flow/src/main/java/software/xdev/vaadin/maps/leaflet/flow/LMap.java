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
package software.xdev.vaadin.maps.leaflet.flow;



import static org.apache.commons.text.StringEscapeUtils.escapeEcmaScript;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.shared.Registration;

import software.xdev.vaadin.maps.leaflet.flow.data.LCenter;
import software.xdev.vaadin.maps.leaflet.flow.data.LComponent;
import software.xdev.vaadin.maps.leaflet.flow.data.LLayerGroup;
import software.xdev.vaadin.maps.leaflet.flow.data.LPoint;
import software.xdev.vaadin.maps.leaflet.flow.data.LTileLayer;


@NpmPackage(value = "leaflet", version = "1.8.0")
@NpmPackage(value = "leaflet.markercluster", version = "1.4.1")
@NpmPackage(value = "leaflet-draw", version = "1.0.4")
@NpmPackage(value = "leaflet-mouse-position", version = "1.2.0")
@Tag("leaflet-map")
// If I import Leaflet and leaflet.markercluster separately I get this error https://stackoverflow.com/questions/44479562/l-is-not-defined-error-with-leaflet
// because vaadin has a bug that does not guarantee that the imports will be in the same order as defined with @JsModule
// Here is the bug issue: https://github.com/vaadin/flow/issues/15825
@JsModule("./leaflet/import-leaflet-with-plugins.js")
// importing the leaflet css
@CssImport("leaflet/dist/leaflet.css")
@CssImport("leaflet.markercluster/dist/MarkerCluster.Default.css")
@CssImport("leaflet.markercluster/dist/MarkerCluster.css")
@CssImport("leaflet-draw/dist/leaflet.draw.css")
@CssImport("./leaflet/leaflet-custom.css")
@CssImport("leaflet-mouse-position/src/L.Control.MousePosition.css")

public class LMap extends Component implements HasSize, HasStyle, HasComponents
{
	private static final String CLIENT_MAP = "this.map";
	private static final String CLIENT_COMPONENTS = "this.components";
	// Were the layer Groups will be stored under the hood.
	private static final String CLIENT_LAYER_GROUPS = "this.layerGroups";
	private static final String CLIENT_TILE_LAYER = "this.tilelayer";
	private final Div divMap = new Div();
	private LCenter center;
	private final List<LComponent> components = new ArrayList<>();
	private final List<LLayerGroup> layerGroups = new ArrayList<>();
	
	public LMap()
	{
		// Default constructor
		this.setFixZIndexEnabled(true);
		
		this.divMap.setSizeFull();
		this.add(this.divMap);
		
		// bind map to div
		this.getElement().executeJs(CLIENT_MAP + "="
			+ "new L.map(this.getElementsByTagName('div')[0]);");
		this.getElement().executeJs(CLIENT_COMPONENTS + "="
			+ "new Array();");
		this.getElement().executeJs(CLIENT_LAYER_GROUPS + "="
			+ "new Array();");
		
		// display map coordinates of mouse position
		this.enableMousePosition();

	}
	
	public LMap(final double lat, final double lon, final int zoom)
	{
		this();
		
		this.setCenter(new LCenter(lat, lon, zoom));
		this.setViewPoint(this.center);
	}
	
	public LMap(final double lat, final double lon, final int zoom, final LTileLayer tileLayer)
	{
		this(lat, lon, zoom);
		this.setTileLayer(tileLayer);
	}
	
	public LMap(final LTileLayer tileLayer)
	{
		this();
		this.setTileLayer(tileLayer);
	}
	
	public void setZoom(final int zoom)
	{
		this.getElement().executeJs(CLIENT_MAP + ".setZoom(" + zoom + ");");
	}
	
	public void setViewPoint(final LCenter viewpoint)
	{
		this.getElement().executeJs(CLIENT_MAP + ".setView("
			+ "[" + viewpoint.getLat() + ", " + viewpoint.getLon() + "], "
			+ viewpoint.getZoom()
			+ ");");
	}
	
	/**
	 * Executes javascript code to display coordinates of mouse position
	 * using the leaflet-mouse-position package https://www.npmjs.com/package/leaflet-mouse-position
	 *
	*/
	public void enableMousePosition() {
		this.getElement().executeJs("L.control.mousePosition({prefix: 'Coordinates: '}).addTo("
			+ CLIENT_MAP + ");");
	}
	
	/**
	 * Uses fitBounds https://leafletjs.com/reference.html#map-fitbounds
	 * to compute zoom level and center coordinates to zoom the map on the given rectangle
	 * @param noPoint : Top let point on the map
	 * @param sePoint : Bottom right point on the map
	 */
	public void centerAndZoom(final LPoint noPoint, final LPoint sePoint)
	{
		this.getElement().executeJs(CLIENT_MAP + ".fitBounds(["
			+ "[" + noPoint.getLat() + ", " + noPoint.getLon() + "],"
			+ "[" + sePoint.getLat() + ", " + sePoint.getLon() + "]"
			+ "]);");
	}
	
	public void setTileLayer(final LTileLayer tl)
	{
		final String removeTileLayerIfPresent = "if (" + CLIENT_TILE_LAYER + ") {"
			+ CLIENT_MAP + ".removeLayer(" + CLIENT_TILE_LAYER + ");"
			+ "}";
		final String addTileLayer = CLIENT_TILE_LAYER + (tl.getWmsLayer() != null ? " = L.tileLayer.wms(" : " = L.tileLayer(")
			+ "'" + escapeEcmaScript(tl.getLink()) + "'"
			+ ",{"
			+ "attribution: '" + escapeEcmaScript(tl.getAttribution()) + "'"
			+ ", maxZoom: " + tl.getMaxZoom()
			+ (tl.getId() != null ? ", id: '" + escapeEcmaScript(tl.getId()) + "'" : "")
			+ (tl.getWmsLayer() != null ? ", layers: '" + escapeEcmaScript(tl.getWmsLayer()) + "'" : "")
			+ "}"
			+ ").addTo(" + CLIENT_MAP + ");";
		this.getElement().executeJs(removeTileLayerIfPresent + "\n" + addTileLayer);
	}
	
	/**
	 * This fixes situations where the leafletmap overlays components like Dialogs
	 * 
	 * @param enabled
	 *            enable or disable the fix
	 */
	protected void setFixZIndexEnabled(final boolean enabled)
	{
		this.getStyle().set("z-index", enabled ? "1" : null);
	}
	
	/**
	 * add Leaflet component(s) to the map
	 */
	public void addLComponents(final LComponent... lComponents)
	{
		this.addLComponents(Arrays.asList(lComponents));
	}
	
	/**
	 * add Leaflet components to the map
	 */
	public void addLComponents(final Collection<LComponent> lComponents)
	{
		for(final LComponent lComponent : lComponents)
		{
			this.addLComponent(lComponent);
		}
	}
	
	protected void addLComponent(final LComponent lComponent)
	{
		this.getComponents().add(lComponent);
		try
		{
			this.getElement().executeJs(lComponent.buildClientJSItems() + "\n"
				+ "item.addTo(" + CLIENT_MAP + ");\n"
				+ (lComponent.getPopup() != null
				? "item.bindPopup('" + escapeEcmaScript(lComponent.getPopup()) + "');\n"
				: "")
				+ CLIENT_COMPONENTS + ".push(item);");
		}
		catch(final JsonProcessingException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * remove Leaflet component(s) to the map
	 */
	public void removeLComponents(final LComponent... lComponents)
	{
		this.removeLComponents(Arrays.asList(lComponents));
	}
	
	/**
	 * remove Leaflet components to the map
	 */
	public void removeLComponents(final Collection<LComponent> lComponents)
	{
		for(final LComponent lComponent : lComponents)
		{
			this.removeLComponent(lComponent);
		}
	}
	
	protected void removeLComponent(final LComponent lComponent)
	{
		final int index = this.components.indexOf(lComponent);
		
		if(index != -1 && this.components.remove(lComponent))
		{
			this.getElement().executeJs("let delItem = " + CLIENT_COMPONENTS + "[" + index + "];\n"
				+ "delItem.remove();\n"
				+ CLIENT_COMPONENTS + ".splice(" + index + ",1);");
		}
	}
	
	public void initDrawControl()
	{
		this.getElement().executeJs(
			"let editableLayers = new L.FeatureGroup();\n"
				+ CLIENT_MAP + ".addLayer(editableLayers);\n"
				+ "     var drawControl = new L.Control.Draw({\n"
				+ "         edit: {\n"
				+ "             featureGroup: editableLayers\n"
				+ "         },\n"
				+ "			draw: {\n"
				+ "    			rectangle: { showArea: false }, \n"
				+ "			}"
				+ "     });\n"
				+ CLIENT_MAP + ".addControl(drawControl);"
				+ CLIENT_MAP + ".on(L.Draw.Event.CREATED, function (e) {\n"
				+ "        var type = e.layerType,\n"
				+ "            layer = e.layer;\n"
				+ "    \n"
				+ "        if (type === 'marker') {\n"
				+ "            layer.bindPopup('A popup!');\n"
				+ "        }\n"
				+ "    \n"
				+ "        editableLayers.addLayer(layer);\n"
				+ "    });"
			
		);
		// we disable rectangle showArea (rectangle: { showArea: false }) to avoid running code with bug (in leaflet.draw)
		// https://stackoverflow.com/questions/57433144/leaflet-draw-on-rectangle-draw-it-throws-error
	}
	
	public void addLLayerGroup(final LLayerGroup lLayerGroup)
	{
		this.layerGroups.add(lLayerGroup);
		try
		{
			this.getElement().executeJs(lLayerGroup.buildClientJSItems() + "\n"
				+ CLIENT_MAP + ".addLayer(item);\n"
				+ CLIENT_LAYER_GROUPS + ".push(item);");
		}
		catch(final JsonProcessingException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public void removeLLayerGroup(final LLayerGroup lLayerGroup)
	{
		final int index = this.layerGroups.indexOf(lLayerGroup);
		
		if(index != -1 && this.layerGroups.remove(lLayerGroup))
		{
			this.getElement().executeJs("let delItem = " + CLIENT_LAYER_GROUPS + "[" + index + "];\n"
				+ CLIENT_MAP + ".removeLayer(delItem);\n"
				+ CLIENT_LAYER_GROUPS + ".splice(" + index + ",1);");
		}
	}
	
	/**
	 * Returns a new component list
	 */
	public List<LComponent> getComponents()
	{
		return this.components;
	}
	
	
	
	public LCenter getCenter()
	{
		return this.center;
	}
	
	/**
	 * Starting Point of the map with latitude, longitude and zoom level
	 */
	public void setCenter(final LCenter start)
	{
		this.center = start;
		this.setViewPoint(start);
	}
	
	public Div getDivMap()
	{
		return this.divMap;
	}
	
	@ClientCallable
	protected void onMarkerClick(final String tag)
	{
		ComponentUtil.fireEvent(this, new MarkerClickEvent(this, true, tag));
	}
	
	public Registration addMarkerClickListener(final ComponentEventListener<MarkerClickEvent> listener)
	{
		return ComponentUtil.addListener(this, MarkerClickEvent.class, listener);
	}
	
	public class MarkerClickEvent extends ComponentEvent<LMap>
	{
		private final String tag;
		
		public MarkerClickEvent(final LMap source, final boolean fromClient, final String tag)
		{
			super(source, fromClient);
			this.tag = tag;
		}
		
		public String getTag()
		{
			return this.tag;
		}
	}
	
	@Override
	protected void onAttach(final AttachEvent attachEvent)
	{
		// https://stackoverflow.com/q/53879753
		this.getElement().executeJs("let tempMap = " + CLIENT_MAP + "\n"
			+ "setTimeout(function () { tempMap.invalidateSize(true); }, 100);");
	}
}
