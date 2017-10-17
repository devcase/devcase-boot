import polyglot from 'node-polyglot';
import $ from 'jquery';

var p = new polyglot();

$.ajax({
	url: '/messagesource',
	method: "GET",
	success: function(messages) {
		p.extend(messages);
		$(document).trigger('messagesource.loaded');
	}
});


export default p;