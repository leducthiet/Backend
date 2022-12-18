$(document).ready(function () {
        $('#accountTable').DataTable({
            lengthMenu: [
                [2, 5, 10, -1],
                [2, 5, 10, 'All'],
            ],
            "language": {
                "decimal":        "",
                "emptyTable":     "Chưa có dữ liệu",
                "info":           "Hiển thị từ _START_ đến _END_ của _TOTAL_ kết quả",
                "infoEmpty":      "Hiển thị từ 0 đến 0 của 0 kết quả",
                "infoFiltered":   "(Lọc từ _MAX_ kết quả)",
                "infoPostFix":    "",
                "thousands":      ",",
                "lengthMenu":     "Hiển thị _MENU_ kết quả",
                "loadingRecords": "Đang tải...",
                "processing":     "",
                "search":         "Tìm kiếm:",
                "zeroRecords":    "Không tìm thấy kết quả phù hợp",
                "paginate": {
                    "first":      "Đầu",
                    "last":       "Cuối",
                    "next":       "Sau",
                    "previous":   "Trước"
                },
                "aria": {
                    "sortAscending":  ": Sắp xếp tăng dần",
                    "sortDescending": ": Sắp xếp giảm dần"
                }
            }
        });
    });