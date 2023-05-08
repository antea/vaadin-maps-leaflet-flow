package software.xdev.vaadin.maps.leaflet.flow.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import software.xdev.vaadin.maps.leaflet.flow.data.entity.Polyline;


public interface PolylineRepository extends JpaRepository<Polyline, Long>
{

}
