$(document).ready(function() {

    // updates schedule options when the screen loads first time
    updateScheduleTypeOptions($("#scheduleType option:selected").text());

    $("#scheduleType").change(function() {

        updateScheduleTypeOptions($("#scheduleType option:selected").text());

    });

    $(".enableTask").change(function() {
        var enabled = this.checked;
        var schedulerConfigId = $(this).parent().parent().find("#schedulerConfigId").text();
//        var aggregatorMappingId = $(this).parent().parent().children("#aggregatorMappingId").text()

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/ajax/scheduler/state",
            dataType: 'json',
            data: JSON.stringify({
                "schedulerConfigId": schedulerConfigId,
                "enabled": enabled
            }),
            success: function (response) {
                console.log(response);

            },
            error: function (response) {
console.log(response);
//                alert("Error enabling scheduler! \n" + response.responseJSON.error);

            }
        })

//        alert(enabled);
//        alert(schedulerConfigId);

    });

    $("#datepicker").datepicker();
    $('#timepicker').timepicker({
        timeFormat: 'HH:mm:ss',
            interval: 30,
            minTime: '00',
            maxTime: '11:30pm',
            defaultTime: '11',
            startTime: '00:00',
            dynamic: false,
            dropdown: true,
            scrollbar: true
    });

});

function updateScheduleTypeOptions(option) {

    if (option == "ONCE") {
        $("#onceSchedule").removeClass("hidden");
        $("#repeatableSchedule").addClass("hidden");
    }
    else if (option == "REPEATABLE") {
        $("#repeatableSchedule").removeClass("hidden");
        $("#onceSchedule").addClass("hidden");
    }

}