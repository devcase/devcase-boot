import $ from 'jquery';
import router from '@/router';
import withRender from './FormUserView.html';

 export default withRender({
		data: function() {
			return {
				user: {}
			}
		},
		methods: {
			save: function(user) {
				$.ajax({
					url: (user.id ? '/api/users/' + user.id : '/api/users'),
					data: JSON.stringify(user),
					contentType: 'application/json',
					method: (user.id ? 'patch' : 'post'),
					success: function(data) {
						router.push('/users/');
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
				$.get('/api/users/' + this.$route.params.id, function(data) {
					self.user = data;
				});
			} else {
				self.user = {};
			}
		}
	});
	
