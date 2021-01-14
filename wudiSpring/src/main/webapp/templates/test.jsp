<%@ page pageEncoding="UTF-8"%>
<html>
    <head>
        <title>test</title>
    </head>
    <body>
        <table>
            <h2 style="color:red">表单提交:</h2>
            <br>
            <form id="info" method="post" action="/wudiSpring/headline/add">
            姓名:<input type="text" name="name"><br>
            学号:<input type="text" name="number"><br>
            优先级:<input  type="test" name="priority"><br>
            结果: <h3>状态码：${result.code}
            信息: ${result.msg} </h3>
            <br>
            <input type="submit" value="提交">
    </body>
</html>