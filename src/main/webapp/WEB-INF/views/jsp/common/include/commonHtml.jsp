<%@ page pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="modal fade" id="notiModal">
    <div class="modal-dialog" style="width: 25%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title"><spring:message code="myhub.label.notification"/></h4>
            </div>
            <div class="modal-body">
                <p>
                    <div id="notiMsg"></div>
                </p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal" id="btnFirst" style="display:none;">
                    <spring:message code="myhub.label.select.yes"/>
                </button>
                <button type="button" class="btn btn-info" data-dismiss="modal" id="btnSecond" style="display:none;">
                    <spring:message code="myhub.label.select.no"/>
                </button>
                <button type="button" class="btn btn-default" data-dismiss="modal" id="btnThird" style="display:none;">
                    <spring:message code="myhub.label.close"/>
                </button>
            </div>
        </div>
    </div>
</div>

<!-- 비밀번호 수정창 -->
<div class="modal fade" id="passwordEditModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">비밀번호 수정</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" onsubmit="return false;">
                    <div class="form-group">
                        <label for="password" class="col-sm-3 control-label"><spring:message code="myhub.label.password"/></label>
                        <div class="col-sm-5">
                            <input type="password" class="form-control" id="password" name="password" placeholder="Password" maxlength="12" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="password" class="col-sm-3 control-label"><spring:message code="myhub.label.password"/></label>
                        <div class="col-sm-5">
                            <input type="password" class="form-control" id="password" name="password" placeholder="Password" maxlength="12" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="cfPassword" class="col-sm-3 control-label"><spring:message code="myhub.label.confirm.password"/></label>
                        <div class="col-sm-5">
                            <input type="password" class="form-control" id="cfPassword" name="cfPassword" placeholder="Password" maxlength="12" value="">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">
                                        수정
                </button>
                <button type="button" class="btn btn-primary" data-dismiss="modal">
                    <spring:message code="myhub.label.close"/>
                </button>
            </div>
        </div>
    </div>
</div>

