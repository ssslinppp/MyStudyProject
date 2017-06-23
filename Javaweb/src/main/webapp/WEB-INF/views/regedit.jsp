<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
<title>数据校验</title>
  <style>
     .errorClass{color:red}
  </style>
</head>
<body>
  <form:form modelAttribute="stuff"  action='validateTest'  >
    <form:errors path="*" cssClass="errorClass" element="div"/>
    <table>
        <tr>
           <td>用户名：</td>
           <td>
              <form:errors path="username" cssClass="errorClass" element="div"/>
              <form:input path="username"  />
           </td>
        </tr>
        <tr>
         <td>密码：</td>
           <td>
              <form:errors path="passwd" cssClass="errorClass"  element="div"/>
              <form:password path="passwd" />  
           </td>
        </tr>
        <tr>
         <td>真实名：</td>
           <td>
              <form:errors path="realName" cssClass="errorClass"  element="div"/>
              <form:input path="realName" />  
           </td>
        </tr>
        <tr>
         <td>生日：</td>
           <td>
             <form:errors path="birthday" cssClass="errorClass" element="div"/>
             <form:input path="birthday" />    
           </td>
        </tr>
        <tr>
         <td>工资：</td>
           <td>
             <form:errors path="salary" cssClass="errorClass" element="div"/>
             <form:input path="salary" />
           </td>
        </tr>
        <tr>
         <td colspan="2"><input type="submit" name="提交"/></td>
        </tr>       
    </table>
  </form:form>
</body>
</html>