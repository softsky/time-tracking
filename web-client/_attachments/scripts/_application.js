'use strict';

angular.module('webClientApp', [
    'ngCookies',
    'ngResource',
    'ngSanitize',
    'ngRoute',
    'ui.calendar',
//    'highcharts-ng',
    'CornerCouch'
]).config(function(){
    // some custom configuration
    Date.prototype.getWeek = function() {
        var onejan = new Date(this.getFullYear(), 0, 1);
        return Math.ceil((((this - onejan) / 86400000) + onejan.getDay() + 1) / 7);
    }
}).config(function ($routeProvider) {
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
        .when('/day/:year/:month/:day', {
            templateUrl: 'views/day.html',
            controller: 'DayCtrl'
        })
        .when('/day/', {
            redirectTo: function(){
                var date = new Date()
                return '/day/' + date.getFullYear() + '/' + (date.getMonth() + 1) + '/' + date.getDate()
            }
        })
        .when('/week', {
            templateUrl: 'views/week.html',
            controller: 'WeekCtrl'
        })
        .when('/month', {
            templateUrl: 'views/month.html',
            controller: 'MonthCtrl'
        })
        .otherwise({
            redirectTo: '/'
        });
})
