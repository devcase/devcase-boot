import $ from 'jquery';
import router from '@/router';
import withRender from './MenuView.html';


export default withRender({
	data: function() {
		return {
			items : [
				{
					label: "domain.user.plural",
					path: "/users"
				},
				{
					label: "domain.oAuth2Client.plural",
					path: "/oauth2clients"
				}

			]
		}
	}
});