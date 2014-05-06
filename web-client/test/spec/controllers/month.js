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

  it('should use uiConfig', function () {
    expect(scope.uiConfig).not.toBe(undefined);
  });
});
