package software.xdev.vaadin.maps.leaflet.flow.demo.data.service;

import java.util.List;

import org.springframework.stereotype.Service;

import software.xdev.vaadin.maps.leaflet.flow.demo.data.entity.Marker;
import software.xdev.vaadin.maps.leaflet.flow.demo.data.entity.Polyline;
import software.xdev.vaadin.maps.leaflet.flow.demo.data.entity.Rectangle;
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
	
	// public void saveMarker(Marker marker) {
	// 	if (marker = null) {
	// 		Sy
	// 	}
	// }
	//
	
	public List<Marker> findAllMarkers() { return markerRepository.findAll(); }
	public List<Polyline> findAllPolylines() { return polylineRepository.findAll(); }
	public List<Rectangle> findAllRectangles() { return rectangleRepository.findAll(); }
	
}
