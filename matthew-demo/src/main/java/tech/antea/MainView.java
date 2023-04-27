package tech.antea;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import software.xdev.vaadin.maps.leaflet.flow.LMap;
import software.xdev.vaadin.maps.leaflet.flow.data.LMarker;
import software.xdev.vaadin.maps.leaflet.flow.data.LTileLayer;

/**
 * The main view contains a button and a click listener.
 */
@Route
public class MainView extends VerticalLayout {
    private LMap map;

    public MainView() {
        Button button = new Button("Click me",
                event -> Notification.show("Clicked!"));
        // coordinates of Montreal Antea office
        // 45.45235633938714, -73.46762601648902
        this.map = new LMap(45.45235633938714, -73.4676260164890, 17);
        final LMarker anteaMarker = new LMarker(45.45235633938714, -73.46762601648902);
        anteaMarker.setPopup("Antea Montreal Office");
        this.map.setTileLayer(LTileLayer.DEFAULT_OPENSTREETMAP_TILE);
        this.map.addLComponents(anteaMarker);
        this.map.setSizeFull();
        add(this.map, button);
        this.setSizeFull();
    }




}
