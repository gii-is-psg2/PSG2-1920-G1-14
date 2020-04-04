<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="donations">
    <h2>Donations</h2>

    <table id="donationsTable" class="table table-striped">
        <thead id="donationsTable">
        <tr>
        	
            <th style="width: 150px;">Amount</th>
            <th style="width: 200px;">Client</th>
            
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${donations}" var="donation">
            <tr>
               
                <td>
               
              	<dt>Amount</dt>
                    <c:out value="${donation.amount}"/>
                </td>
                <td>
                    <c:out value="${donation.client}"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>