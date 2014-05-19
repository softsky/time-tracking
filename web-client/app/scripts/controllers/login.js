'use strict';

angular.module('webClientApp')
  .controller('LoginCtrl', function ($scope, $location, $data) {
      $scope.server = $data.server;
      $scope.server.method = "GET"
      $scope.loginUser = "archer";
      $scope.loginPass = "XtujNj12";
      
      $scope.submitLogin = function() {
          $scope.server.login($scope.loginUser, $scope.loginPass)
              .success( function() {
                  $scope.loggedIn = true;
                  // $scope.loginPass = $scope.loginUser = '';
                  $location.path("/")
              });
      };

  });
