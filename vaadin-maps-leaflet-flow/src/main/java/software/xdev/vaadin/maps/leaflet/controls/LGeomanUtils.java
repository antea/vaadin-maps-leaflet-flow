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

import software.xdev.vaadin.maps.leaflet.map.LMap;
import software.xdev.vaadin.maps.leaflet.registry.LComponentManagementRegistry;


/**
 * Control for editing functionalities. See <a href="https://www.geoman.io/docs/toolbar">Geoman documentation</a>
 */
public class LGeomanUtils
{
	/**
	 * The toolbar is not exactly a LControl, since Geoman requires the invocation of the method map.pm.addControls(options)
	 * to add it.
	 * So to add the Geoman Toolbar, invoke this method, passing a map, the LComponentManagementRegistry and the options
	 * to configure the toolbar, {@link LGeomanOptions}
	 * @param lMap The map where we want the draw/editing toolbar
	 * @param compReg The management registry, here it is used only to serialize the options
	 * @param geomanOptions The options to configure the toolbar
	 */
	public static void addToolbar(
		LMap lMap,
		LComponentManagementRegistry compReg,
		LGeomanOptions geomanOptions)
	{
		lMap.invokeSelf(".pm.addControls(" +  compReg.writeOptions(geomanOptions) + ")");
	}
	
	
	
}
