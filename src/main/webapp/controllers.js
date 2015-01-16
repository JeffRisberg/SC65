myApp.controller('HomeController', ['$scope', '$http', function ($scope, $http) {
    $scope.appState = "home";

    //$scope.items = [
    //    {name: 'Challenge1', startDate: new Date(101, 5, 5), endDate: new Date(101, 6, 1)},
    //    {name: 'Challenge2', startDate: new Date(101, 7, 5), endDate: new Date(101, 8, 1)}
    //];

    $scope.items = {};

    $scope.remove = function (index) {
            $scope.items.splice(index, 1);
    };

    $scope.challengeAdd = function () {
            $scope.items.push({name: $scope.addName, startDate: new Date(2014, 7, 5), endDate: new Date(2014, 8, 1)});
            $scope.addName = "";
            $scope.addStartDate = "";
            $scope.addEndDate = "";

            $scope.appState = "challengeList";
        };

    // perform the initial fetch
    $http.get('challenge.json').
        success(function (response, status, headers, config) {

            $scope.items = response.data;
        }).
        error(function (data, status, headers, config) {
            // log error
            console.log("error");
        })

}]);