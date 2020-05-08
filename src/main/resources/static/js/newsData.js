$(document).ready(function() {

    // updates news table when the screen loads first time
    updateNewsTable($("#aggregatorId option:selected").val());

    $("#aggregatorId").change(function() {

        updateNewsTable($("#aggregatorId option:selected").val());

    });

});

function updateNewsTable(option) {

    $.ajax({
        type: "GET",
        url: "/ajax/news/data/" + option,
        success: function (response) {

            $("#dataTable").html(response);

        },
        error: function (response) {
             console.log(response);
    //                alert("Error enabling scheduler! \n" + response.responseJSON.error);

        }
    });

}