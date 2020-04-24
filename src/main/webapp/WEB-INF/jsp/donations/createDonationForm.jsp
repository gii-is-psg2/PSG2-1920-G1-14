<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="create donation">
   
    <h2>
        <c:if test="${donation['new']}">New </c:if> Donation
    </h2>

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
        <form:form modelAttribute="donation" class="form-horizontal">
            <input type="hidden" name="id" value="${donation.id}"/>
            <form:hidden path="cause" value="${donation.cause.id}"/>
            <div class="form-group has-feedback">
                
                <petclinic:selectField names="${owners}"  size="${owners.size()}" label="Client" name="client"/>
                <petclinic:numberField label="Amount" name="amount" step="0.01" min="0"/>
                
              
            </div>
             <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${donation['new']}">
                        <button class="btn btn-default" type="submit">Add Donation</button>
                    </c:when>
                </c:choose>
            </div>
        </div>
        </form:form>
</petclinic:layout>
