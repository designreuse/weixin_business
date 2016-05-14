<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="row">
	<div class="col-xs-12 col-sm-12">
		<form class="form-horizontal" id="form_edit_auth">
			<fieldset>
				<div class="form-group">
					<label class="col-sm-2 control-label">操作权限名称<span
						class="required">*</span>
					</label>
					<div class="col-sm-8">
						<input type="hidden" class="form-control" name="id"
							value="${auth.id}" /> <input type="text" class="form-control"
							name="name" value="${auth.name}" readonly />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">描述</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" name="descp"
							value="${auth.descp}" />
					</div>
				</div>


				<div class="form-group">
					<label class="col-sm-2 control-label">显示索引<span
						class="required">*</span>
					</label>
					<div class="col-sm-8">
						<select name="showIndex" id="showIndex"
							class="populate placeholder">
							<c:forEach var="item" varStatus="status" begin="1"
								end="${maxLength}">
								<option value="${status.index}"
									<c:if test="${status.index == auth.showIndex}"> selected </c:if>>${status.index}
								</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</fieldset>
		</form>
	</div>
</div>

<script type="text/javascript">
	function validAuth() {
		$("#form_edit_auth").bootstrapValidator({
			message : "invalid",
			fields : {
				descp : {
					validators : {
						notEmpty : {
							message : '描述信息必填'
						}
					}
				}
			}
		});
	}

	function DemoSelect2() {
		$('#showIndex').select2();
	}

	$(document).ready(function() {
		$('.form-control').tooltip();
		LoadSelect2Script(DemoSelect2);
		LoadBootstrapValidatorScript(validAuth);
		// Add drag-n-drop feature to boxes
		WinMove();

	});
</script>
