import $ from 'jquery';
import router from '@/router';
import withRender from './MenuView.html';
import polyglot from '@/polyglot';


export default withRender({
	data: function() {
		return {
			polyglot : polyglot,
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