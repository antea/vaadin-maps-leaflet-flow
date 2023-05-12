package software.xdev.vaadin.data.service;

import java.util.List;

import org.springframework.stereotype.Service;

import software.xdev.vaadin.data.entity.Marker;
import software.xdev.vaadin.data.entity.Polyline;
import software.xdev.vaadin.data.repository.MarkerRepository;
import software.xdev.vaadin.data.repository.PolylineRepository;
import software.xdev.vaadin.data.entity.Rectangle;
import software.xdev.vaadin.data.repository.RectangleRepository;


@Service
public class DbService {
	private final MarkerRepository markerRepository;
	private final PolylineRepository polylineRepository;
	private final RectangleRepository rectangleRepository;
	
	
	// default constructor
	public DbService() {
		this.markerRepository = null;
		this.polylineRepository = null;
		this.rectangleRepository = null;
	}

	public DbService(MarkerRepository markerRepository, PolylineRepository polylineRepository, RectangleRepository rectangleRepository) {
		this.markerRepository = markerRepository;
		this.polylineRepository = polylineRepository;
		this.rectangleRepository = rectangleRepository;
	}
	
	public void saveMarker(Marker marker) {
		if (marker == null) {
			System.err.println("Error, marker is null");
			return;
		}
		markerRepository.save(marker);
	}
	
	public void deleteMarker(Marker marker) { markerRepository.delete(marker); }
	
	public void savePolyline(Polyline polyline) {
		if (polyline == null) {
			System.err.println("Error, Polyline is null");
			return;
		}
		polylineRepository.save(polyline);
	}
	
	public void deletePolyline(Polyline polyline) { polylineRepository.delete(polyline); }
	
	public void saveRectangle(Rectangle rectangle) {
		if (rectangle == null) {
			System.err.println("Error, Rectangle is null");
			return;
		}
		rectangleRepository.save(rectangle);
	}
	
	public void deleteRectangle(Rectangle rectangle) { rectangleRepository.delete(rectangle); }

	
	public List<Marker> findAllMarkers() { return markerRepository.findAll(); }
	public List<Polyline> findAllPolylines() { return polylineRepository.findAll(); }
	public List<Rectangle> findAllRectangles() { return rectangleRepository.findAll(); }
}
