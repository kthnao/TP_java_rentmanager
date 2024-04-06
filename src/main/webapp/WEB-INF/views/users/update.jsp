<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <!-- Left side column. contains the logo and sidebar -->
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                Vehicles
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box">
                        <form class="form-horizontal" method="post">
                            <c:choose>
                                <c:when test="${client.isPresent()}">
                                    <input type="hidden" name="id" value="${client.get().id()}">
                                </c:when>
                                <c:otherwise>
                                    <input type="hidden" name="id" value="">
                                </c:otherwise>
                            </c:choose>
                            <div class="box-body">
                                <div class="form-group">
                                    <label for="last_name" class="col-sm-2 control-label">Nom</label>
                                    <div class="col-sm-10">
                                       <input type="text" class="form-control" id="nom" name="last_name" placeholder="Nom" value="${client.isPresent() ? client.get().nom() : ''}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="first_name" class="col-sm-2 control-label">Prenom</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="prenom" name="first_name" placeholder="Prenom" value="${client.isPresent() ? client.get().prenom() : ''}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="email" class="col-sm-2 control-label">Email</label>
                                    <div class="col-sm-10">
                                        <input type="email" class="form-control" id="email" name="email" placeholder="Email" value="${client.isPresent() ? client.get().email() : ''}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="birthdate" class="col-sm-2 control-label">Naissance</label>
                                    <div class="col-sm-10">
                                        <input type="date" class="form-control" id="naissance" name="birthdate" placeholder="Naissance" value="${client.isPresent() ? client.get().naissance() : ''}">
                                    </div>
                                    <div class="col-sm-10">
                                        <c:if test="${not empty requestScope.clientError}">
                                                <p style="color: red;"><c:out value="${requestScope.clientError}" /></p>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                            <!-- /.box-body -->
                            <div class="box-footer">
                                <button type="submit" class="btn btn-info pull-right">Modifier</button>
                            </div>
                            <!-- /.box-footer -->
                        </form>
                    </div>
                    <!-- /.box -->
                </div>
                <!-- /.col -->
            </div>
        </section>
        <!-- /.content -->
    </div>

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<!-- ./wrapper -->

<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
</body>
</html>
