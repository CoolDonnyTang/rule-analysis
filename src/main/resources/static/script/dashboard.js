function checkAddrInfo() {
    $.ajax({
        type:"get",
        url:appName+"addr/status" ,
        error:function() {
            alert("连接服务器失败");
        },
        success:function(result) {
            if(result.status == 200) {
                if(result.data == true) {
                    $("#addrContainer").hide();
                } else {
                    $("#addrContainer").show();
                }
            } else {
                alert(result.message);
            }
        }
    });
}

function submitAddrInfo(addrInfo) {
    if(!checkIpAddr(addrInfo.sendToAddr)) {
        alert("规则发送地址格式错误");
        return;
    }
    if(!checkIpPort(addrInfo.sendToPort)) {
        alert("规则发送端口格式错误");
        return;
    }
    if(!checkIpPort(addrInfo.receivePort)) {
        alert("数据接收端口格式错误");
        return;
    }
    $.ajax({
        data:addrInfo,
        dataType:"json",
        type:"post",
        url:appName+"addr" ,
        error:function() {
            alert("连接服务器失败");
        },
        success:function(result) {
            if(result.status == 200) {
                $("#addrContainer").hide();
            } else {
                alert(result.message);
            }
        }
    });
}

function loadCurrentIpReg() {
    //clear
    $("#includeReg").val("().().().():()");
    $("#excludeReg").val("().().().():()");

    //load info
    $.ajax({
        type:"get",
        url:appName+"ipReg" ,
        error:function() {
            alert("连接服务器失败");
        },
        success:function(result) {
            if(result.status == 200) {
                var data = result.data;
                updateIpRegAndStatus(data);
            } else {
                alert(result.message);
            }
        }
    });
}

function updateIpRegAndStatus(data) {
    console.log(data);
    if(data) {
        $("#includeReg").val(data.sourceIP);
        $("#excludeReg").val(data.targetIP);

        //set message
        $("#ipRegContent").html("");
        var $message = $("<span class='glyphicon glyphicon-info-sign ip-reg-icon' aria-hidden='true'></span>");
        var $status = $("<span></span>");
        $message.attr("title", data.message);
        $status.text(data.status);
        $("#ipRegContent").append($message);
        $("#ipRegContent").append($status);
        if("Failed" === data.status) {
            $message.addClass("color-red");
            $status.addClass("color-red");
        } else if("Success" === data.status) {
            $message.addClass("color-green");
            $status.addClass("color-green");
        }
    } else {
        $("#includeReg").val("().().().():()");
        $("#excludeReg").val("().().().():()");
        $("#ipRegContent").html("");
    }
}

function updateIpReg(ipReg) {
    //check ip regex
    if(!checkIpInput(ipReg.sourceIP)) {
        alert("源IP规则格式错误！！！");
        return;
    }
    if(!checkIpInput(ipReg.targetIP)) {
        alert("目的IP规则格式错误！！！");
        return;
    }

    console.log(ipReg);

    $.ajax({
        data:ipReg,
        dataType:"json",
        type:"post",
        url:appName+"ipReg",
        error:function() {
            alert("连接服务器失败");
        },
        success:function(result) {
            updateIpRegAndStatus(result.data)
            if(result.status == 200) {
                alert("Success.");
            } else {
                alert(result.message);
            }
        }
    });
}


function checkIpRegex(IpRegex) {
    if(IpRegex===null || IpRegex===undefined) {
        return false
    }
    var reg = /^\((([1-9])|([1-9]\d)|(1\d{2})|((2[0-4]\d)|(25[0-5])))(\|(([1-9])|([1-9]\d)|(1\d{2})|((2[0-4]\d)|(25[0-5]))))*\)(\.\((([1-9])|([1-9]\d)|(1\d{2})|((2[0-4]\d)|(25[0-5])))(\|(([1-9])|([1-9]\d)|(1\d{2})|((2[0-4]\d)|(25[0-5]))))*\)){3}:\(((\d)|([1-9]\d{1,3})|((5\d{4})|(6[0-4]\d{3})|(65[0-4]\d{2})|(655[0-2]\d)|(6553[0-5])))(\|((\d)|([1-9]\d{1,3})|((5\d{4})|(6[0-4]\d{3})|(65[0-4]\d{2})|(655[0-2]\d)|(6553[0-5]))))*\)$/;
    return reg.test(IpRegex);
}

function checkIpInput(IpInput) {
    if(IpInput===null || IpInput===undefined) {
        return false;
    }
    var reg = /\([0-9\-\|]*\)(\.\([0-9\-\|]*\)){3}\:\([0-9\-\|]*\)/;
    return reg.test(IpInput);
}