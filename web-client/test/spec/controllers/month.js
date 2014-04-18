'use strict';

describe('Controller: MonthCtrl', function () {

  // load the controller's module
  beforeEach(module('webClientApp'));

  var MonthCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    MonthCtrl = $controller('MonthCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
