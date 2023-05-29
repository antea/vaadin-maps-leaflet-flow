package software.xdev.vaadin.maps.leaflet.flow.data;

import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;


public class LImageOverlay implements LComponent{
	private String imagePath;
	private double[] topLeft;
	private double[] topRight;
	private double[] bottomLeft;
	
	// default constructor spawns hardcoded image
	public LImageOverlay() {
		this.imagePath = "\"frontend/leaflet/cb17-100-median-age.svg\"";
	}
	
	public LImageOverlay(String imagePath) {
		this.imagePath = imagePath;
	}
	
	public LImageOverlay(String imagePath, double[] topLeft, double[] topRight, double[] bottomLeft) {
		this.imagePath = imagePath;
		this.topLeft = topLeft;
		this.topRight = topRight;
		this.bottomLeft = bottomLeft;
	}
	
	@Override
	public String buildClientJSItems() throws JsonProcessingException
	{
		return "let item = L.distortableImageOverlay("+ imagePath +");";
	}
	
	@Override
	public String getPopup()
	{
		return null;
	}
	
	@Override
	public UUID getId()
	{
		return null;
	}
	
	@Override
	public void setId(final UUID id)
	{
	
	}
}
