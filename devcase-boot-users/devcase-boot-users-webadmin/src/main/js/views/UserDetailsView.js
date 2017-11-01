import $ from 'jquery';
import router from '@/router';
import polyglot from '@/polyglot';
import withRender from './UserDetailsView.html';


export default withRender({
	props: ['id'],
	data: function() {
		return {
			entity: {},
			fields: [
				{ name: 'username', entityName: 'user', required: true, type: 'text' },
				{ name: 'locked', entityName: 'user', required: true, type: 'boolean' },
				{ name: 'enabled', entityName: 'user', required: true, type: 'boolean' }
			]
		}
	},
	methods: {
		t2: polyglot.t
	}


});
