<%@ tag trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://boot.devcase.com.br/dwf" prefix="dwf"%>
<%@ attribute name="columns" required="true" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="page" required="false" type="org.springframework.data.domain.Page"%>
<c:set var="maxXsCols" value="1" />
<jsp:useBean id="queryStringBuilder" class="br.com.devcase.boot.dwftaglibs.util.QueryStringBuilder" />
<c:set var="queryStringBuilder" value="${queryStringBuilder.fromRequest(pageContext.request)}"/>
<c:set var="pagePath" value="/${pathPrefix}"/><!-- CrudController -->
<table class="table table-sm table-hover">
	<thead>
		<c:forTokens items="${columns}" delims="," var="column" varStatus="loopStatus">
			<th class="${loopStatus.count > maxXsCols ? 'd-none d-sm-table-cell' : ''}">
				<c:set var="sortDirection" value="${pageable.sort.getOrderFor(column).direction}"/>
				<a class="${empty sortDirection ? 'text-dark' : '' }" href="${pagePath}${queryStringBuilder.put('sort', sortDirection eq 'ASC' ? column.concat(',DESC') : column).buildStartingWith('?')}"><dwf:labelTextFor property="${column}" />
					<c:if test="${sortDirection eq 'ASC'}"><i class="ion-arrow-up-b ml-2"></i></c:if>
					<c:if test="${sortDirection eq 'DESC'}"><i class="ion-arrow-down-b ml-2"></i></c:if>
				</a>
			</th>
		</c:forTokens>
	</thead>
	<tbody>
		<c:forEach items="${page.content}" var="entity">
			<tr>
				<c:forTokens items="${columns}" delims="," var="column" varStatus="loopStatus">
					<dwf:resolveEL el="entity.${column}" var="value" />
					<c:choose>
						<c:when test="${loopStatus.count eq 1}">
							<td class="${loopStatus.count > maxXsCols ? 'd-none d-sm-table-cell' : ''}">
								<a href="/${entityName}/${entity.id}"><dwf:autoFormat value="${value}" /></a></td>
						</c:when>
						<c:otherwise>
							<td class="${loopStatus.count > maxXsCols ? 'd-none d-sm-table-cell' : ''}"><dwf:autoFormat value="${value}" /></td>
						</c:otherwise>
					</c:choose>
				</c:forTokens>
			</tr>
		</c:forEach>
	</tbody>
</table>
<!-- PAGINATOR -->
<c:if test="${page.totalPages > 1}">
<nav aria-label="Navegação por páginas" class="mt-3">
	<ul class="pagination justify-content-center">
		<!-- previous page -->
		<li class="page-item ${page.first ? 'disabled' : ''}"><a class="page-link"
			href="${pagePath}${queryStringBuilder.put('page', page.number - 1).buildStartingWith('?')}"
		><spring:message code="action.previous" /></a></li>

		<!-- previous page -->
		<c:forEach begin="0" end="${page.totalPages - 1}" var="pageNum">
			<li class="page-item ${pageNum eq page.number ? 'active' : '' }"><a class="page-link"
				href="${pagePath}${queryStringBuilder.put('page', pageNum).buildStartingWith('?')}"
			><fmt:formatNumber value="${pageNum + 1}"/></a></li>
		</c:forEach>

		<!-- next page -->
		<li class="page-item ${page.last ? 'disabled' : ''}"><a class="page-link"
			href="${pagePath}${queryStringBuilder.put('page', page.number + 1).buildStartingWith('?')}"
		><spring:message code="action.next" /></a></li>
	</ul>
</nav>
</c:if>
