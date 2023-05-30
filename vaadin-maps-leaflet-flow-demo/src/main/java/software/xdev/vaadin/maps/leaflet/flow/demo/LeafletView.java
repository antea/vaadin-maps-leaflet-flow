package software.xdev.vaadin.maps.leaflet.flow.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.AnchorTarget;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import software.xdev.vaadin.data.entity.Marker;
import software.xdev.vaadin.data.entity.Polyline;
import software.xdev.vaadin.data.entity.Rectangle;
import software.xdev.vaadin.data.service.DbService;
import software.xdev.vaadin.maps.leaflet.flow.LMap;
import software.xdev.vaadin.maps.leaflet.flow.data.LCenter;
import software.xdev.vaadin.maps.leaflet.flow.data.LCircle;
import software.xdev.vaadin.maps.leaflet.flow.data.LComponent;
import software.xdev.vaadin.maps.leaflet.flow.data.LDivIcon;
import software.xdev.vaadin.maps.leaflet.flow.data.LIcon;
import software.xdev.vaadin.maps.leaflet.flow.data.LImageOverlay;
import software.xdev.vaadin.maps.leaflet.flow.data.LMarker;
import software.xdev.vaadin.maps.leaflet.flow.data.LPoint;
import software.xdev.vaadin.maps.leaflet.flow.data.LPolygon;
import software.xdev.vaadin.maps.leaflet.flow.data.LPolyline;
import software.xdev.vaadin.maps.leaflet.flow.data.LRectangle;
import software.xdev.vaadin.maps.leaflet.flow.data.LTileLayer;



@Route("")
public class LeafletView extends VerticalLayout
{
	private boolean viewLunch = false;
	private DbService dbService;
	
	/**
	 * UI-Components
	 */
	private final Button btnLunch = new Button("Where do XDEV employees go for lunch?");
	private final Button btnCenter = new Button("Center on Caribbean");
	private final Button clusterButton = new Button("Configure");
	private final Button btnAddUsaMap = new Button("Add USA map");
	private final Button btnAddPID = new Button("Add PID");
	private final TextField clusterPixels = new TextField();
	private LMap map;
	
	private LMarker markerZob;
	private LMarker markerRathaus;
	
	private LCircle circleRange;
	private LMarker markerPizza;
	private LMarker markerKebab;
	private LMarker markerAsia;
	private LMarker markerGreek;
	private LMarker markerBakery;
	private LMarker markerLeberkaese;
	private LRectangle testRect;
	private LPolyline testPolyline;
	
	private List<LComponent> normalListComponents;
	
	private List<LComponent> lunchListComponents;
	
	public LeafletView(DbService service)
	{
		this.dbService = service;
		this.initMapComponents();
		
		this.btnLunch.addClickListener(this::btnLunchClick);
		this.clusterButton.addClickListener(this::clusterButtonClick);
		this.btnAddUsaMap.addClickListener(this::btnUsaMapClick);
		this.btnAddPID.addClickListener(this::btnPIDClick);
		
		// TextField used to configure marker cluster radius
		this.clusterPixels.setPlaceholder("Cluster Radius (px)");
		// Event listener prevents non numerical inputs
		this.clusterPixels.addValueChangeListener(event -> {
			try{
				String value = this.clusterPixels.getValue();
				char c = value.charAt(value.length() - 1);
				// Checking if new value is not a number
				if (value.length() == 1 && c == '0') {
					Notification.show("Cluster Radius must be at least 1 pixel.");
					this.clusterPixels.setValue("");
				} else if (!Character.isDigit(c) || Integer.parseInt(value) > 100) {
					Notification.show("Cluster Radius must be an integer (1-100).");
					this.clusterPixels.setValue(value.substring(0, value.length() - 1));
				}
			} catch (StringIndexOutOfBoundsException e) {
		
			}
		});
		// Value change mode -> ensures that the event is called for each change
		this.clusterPixels.setValueChangeMode(ValueChangeMode.EAGER);
		
		final HorizontalLayout clusterConfiguration = new HorizontalLayout();
		clusterConfiguration.add(
			this.clusterPixels,
			this.clusterButton
		);
		
		final HorizontalLayout hlButtonContainer = new HorizontalLayout();
		hlButtonContainer.setWidthFull();
		hlButtonContainer.setJustifyContentMode(JustifyContentMode.BETWEEN);
		hlButtonContainer.setPadding(false);
		hlButtonContainer.setSpacing(false);
		hlButtonContainer.add(
			this.btnLunch,
			this.btnCenter,
			new Button("Open dialog over map", ev ->
			{
				final Icon icoClose = VaadinIcon.CLOSE.create();
				
				final Dialog dialog = new Dialog(icoClose);
				dialog.setWidth("50vw");
				dialog.setHeight("50vh");
				dialog.open();
				
				icoClose.addClickListener(iev -> dialog.close());
			}),
			btnAddUsaMap,btnAddPID,
			clusterConfiguration
		);
		this.btnCenter.addClickListener(e-> map.centerAndZoom(new LPoint(14.467727, -61.69703), new LPoint(16.33426,-60.921676)));
		this.add(this.map, hlButtonContainer);
		this.setSizeFull();
	}
	
