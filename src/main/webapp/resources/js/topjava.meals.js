$(function () {
    makeEditable({
        ajaxUrl: "ajax/meals/",
        datatableApi: $("#datatable").DataTable ({
            "pading": false,
            "info": true,
            "columns":[
                {
                    "data":"dateTime"
                },
                {
                    "data":"description"
                },
                {
                    "data":"calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        })}
    )
});
