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
package software.xdev.vaadin.maps.leaflet.flow.data;

import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;


public class LImageOverlay implements LComponent{
	private String imagePath;
	private double[] nw;
	private double[] ne;
	private double[] sw;
	private double[] se;
	
	public LImageOverlay(String imagePath) {
		this.imagePath = imagePath;
	}
	
	public LImageOverlay(String imagePath, double[] nw, double[] ne, double[] sw, double[] se) {
		this.imagePath = imagePath;
		this.nw = nw;
		this.ne = ne;
		this.sw = sw;
		this.se = se;
	}
	
	@Override
	public String buildClientJSItems() throws JsonProcessingException
	{
		return "let item = L.distortableImageOverlay("
			+ "'"+ imagePath +"'"
			+ (this.nw != null? ","
			+ "{\n"
			+ "    corners: [\n"
			+ "        L.latLng("+this.nw[0]+","+this.nw[1]+"),\n"
			+ "        L.latLng("+this.ne[0]+","+this.ne[1]+"),\n"
			+ "        L.latLng("+this.sw[0]+","+this.sw[1]+"),\n"
			+ "        L.latLng("+this.se[0]+","+this.se[1]+"),\n"
			+ "    ],\n"
			+ "}" : "")
			+ ");";
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
