<%@ include file="/WEB-INF/jsp/base.jspf" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Seamless Signon</title>
	</head>
	<body>
		<form id="seamless" name="seamless" action='${seamlessLoginUrl}' method="post" > 
			<input type="hidden" name="j_username" id="j_username" value='${userName}' />
			<input type="hidden" name="j_password" id="j_password" value='${password}' />
		</form>
		
		<script type="text/javascript">
 			function myfunc () {
 				var frm = document.getElementById("seamless");
 				frm.submit();
 			}
 			window.onload = myfunc;
 		</script>
	</body>
</html>