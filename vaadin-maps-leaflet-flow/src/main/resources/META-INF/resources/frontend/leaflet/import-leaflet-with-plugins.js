// We cannot import Leaftlet and the plugins using many @JsModule annotations, because Vaadin has a bug that does not
// guarantee that the imports will be in the same order as defined with @JsModule: https://github.com/vaadin/flow/issues/15825
import 'leaflet/dist/leaflet.js';
import 'leaflet.markercluster/dist/leaflet.markercluster.js';
import '@geoman-io/leaflet-geoman-free';