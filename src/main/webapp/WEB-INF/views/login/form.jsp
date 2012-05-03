<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../common/header.jsp"%>
<div class="container-fluid">
<c:if test="${not empty message }">
	<div class="row-fluid">
		<div class="span12">
			<div class="alert centered">${message}</div>
		</div>
	</div>
</c:if>
	<div class="row-fluid">
		<div class="span6 centered">
			<h2>Login</h2>
			<form method="post" action="j_spring_security_check">
				<p>
					<label for="j_username">Username:</label> <input id="j_username" name="j_username" type="text" />
				</p>
				<p>
					<label for="j_password">Password:</label> <input id="j_password" name="j_password" type="password" />
				</p>
				<p>
					<input type="submit" value="Login" />
				</p>
			</form>
		</div>
		<div class="span6 centered">
			<h2>Register</h2>
			<form method="post" action="login/register">
				<p>
					<label for="r_username">Username:</label> <input id="r_username" name="r_username" type="text" />
				</p>
				<p>
					<label for="r_password">Password:</label> <input id="r_password" name="r_password" type="password" />
				</p>
				<p>
					<label for="r_password_confirm">Confirm Password:</label> <input id="r_password_confirm" name="r_password_confirm"
						type="password" />
				</p>
				<p>
					<label for="r_email">Email Address:</label> <input id="r_email" name="r_email" type="text" />
				</p>
				<p>
					<label for="r_fullname">Full Name:</label> <input id="r_fullname" name="r_fullname" type="text" />
				</p>
				<p>
					<input type="submit" value="Register" />
				</p>
			</form>
		</div>
	</div>
</div>
<%@ include file="../common/footer.jsp"%>