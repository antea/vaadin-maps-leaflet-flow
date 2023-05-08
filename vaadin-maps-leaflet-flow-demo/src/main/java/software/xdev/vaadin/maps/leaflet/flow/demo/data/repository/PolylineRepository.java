package software.xdev.vaadin.maps.leaflet.flow.demo.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import software.xdev.vaadin.maps.leaflet.flow.demo.data.entity.Polyline;


public interface PolylineRepository extends JpaRepository<Polyline, Long>
{

}
