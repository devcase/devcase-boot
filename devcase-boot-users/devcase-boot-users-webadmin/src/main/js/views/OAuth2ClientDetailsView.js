import $ from 'jquery';
import router from '@/router';
import withRender from './OAuth2ClientDetailsView.html';

export default withRender({
	props: ['id'],
	data: function() {
		return {
			entity: {},
			fields: [
				{ name: 'name', entityName: 'oAuth2Client', required: true, type: 'text' },
				{ name: 'clientId', entityName: 'oAuth2Client', required: true, type: 'text' },
				{ name: 'clientSecret', entityName: 'oAuth2Client', required: true, type: 'text' },
				{ name: 'authorizedGrantTypes', entityName: 'oAuth2Client', required: true, type: 'text' },
				{ name: 'authorities', entityName: 'oAuth2Client', required: false, type: 'text' },
				{ name: 'accessTokenValidity', entityName: 'oAuth2Client', required: true, type: 'number' },
				{ name: 'refreshTokenValidity', entityName: 'oAuth2Client', required: true, type: 'number' },
				{ name: 'autoApprove', entityName: 'oAuth2Client', required: true, type: 'boolean' },
				{ name: 'scope', entityName: 'oAuth2Client', required: true, type: 'text' }
			]
		}
	}

});

