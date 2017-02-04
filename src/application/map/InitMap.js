var map;
var nodes=[];
var noAlarmInactive='images/NoAlarm.jpg', noAlarmActive='images/NoAlarmBright.jpg';
var alarmInactive='images/Alarm.jpg', alarmActive='images/AlarmBright.jpg';
function initialize() {
    var mapOptions = {
        center: new google.maps.LatLng(12.5631433, 80.1758976),
        zoom: 14,
        mapTypeId:google.maps.MapTypeId.HYBRID,
        mapTypeControl:false,
        streetViewControl:false,
        minZoom: 12
    };
    map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);
    
    var routerMarker='images/Router.jpg';
    var router1=new google.maps.Marker({
        position: new google.maps.LatLng(12.538728, 80.164020),
        map: map,
        icon: routerMarker,
        title: "Main Gate 1",
        id: 768,
        opacity: 0.65
    });
    router1.addListener('mouseover', function() {
        router1.setOpacity(1.0);
    });
    router1.addListener('mouseout', function() {
        router1.setOpacity(0.65);
    });
    var router2=new google.maps.Marker({
        position: new google.maps.LatLng(12.538917, 80.164087),
        map: map,
        icon: routerMarker,
        title: "Main Gate 2",
        opacity: 0.65
    });
    router2.addListener('mouseover', function() {
        router2.setOpacity(1.0);
    });
    router2.addListener('mouseout', function() {
        router2.setOpacity(0.65);
    });
    var router3=new google.maps.Marker({
        position: new google.maps.LatLng(12.538626, 80.164302),
        map: map,
        icon: routerMarker,
        title: "Main Gate 3",
        opacity: 0.65
    });
    router3.addListener('mouseover', function() {
        router3.setOpacity(1.0);
    });
    router3.addListener('mouseout', function() {
        router3.setOpacity(0.65);
    });
    var router4=new google.maps.Marker({
        position: new google.maps.LatLng(12.556152, 80.164027),
        map: map,
        icon: routerMarker,
        opacity: 0.65
    });
    router4.addListener('mouseover', function() {
        router4.setOpacity(1.0);
    });
    router4.addListener('mouseout', function() {
        router4.setOpacity(0.65);
    });
    var router5=new google.maps.Marker({
        position: new google.maps.LatLng(12.561810, 80.163906),
        map: map,
        icon: routerMarker,
        opacity: 0.65
    });
    router5.addListener('mouseover', function() {
        router5.setOpacity(1.0);
    });
    router5.addListener('mouseout', function() {
        router5.setOpacity(0.65);
    });
    var router6=new google.maps.Marker({
        position: new google.maps.LatLng(12.561193, 80.164336),
        map: map,
        icon: routerMarker,
        opacity: 0.65
    });
    router6.addListener('mouseover', function() {
        router6.setOpacity(1.0);
    });
    router6.addListener('mouseout', function() {
        router6.setOpacity(0.65);
    });
    var router7=new google.maps.Marker({
        position: new google.maps.LatLng(12.561156, 80.164907),
        map: map,
        icon: routerMarker,
        opacity: 0.65
    });
    router7.addListener('mouseover', function() {
        router7.setOpacity(1.0);
    });
    router7.addListener('mouseout', function() {
        router7.setOpacity(0.65);
    });
    var router8=new google.maps.Marker({
        position: new google.maps.LatLng(12.564658, 80.164245),
        map: map,
        icon: routerMarker,
        opacity: 0.65
    });
    router8.addListener('mouseover', function() {
        router8.setOpacity(1.0);
    });
    router8.addListener('mouseout', function() {
        router8.setOpacity(0.65);
    });
    var router9=new google.maps.Marker({
        position: new google.maps.LatLng(12.569397, 80.164811),
        map: map,
        icon: routerMarker,
        opacity: 0.65
    });
    router9.addListener('mouseover', function() {
        router9.setOpacity(1.0);
    });
    router9.addListener('mouseout', function() {
        router9.setOpacity(0.65);
    });
    var router10=new google.maps.Marker({
        position: new google.maps.LatLng(12.557711, 80.171808),
        map: map,
        icon: routerMarker,
        opacity: 0.65
    });
    router10.addListener('mouseover', function() {
        router10.setOpacity(1.0);
    });
    router10.addListener('mouseout', function() {
        router10.setOpacity(0.65);
    });
    var router11=new google.maps.Marker({
        position: new google.maps.LatLng(12.557193, 80.172192),
        map: map,
        icon: routerMarker,
        opacity: 0.65
    });
    router11.addListener('mouseover', function() {
        router11.setOpacity(1.0);
    });
    router11.addListener('mouseout', function() {
        router11.setOpacity(0.65);
    });
    var router12=new google.maps.Marker({
        position: new google.maps.LatLng(12.557973, 80.172688),
        map: map,
        icon: routerMarker,
        opacity: 0.65
    });
    router12.addListener('mouseover', function() {
        router12.setOpacity(1.0);
    });
    router12.addListener('mouseout', function() {
        router12.setOpacity(0.65);
    });
    var router13=new google.maps.Marker({
        position: new google.maps.LatLng(12.559438, 80.173392),
        map: map,
        icon: routerMarker,
        opacity: 0.65
    });
    router13.addListener('mouseover', function() {
        router13.setOpacity(1.0);
    });
    router13.addListener('mouseout', function() {
        router13.setOpacity(0.65);
    });
    var router14=new google.maps.Marker({
        position: new google.maps.LatLng(12.560662, 80.173914),
        map: map,
        icon: routerMarker,
        opacity: 0.65
    });
    router14.addListener('mouseover', function() {
        router14.setOpacity(1.0);
    });
    router14.addListener('mouseout', function() {
        router14.setOpacity(0.65);
    });
    var router15=new google.maps.Marker({
        position: new google.maps.LatLng(12.561041, 80.174026),
        map: map,
        icon: routerMarker,
        opacity: 0.65
    });
    router15.addListener('mouseover', function() {
        router15.setOpacity(1.0);
    });
    router15.addListener('mouseout', function() {
        router15.setOpacity(0.65);
    });
    var router16=new google.maps.Marker({
        position: new google.maps.LatLng(12.561019, 80.174412),
        map: map,
        icon: routerMarker,
        opacity: 0.65
    });
    router16.addListener('mouseover', function() {
        router16.setOpacity(1.0);
    });
    router16.addListener('mouseout', function() {
        router16.setOpacity(0.65);
    });
    var router17=new google.maps.Marker({
        position: new google.maps.LatLng(12.562505, 80.175047),
        map: map,
        icon: routerMarker,
        opacity: 0.65
    });
    router17.addListener('mouseover', function() {
        router17.setOpacity(1.0);
    });
    router17.addListener('mouseout', function() {
        router17.setOpacity(0.65);
    });
    var router18=new google.maps.Marker({
        position: new google.maps.LatLng(12.563384, 80.175564),
        map: map,
        icon: routerMarker,
        opacity: 0.65
    });
    router18.addListener('mouseover', function() {
        router18.setOpacity(1.0);
    });
    router18.addListener('mouseout', function() {
        router18.setOpacity(0.65);
    });
    var router19=new google.maps.Marker({
        position: new google.maps.LatLng(12.564590, 80.175851),
        map: map,
        icon: routerMarker,
        opacity: 0.65
    });
    router19.addListener('mouseover', function() {
        router19.setOpacity(1.0);
    });
    router19.addListener('mouseout', function() {
        router19.setOpacity(0.65);
    });
    var router20=new google.maps.Marker({
        position: new google.maps.LatLng(12.567869, 80.175594),
        map: map,
        icon: routerMarker,
        opacity: 0.65
    });
    router20.addListener('mouseover', function() {
        router20.setOpacity(1.0);
    });
    router20.addListener('mouseout', function() {
        router20.setOpacity(0.65);
    });
    var router21=new google.maps.Marker({
        position: new google.maps.LatLng(12.567941, 80.176263),
        map: map,
        icon: routerMarker,
        opacity: 0.65
    });
    router21.addListener('mouseover', function() {
        router21.setOpacity(1.0);
    });
    router21.addListener('mouseout', function() {
        router21.setOpacity(0.65);
    });
    var router22=new google.maps.Marker({
        position: new google.maps.LatLng(12.569904, 80.175458),
        map: map,
        icon: routerMarker,
        opacity: 0.65
    });
    router22.addListener('mouseover', function() {
        router22.setOpacity(1.0);
    });
    router22.addListener('mouseout', function() {
        router22.setOpacity(0.65);
    });
    var router23=new google.maps.Marker({
        position: new google.maps.LatLng(12.570872, 80.175365),
        map: map,
        icon: routerMarker,
        opacity: 0.65
    });
    router23.addListener('mouseover', function() {
        router23.setOpacity(1.0);
    });
    router23.addListener('mouseout', function() {
        router23.setOpacity(0.65);
    });
    var router24=new google.maps.Marker({
        position: new google.maps.LatLng(12.571051, 80.174922),
        map: map,
        icon: routerMarker,
        opacity: 0.65
    });
    router24.addListener('mouseover', function() {
        router24.setOpacity(1.0);
    });
    router24.addListener('mouseout', function() {
        router24.setOpacity(0.65);
    });
    var router25=new google.maps.Marker({
        position: new google.maps.LatLng(12.571160, 80.176013),
        map: map,
        icon: routerMarker,
        opacity: 0.65
    });
    router25.addListener('mouseover', function() {
        router25.setOpacity(1.0);
    });
    router25.addListener('mouseout', function() {
        router25.setOpacity(0.65);
    });

    nodes[0]=new google.maps.Marker({
        position: new google.maps.LatLng(12.538095, 80.163839),
        map: map,
        id: 768,
        icon: noAlarmInactive
    });
    nodes[0].addListener('click', function() {
        window.alert(768);
    });
    nodes[1]=new google.maps.Marker({
        position: new google.maps.LatLng(12.538042, 80.164011),
        map: map,
        id: 1025,
        icon: noAlarmInactive
    });
    nodes[1].addListener('click', function() {
        window.alert(1281);
    });
    nodes[2]=new google.maps.Marker({
        position: new google.maps.LatLng(12.538201, 80.163876),
        map: map,
        id: 1281,
        icon: noAlarmInactive
    });
    nodes[2].addListener('click', function() {
        window.alert(1025);
    });
    nodes[3]=new google.maps.Marker({
        position: new google.maps.LatLng(12.538157, 80.164040),
        map: map,
        icon: noAlarmInactive
    });
    nodes[4]=new google.maps.Marker({
        position: new google.maps.LatLng(12.538340, 80.163929),
        map: map,
        icon: noAlarmInactive
    });
    nodes[5]=new google.maps.Marker({
        position: new google.maps.LatLng(12.538290, 80.164093),
        map: map,
        icon: noAlarmInactive
    });
    nodes[6]=new google.maps.Marker({
        position: new google.maps.LatLng(12.538151, 80.163800),
        map: map,
        icon: noAlarmInactive
    });
}
google.maps.event.addDomListener(window, 'load', initialize);