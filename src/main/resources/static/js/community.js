function post() {
    var comment_id = $("#comment_id").val();
    var comment_content = $("#comment_content").val();
    if(!comment_content){
        alert("您输入的内容不能为空");
        return;
    }
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
                window.location.reload();
            } else {
                if(response.code == 2003){
                    var confirm1 = confirm(response.message);
                    if(confirm1){
                        window.open("https://github.com/login/oauth/authorize?client_id=41ffae5a42105e0c8352&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closePage",true);
                    }
                } else {
                    alert(response.message);
                }
            }
        },
        error: function () {
            //请求出错处理
        }
    });
    console.log(comment_id);
    console.log(comment_content);
}