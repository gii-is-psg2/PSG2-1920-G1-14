<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="donations">

    <div class="panel panel-default">
        <div class="panel-heading">
            <h4>${cause.name} - ${cause.organization}</h4>
        </div>
        <div class="panel-body">
            <table class="table table-striped">
                <tr>
                    <th>Description</th>
                    <td>${cause.description}</td>
                </tr>
                <tr>
                    <th>Budget target</th>
                    <td><fmt:formatNumber type="currency" maxFractionDigits="2" value="${cause.budgetTarget}" currencySymbol="&euro;" /></td>
                </tr>
                <tr>
                    <th>Budget achieved</th>
                    <td><fmt:formatNumber type="currency" maxFractionDigits="2" value="${cause.amount}" currencySymbol="&euro;" /></td>
                </tr>
                <tr>
                    <th>Status</th>
                    <td><c:choose>
                        <c:when test="${!cause.closed}">Opened</c:when>
                        <c:otherwise>Closed</c:otherwise>
                    </c:choose></td>
                </tr>
            </table>
        </div>
    </div>

    <hr>
    <h4>Donations</h4>
    <table id="donationsTable" class="table table-striped">
        <thead>
        <tr>
        	
            <th>Amount</th>
            <th>Client</th>
          
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${donations}" var="donation">
            <tr>
               
                <td>
                    <fmt:formatNumber type="currency" maxFractionDigits="2" value="${donation.amount}" currencySymbol="&euro;" /></td>
                </td>
                <td>
                    <c:out value="${donation.client.firstName} ${donation.client.lastName}"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
