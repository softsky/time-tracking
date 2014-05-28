'use strict';

angular.module('webClientApp')
  .controller('LogoutCtrl', function ($scope, $location, $data) {
      $scope.server = $data.server;
      $scope.server.method = "GET"
      
      $scope.server.logout()
          .success( function() {
              $scope.loggedIn = false;
              $location.path("/")
          });

  });
