import $ from 'jquery';
import router from '@/router';
import withRender from './OAuth2ClientFormView.html';

export default withRender({
	props: ['id'],
	data: function() {
		return {
			fields: [
				{ name: 'name', label: 'Nome', required: true, type: 'text' },
				{ name: 'clientId', label: 'Client Id', required: true, type: 'text' },
				{ name: 'clientSecret', label: 'Client Secret', required: true, type: 'text' },
				{ name: 'authorizedGrantTypes', label: 'Authorized Grant Types', required: true, type: 'text' },
				{ name: 'authorities', label: 'Authorities', required: false, type: 'text' },
				{ name: 'accessTokenValidity', label: 'Access Token Validity', required: true, type: 'number' },
				{ name: 'refreshTokenValidity', label: 'Refresh Token Validity', required: true, type: 'number' },
				{ name: 'autoApprove', label: 'Aprovação automática', required: true, type: 'boolean' },
				{ name: 'scope', label: 'Escopo', required: true, type: 'text' }
			]
		}
	}

});

