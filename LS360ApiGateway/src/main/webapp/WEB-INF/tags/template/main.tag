
<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" %>
<%@ attribute name="htmlTitle" type="java.lang.String" rtexprvalue="true" required="true" %>
<%@ attribute name="bodyTitle" type="java.lang.String" rtexprvalue="true" required="true" %>
<%@ attribute name="headContent" fragment="true" required="false" %>
<%@ attribute name="navigationContent" fragment="true" required="true" %>

<%@ include file="/WEB-INF/jsp/base.jspf" %>

<!DOCTYPE html>
<html>
    <head>
        <title><spring:message code="title.customer.support" /> :: <c:out value="${fn:trim(htmlTitle)}" /></title>
        <link rel="stylesheet" href="<c:url value="/resource/stylesheet/main.css" />" />
        <script src="http://code.jquery.com/jquery-2.2.2.js"></script>
        <script type="text/javascript" lang="javascript">
            var postInvisibleForm = function(url, fields) {
            	var form = $('<form id="mapForm" method="post"></form>').attr({ action: url, style: 'display: none;' });
            	for(var key in fields) {
                	if(fields.hasOwnProperty(key)) {
                		form.append($('<input type="hidden">').attr({name: key, value: fields[key]}));
                	}     
            	}
            	
            	form.append($('<input type="hidden">').attr({name: '${_csrf.parameterName}', value: '${_csrf.token}'}));
            	
           	 	$('body').append(form);
            	form.submit();
        	};
        </script>
        <jsp:invoke fragment="headContent" />
    </head>
    <body>
        <h1><spring:message code="title.company" /></h1>
        <table border="0" id="bodyTable">
            <tbody>
                <tr>
                    <td class="sidebarCell">
                        <jsp:invoke fragment="navigationContent" />
                    </td>
                    <td class="contentCell">
                        <h2><c:out value="${fn:trim(bodyTitle)}" /></h2>
                        <jsp:doBody />
                    </td>
                </tr>
            </tbody>
        </table>      
    </body>
</html>
