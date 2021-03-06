<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="create cause">
   
        <h2>
            <c:if test="${cause['new']}">New </c:if> Cause
        </h2>
        <form:form modelAttribute="cause" class="form-horizontal">
            <input type="hidden" name="id" value="${cause.id}"/>
            <div class="form-group has-feedback">
                
                <petclinic:inputField label="Name" name="name"/>
                <petclinic:inputField label="Description" name="description"/>
                <petclinic:numberField label="Budget Target" name="budgetTarget" step="0.01" min="0"/>
                <petclinic:inputField label="Organization" name="organization"/>
              
            </div>
             <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${cause['new']}">
                        <button class="btn btn-default" type="submit">Add Cause</button>
                    </c:when>
                </c:choose>
            </div>
        </div>
        </form:form>
</petclinic:layout>
