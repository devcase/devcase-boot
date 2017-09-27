import Vue from 'vue';
import $ from 'jquery';
import App from '@/App';
import router from '@/router';
import DataGrid from '@/components/DataGrid.vue';

$(function() {
	//setup jquery.ajax to send csrf token each post
	$(document).ajaxSend(function( event, xhr, settings ) {
		xhr.setRequestHeader(csrftoken.name, csrftoken.token);
	});
});

Vue.directive('customvalidation', {
	inserted: function(el, binding, vnode) {
		$(el).attr('novalidate', '');
		el.addEventListener("submit", function(event) {
			event.preventDefault();
			event.stopPropagation();
			if(el.checkValidity()) {
				el.dispatchEvent(new Event('validsubmit'));
			} else {
				el.classList.add("was-validated");
			}
		});
		$(el).find('input:first').each(function() {this.focus()});
	}
});

Vue.component('data-grid', DataGrid);

var app = new Vue({
	router,
	render: h => h(App)
}).$mount('#app');
	
