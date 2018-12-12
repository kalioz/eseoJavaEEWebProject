Vue.component("v-select", VueSelect.VueSelect);

new Vue({
    el: "#app",
    data: {
        options: [],
        city1: null,
        city2: null
    },

    methods: {
        onSearch: function onSearch(search, loading) {
            loading(true);
            this.search(loading, search, this);
        },
        search: _.debounce(function (loading, search, vm) {
            fetch("api/communes?name=" +
                escape(search)).then(function (res) {
                res.json().then(function (json) {
                    for (var i = 0; i < json.length; i++) {
                        json[i].label = json[i].nom;
                    }
                    return vm.options = json;
                });
                loading(false);
            });
        }, 350),
        distance: function distance(city1, city2) {
            var lat1 = city1.latitude * Math.PI / 180,
                lon1 = city1.longitude * Math.PI / 180,
                lat2 = city2.latitude * Math.PI / 180,
                lon2 = city2.longitude * Math.PI / 180;
            var R = 6371;
            var d = R * Math.acos(Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1) + Math.sin(lat1) * Math.sin(lat2));

            return Math.round(d * 100) / 100;
        },
        lat2svg: function lat2svg(lat) {

        }
    }
});