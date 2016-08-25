<html>
<head>

<script type="text/javascript">

//-----------------
// 屏蔽浏览器工具条
//-----------------
function newPage(pageFile,winNm){
 new_window = window.open(pageFile,winNm,'resizable=no,scrollbars=no,status=no,width=0,height=0');
 new_window.moveTo(0,0);
 new_window.resizeTo(new_window.screen.availWidth, new_window.screen.availHeight);
}

//--------------------
// 不提示关闭对话框的浏览器关闭方法。
//--------------------
function closeWindow() {
 var w=window.open("","_top")
 w.opener=window
 w.close()
}

newPage("index", "LoginWin");
</script>
</head>
<body>
</body>
</html>
