<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
	<title>Denarius</title>
	
	<base href="http://localhost:8080/Denarius/"></base>
	
	<script src="resources/js/jquery-1.7.2.min.js"></script>	
	<script src="resources/js/bootstrap.min.js"></script>
	<script src="resources/js/knockout-2.0.0.js"></script>
	<script src="resources/js/denarius.js"></script>
	
	<link href="resources/css/denarius.css" rel="stylesheet" />
    <link href="resources/css/bootstrap.min.css" rel="stylesheet" />
    <link href="resources/css/bootstrap-responsive.min.css" rel="stylesheet" />	
</head>
<body>
	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
<!-- 					<span class="icon-bar"></span> -->
				</a>
				<a style="float: left" href="#"><img src="resources/img/logo.png" height="30" alt="denarius logo"></a>
				<div class="nav-collapse">
					<ul class="nav">
						<li class="active"><a href="#">Home</a></li>
						<li class="about"><a href="#about">About</a></li>
					</ul>
					<p class="navbar-text pull-right">Data Backend: Spring MVC</p>
				</div>
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<!-- Top Row -->
		<div class="row-fluid">
			<!-- Left Column: Accounts + Balances -->
			<div class="span6">
				<h2>Accounts &amp; Balances</h2>
				<table class="table">
					<thead>
						<tr>
							<th>Account</th>
							<th>1<sup>st</sup></th>
							<th>2<sup>nd</sup></th>
							<th>9<sup>th</sup></th>
							<th>16<sup>th</sup></th>
							<th>23<sup>rd</sup></th>
							<th>30<sup>th</sup></th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>Cash</td>
							<td>£20.00</td>
							<td>£20.00</td>
							<td>£50.00</td>
							<td>£45.00</td>
							<td>£15.00</td>
							<td class="value-predicted">???</td>
						</tr>
						<tr>
							<td>Change</td>
							<td>£26.49</td>
							<td>£26.49</td>
							<td>£26.15</td>
							<td>£26.55</td>
							<td>£24.45</td>
							<td class="value-predicted">???</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- Right Column: Totals, Targets, Profit -->
			<div class="span6">
				<h2>At-A-Glance</h2>
				<div class="container-fluid">
					<div class="row-fluid">
						<div class="span6">Overall Funds:</div>
						<div class="span6">£100</div>
					</div>
					<div class="row-fluid">
						<div class="span6">Monthly Difference:</div>
						<div class="span6">+ £30.12</div>
					</div>
					<div class="row-fluid">
						<div class="span6">Monthly Variation:</div>
						<div class="span6">= £0.00</div>
					</div>
				</div>
			</div>
		</div>
	
		<!-- Bottom Row -->	
		<div class="row-fluid">
			<!-- Itinerary -->
			<div class="span12">
				<h2>Itinerary</h2>
				<table class="table">
					<thead>
						<tr>
							<th>Date</th>
							<th>Description</th>
							<th>Account</th>
							<th>Amount</th>
							<th>Recurring?</th>
							<th>Edit</th>
							<th>Remove</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>23-Apr-2012</td>
							<td>Robyn's Phone Bill</td>
							<td>Cashcard</td>
							<td>£20.00</td>
							<td>Y</td>
							<td>E</td>
							<td>R</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		
		<hr />
		
		<footer>
			<p>&copy; Andrew Dodd 2012</p>
		</footer>
	</div>
</body>
</html>