<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<sec:authentication property="principal" var="authentication" />
<div class="row">
	<div class="col-xs-9 col-sm-9">
		<form class="form-horizontal" id="form_create_config">
			<fieldset>
				<div class="form-group">
					<label class="col-sm-3 control-label">第三方名称<span
						class="required">*</span> </label>
					<div class="col-sm-9">
						
						${config.name}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">远程验证地址<span
						class="required">*</span> </label>
					<div class="col-sm-9">
						${config.remoteUrl }
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-3 control-label">处理类<span
						class="required">*</span> </label>
					<div class="col-sm-9">
						 ${config.className }
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">其他参数<span
						class="required">*</span> </label>
					<div class="col-sm-9">
						  ${config.other }
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">组名称<span
						class="required">*</span> </label>
					<div class="col-sm-9">
						${config.groupName }
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">成员<span
						class="required">*</span> </label>
					<div class="col-sm-9">
						${userCount}
					</div>
				</div>
				
				
				
			</fieldset>
		</form>
	</div>
	 
</div>