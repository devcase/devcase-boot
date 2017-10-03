import $ from 'jquery';
import router from '@/router';
import withRender from './UserDetailsView.html';


 export default withRender({
	data: function() {
		return {
			user: {}
		}
	},
	methods: {
		list: function() {
			router.push('/users/');
		},
		edit: function() {
			router.push('/users/edit/' + this.user.id);
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