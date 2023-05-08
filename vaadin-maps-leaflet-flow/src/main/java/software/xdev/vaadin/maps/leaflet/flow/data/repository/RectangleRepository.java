package software.xdev.vaadin.maps.leaflet.flow.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import software.xdev.vaadin.maps.leaflet.flow.data.entity.Rectangle;


public interface RectangleRepository extends JpaRepository<Rectangle, Long>
{

}
