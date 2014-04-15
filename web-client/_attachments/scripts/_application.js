'use strict';

angular.module('webClientApp', [
    'ngCookies',
    'ngResource',
    'ngSanitize',
    'ngRoute',
    'ui.calendar',
//    'highcharts-ng',
    'CornerCouch'
]).config(function ($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'views/main.html',
            controller: 'MainCtrl'
        })
        .when('/login', {
            templateUrl: 'views/login.html',
            controller: 'LoginCtrl'
        })
        .when('/calendar', {
            templateUrl: 'views/calendar.html',
            controller: 'CalendarCtrl'
        })
        .otherwise({
            redirectTo: '/'
        });
})
