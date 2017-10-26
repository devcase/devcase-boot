import $ from 'jquery';
import router from '@/router';
import withRender from './UserFormView.html';

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
	}

});
