import $ from 'jquery';
import router from '@/router';
import polyglot from '@/polyglot';
import withRender from './CrudForm.html';



export default withRender({
	props: ['id', 'repositoryPath', 'entityRootPath', 'fields', 'value' ],
	data: function() {
		return {
		};
	},
	mounted: function() {
		this.loadData();
	},
	methods: {
		loadData: function() {
			var self = this;
			if(this.$route.params.id) {
				$.get(self.repositoryPath + '/' + this.id, function(data) {
					self.$emit('input', data);
				});
			} else {
				self.$emit('input', {});
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