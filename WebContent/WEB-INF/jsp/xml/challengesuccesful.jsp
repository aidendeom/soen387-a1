<%@ page trimDirectiveWhitespaces="true" %>
<%@ page language="java" contentType="text/xml; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<?xml version="1.0" encoding="UTF-8"?>
<checkers>
<status>success</status>
<challenge status="${challenge.status.id}" id="${challenge.id}" version="${challenge.version}">
<challenger refid="${challenge.challenger.id}" />
<challengee refid="${challenge.challengee.id}" />
</challenge>
</checkers>