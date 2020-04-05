<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="causes">
    <h2>Causes</h2>

    <table id="causesTable" class="table table-striped">
        <thead id="causeTable">
        <tr>
        	
            <th>Name</th>
            <th>Description</th>
            <th>Budget Target</th>
            <th>Organization</th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${causes}" var="cause">
            <tr>
                <td>
                    <spring:url value="/causes/{causeId}" var="causeUrl">
                        <spring:param name="causeId" value="${cause.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(causeUrl)}"><c:out value="${cause.name} "/></a>
                </td>
                <td>
                    <c:out value="${cause.description}"/>
                </td>
                <td>
                    <c:out value="${cause.budgetTarget}"/>
                </td>
                <td>
                    <c:out value="${cause.organization}"/>
                </td>
                <td>
                	<c:out value="${cause.amount}"></c:out>
                </td>
               
               <td>
                                <spring:url value="/causes/{causeId}/donations/new" var="newDonationUrl">
                                    <spring:param name="causeId" value="${cause.id}"/>
                                </spring:url>
                                <a href="${fn:escapeXml(newDonationUrl)}">Make a donation</a>
                            </td>
            
            
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>