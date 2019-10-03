function login(page) {
    var token = $("#tokenInput").val();
    if(token.trim() === "") {
        alert("请输入Token")
    } else {
        $.ajax({
            data:{"token":token},
            dataType:"json",
            type:"post",
            url:appName+"login" ,
            error:function() {
                alert("连接服务器失败");
            },
            success:function(result) {
                if(result.status == 200) {
                    location.href=appName + page;
                } else {
                    alert(result.message);
                }
            }
        });
    }

}