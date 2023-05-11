package software.xdev.vaadin.maps.leaflet.flow.data.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import software.xdev.vaadin.maps.leaflet.flow.data.entity.Polyline;


public interface PolylineRepository extends JpaRepository<Polyline, UUID>
{

}
