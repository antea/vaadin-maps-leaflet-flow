package tech.antea;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import software.xdev.vaadin.maps.leaflet.flow.LMap;
import software.xdev.vaadin.maps.leaflet.flow.data.*;

import java.util.Arrays;

/**
 * Main View for leaflet add-on demo
 */
@Route
public class MainView extends VerticalLayout {
    private LMap map;

    public MainView() {
        // set map center to coordinates of Montreal Antea office
        this.map = new LMap(45.45235633938714, -73.4676260164890, 17);

        LMarker anteaMarker = new LMarker(45.45235633938714, -73.46762601648902);
        anteaMarker.setPopup("Antea Montreal Office");

        // set custom icon
        anteaMarker.setIcon(new LIcon("https://antea.tech/wp-content/uploads/2022/07/antea-logo-1.png"));
        this.map.setTileLayer(LTileLayer.DEFAULT_OPENSTREETMAP_TILE);


        var circle = new LCircle(45.45235633938714, -73.46762601648902, 900);
        var poly = new LPolygon(
            Arrays.asList(
                new LPoint(45.45235633938714, -73.46762601648902),
                new LPoint(45.6, -73),
                new LPoint(45.7, -73)
            )
        );

        // adjust polygon properties
        poly.setFill(true);
        poly.setFillOpacity(1.0);
        poly.setFillColor("#3366ff");
        poly.setStroke(false);
        poly.setPopup("This is a polygon");


        // add map components to map
        this.map.addLComponents(anteaMarker, circle, poly);
        this.map.setSizeFull();

        add(this.map);
        this.setSizeFull();
    }




}
