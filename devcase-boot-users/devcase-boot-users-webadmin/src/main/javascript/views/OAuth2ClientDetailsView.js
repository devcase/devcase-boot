import $ from 'jquery';
import router from '@/router';
import withRender from './OAuth2ClientDetailsView.html';


 export default withRender({
	data: function() {
		return {
			entity: {},
			entityName: 'oauth2client',
			pathPrefix: '/oauth2clients'
		}
	},
	methods: {
		list: function() {
			router.push(this.pathPrefix);
		},
		edit: function() {
			router.push(this.pathPrefix + '/edit/' + this.entity.id);
		}
	},
	mounted: function() {
		var self = this;
		if(this.$route.params.id) {
			$.get('/api' + self.pathPrefix + '/' + this.$route.params.id, function(data) {
				self.entity = data;
			});
		} else {
			self.entity = {};
		}
	}
});