// If I import Leaflet and leaflet.markercluster separately I get this error https://stackoverflow.com/questions/44479562/l-is-not-defined-error-with-leaflet
// because vaadin has a bug that does not guarantee that the imports will be in the same order as defined with @JsModule
// Here is the bug issue: https://github.com/vaadin/flow/issues/15825
import 'leaflet/dist/leaflet.js';
import 'leaflet.markercluster/dist/leaflet.markercluster.js';
import '@geoman-io/leaflet-geoman-free';