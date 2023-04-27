package software.xdev.vaadin.maps.leaflet.flow.demo;

import java.util.Arrays;
import java.util.List;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.AnchorTarget;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import com.vaadin.flow.router.RouteAlias;
import software.xdev.vaadin.maps.leaflet.flow.LMap;
import software.xdev.vaadin.maps.leaflet.flow.data.LCenter;
import software.xdev.vaadin.maps.leaflet.flow.data.LCircle;
import software.xdev.vaadin.maps.leaflet.flow.data.LComponent;
import software.xdev.vaadin.maps.leaflet.flow.data.LDivIcon;
import software.xdev.vaadin.maps.leaflet.flow.data.LIcon;
import software.xdev.vaadin.maps.leaflet.flow.data.LMarker;
import software.xdev.vaadin.maps.leaflet.flow.data.LPoint;
import software.xdev.vaadin.maps.leaflet.flow.data.LPolygon;
import software.xdev.vaadin.maps.leaflet.flow.data.LTileLayer;


@Route("")
public class LeafletView extends VerticalLayout
{
	private boolean viewLunch = false;
	
	/**
	 * UI-Components
	 */
	private final Button btnLunch = new Button("Where do XDEV employees go for lunch?");
	private final Button btnCenter = new Button("Center on Caribbean");
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
	
	public LeafletView()
	{
		this.initMapComponents();
		
		this.btnLunch.addClickListener(this::btnLunchClick);
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
			}));
		this.btnCenter.addClickListener(e-> map.centerAndZoom(new LPoint(14.467727, -61.69703), new LPoint(16.33426,-60.921676)));
		this.add(this.map, hlButtonContainer);
		this.setSizeFull();
	}
	
	private void btnLunchClick(final ClickEvent<Button> event)
	{
		this.viewLunch = !this.viewLunch;
		
		final List<LComponent> normalComponents = Arrays.asList(this.markerRathaus, this.markerZob);
		final List<LComponent> lunchComponents = Arrays.asList(
			this.circleRange,
			this.markerPizza,
			this.markerKebab,
			this.markerAsia,
			this.markerGreek,
			this.markerBakery,
			this.markerLeberkaese);
		
		this.map.setViewPoint(new LCenter(49.675126, 12.160733, this.viewLunch ? 16 : 17));
		this.map.removeLComponents(this.viewLunch ? normalComponents : lunchComponents);
		this.map.addLComponents(this.viewLunch ? lunchComponents : normalComponents);
		
		this.btnLunch.setText(this.viewLunch ? "Go back to the normal view" : "Where do XDEV employees go for lunch?");
	}
	
	private void initMapComponents()
	{
		this.markerZob = new LMarker(49.673470, 12.160108, "ZoB");
		this.markerZob.setPopup("Central bus station");
		
		final LMarker markerXDev = new LMarker(49.675806677512824, 12.160990185846394);
		final LIcon xDevLogo = new LIcon("frontend/leaflet/testImage.png");
		
		xDevLogo.setIconSize(100, 20);
		xDevLogo.setIconAnchor(50, 0);
		markerXDev.setPopup("<a href='https://xdev.software/en' target='" + AnchorTarget.BLANK.getValue() + "'>XDEV Software GmbH</a>");
		markerXDev.setIcon(xDevLogo);
		
		final LMarker markerInfo = new LMarker(49.674095, 12.162257);
		final LDivIcon div = new LDivIcon(
			"<p><center><b>Welcome to Weiden in der Oberpfalz!</b></center></p><p>This demo shows you different markers,<br> popups, polygons and other stuff</p>");
		
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
		
		this.markerRathaus = new LMarker(49.675519, 12.163868, "L-22556");
		this.markerRathaus.setPopup("Old Town Hall");
		
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
		
		this.map.addLComponents(
			markerXDev,
			markerInfo,
			this.markerZob,
			polygonNoc,
			this.markerRathaus);
	}
}
