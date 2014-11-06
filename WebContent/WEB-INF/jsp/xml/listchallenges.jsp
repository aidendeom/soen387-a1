<%@ page trimDirectiveWhitespaces="true" %>
<%@ page language="java" contentType="text/xml; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<?xml version="1.0" encoding="UTF-8"?>
<checkers>
<status>success</status>
 <challenges>
 <c:forEach var="chal" items="${challenges }">
  <challenge id="${chal.id}" version="${chal.version}" status="${chal.status.id}">
   <challenger refid="${chal.challenger.id}" />
   <challengee refid="${chal.challengee.id}" />
  </challenge>
</c:forEach>
 </challenges>
</checkers>