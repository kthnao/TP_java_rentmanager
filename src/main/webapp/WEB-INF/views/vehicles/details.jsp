<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
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
        <!-- Main content -->
        <section class="content">

            <div class="row">
                <div class="col-md-3">

                    <!-- Profile Image -->
                    <div class="box box-primary">
                        <div class="box-body box-profile">
                            <h3 class="profile-username text-center">${vehicle.constructeur()}</h3>
                            <h3 class="profile-username text-center">${vehicle.modele()}</h3>
                            <h3 class="profile-username text-center">${vehicle.nb_places()} Places </h3>

                            <ul class="list-group list-group-unbordered">
                                <li class="list-group-item">
                                    <b>Nombre de reservations</b> <span class="pull-right">${nbRents}</span>
                                </li>
                                <li class="list-group-item">
                                    <b>Nombre d utilisateurs</b> <span class="pull-right">${nbClients}</span>
                                </li>
                            </ul>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->
                </div>
                <!-- /.col -->
                <div class="col-md-9">
                    <div class="nav-tabs-custom">
                        <ul class="nav nav-tabs">
                            <li class="active"><a href="#rents" data-toggle="tab">Reservations</a></li>
                            <li><a href="#users" data-toggle="tab">Utilisateur</a></li>
                        </ul>
                        <div class="tab-content">
                            <div class="active tab-pane" id="rents">
                                <div class="box-body no-padding">
                                    <table class="table table-striped">
                                        <tr>
                                            <th style="width: 10px">#</th>
                                            <th>Utilisateur</th>
                                            <th>Date de debut</th>
                                            <th>Date de fin</th>
                                        </tr>
                                        <c:forEach items="${rents}" var="rent" varStatus="loop">
                                            <tr>
                                                <td>${rent.id()}</td>
                                                <td>${clients[loop.index].nom()} ${clients[loop.index].prenom()}</td>
                                                <td>${rent.debut()}</td>
                                                <td>${rent.fin()}</td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </div>
                            </div>
                            <!-- /.tab-pane -->
                                <div class="tab-pane" id="users">
                                    <!-- /.box-header -->
                                    <div class="box-body no-padding">
                                        <table class="table table-striped">
                                            <tr>
                                                <th style="width: 10px">#</th>
                                                <th>Nom</th>
                                                <th>Email</th>
                                                <th style=>Naissance</th>
                                            </tr>
                                            <c:forEach items="${clients}" var="client">
                                                <tr>
                                                    <td>${client.id()}.</td>
                                                    <td>${client.nom()} ${client.prenom()}</td>
                                                    <td>${client.email()}</td>
                                                    <td>${client.naissance()}</td>
                                                </tr>
                                            </c:forEach>
                                        </table>
                                    </div>
                                </div>
                            <!-- /.tab-pane -->
                        </div>
                        <!-- /.tab-content -->
                    </div>
                    <!-- /.nav-tabs-custom -->
                </div>
                <!-- /.col -->
            </div>
            <!-- /.row -->

        </section>
        <!-- /.content -->
    </div>

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<!-- ./wrapper -->

<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
</body>
</html>
