package tech.antea;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import software.xdev.vaadin.maps.leaflet.flow.LMap;
import software.xdev.vaadin.maps.leaflet.flow.data.LCircle;
import software.xdev.vaadin.maps.leaflet.flow.data.LIcon;
import software.xdev.vaadin.maps.leaflet.flow.data.LMarker;
import software.xdev.vaadin.maps.leaflet.flow.data.LTileLayer;

/**
 * The main view contains a button and a click listener.
 */
@Route
public class MainView extends VerticalLayout {
    private LMap map;

    public MainView() {
//        Button button = new Button("Click me",
//                event -> Notification.show("Clicked!"));

        // coordinates of Montreal Antea office
        // 45.45235633938714, -73.46762601648902
        this.map = new LMap(45.45235633938714, -73.4676260164890, 17);


        final LMarker anteaMarker = new LMarker(45.45235633938714, -73.46762601648902);
        anteaMarker.setPopup("Antea Montreal Office");

        // set custom icon
        anteaMarker.setIcon(new LIcon("https://antea.tech/wp-content/uploads/2022/07/antea-logo-1.png"));
        this.map.setTileLayer(LTileLayer.DEFAULT_OPENSTREETMAP_TILE);


        var circle = new LCircle(45.45235633938714, -73.46762601648902, 900);

        // add map components to map
        this.map.addLComponents(anteaMarker, circle);
        this.map.setSizeFull();

        add(this.map);
        this.setSizeFull();
    }




}
