import $ from 'jquery';
import router from '@/router';
import withRender from './UserFormView.html';

export default withRender({
	props: ['id'],
	data: function() {
		return {
			fields: [
				{ name: 'name', label: 'Nome', required: true, type: 'text' },
				{ name: 'locked', label: 'Bloqueado', required: true, type: 'boolean' },
				{ name: 'enabled', label: 'Habilitado', required: true, type: 'boolean' }
			]
		}
	}

});
