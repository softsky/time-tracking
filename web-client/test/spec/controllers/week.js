'use strict';

describe('Controller: WeekCtrl', function () {

  // load the controller's module
  beforeEach(module('webClientApp'));

  var WeekCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    WeekCtrl = $controller('WeekCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
