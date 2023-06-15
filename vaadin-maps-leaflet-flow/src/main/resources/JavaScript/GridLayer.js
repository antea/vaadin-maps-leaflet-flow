L.ColoredTiles = L.GridLayer.extend({
    colorLatLngs: [],
    initialize (latlngs, options) {
        L.Util.setOptions(this, options);
        if(latlngs && L.Util.isArray(latlngs) && latlngs.length > 0) {
            this.colorLatLngs = latlngs;
        }

        if(this.options.debug) {
            this._showDebugLatLngs();
        }
    },
    createTile (coords) {
        var tile = L.DomUtil.create('canvas', 'leaflet-tile');
        var ctx = tile.getContext('2d');
        var size = this.getTileSize()
        // console.log("size: " + size.toString())
        tile.width = size.x
        tile.height = size.y

        // calculate projection coordinates of top left tile pixel
        var nwPoint = coords.scaleBy(size)

        // calculate geographic coordinates of top left tile pixel
        var nw = map.unproject(nwPoint, coords.z)

        ctx.fillStyle = 'white';
        ctx.fillRect(0, 0, size.x, 50);
        ctx.fillStyle = 'black';
        ctx.fillText('x: ' + coords.x + ', y: ' + coords.y + ', zoom: ' + coords.z, 20, 20);
        ctx.fillText('lat: ' + nw.lat + ', lon: ' + nw.lng, 20, 40);
        ctx.strokeStyle = 'black';
        ctx.beginPath();
        ctx.moveTo(0, 0);
        ctx.lineTo(size.x - 1, 0);
        ctx.lineTo(size.x - 1, size.y - 1);
        ctx.lineTo(0, size.y - 1);
        ctx.closePath();
        ctx.stroke();

        if (this._isLatLngInCoords(coords)) {
            ctx.fillStyle = 'rgba(0, 0, 240, 0.3)';
            ctx.fillRect(0, 0, size.x, size.y);
        }

        return tile;
    },
    _isLatLngInCoords(coords) {
        var tileBounds = this._tileCoordsToBounds(coords);
        return this.colorLatLngs && this.colorLatLngs.some(function (a) {
            return tileBounds.contains(a);
        });
    },
    setColorLatLngs(latlngs){
        this.colorLatLngs = latlngs;
        this.redraw();

        if(this.options.debug) {
            this._showDebugLatLngs();
        }
    },
    _showDebugLatLngs(){
        this.fg = this.fg || L.featureGroup().addTo(map);
        this.fg.clearLayers();
        this.colorLatLngs && this.colorLatLngs.forEach((latlng)=>{
            L.marker(latlng).addTo(this.fg);
        })
    }
});

var colorLatLngs = [
    L.latLng(0,0),
    L.latLng(2,2),
];

var tiles = new L.ColoredTiles(colorLatLngs).addTo(this.map)