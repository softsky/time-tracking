'use strict';

describe('Controller: ChartCtrl', function () {

  // load the controller's module
  beforeEach(module('webClientApp'));

  var ChartCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ChartCtrl = $controller('ChartCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
