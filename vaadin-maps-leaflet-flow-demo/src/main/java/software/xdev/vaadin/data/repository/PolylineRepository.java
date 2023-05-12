package software.xdev.vaadin.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import software.xdev.vaadin.data.entity.Polyline;


public interface PolylineRepository extends JpaRepository<Polyline, Long>
{

}
