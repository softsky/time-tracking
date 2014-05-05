'use strict';

angular.module('webClientApp')
    .config(function($dataProvider){
	// doing some config on $dataProvider
    })
    .controller('DayCtrl',function ($scope, $location, $routeParams, $log, $data, DefaultUIConfig) {
	$log.info($routeParams);
	
	$scope.year = $routeParams.year;
	$scope.month = $routeParams.month;
	$scope.day = $routeParams.day;

	$scope.username = "";

        $scope.events = [];
        $scope.eventSources = [ $scope.events ]


	$scope.db = $data.db;
	$scope.$watch("db.data", function(data){
            $scope.events.length = 0;
            for(var user in data){
                data[user].forEach(function(interval){
                    $scope.events.push(angular.extend(interval, {title: user, color: user.toColor(), allDay: false}))
                })
            }
	}, true)
	
	$scope.$watch("username", function(newVal){
	    var thisDate = new Date($scope.year, parseInt($scope.month) - 1, $scope.day).getTime()
	    $scope.db.list("web-client", "intervals", "byDateAndUsername", {
		descending: false, 
		startkey: [(thisDate), $scope.username], 
		endkey: [(thisDate + (24 * 3600 * 1000)), $scope.username]  // adding 24 hours
	    })
	})

        $scope.uiConfig = {
            calendar:{
                height: '800',
                editable: false,
                year: $scope.year, 
                month: $scope.month - 1, 
                date: $scope.day,
                header:{
                    center: 'title',
                    right: ''
                },
                allDaySlot: false,
                defaultView: 'agendaDay',
                dayClick: $scope.alertEventOnClick,
                eventDrop: $scope.alertOnDrop,
                eventResize: $scope.alertOnResize,
            }
        };

        $scope.uiConfig.calendar = angular.extend(DefaultUIConfig, $scope.uiConfig.calendar);

    })

