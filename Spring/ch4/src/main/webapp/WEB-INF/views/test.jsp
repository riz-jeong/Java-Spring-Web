<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
</head>
<body>
<h2>commentTest</h2>
comment: <input type="text" name="comment"><br>
<button id="sendBtn" type="button">SEND</button>
<button id="modBtn" type="button">수정</button>
<div id="commentList"></div>
<script>
    let bno = 3;
    let showList = function(bno){
        $.ajax({
            type:'GET',       // 요청 메서드
            url: '/ch4/comments?bno='+bno,  // 요청 URI
            success : function(result){
                $("#commentList").html(toHtml(result));
            },
            error   : function(){ alert("error") } // 에러가 발생했을 때, 호출될 함수
        }); // $.ajax()
    }
    $(document).ready(function(){
        showList(bno);

        $("#modBtn").click(function(){
            let cno = $(this).attr("data-cno");
            let comment = $("input[name=comment]").val();

            if(comment.trim()=='') {
                alert("댓글을 입력해주세요.");
                $("input[name=comment]").focus()
                return;
            }

            // /ch4/comments?bon=1000 POST
            $.ajax({
                type:'PATCH',       // 요청 메서드
                url: '/ch4/comments/'+cno,  // 요청 URI /ch4/comments/1000 PATCH
                headers : { "content-type": "application/json"}, // 요청 헤더
                data : JSON.stringify({cno:cno, comment:comment}),  // 서버로 전송할 데이터. stringify()로 직렬화 필요.
                success : function(result){
                    alert(result);
                    showList(bno);
                },
                error   : function(){ alert("error") } // 에러가 발생했을 때, 호출될 함수
            }); // $.ajax()
        });

        $("#sendBtn").click(function(){
            let comment = $("input[name=comment]").val();

            if(comment.trim()=='') {
                alert("댓글을 입력해주세요.");
                $("input[name=comment]").focus()
                return;
            }

            // /ch4/comments?bon=1000 POST
            $.ajax({
                type:'POST',       // 요청 메서드
                url: '/ch4/comments?bno='+bno,  // 요청 URI /ch4/comments?bon=1000 POST
                headers : { "content-type": "application/json"}, // 요청 헤더
                data : JSON.stringify({bno:bno, comment:comment}),  // 서버로 전송할 데이터. stringify()로 직렬화 필요.
                success : function(result){
                    alert(result);
                    showList(bno);
                },
                error   : function(){ alert("error") } // 에러가 발생했을 때, 호출될 함수
            }); // $.ajax()
        });

        $("#commentList").on("click",".modBtn",function(){
            let cno = $(this).parent().attr("data-cno");
            let comment = $('span.comment', $(this).parent()).text();

            // 1. comment의 내용을 input에 뿌려주기
            $("input[name=comment]").val(comment);
            // 2. cno전달하기
            $("#modBtn").attr("data-cno", cno);
        });

        // $(".delBtn").click(function(){
        //     alert("delBtnClicked");
        // });
        $("#commentList").on("click",".delBtn",function(){
            let cno = $(this).parent().attr("data-cno");
            let bno = $(this).parent().attr("data-bno");
            $.ajax({
                type:'DELETE',       // 요청 메서드
                url: '/ch4/comments/'+cno+'?bno='+bno,  // 요청 URI
                success : function(result){
                    alert(result)
                    showList(bno);
                },
                error   : function(){ alert("error") } // 에러가 발생했을 때, 호출될 함수
            }); // $.ajax()
        });
    });

    let toHtml = function(comments) {
        let tmp = "<ul>";

        comments.forEach(function(comment){
            tmp += '<li data-cno=' + comment.cno
            tmp += ' data-pcno=' + comment.pcno
            tmp += ' data-bno=' + comment.bno + '>'
            tmp += ' commenter=<span class="commenter">' + comment.commenter + '</span>'
            tmp += ' comment=<span class="comment">' + comment.comment + '</span>'
            tmp += ' up_date=' + comment.up_date
            tmp += '<button class="modBtn">수정</button>'
            tmp += '<button class="delBtn">삭제</button>'
            tmp += '</li>'
        })

        return tmp + "</ul>";
    }
</script>
</body>
</html>