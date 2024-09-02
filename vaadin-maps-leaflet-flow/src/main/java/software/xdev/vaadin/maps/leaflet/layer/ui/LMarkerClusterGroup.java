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
package software.xdev.vaadin.maps.leaflet.layer.ui;

import java.util.Arrays;

import software.xdev.vaadin.maps.leaflet.layer.LLayer;
import software.xdev.vaadin.maps.leaflet.layer.LLayerGroup;
import software.xdev.vaadin.maps.leaflet.registry.LComponentManagementRegistry;


/**
 * <p>
 * Leaflet plugin Marker Cluster, see <a href="https://github.com/Leaflet/Leaflet.markercluster">Marker cluster Leaflet
 * plugin</a>
 * </p>
 * <p>
 * LMarkerClusterGroup does not take layers as parameters, please use {@link #addLayers(LLayer[])} method.
 * </p>
 */
public class LMarkerClusterGroup extends LLayerGroup
{
	public LMarkerClusterGroup(final LComponentManagementRegistry compReg)
	{
		super("L.markerClusterGroup", compReg);
	}
	
	/**
	 * To add markers to this cluster group
	 *
	 * @param layers The LLayer to be added to this cluster group
	 */
	public LLayerGroup addLayers(final LLayer<?>... layers)
	{
		Arrays.stream(layers).forEach(this::addLayer);
		return this.self();
	}
}
