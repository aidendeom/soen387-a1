<%@ page trimDirectiveWhitespaces="true" %>
<%@ page language="java" contentType="text/xml; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<?xml version="1.0" encoding="UTF-8"?>
<checkers>
<status>success</status>
 <games>
 <c:forEach var="chal" items="${challenges }">
  <game id="${chal.id}" status="${chal.status}">
   <firstPlayer refid="${chal.challenger.id}" />
   <secondPlayer refid="${chal.challengee.id}" />
   <currentPlayer refid="${chal.challenger.id}" />
  </game>
</c:forEach>
 </games>
</checkers>