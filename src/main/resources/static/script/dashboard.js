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

                //展示解析的命令
                displayCmd(result.data);

                //展示机器码
                displayBinaryCode(result.data);

                //绘制状态图
                displayStatus(result.data);
            } else {
                alert(result.message);
            }
        }
    });
}

function displayCmd(data) {
    $("#cmdDisplay").empty();
    var cmds = data.cmdParseResult.allCommandString;
    if(cmds!=null && cmds!=undefined) {
        for(var index in cmds) {
            var $li = $("<li></li>");
            $li.text(cmds[index]);
            $("#cmdDisplay").append($li);
        }
    }
}

function displayBinaryCode(data) {
    $("#binaryCode").empty();
    var code = data.binaryCode;
    if(code!=null && code!=undefined) {
        for(var index in code) {
            var $li = $("<li></li>");
            $li.text(code[index] + "-" + code[index].length);
            $("#binaryCode").append($li);
        }
    }
}

function displayStatus(data) {
    $("#statusDisplay").empty();

    var status = data.cmdParseResult.allStatus;
    console.log("draw status");
    console.log(status);
    if(status!=null && status!=undefined && status.length>0) {
        var leftPadding = 20;
        var topPadding = 50;
        var top = 200;
        var yy = 200;

        var circleBorderColor = "#000";
        var circleBorderWidth = 3;
        var circleFillColor = "#fff";

        var width = (status.length+1)*yy + leftPadding*2;
        var height = topPadding*2 + top;

        var lineColor = "#003322";
        var lineWidth = 3;

        var textTopSgap = 8;
        var textColor = "#17A9C6";
        var textFontSize = 16;
        var textFontFamily = "century gothic";

        var falsePointY = topPadding + top;
        var falsePointX = width/2;
        var r = 15;

        var paper = Raphael("statusDisplay", width, height);

        //draw false
        var circleFalse = paper.circle(falsePointX, falsePointY, r).attr({
            fill:circleFillColor, //填充色
            stroke:circleBorderColor, //边缘线
            "stroke-width":circleBorderWidth //
        });
        var textFalse = paper.text(falsePointX-r-circleBorderWidth, falsePointY+r+circleBorderWidth+textTopSgap,"False").attr({
            "fill":textColor, // font-color
            "font-size":textFontSize, // font size in pixels
            "text-anchor":"start",
            "font-family":textFontFamily // font family of the text
        });

        //初始节点
        var startPointY = topPadding;
        var startPointX = leftPadding;
        //draw start
        var circleStart = paper.circle(startPointX, startPointY, r).attr({
            fill:circleFillColor, //填充色
            stroke:circleBorderColor, //边缘线
            "stroke-width":circleBorderWidth //
        });
        var textStart = paper.text(startPointX-r-circleBorderWidth, startPointY-r-circleBorderWidth-textTopSgap,"Start").attr({
            "fill":textColor, // font-color
            "font-size":textFontSize, // font size in pixels
            "text-anchor":"start",
            "font-family":textFontFamily // font family of the text
        });

        //Action节点
        var actionPointX = width-leftPadding;
        //draw start
        var circleAction = paper.circle(actionPointX, startPointY, r).attr({
            fill:circleFillColor, //填充色
            stroke:circleBorderColor, //边缘线
            "stroke-width":circleBorderWidth //
        });
        var textAction = paper.text(actionPointX-r-circleBorderWidth-10, startPointY-r-circleBorderWidth-textTopSgap,"Action").attr({
            "fill":textColor, // font-color
            "font-size":textFontSize, // font size in pixels
            "text-anchor":"start",
            "font-family":textFontFamily // font family of the text
        });

        for(var i=0; i<status.length; i++) {
            var partStatus = status[i].join("|");
            var pointX = startPointX + (i+1)*yy;
            var circle = paper.circle(pointX, startPointY, r).attr({
                fill:circleFillColor, //填充色
                stroke:circleBorderColor, //边缘线
                "stroke-width":circleBorderWidth //
            });
            var textPart = paper.text(pointX-r-circleBorderWidth, startPointY-r-circleBorderWidth-textTopSgap,"Not empty part" + (i+1)).attr({
                "fill":textColor, // font-color
                "font-size":textFontSize, // font size in pixels
                "text-anchor":"start",
                "font-family":textFontFamily // font family of the text
            });

            var yy1 = startPointY;
            //起始节点连接其他节点
            if(i==0) {
                var startX = startPointX + r;
                var startY = yy1;
                var endX = pointX - r;
                var endY = yy1;
                var param = "M" + startX + "," + startY + "L" + endX + "," + endY;
                //连接到下一个节点
                paper.path(param).attr({
                    "stroke":lineColor,
                    "stroke-width": lineWidth
                });

                // //连接到false节点
                // var startX = startX - r;
                // var startY = yy1 + r;
                // var endX = falsePointX;
                // var endY = falsePointY - r;
                // var paramFalse = "M" + startX + "," + startY + "L" + endX + "," + endY;
                // //连接到下一个节点
                // paper.path(paramFalse).attr({
                //     "stroke":lineColor,
                //     "stroke-width": lineWidth
                // });
            }

            //连接到下一个节点
            var startX = pointX + r;
            var startY = yy1;
            var endX = startX + yy - 2*r;
            var endY = yy1;
            var param2 = "M" + startX + "," + yy1 + "L" + endX + "," + endY;
            console.log("p2: " + param2);
            paper.path(param2).attr({
                "stroke":lineColor,
                "stroke-width": lineWidth
            });

            //连接到false节点
            var startX = startX - r;
            var startY = yy1 + r;
            var endX = falsePointX;
            var endY = falsePointY - r;
            var paramFalse = "M" + startX + "," + startY + "L" + endX + "," + endY;
            //连接到下一个节点
            paper.path(paramFalse).attr({
                "stroke":lineColor,
                "stroke-width": lineWidth
            });

        }

    }
}

function drawLine(paper, startX, startY, endX, endY) {
    var paramFalse = "M" + startX + "," + startY + "L" + endX + "," + endY;
    //连接到下一个节点
    paper.path(paramFalse).attr({
        "stroke":lineColor,
        "stroke-width": lineWidth
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
            updateIpRegAndStatus(result.data);

            //展示解析的命令
            displayCmd(result.data);

            //展示机器码
            displayBinaryCode(result.data);

            //绘制状态图
            displayStatus(result.data);

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