	private void btnUsaMapClick(final ClickEvent<Button> event) {
		try {
			this.map.addLComponents(true, new LImageOverlay("frontend/leaflet/cb17-100-median-age.svg"));
			Notification.show("USA map added");
		}
		
		catch (final Exception e) {
			Notification.show(e.toString());
		}
	}
	
	private void btnPIDClick(final ClickEvent<Button> event) {
		try {
			this.map.addLComponents(true, new LImageOverlay("frontend/leaflet/0020-GD-A-62906.svg"));
			Notification.show("PID added");
		}
		
		catch (final Exception e) {
			Notification.show(e.toString());
		}
	}
	
	// Event listener to change marker cluster radius
	private void clusterButtonClick(final ClickEvent<Button> event) {
		if (this.clusterPixels.getValue().isEmpty()){
			Notification.show("Please enter a value.");
		} else {
			// Layout rerendered with a new cluster radius.
			int clusterRadius = Integer.parseInt(this.clusterPixels.getValue());
			// this.normalLayerGroup.setClusterRadius(clusterRadius);
			// this.lunchLayerGroup.setClusterRadius(clusterRadius);
			// this.map.removeLLayerGroup(this.viewLunch ? this.lunchLayerGroup : this.normalLayerGroup );
			// this.map.addLLayerGroup(this.viewLunch ? this.lunchLayerGroup : this.normalLayerGroup);
			
			// Global map marker cluster group
			this.map.setClusterRadius(clusterRadius);
		}
	}
	
	private void btnLunchClick(final ClickEvent<Button> event)
	{
		this.viewLunch = !this.viewLunch;
		
		this.map.setViewPoint(new LCenter(49.675126, 12.160733, this.viewLunch ? 16 : 17));
		this.map.removeLComponents(true, this.viewLunch ? this.normalListComponents : this.lunchListComponents);
		this.map.addLComponents(true, this.viewLunch ? this.lunchListComponents : this.normalListComponents);
		
		if(this.viewLunch) {
			this.map.addLComponents(false, this.circleRange);
			this.map.addLComponents(true, this.testRect, this.testPolyline);
		} else {
			this.map.removeLComponents(false, this.circleRange);
			this.map.removeLComponents(true, this.testRect, this.testPolyline);
		}
		
		this.btnLunch.setText(this.viewLunch ? "Go back to the normal view" : "Where do XDEV employees go for lunch?");
	}
	
