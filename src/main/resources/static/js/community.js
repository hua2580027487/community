function post() {
    var comment_id = $("#comment_id").val();
    var comment_content = $("#comment_content").val();
    $.ajax({
        url: "/comment", //请求的url地址
        dataType: "json", //返回格式为json
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": comment_id,
            "content": comment_content,
            "type": 1
        }), //参数值
        type: "post", //请求方式
        success: function (response) {
            //请求成功时处理
            if (response.code == 200) {
                $("#comment_section").hide();
            } else {
                alert(response.message);
            }
        },
        error: function () {
            //请求出错处理
        }
    });
    console.log(comment_id);
    console.log(comment_content);
}