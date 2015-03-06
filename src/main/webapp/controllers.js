myApp.controller('HomeController', ['$scope', '$http', function ($scope, $http) {
    $scope.appState = "home";

    $scope.challenges = {};
    $scope.activities = {};

    $scope.teamworkTypes = [
        { name: "Individual", value: 1},
        { name: "Team", value: 2}
    ];

    $scope.addTeamworkType = $scope.teamworkTypes[0];

    $scope.remove = function (index) {
        $scope.items.splice(index, 1);
    };

    $scope.challengeAdd = function () {
        var name = $scope.addName;
        var startDate = Date.parse($scope.addStartDate);
        var endDate = Date.parse($scope.addEndDate);
        var teamworkTypeValue = $scope.addTeamworkType.value;
        var teamworkType = $scope.teamworkTypes[0];

        $scope.teamworkTypes.forEach(function (t) {
            if (t.value == teamworkTypeValue) {
                teamworkType = t;
            }
        });

        var challenge = {name: name, startDate: startDate, endDate: endDate, active: true, teamworkType: teamworkType};

        $scope.challenges.push(challenge);
        $scope.addName = "";
        $scope.addStartDate = "";
        $scope.addEndDate = "";
        $scope.addTeamworkType = $scope.teamworkTypes[0];

        $http.put("challenge", challenge).
            success(function (response, status) {
                $scope.appState = "challengeList";
            });
    };

    function fetchChallenges() {
        // perform the initial fetches
        $http.get('challenge.json').
            success(function (response, status, headers, config) {

                $scope.challenges = [];
                angular.forEach(response.data, function (value) {
                    var challenge = value;
                    challenge.startDate = new Date(value.startDate);
                    challenge.endDate = new Date(value.endDate);
                    $scope.challenges.push(challenge);
                });
            }).
            error(function (data, status, headers, config) {
                // log error
                console.log("error");
            })
    }

    function fetchActivities() {
        $http.get('activity.json').
            success(function (response, status, headers, config) {

                $scope.activities = response.data;
            }).
            error(function (data, status, headers, config) {
                // log error
                console.log("error");
            })
    }

    fetchChallenges();
    fetchActivities();
}]);