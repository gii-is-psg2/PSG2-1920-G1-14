<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="create donation">
   
        <h2>
            <c:if test="${donation['new']}">New </c:if> Donation
        </h2>
        <form:form modelAttribute="donation" class="form-horizontal">
            <input type="hidden" name="id" value="${donation.id}"/>
            <form:hidden path="cause" value="${donation.cause.id}"/>
            <div class="form-group has-feedback">
                
                <petclinic:inputField label="Name" name="client"/>
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