	private void initMapComponents()
	{
		this.testRect = new LRectangle(new LPoint(49.675126, 12.160733), new LPoint(49.67599, 12.16993));
		
		this.markerZob = new LMarker(49.673470, 12.160108, "ZoB");
		this.markerZob.setPopup("Central bus station");
		
		final LMarker markerWithDifferentIcon = new LMarker(49.675806677512824, 12.160990185846394);
		final LIcon testIcon = new LIcon("frontend/leaflet/testImage.png");
		
		testIcon.setIconSize(32, 32);
		testIcon.setIconAnchor(16, 0);
		markerWithDifferentIcon.setPopup("<a href='https://vaadin.com/docs/v23/tutorial/installing-and-offline-pwa' target='" + AnchorTarget.BLANK.getValue() + "'>Custom Icon</a>");
		markerWithDifferentIcon.setIcon(testIcon);
		
		final LMarker markerInfo = new LMarker(49.674095, 12.162257);
		final LDivIcon div = new LDivIcon(
			"<p><center><b>Welcome to Weiden in der Oberpfalz!</b></center></p><p>This demo shows you different markers,<br> popups, polygons and other stuff</p>");
		
		// maybe find a way to disable icon size
		// so that buildClientJSItems in LMarker does not create it with a default value that is too small or too big
		// forcing us to do this vvv
		div.setIconSize(220, 80);
		markerInfo.setDivIcon(div);
		
		final LPolygon polygonNoc = new LPolygon(
			Arrays.asList(
				new LPoint(49.674883, 12.159098),
				new LPoint(49.675719, 12.160248),
				new LPoint(49.676080, 12.159985),
				new LPoint(49.675750, 12.158008),
				new LPoint(49.675306, 12.158499)));
		polygonNoc.setFill(true);
		polygonNoc.setFillColor("#3366ff");
		polygonNoc.setFillOpacity(0.8);
		polygonNoc.setStroke(false);
		polygonNoc.setPopup("NOC-Nordoberpfalz Center");
		
		this.testPolyline = new LPolyline(
			Arrays.asList(
				new LPoint(49.67499, 12.15909),
				new LPoint(49.67599, 12.16024),
				new LPoint(49.67699, 12.15998),
				new LPoint(49.67599, 12.15800),
				new LPoint(49.67599, 12.15849)));
		this.testPolyline.setStrokeColor("#FF0000");
		this.testPolyline.setPopup("custom polyline");
		
		// this.markerRathaus = new LMarker(49.675519, 12.163868, "L-22556");
		// this.markerRathaus.setPopup("Old Town Hall");
		
		this.circleRange = new LCircle(49.675126, 12.160733, 450);
		
		this.markerPizza = new LMarker(49.674413, 12.160925);
		this.markerPizza.setPopup("Pizza!");
		
		this.markerKebab = new LMarker(49.673026, 12.156278);
		this.markerKebab.setPopup("Kebab!");
		
		this.markerAsia = new LMarker(49.675039, 12.162127);
		this.markerAsia.setPopup("Asian Food");
		
		this.markerGreek = new LMarker(49.675126, 12.161899);
		this.markerGreek.setPopup("Greek Food");
		
		this.markerBakery = new LMarker(49.674806, 12.160249);
		this.markerBakery.setPopup("Fresh baked stuff");
		
		this.markerLeberkaese = new LMarker(49.673800, 12.160113);
		this.markerLeberkaese.setPopup("Fast food like Leberkäsesemmeln");
		
		
		this.normalListComponents = Arrays.asList(this.markerBakery, this.markerZob);
		
		this.lunchListComponents = Arrays.asList(
				this.markerPizza,
				this.markerKebab,
				this.markerAsia,
				this.markerGreek,
				this.markerBakery,
				this.markerLeberkaese);
		
		
		this.map = new LMap(49.675126, 12.160733, 17);
		
		//this.map.setTileLayer(LTileLayer.DEFAULT_OPENSTREETMAP_TILE);
		
		LTileLayer wmsTileLayer = new LTileLayer(
			"http://ows.mundialis.de/services/service?",
			"placeHolderAttribution", 18);
		wmsTileLayer.setWmsLayer("TOPO-OSM-WMS");
		
		this.map.setTileLayer(wmsTileLayer);
		
		this.map.setSizeFull();
		// add some logic here for called Markers (token)
		this.map.addMarkerClickListener(ev -> System.out.println(ev.getTag()));
		
		// this.map.addLComponents(true,
		// 	markerWithDifferentIcon,
		// 	markerInfo,
		// 	polygonNoc
		// );

		// query db and add all components to map as LComponents
		this.loadDbComponents();
		
		this.map.initGeomanControls();
		this.map.addListener(LMap.SaveMarkerEvent.class,
			(e) -> {
				var lMarker = e.getMarker();
				dbService.saveMarker(new Marker(lMarker.getId(), lMarker.getLat(), lMarker.getLon()));
			});
		
		this.map.addListener(LMap.SavePolylineEvent.class,
			(e) -> {
				var lPolyline = e.getPolyline();
				dbService.savePolyline( new Polyline(lPolyline.getId(), lPolyline.getList()));
			});
		
		this.map.addListener(LMap.SaveRectangleEvent.class,
			(e) -> {
				var LRect = e.getRectangle();
				dbService.saveRectangle( new Rectangle( LRect.getId(), LRect.getNwPoint().getLat(), LRect.getNwPoint().getLon(), LRect.getSePoin().getLat(), LRect.getSePoin().getLon() ));
			});
		
		this.map.addListener(LMap.DeleteMarkerEvent.class,
			(e) -> {
				var lMarker = e.getMarker();
				dbService.deleteMarker( new Marker(lMarker.getId(), lMarker.getLat(), lMarker.getLon()));
				});
		
		this.map.addListener(LMap.DeletePolylineEvent.class,
			(e) -> {
				var lPolyline = e.getPolyline();
				// get the actual object by id, this is a fix because creating an object through the constructor wasn't deleting properly
				dbService.deletePolyline( dbService.getPolylineById(lPolyline.getId()));
			});
		
		this.map.addListener(LMap.DeleteRectangleEvent.class,
			(e) -> {
				var LRect = e.getRectangle();
				dbService.deleteRectangle( new Rectangle( LRect.getId(), LRect.getNwPoint().getLat(), LRect.getNwPoint().getLon(), LRect.getSePoin().getLat(), LRect.getSePoin().getLon() ));
			});
	}
	
	private void loadDbComponents() {
		var markerList = dbService.findAllMarkers();
		var poylineList = dbService.findAllPolylines();
		var rectList = dbService.findAllRectangles();
		List<LComponent> componentList = new ArrayList<LComponent>();
		
		// take list of markers from db and add them to list as LMarkers
		for (var marker : markerList) {
			componentList.add(new LMarker(marker.getId(), marker.getLat(), marker.getLong()));
		}
		
		// add rect from db to map as LRectangles
		for (var rect : rectList) {
			// convert doubles to LPoint
			var nwPoint = new LPoint(rect.getNwLat(), rect.getNwLong());
			var sePoint = new LPoint(rect.getSeLat(), rect.getSeLong());
			
			componentList.add(new LRectangle(rect.getId(), nwPoint, sePoint));
		}
		
		// add Polylines from db to map as LPolylines
		for (var polyline: poylineList) {
			componentList.add(new LPolyline(polyline.getId(), polyline.getLPoints()));
		}
		
		// add components to map
		this.map.addLComponents(false, componentList);
	}
}
