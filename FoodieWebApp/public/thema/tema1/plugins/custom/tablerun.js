$("#table").DataTable({
    "responsive": true, "lengthChange": false,searching: false, "autoWidth": false,"paging":false,"info":false,"ordering":false,"oLanguage": {"sZeroRecords": "", "sEmptyTable": ""}
}).buttons().container().appendTo('#table_wrapper .col-md-6:eq(0)');
