<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/views/common/head.jsp" %>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <!-- Left side column. contains the logo and sidebar -->
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>Vehicles</h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box">
                        <form class="form-horizontal" method="post" >
                            <c:choose>
                                <c:when test="${reservation.isPresent()}">
                                    <input type="hidden" name="id" value="${reservation.get().id()}">
                                </c:when>
                                <c:otherwise>
                                    <input type="hidden" name="id" value="">
                                </c:otherwise>
                            </c:choose>
                            <div class="box-body">
                                <div class="form-group">
                                    <label for="car" class="col-sm-2 control-label">Voiture</label>
                                    <div class="col-sm-10">
                                       <select class="form-control" id="car" name="car" placeholder="Voiture">
                                           <c:forEach items="${vehicles}" var="vehicle">
                                               <option value="${vehicle.id()}">${vehicle.constructeur()} ${vehicle.modele()}</option>
                                           </c:forEach>
                                       </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="client" class="col-sm-2 control-label">Client</label>
                                    <div class="col-sm-10">
                                        <select class="form-control" id="client" name="client" placeholder="Client">
                                            <c:forEach items="${clients}" var="client">
                                                <option value="${client.id()}">${client.prenom()} ${client.nom()}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="begin" class="col-sm-2 control-label">Date de début</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="begin" name="begin" placeholder="Debut" value="${reservation.isPresent() ? reservation.get().debut() : ''}" required data-inputmask="'alias': 'dd/mm/yyyy'" data-mask>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="end" class="col-sm-2 control-label">Date de fin</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="end" name="end" placeholder="Fin" value="${reservation.isPresent() ? reservation.get().fin() : ''}" required data-inputmask="'alias': 'dd/mm/yyyy'" data-mask>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-10">
                                        <c:if test="${not empty requestScope.rentError}">
                                            <p style="color: red;"><c:out value="${requestScope.rentError}" /></p>
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
