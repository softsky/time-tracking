'use strict';

function this_month_start(){
    var now = new Date()
    return new Date(now.getYear(), now.getMonth(), 1).getTime();
}

function this_month_end(){
    var now = new Date()
    return new Date(now.getYear(), now.getMonth(), 31).getTime();
}

angular.module('webClientApp')
    .config(function($dataProvider){
	// doing some config on $dataProvider
    })
    .controller('DayCtrl',function ($scope, $location, $routeParams, $log, $data) {
	$log.info($routeParams);
	$scope.year = $routeParams.year;
	$scope.month = $routeParams.month;
	$scope.day = $routeParams.day;

	$scope.db = $data.db;
	$scope.$watch("db.rows", function(newVal){
	    $log.info(newVal);
	}, true)
	
	var thisDate = new Date($scope.year, parseInt($scope.month) - 1, $scope.day).getTime()
	$scope.db.query("web-client", "byDateAndUsername", {
            include_docs: true, 
            descending: false, 
            limit: 20, 
            start_key: "[\""  + thisDate + "\", \"skekes\"]", 
            start_end: "[\""  + thisDate + (24 * 3600 * 1000) + "\", \"skekes\"]",  // adding 24 hours day
	})
  })

