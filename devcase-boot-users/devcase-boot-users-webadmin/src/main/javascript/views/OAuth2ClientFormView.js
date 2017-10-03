import $ from 'jquery';
import router from '@/router';
import withRender from './OAuth2ClientFormView.html';

export default withRender({
		data: function() {
			return {
				entity: {},
				entityName: 'oauth2client',
				pathPrefix: '/oauth2clients'
			}
		},
		methods: {
			save: function(entity) {
				var self = this;
				$.ajax({
					url: (entity.id ? '/api' + self.pathPrefix + '/' + entity.id : '/api' + self.pathPrefix),
					data: JSON.stringify(entity),
					contentType: 'application/json',
					method: (entity.id ? 'patch' : 'post'),
					success: function(data) {
						router.push(self.pathPrefix + '/' + (entity.id ? entity.id : ''));
					}
				});
			},
			cancel: function() {
				router.go(-1);
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
	
