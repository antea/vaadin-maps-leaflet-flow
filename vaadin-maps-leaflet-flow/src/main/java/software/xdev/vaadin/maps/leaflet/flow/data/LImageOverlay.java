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
	
	}
	
	public LImageOverlay(String imagePath, double[] topLeft, double[] topRight, double[] bottomLeft) {
	
	}
	
	@Override
	public String buildClientJSItems() throws JsonProcessingException
	{
		return "let imageUrl = \"frontend/leaflet/cb17-100-median-age.svg\";\n"
			+ "let errorOverlayUrl = 'https://cdn-icons-png.flaticon.com/512/110/110686.png';\n"
			+ "let altText = 'US median age between the years 2000 and 2016';\n"
			+ "\n"
			+ "let topleft    = L.latLng(44.497933,-9.706642),\n"
			+ "    topright   = L.latLng(40.415020,-2.670899),\n"
			+ "    bottomleft = L.latLng(38.101557,-12.213126);\n"
			+ "let imageOverlay = L.imageOverlay.rotated(imageUrl, topleft, topright, bottomleft, {\n"
			+ "    opacity: 0.8,\n"
			+ "    errorOverlayUrl: errorOverlayUrl,\n"
			+ "    alt: altText,\n"
			+ "    interactive: true,\n"
			+ "    attribution: '<a href=\"https://census.gov/newsroom/press-releases/2017/cb17-100.html\">U.S. Census"
			+ " Bureau</a>'\n"
			+ "}).addTo(this.map);";
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
