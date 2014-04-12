'use strict';

angular.module('webClientApp')
  .controller('MainCtrl', function ($scope, $log, cornercouch) {
      $scope.server = cornercouch("http://vm81.softsky.com.ua:5984");
      $scope.server.method = "GET"
      
      $scope.submitLogin = function() {
          $scope.server.login($scope.loginUser, $scope.loginPass)
              .success( function() {
                  $scope.loggedIn = true;
                  // $scope.loginPass = $scope.loginUser = '';
                  $scope.showInfo = true;

                  $scope.server.getInfo();
                  $scope.server.getDatabases();
                  $scope.server.getUUIDs(3);
                  $scope.server.getUserDoc();
                  $scope.db = $scope.server.getDB("softsky_timetracking");
                  $scope.db.method = "GET"
                  $scope.db.getInfo();

                  $scope.db.query("web-client", "byUsername", { include_docs: true, descending: true, limit: 20, key: "archer" });
              });
      };

      $scope.formatDate = function(row){
          return new Date(parseInt(row.doc._id));
      }

      $scope.getImagePath = function(row){
          return "../../" + row.doc._id + "/" + Object.keys(row.doc._attachments)[0];
      }

      $scope.nextClick = function() { $scope.db.queryNext(); delete $scope.detail }
      $scope.prevClick = function() { $scope.db.queryPrev(); delete $scope.detail }

      $scope.logout = function(){
          $scope.server.logout()
          $scope.loggedIn = false;
      }
  });
