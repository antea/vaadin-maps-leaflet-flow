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



import static java.lang.Long.parseLong;
import static org.apache.commons.text.StringEscapeUtils.escapeEcmaScript;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

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
import software.xdev.vaadin.maps.leaflet.flow.data.LMarker;
import software.xdev.vaadin.maps.leaflet.flow.data.LPoint;
import software.xdev.vaadin.maps.leaflet.flow.data.LPolyline;
import software.xdev.vaadin.maps.leaflet.flow.data.LRectangle;
import software.xdev.vaadin.maps.leaflet.flow.data.LTileLayer;


@NpmPackage(value = "leaflet", version = "1.8.0")
@NpmPackage(value = "leaflet.browser.print", version = "2.0.2")
@NpmPackage(value = "leaflet.markercluster", version = "1.4.1")
@NpmPackage(value = "@geoman-io/leaflet-geoman-free", version = "2.14.2")
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
@CssImport("@geoman-io/leaflet-geoman-free/dist/leaflet-geoman.css")
@CssImport("./leaflet/leaflet-custom.css")
@CssImport("leaflet-mouse-position/src/L.Control.MousePosition.css")

public class LMap extends Component implements HasSize, HasStyle, HasComponents
{
	private static final String CLIENT_MAP = "this.map";
	private static final String CLIENT_COMPONENTS = "this.components";
	// Were the layer Groups will be stored under the hood.
	private static final String CLIENT_LAYER_GROUPS = "this.layerGroups";
	private static final String CLIENT_TILE_LAYER = "this.tilelayer";
	private static final String CLIENT_GLOBAL_MCG = "this.markerClusterGroup";
	public static final String CLIENT_ENABLE_MAP_DRAGGING_FUNCTION = "this.enableMapDragging";
	public static final String CLIENT_REMOVE_MARKER_GLOBAL_MCG = "this.removeMarkerFromGlobalClusterGroup";
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
			+ "new L.map(this.getElementsByTagName('div')[0]);\n"
			+ CLIENT_MAP + "._layersMaxZoom = 18;\n"  // why am I doing this: https://github.com/mapbox/mapbox-gl-leaflet/issues/113, it was 19 before and I has some small bugs with marker cluster for some reason, I made it 18 and it worked (think its because its the default)
			+ CLIENT_ENABLE_MAP_DRAGGING_FUNCTION + " = () => "+ CLIENT_MAP +".dragging.enable();\n" // I made this method is because: for some reason when you drag a marker you cannot drag the map after
			+ CLIENT_REMOVE_MARKER_GLOBAL_MCG + " = (layer) => "+ CLIENT_GLOBAL_MCG +".removeLayer(layer);\n"
		);
		this.getElement().executeJs(CLIENT_COMPONENTS + "="
			+ "new Array();");
		this.getElement().executeJs(CLIENT_LAYER_GROUPS + "="
			+ "new Array();");
		this.getElement().executeJs(CLIENT_GLOBAL_MCG + "="
			+ "L.markerClusterGroup();\n"
			+ CLIENT_MAP + ".addLayer(" + CLIENT_GLOBAL_MCG + ");");
		
		// display map coordinates of mouse position
		this.enableMousePosition();
	}
	
	public void setClusterRadius(int clusterRadius)
	{
		// This does not work: this.getElement().executeJs(CLIENT_GLOBAL_MCG + ".options.maxClusterRadius = " + clusterRadius + ";");
		this.getElement().executeJs(CLIENT_MAP + ".removeLayer("+CLIENT_GLOBAL_MCG+");\n"
			+ "let tempMCG = L.markerClusterGroup({maxClusterRadius: " + clusterRadius + "});\n"
			+ CLIENT_GLOBAL_MCG + ".eachLayer(function (layer) {\n"
			+ "  tempMCG.addLayer(layer);\n"
			+ "});\n"
			+ CLIENT_GLOBAL_MCG + "=" + "tempMCG\n"
			+ CLIENT_MAP + ".addLayer(" + CLIENT_GLOBAL_MCG + ");"
		);
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
	public void addLComponents(boolean fireEvent, final LComponent... lComponents)
	{
		this.addLComponents(fireEvent, Arrays.asList(lComponents));
	}
	
	/**
	 * add Leaflet components to the map
	 */
	public void addLComponents(boolean fireEvent, final Collection<LComponent> lComponents)
	{
		for(final LComponent lComponent : lComponents)
		{
			this.addLComponent(fireEvent, lComponent);
		}
	}
	
	// fireEvent: means do we fire a create event? (might be false if we are populating data from the database
	protected void addLComponent(boolean fireEvent, final LComponent lComponent)
	{
		this.getComponents().add(lComponent);
		try
		{
			this.getElement().executeJs(lComponent.buildClientJSItems() + "\n"
				+ (lComponent instanceof LMarker
				? CLIENT_GLOBAL_MCG + ".addLayer(item);\n"
				: "item.addTo(" + CLIENT_MAP + ");\n")
				+ (lComponent.getPopup() != null
				? "item.bindPopup('" + escapeEcmaScript(lComponent.getPopup()) + "');\n"
				: "")
				+ CLIENT_COMPONENTS + ".push(item);\n"
				+ (fireEvent
				? fireCreateEventJSFunction +"(item, this.$server);\n" // this will set the dbId of the layer in JS
				: "item.dbId = '" + lComponent.getId().toString() + "';\n") // here we do not send the create event, but we need to set the id to track it.
				+ setupEventsJSFunction +"(item, this.$server);\n");
		}
		catch(final JsonProcessingException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * remove Leaflet component(s) to the map
	 */
	public void removeLComponents(boolean fireEvent, final LComponent... lComponents)
	{
		this.removeLComponents(fireEvent, Arrays.asList(lComponents));
	}
	
	/**
	 * remove Leaflet components to the map
	 */
	public void removeLComponents(boolean fireEvent, final Collection<LComponent> lComponents)
	{
		for(final LComponent lComponent : lComponents)
		{
			this.removeLComponent(fireEvent, lComponent);
		}
	}
	
	protected void removeLComponent(boolean fireEvent, final LComponent lComponent)
	{
		final int index = this.components.indexOf(lComponent);
		
		if(index != -1 && this.components.remove(lComponent))
		{
			this.getElement().executeJs("let delItem = " + CLIENT_COMPONENTS + "[" + index + "];\n"
				+ (lComponent instanceof LMarker
				? CLIENT_GLOBAL_MCG + ".removeLayer(delItem);\n"
				: "delItem.remove();\n")
				+ CLIENT_COMPONENTS + ".splice(" + index + ",1);\n"
				+ (fireEvent
				? fireDeleteEventJSFunction +"(delItem, this.$server);\n"
				: ""));
		}
	}
	
	
	// use it like this: fireCreateEventJSFunction+"(layer);\n"
	private static final String fireCreateEventJSFunction =
		"const fireCreateEventJSFunction = async (layer, vaadinServer) => {\n"
			+ "	 if(layer.constructor === L.Marker) {\n"
			+ "     let pos = layer.getLatLng();\n"
			+ "     let id = await vaadinServer.fireCreateMarkerEvent([pos.lat, pos.lng]);\n"
			+ "     layer.dbId = id;\n"
			+ "  } else if(layer.constructor === L.Rectangle){\n" // rectangle else-if has to be before Polyline because rectangle extends polyline. Otherwise, if layer is a rectangle it will activate the polyline if statement path.
			+ "     let nw = layer.getBounds().getNorthWest();\n"
			+ "     let se = layer.getBounds().getSouthEast();\n"
			+ "     let id = await vaadinServer.fireCreateRectangleEvent([nw.lat, nw.lng], [se.lat, se.lng]);\n"
			+ "     layer.dbId = id;\n"
			+ "  } else if(layer.constructor === L.Polyline){\n"
			+ "     let verts = layer.getLatLngs();\n"
			+ "     let vertsLatLng = verts.map((pos) => [pos.lat, pos.lng]);\n"
			+ "     let id = await vaadinServer.fireCreatePolylineEvent(...vertsLatLng);\n"
			+ "     layer.dbId = id;\n"
			+ "  }\n"
			+ "}\n"
			+ "fireCreateEventJSFunction"; // use it like this in java: fireCreateEventJSFunction+"(layer);\n"
	
	// use it like this: fireDeleteEventJSFunction+"(layer);\n"
	private static final String fireDeleteEventJSFunction =
		"const fireDeleteEventJSFunction = async (layer, vaadinServer) => {\n"
			+ "	 if(layer.constructor === L.Marker) {\n"
			+ "     let pos = layer.getLatLng();\n"
			+ "     vaadinServer.fireDeleteMarkerEvent(layer.dbId, [pos.lat, pos.lng]);\n"
			+ "  } else if(layer.constructor === L.Rectangle){\n"
			+ "     let nw = layer.getBounds().getNorthWest();\n"
			+ "     let se = layer.getBounds().getSouthEast();\n"
			+ "     vaadinServer.fireDeleteRectangleEvent(layer.dbId, [nw.lat, nw.lng], [se.lat, se.lng]);\n"
			+ "  } else if(layer.constructor === L.Polyline){\n"
			+ "     let verts = layer.getLatLngs();\n"
			+ "     let vertsLatLng = verts.map((pos) => [pos.lat, pos.lng]);\n"
			+ "     vaadinServer.fireDeletePolylineEvent(layer.dbId, ...vertsLatLng);\n"
			+ "  }\n"
			+ "}\n"
			+ "fireDeleteEventJSFunction"; // use it like this in java: fireDeleteEventJSFunction+"(layer);\n"
	
	// use it like this: setupEventsJSFunction+"(layer);\n"
	private static final String setupEventsJSFunction =
		"const setupEventsJSFunction = async (layer, vaadinServer) => {\n"
			+ "	 if(layer.constructor === L.Marker) {\n"
			+ "     layer.on('pm:edit', (e) => {\n"
			+ "       "+CLIENT_ENABLE_MAP_DRAGGING_FUNCTION+"();\n" // (declared in the constructor of LMap) I made this method is because: for some reason when you drag a marker you cannot drag the map after
			+ "       let pos = e.layer.getLatLng();\n"
			+ "       vaadinServer.fireSaveMarkerEvent(e.layer.dbId, [pos.lat, pos.lng]);\n"
			+ "     });"
			+ "     layer.on('pm:remove', (e) => {\n"
			+ "       "+CLIENT_REMOVE_MARKER_GLOBAL_MCG+"(e.layer);\n"
			+ "       let pos = e.layer.getLatLng();\n"
			+ "       vaadinServer.fireDeleteMarkerEvent(e.layer.dbId, [pos.lat, pos.lng]);\n"
			+ "     });\n"
			+ "  } else if(layer.constructor === L.Rectangle){\n" // rectangle else-if has to be before Polyline because rectangle extends polyline. Otherwise, if layer is a rectangle it will activate the polyline if statement path.
			+ "     layer.on('pm:edit', (e) => {\n"
			+ "       let nw = e.layer.getBounds().getNorthWest();\n"
			+ "       let se = e.layer.getBounds().getSouthEast();\n"
			+ "       vaadinServer.fireSaveRectangleEvent(e.layer.dbId, [nw.lat, nw.lng], [se.lat, se.lng]);\n"
			+ "     });"
			+ "     layer.on('pm:remove', (e) => {\n"
			+ "       let nw = e.layer.getBounds().getNorthWest();\n"
			+ "       let se = e.layer.getBounds().getSouthEast();\n"
			+ "       vaadinServer.fireDeleteRectangleEvent(e.layer.dbId, [nw.lat, nw.lng], [se.lat, se.lng]);\n"
			+ "     });\n"
			+ "  } else if(layer.constructor === L.Polyline){\n"
			+ "     layer.on('pm:edit', (e) => {\n"
			+ "       let verts = e.layer.getLatLngs();\n"
			+ "       let vertsLatLng = verts.map((pos) => [pos.lat, pos.lng]);\n"
			+ "       vaadinServer.fireSavePolylineEvent(e.layer.dbId, ...vertsLatLng);\n"
			+ "     });"
			+ "     layer.on('pm:remove', (e) => {\n"
			+ "       let verts = e.layer.getLatLngs();\n"
			+ "       let vertsLatLng = verts.map((pos) => [pos.lat, pos.lng]);\n"
			+ "       vaadinServer.fireDeletePolylineEvent(e.layer.dbId, ...vertsLatLng);\n"
			+ "     });\n"
			+ "  }\n"
			+ "}\n"
			+ "setupEventsJSFunction"; // use it like this in java: setupEventsJSFunction+"(layer);\n"
	
	// adds geoman and all the events needed
	public void initGeomanControls()
	{
		
		this.getElement().executeJs(
			CLIENT_MAP + ".pm.addControls({  \n"
				+ "  position: 'topleft',  \n"
				//+ "  drawCircle: false,  \n"
				+ "}); "
				+ "let addMarkerToClusterGroup = (layer) => "+ CLIENT_GLOBAL_MCG +".addLayer(layer);\n"// I did this because this.markerClusterGroup is undefined in the callback bellow (because it's an event listener)
				+ "const vaadinServer = this.$server;\n"
				+ CLIENT_MAP + ".on('pm:create', async (e) => {\n"
				+ "	 if(e.layer.constructor === L.Marker) {\n"
				+ "     e.layer.remove();\n" // so its not added to the map in addition to being added to the cluster
				+ "     addMarkerToClusterGroup(e.layer);\n"
				+ "  }\n"
				+    setupEventsJSFunction + "(e.layer, vaadinServer);\n"
				+    fireCreateEventJSFunction + "(e.layer, vaadinServer);\n"
				+ "});"
		);
		
		this.getElement().executeJs(
			"const scale = 100 / window.devicePixelRatio;"
				+ "L.control.browserPrint({position: 'topleft', title: 'Print Map'}).addTo(" + CLIENT_MAP + ");\n"
				+ CLIENT_MAP + ".on('browser-pre-print', function(e) {\n"
				+ "document.querySelector('body').style.zoom = `${scale}%`;\n"
				+ "});\n"
				+ CLIENT_MAP + ".on('browser-print-end', function(e) {\n"
				+ "document.querySelector('body').style.zoom = '100%';\n"
				+ "});\n"
				+ CLIENT_MAP + ".on('browser-print-cancel', function(e) {\n"
				+ "document.querySelector('body').style.zoom = '100%';\n"
				+ "});\n"
		);
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
	
	// Events
	
	public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
		ComponentEventListener<T> listener) {
		return getEventBus().addListener(eventType, listener);
	}
	
	public static abstract class MarkerEvent extends ComponentEvent<LMap> {
		private LMarker marker;
		
		protected MarkerEvent(LMap source, LMarker marker) {
			super(source, false); // fromClient - true if the event originated from the client side, false otherwise
			// its false because we will fire the even programmatically with fireEvent() method.
			this.marker = marker;
		}
		
		public LMarker getMarker() {
			return marker;
		}
	}
	
	public static class SaveMarkerEvent extends MarkerEvent {
		SaveMarkerEvent(LMap source, LMarker marker) {
			super(source, marker);
		}
	}
	
	public static class DeleteMarkerEvent extends MarkerEvent {
		DeleteMarkerEvent(LMap source, LMarker marker) {
			super(source, marker);
		}
	}
	
	public static abstract class RectangleEvent extends ComponentEvent<LMap> {
		private LRectangle rectangle;
		
		protected RectangleEvent(LMap source, LRectangle rectangle) {
			super(source, false);
			this.rectangle = rectangle;
		}
		
		public LRectangle getRectangle() {
			return rectangle;
		}
	}
	
	public static class SaveRectangleEvent extends RectangleEvent {
		SaveRectangleEvent(LMap source, LRectangle rectangle) {
			super(source, rectangle);
		}
	}
	
	public static class DeleteRectangleEvent extends RectangleEvent {
		DeleteRectangleEvent(LMap source, LRectangle rectangle) {
			super(source, rectangle);
		}
	}
	
	public static abstract class PolylineEvent extends ComponentEvent<LMap> {
		private LPolyline polyline;
		
		protected PolylineEvent(LMap source, LPolyline polyline) {
			super(source, false);
			this.polyline = polyline;
		}
		
		public LPolyline getPolyline() {
			return polyline;
		}
	}
	
	public static class SavePolylineEvent extends PolylineEvent {
		SavePolylineEvent(LMap source, LPolyline polyline) {
			super(source, polyline);
		}
	}
	
	public static class DeletePolylineEvent extends PolylineEvent {
		DeletePolylineEvent(LMap source, LPolyline polyline) {
			super(source, polyline);
		}
	}
	
	// make this method return the ID, so it can be stored in JS: https://github.com/geoman-io/leaflet-geoman/issues/248#issuecomment-343852257
	
	@ClientCallable
	private String fireCreatePolylineEvent(final Double[]... points){
		List<LPoint> lPoints = extractLPoints(points);
		LPolyline polyline = new LPolyline(lPoints);
		fireEvent(new SavePolylineEvent(this, polyline));
		return polyline.getId().toString();
	}
	
	@ClientCallable
	private void fireSavePolylineEvent(final String id, final Double[]... points){
		List<LPoint> lPoints = extractLPoints(points);
		LPolyline polyline = new LPolyline(lPoints);
		polyline.setId(UUID.fromString(id));
		fireEvent(new SavePolylineEvent(this, polyline));
	}
	
	@ClientCallable
	private void fireDeletePolylineEvent(final String id, final Double[]... points){
		List<LPoint> lPoints = extractLPoints(points);
		LPolyline polyline = new LPolyline(lPoints);
		polyline.setId(UUID.fromString(id));
		fireEvent(new DeletePolylineEvent(this, polyline));
	}
	
	@ClientCallable
	private String fireCreateMarkerEvent(final Double[] point){
		LPoint lPoint = extractLPoint(point);
		LMarker marker = new LMarker(lPoint);
		fireEvent(new SaveMarkerEvent(this, marker));
		return marker.getId().toString();
	}
	
	@ClientCallable
	private void fireSaveMarkerEvent(final String id, final Double[] point){
		LPoint lPoint = extractLPoint(point);
		LMarker marker = new LMarker(lPoint);
		marker.setId(UUID.fromString(id));
		fireEvent(new SaveMarkerEvent(this, marker));
	}
	
	@ClientCallable
	private void fireDeleteMarkerEvent(final String id, final Double[] point){
		LPoint lPoint = extractLPoint(point);
		LMarker marker = new LMarker(lPoint);
		marker.setId(UUID.fromString(id));
		fireEvent(new DeleteMarkerEvent(this, marker));
	}
	
	@ClientCallable
	private String fireCreateRectangleEvent(final Double[] noPoint, final Double[] sePoint){
		LPoint nwLPoint = extractLPoint(noPoint);
		LPoint seLPoint = extractLPoint(sePoint);
		LRectangle rectangle = new LRectangle(nwLPoint, seLPoint);
		fireEvent(new SaveRectangleEvent(this, rectangle));
		return rectangle.getId().toString();
	}
	
	@ClientCallable
	private void fireSaveRectangleEvent(final String id, final Double[] noPoint, final Double[] sePoint){
		LPoint nwLPoint = extractLPoint(noPoint);
		LPoint seLPoint = extractLPoint(sePoint);
		LRectangle rectangle = new LRectangle(nwLPoint, seLPoint);
		rectangle.setId(UUID.fromString(id));
		fireEvent(new SaveRectangleEvent(this, rectangle));
	}
	
	@ClientCallable
	private void fireDeleteRectangleEvent(final String id, final Double[] noPoint, final Double[] sePoint){
		LPoint nwLPoint = extractLPoint(noPoint);
		LPoint seLPoint = extractLPoint(sePoint);
		LRectangle rectangle = new LRectangle(nwLPoint, seLPoint);
		rectangle.setId(UUID.fromString(id));
		fireEvent(new DeleteRectangleEvent(this, rectangle));
	}
	
	private long stringToLong(String input){
		return parseLong(input);
	}
	
	private List<LPoint> extractLPoints(final Double[][] points)
	{
		List<LPoint> lPoints = new ArrayList<>();
		for(Double[] point : points)
		{
			LPoint lPoint = extractLPoint(point);
			lPoints.add(lPoint);
		}
		return lPoints;
	}
	
	private LPoint extractLPoint(final Double[] point)
	{
		if(point.length != 2){
			throw new IllegalArgumentException("point array has to have length of 2");
		}
		LPoint lPoint = new LPoint(point[0], point[1]);
		return lPoint;
	}
}
