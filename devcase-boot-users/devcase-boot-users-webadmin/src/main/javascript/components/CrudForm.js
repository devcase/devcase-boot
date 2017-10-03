import $ from 'jquery';
import router from '@/router';
import withRender from './CrudForm.html';
import InputField from './InputField';



export default withRender({
	props: ['id', 'repositoryPath', 'entityRootPath', 'fields'],
	data: function() {
		return {
			entity : {}
		};
	},
	components: { 'input-field':  InputField },
	mounted: function() {
		this.loadData();
	},
	methods: {
		loadData: function() {
			var self = this;
			if(this.$route.params.id) {
				$.get(self.repositoryPath + '/' + this.id, function(data) {
					self.entity = data;
				});
			} else {
				self.entity = {};
			}
		},
		cancel: function() {
			router.go(-1);
		},
		save: function(entity) {
			var self = this;
			$.ajax({
				url: (entity.id ? self.repositoryPath + '/' + entity.id : self.repositoryPath),
				data: JSON.stringify(entity),
				contentType: 'application/json',
				method: (entity.id ? 'patch' : 'post'),
				success: function(data) {
					router.go(-1);
				}
			});
		}
	}
});