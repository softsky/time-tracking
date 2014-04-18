'use strict';

angular.module('webClientApp')
  .controller('DayCtrl', function ($scope, $location, $routeParams, $log) {
      $log.info($routeParams);
      $scope.year = $routeParams.year;
      $scope.month = $routeParams.month;
      $scope.day = $routeParams.day;
  });
