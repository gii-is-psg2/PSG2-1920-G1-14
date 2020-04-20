<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="causes">
	<h2>Causes</h2>
	<input type="hidden" name="id" value="${cause.id}" />
	<table id="causesTable" class="table table-striped">
		<thead id="causeTable">
			<tr>

				<th>Name</th>
				<th>Description</th>
				<th>Budget Target</th>
				<th>Organization</th>
				<th>Amount</th>
				<th>Donation</th>

			</tr>
		</thead>
		<tbody>
			<c:forEach items="${causes}" var="cause">
				<tr>
					<td><spring:url value="/causes/{causeId}" var="causeUrl">
							<spring:param name="causeId" value="${cause.id}" />
						</spring:url> <a href="${fn:escapeXml(causeUrl)}"><c:out
								value="${cause.name} " /></a></td>
					<td><c:out value="${cause.description}" /></td>
					<td><fmt:formatNumber type="currency" maxFractionDigits="2" value="${cause.budgetTarget}" currencySymbol="&euro;" /></td>
					<td><c:out value="${cause.organization}" /></td>
					<td><fmt:formatNumber type="currency" maxFractionDigits="2" value="${cause.amount}" currencySymbol="&euro;" /></td>

					<td>
                        <c:choose>
                            <c:when test="${cause.closed}">
                                Budget target achieved
                            </c:when>
                            <c:otherwise>
                            <spring:url value="/causes/{causeId}/donations/new"
                                        var="newDonationUrl">
                                <spring:param name="causeId" value="${cause.id}" />
                            </spring:url> <a href="${fn:escapeXml(newDonationUrl)}">Make a donation</a>
                            </c:otherwise>
                        </c:choose>
                    </td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<spring:url value="causes/new" var="addCause">
					</spring:url>
					<a href="${fn:escapeXml(addCause)}" class="btn btn-default">Add New Cause</a>
</petclinic:layout>
