import $ from 'jquery';
import router from '@/router';
import withRender from './MenuView.html';


 export default withRender({
	data: function() {
		return {
			items : [
				{
					label: "Usuários",
					path: "/users"
				},
				{
					label: "Clientes OAuth2",
					path: "/oauth2clients"
				}

			]
		}
	}
});