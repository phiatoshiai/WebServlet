<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>Customer List</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</head>
<body>
<div class="alert alert-success">
    <h1>QUẢN LÍ KHÁCH HÀNG</h1>
    <br>
    <div class="container-fluid">
        <form action="/customers-manager" method="get">
            <label for="timkiem">
                Tìm kiếm thông tin khách hàng <i class="fa fa-search" aria-hidden="true"></i>
            </label>
            <div class="row">
                <div class="col-md-4">
                    <input type="number" class="form-control" placeholder="Nhập ID của khách hàng (là số)" name="id" id="timkiem">
                    <input type="hidden" name="action" value="search">
                </div>

                <div class="col-md-2">
                    <button type="submit" class="btn btn-dark">Tìm kiếm</button>
                </div>
            </div>
        </form>
        <div>
            <c:if test='${reqId != null}'>
                <p>Không tìm thấy khách hàng có ID cần tìm ${reqId}</p>
            </c:if>
        </div>
    </div>
</div>

<div class="container">
    <div class="row">
        <div class="col-md-8">
            <div class="text text-success text-center">
                <h3>Danh sách khách hàng</h3>
            </div>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Tên</th>
                    <th>Email</th>
                    <th>Địa chỉ</th>
                    <th>Sửa</th>
                    <th>Xóa</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${customerList}" var="customer">
                    <c:choose>
                        <c:when test="${editId==customer.getId()}">
                            <tr>
                                <form action="/customers-manager" method="POST">
                                    <input type="hidden" name="action" value="edit">
                                    <input type="hidden" name="id" value="${customer.getId()}">
                                    <td>${customer.getId()}</td>
                                    <td><input type="text" class="form-control" name="name"
                                               value="${customer.getName()}"></td>
                                    <td><input type="text" class="form-control" name="email"
                                               value="${customer.getEmail()}"></td>
                                    <td><input type="text" class="form-control" name="address"
                                               value="${customer.getAddress()}"></td>
                                    <td>
                                        <button type="submit"><i class="fa fa-save"></i></button>
                                    </td>
                                    <td><a href="/customers-manager"><i class="fa fa-times"></i></a></td>
                                </form>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td>${customer.getId()}</td>
                                <td>${customer.getName()}</td>
                                <td>${customer.getEmail()}</td>
                                <td>${customer.getAddress()}</td>
                                <td><a href="/customers-manager?action=edit&id=${customer.getId()}">
                                    <i class="fa fa-edit" aria-hidden="true"></i></a>
                                </td>
                                <td><a href="/customers-manager?action=delete&id=${customer.getId()}"
                                       onclick="return confirm('Bạn muốn xóa khách hàng này?'+${customer.getName()})">
                                    <i class="fa fa-trash"></i></a>
                                </td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col-sm-4">
            <div class="text text-success text-center">
                <h3>Đăng kí khách hàng mới</h3>
            </div>

            <form action="/customers-manager" method="post">
                <input type="hidden" name="action" value="create">
                <fieldset>
                    <table>
                        <tr>Tên</tr>
                        <tr><input type="text" class="form-control" name="name" id="name" placeholder="Nhập tên">
                        </tr>
                        <tr>Email</tr>
                        <tr><input type="text" class="form-control" name="email" id="email" placeholder="Nhập email">
                        </tr>
                        <tr>Địa chỉ</tr>
                        <tr><input type="text" class="form-control" name="address" id="address"
                                   placeholder="Nhập địa chỉ">
                            <br>
                        </tr>
                        <td><input type="submit" class="btn btn-primary" value="Tạo khách hàng"></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
            <div>
                <c:if test='${messagecreate != null}'>
                    <span class="message">${messagecreate}</span>
                </c:if>
            </div>
        </div>
    </div>
</div>
</body>
</html>