<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="owners">

    <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#start").datepicker({dateFormat: 'yy/mm/dd'});
            });
            $(function () {
                $("#finish").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
    <jsp:body>

        <h2><c:if test="${book['new']}">New </c:if>Book</h2>

        <b>Pet</b>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Name</th>
                <th>Birth Date</th>
                <th>Type</th>
                <th>Owner</th>
            </tr>
            </thead>
            <tr>
                <td><c:out value="${book.pet.name}"/></td>
                <td><petclinic:localDate date="${book.pet.birthDate}" pattern="yyyy/MM/dd"/></td>
                <td><c:out value="${book.pet.type.name}"/></td>
                <td><c:out value="${book.pet.owner.firstName} ${book.pet.owner.lastName}"/></td>
            </tr>
        </table>

        <form:form modelAttribute="book" class="form-horizontal">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Start" name="start"/>
                <petclinic:inputField label="Finish" name="finish"/>
                <petclinic:inputField label="Details" name="details"/>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="petId" value="${book.pet.id}"/>
                    <button class="btn btn-default" type="submit">Add Book</button>
                </div>
            </div>
        </form:form>

    </jsp:body>

</petclinic:layout>
