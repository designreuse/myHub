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
                <div id="changeButton" style="display:none;">
                    <button type="button" class="btn btn-default" data-dismiss="modal" id="selectYes" style="display:none;">
                        <spring:message code="myhub.label.select.yes"/>
                    </button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal" id="selectNo">
                        <spring:message code="myhub.label.select.no"/>
                    </button>
                </div>
                <div id="defaultButton" style="display:none;">
	                <button type="button" class="btn btn-primary" data-dismiss="modal" id="selectNo">
	                    <spring:message code="myhub.label.close"/>
	                </button>
                </div>
            </div>
        </div>
    </div>
</div>

