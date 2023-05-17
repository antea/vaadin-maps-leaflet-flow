package software.xdev.vaadin.data.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import software.xdev.vaadin.maps.leaflet.flow.data.LPoint;


@Entity
public class Polyline extends AbstractEntity
{
	
	// https://stackoverflow.com/questions/2990799/difference-between-fetchtype-lazy-and-eager-in-java-persistence-api
	@ElementCollection(fetch = FetchType.EAGER)
	private List<Double> points = new ArrayList<>();
	
	// default constructor
	public Polyline() {}
	public Polyline(UUID id, List<Double> points) {
		super(id);
		this.points = points;
	}
	
	public List<Double> getPoints() { return this.points; }
	public void setPoints(List<Double> points) { this.points = points; }
	
	public List<LPoint> getLPoints() {
		// check that there are  pairs of points
		if (this.points.size() % 2 != 0) {
			return null;
		}
		else {
			var pointList = new ArrayList<LPoint>();
			
			// take every 2 points, create an LPoint and add to the list
			for (int i = 0; i < this.points.size(); i = i + 2) {
				pointList.add(new LPoint(points.get(i), points.get(i + 1)));
			}
			
			return pointList;
		}
	}
	
}
