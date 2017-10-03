import $ from 'jquery';
import router from '@/router';
import withRender from './CrudDetails.html';



export default withRender({
	props: ['id', 'repositoryPath', 'entityRootPath', 'fields', 'entityName'],
	data: function() {
		return {
			entity : {}
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
					self.entity = data;
				});
			} else {
				self.entity = {};
			}
		},
		cancel: function() {
			router.go(-1);
		},
		edit: function() {
			router.push(this.entityRootPath + '/' + this.id + '/edit');
		}
	}
});