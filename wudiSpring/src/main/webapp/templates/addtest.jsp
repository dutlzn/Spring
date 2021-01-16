<%@ page pageEncoding="UTF-8"%>
<html>
    <head>
        <title>test</title>
    </head>
    <body>
        <table>
            <h2 style="color:red">JSP表单</h2>
            <br>
            <form id="headlineTest" method="POST" action="/wudiSpring/headline/add">
            头条说明:<input type="text" name="lineName" value="123"><br>
            头条链接:<input type="text" name="lineLink" value="123"> <br>
            头条图片地址:<input type="text" name="lineImg" value="123"> <br>
            优先级:<input type="text" name="priority" value="123"><br>
            结果: <h3>状态码：${result.code}
            信息: ${result.msg} </h3>
            <br>
            <input type="submit" value="提交">
           </form>
           </table>
    </body>
</html>