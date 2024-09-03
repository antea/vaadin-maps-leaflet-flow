/*
 * Copyright © 2019 XDEV Software (https://xdev.software)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package software.xdev.vaadin.maps.leaflet.controls;

/**
 * For options details see <a href="https://www.geoman.io/docs/toolbar">Geoman Toolbar docs</a>
 */
public class LGeomanOptions extends LControlOptions<LGeomanOptions>
{
	
	public static final String POSITION_TOP_LEFT = "topleft";
	public static final String POSITION_TOP_RIGHT = "topright";
	public static final String POSITION_BOTTOM_LEFT = "bottomleft";
	public static final String POSITION_BOTTOM_RIGHT = "bottomright";
	// Snap the dragged marker/vertex to other layers for precision drawing.
	private Boolean snapIgnore;
	// Shows all draw buttons / buttons in the draw block.
	private Boolean drawControls;
	// Shows all edit buttons / buttons in the edit block.
	private Boolean editControls;
	// Shows all buttons in the custom block.
	private Boolean customControls;
	// All buttons will be displayed as one block Customize Controls.
	private Boolean oneBlock;
	
	// Adds button to draw Markers.
	private Boolean drawMarker;
	// Adds button to draw CircleMarkers.
	private Boolean drawCircleMarker;
	// Adds button to draw Line.
	private Boolean drawPolyline;
	// Adds button to draw Rectangle.
	private Boolean drawRectangle;
	// Adds button to draw Polygon.
	private Boolean drawPolygon;
	// Adds button to draw Circle.
	private Boolean drawCircle;
	// Adds button to draw Text.
	private Boolean drawText;
	// Adds button to toggle Edit Mode for all layers.
	private Boolean editMode;
	// Adds button to toggle Drag Mode for all layers.
	private Boolean dragMode;
	// Adds a button to remove layers.
	private Boolean removalMode;
	// Adds a button to rotate layers.
	private Boolean rotateMode;
	
	public Boolean getDrawMarker()
	{
		return drawMarker;
	}
	
	public void setDrawMarker(final Boolean drawMarker)
	{
		this.drawMarker = drawMarker;
	}
	
	public Boolean getDrawCircleMarker()
	{
		return drawCircleMarker;
	}
	
	public void setDrawCircleMarker(final Boolean drawCircleMarker)
	{
		this.drawCircleMarker = drawCircleMarker;
	}
	
	public Boolean getDrawPolyline()
	{
		return drawPolyline;
	}
	
	public void setDrawPolyline(final Boolean drawPolyline)
	{
		this.drawPolyline = drawPolyline;
	}
	
	public Boolean getDrawRectangle()
	{
		return drawRectangle;
	}
	
	public void setDrawRectangle(final Boolean drawRectangle)
	{
		this.drawRectangle = drawRectangle;
	}
	
	public Boolean getDrawPolygon()
	{
		return drawPolygon;
	}
	
	public void setDrawPolygon(final Boolean drawPolygon)
	{
		this.drawPolygon = drawPolygon;
	}
	
	public Boolean getDrawCircle()
	{
		return drawCircle;
	}
	
	public void setDrawCircle(final Boolean drawCircle)
	{
		this.drawCircle = drawCircle;
	}
	
	public Boolean getDrawText()
	{
		return drawText;
	}
	
	public void setDrawText(final Boolean drawText)
	{
		this.drawText = drawText;
	}
	
	public Boolean getEditMode()
	{
		return editMode;
	}
	
	public void setEditMode(final Boolean editMode)
	{
		this.editMode = editMode;
	}
	
	public Boolean getDragMode()
	{
		return dragMode;
	}
	
	public void setDragMode(final Boolean dragMode)
	{
		this.dragMode = dragMode;
	}
	
	public Boolean getRemovalMode()
	{
		return removalMode;
	}
	
	public void setRemovalMode(final Boolean removalMode)
	{
		this.removalMode = removalMode;
	}
	
	public Boolean getRotateMode()
	{
		return rotateMode;
	}
	
	public void setRotateMode(final Boolean rotateMode)
	{
		this.rotateMode = rotateMode;
	}
	
	public Boolean getSnapIgnore()
	{
		return snapIgnore;
	}
	
	public void setSnapIgnore(final Boolean snapIgnore)
	{
		this.snapIgnore = snapIgnore;
	}
	
	public Boolean getDrawControls()
	{
		return drawControls;
	}
	
	public void setDrawControls(final Boolean drawControls)
	{
		this.drawControls = drawControls;
	}
	
	public Boolean getEditControls()
	{
		return editControls;
	}
	
	public void setEditControls(final Boolean editControls)
	{
		this.editControls = editControls;
	}
	
	public Boolean getCustomControls()
	{
		return customControls;
	}
	
	public void setCustomControls(final Boolean customControls)
	{
		this.customControls = customControls;
	}
	
	public Boolean getOneBlock()
	{
		return oneBlock;
	}
	
	public void setOneBlock(final Boolean oneBlock)
	{
		this.oneBlock = oneBlock;
	}
}
