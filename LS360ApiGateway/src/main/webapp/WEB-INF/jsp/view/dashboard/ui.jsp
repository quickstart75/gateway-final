<spring:message code="title.roleList" var="listRoles" />
<template:basic htmlTitle="${listRoles}" bodyTitle="${listRoles}">
    <c:choose>
        <c:when test="${empty token}">
            <i><spring:message code="message.token.none" /></i>
            <a href="<c:url value="/dashboard/token" />"><spring:message code="nav.item.token.get" /></a><br />
            <c:out value="${error}" />&nbsp;
        </c:when>
        <c:otherwise>
            <c:out value="${token}" />&nbsp;  
        </c:otherwise>
    </c:choose>
</template:basic>
