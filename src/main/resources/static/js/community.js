/**
 * 回复请求
 */
function post() {
    var question_id = $("#question_id").val();
    var comment_content = $("#comment_content").val();
    commentToTarget(question_id,1,comment_content);
    // console.log(comment_id);
    // console.log(comment_content);
}

function commentToTarget(targetId, type, content) {
    // alert("targetId"+targetId);
    // alert("type"+type);
    // alert("content"+content);
    // console.log(targetId);
    // console.log(type);
    // console.log(content);
    if (!content) {
        alert("您输入的内容不能为空");
        return;
    }
    $.ajax({
        url: "/comment", //请求的url地址
        dataType: "json", //返回格式为json
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }), //参数值
        type: "post", //请求方式
        success: function (response) {
            //请求成功时处理
            if (response.code == 200) {
                window.location.reload();
            } else {
                if (response.code == 2003) {
                    var confirm1 = confirm(response.message);
                    if (confirm1) {
                        window.open("https://github.com/login/oauth/authorize?client_id=41ffae5a42105e0c8352&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closePage", true);
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
}

function comment(e) {
    var commentId = e.getAttribute("data-id");
    // var question_id = $("#question_id").val();
    var content = $("#input-" + commentId).val();
    console.log(commentId);
    console.log(content);
    commentToTarget(commentId,2,content);
}

function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);
    //获取二级评论的状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        //折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {
            //展开二级评论
            comments.addClass("in");
            // 标记二级评论展开状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        } else {
            $.getJSON("/comment/" + id, function (data) {
                console.log(data);
                $.each(data.data.reverse(), function (index, comment) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
                //展开二级评论
                comments.addClass("in");
                // 标记二级评论展开状态
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            });

                    // $.getJSON("/comment/" + id,function (data) {
                    //     console.log(data);
                    //     var subCommentContainer = $("#comment-" + id);
                    //     $.each(data.data,function (index,comment) {
                    //         var c = $("<div/>", {
                    //             "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments",
                    //             html:comment.content
                    //         });
                    //         subCommentContainer.prepend(c);
                    //     });
                    //     //展开二级评论
                    //     comments.addClass("in");
                    //     //二级评论状态
                    //     e.setAttribute("data-collapse", "in");
                    //     e.classList.add("active");
                    // });
                }
            }
}