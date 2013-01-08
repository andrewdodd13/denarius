<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ include file="common/header.jsp" %>
	<div class="container-fluid">
		<!-- Month Header -->
		<div class="row-fluid border-bottom">
			<div class="span1">
				<h2>&lt;&lt;</h2>
			</div>
			<div class="span10 centered">
				<h2 id="month-title">[Loading...]</h2>
			</div>
			<div class="span1">
				<h2>&gt;&gt;</h2>
			</div>
		</div>
		
		<!-- Top Row -->
		<div class="row-fluid">
			<!-- Left Column: Accounts + Balances -->
			<div class="span6">
				<h3>Accounts &amp; Balances</h3>
				<table id="accounts-table" class="table">
					<thead>
						<tr>
							<th>Account Name</th>
							<!-- ko foreach: headers -->
							<th data-bind="text: $data"></th>
							<!-- /ko -->
						</tr>
					</thead>
					<tbody data-bind="foreach: accounts">
						<tr>
							<td data-bind="text: $data.accountName"></td>
							<!-- ko foreach: $data.entries -->
							<td>
								<input type="text" data-bind="value: $data.valueOnDate" class="input-mini" />
								<span data-bind="text: formatCurrency($data.valueOnDate)"></span>
							</td>
							<!-- /ko -->
						</tr>
					</tbody>
					<tfoot>
						<tr>
							<td>Totals</td>
							<!-- ko foreach: totals -->
							<td data-bind="text: formatCurrency($data.totalValue)"></td>
							<!-- /ko -->
						</tr>
					</tfoot>
				</table>
			</div>
			<!-- Right Column: Totals, Targets, Profit -->
			<div class="span6">
				<h3>At-A-Glance</h3>
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
				<h3>Itinerary</h3>
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
	</div>
	
	<script type="text/javascript" src="resources/js/denarius-home.js"></script>
<%@ include file="common/footer.jsp" %>