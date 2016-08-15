var app = angular.module('SegTempApp',['ngRoute','routeStyles','angularify.semantic.dropdown','ngFileUpload']);
app.config(['$routeProvider', function($routeProvider) {
	$routeProvider.when('/home', {templateUrl: '_segtemp-core/components/home/homeView.html', controller:'homeCtrl', css: ['assets/css/home.css']});
    $routeProvider.otherwise({redirectTo: '/home'});
}]);
