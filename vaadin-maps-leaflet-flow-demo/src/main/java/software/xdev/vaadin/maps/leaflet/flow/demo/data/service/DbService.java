package software.xdev.vaadin.maps.leaflet.flow.demo.data.service;

import org.springframework.stereotype.Service;

import software.xdev.vaadin.maps.leaflet.flow.demo.data.repository.MarkerRepository;
import software.xdev.vaadin.maps.leaflet.flow.demo.data.repository.PolylineRepository;
import software.xdev.vaadin.maps.leaflet.flow.demo.data.repository.RectangleRepository;


@Service
public class DbService {
	private final MarkerRepository markerRepository;
	private final PolylineRepository polylineRepository;
	private final RectangleRepository rectangleRepository;
	
	public DbService(MarkerRepository markerRepository, PolylineRepository polylineRepository, RectangleRepository rectangleRepository) {
		this.markerRepository = markerRepository;
		this.polylineRepository = polylineRepository;
		this.rectangleRepository = rectangleRepository;
	}
	
	
}
