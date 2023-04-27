package tech.antea;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import software.xdev.vaadin.maps.leaflet.flow.LMap;
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

        this.map = new LMap(49.675126, 12.160733, 17);
        this.map.setTileLayer(LTileLayer.DEFAULT_OPENSTREETMAP_TILE);
        this.map.setSizeFull();
        add(this.map, button);
        this.setSizeFull();
    }




}
