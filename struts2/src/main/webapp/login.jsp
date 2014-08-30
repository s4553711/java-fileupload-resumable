<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>    
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<META HTTP-EQUIV="EXPIRES" CONTENT="0"> 
		<title>Insert title here</title>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" type="text/css" />
		<script type="text/javascript" src="https://code.jquery.com/jquery-1.11.0.min.js"></script>
		<script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="js/resumable.js"></script>
		<script type="text/javascript" src="js/main.js"></script>
	</head>
	<body>
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<h3>Welcome User, please login first</h3>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<p id="successMsg" class="bg-success" style="display:none">Uploaded Success</p>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<form id="fileUploadform" class="form" action="login" method="POST" enctype="multipart/form-data">
						<div class="form-group">
							<label for="InputID">UserID</label>
							<input type="text" name="name" class="form-control" id="InputID" placeholder="Enter ID">
						</div>
						<div class="form-group">
							<label for="InputPass">Password</label>
							<input type="password" name="pwd" class="form-control" id="InputPass" placeholder="Password">
						</div>
						<div class="form-group">
							<label for="vcfile">Input File</label>
							<input type="file" name="vcfile" size="40" value="" id="vcfile">
						</div>
						<button type="submit" class="btn btn-default" id="uploadBtn">Login</button>
					</form>
					<button type="submit" class="btn btn-default" id="uploadBtn2">Login2</button>
				</div>
			</div>
		</div>
	</body>
</html>