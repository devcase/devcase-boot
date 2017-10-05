import $ from 'jquery';
import router from '@/router';
import withRender from './CrudDetails.html';
import polyglot from '@/polyglot';

export default withRender({
	props: ['id', 'repositoryPath', 'entityRootPath', 'fields', 'value' ],
	data: function() {
		return {
			polyglot : polyglot
		};
	},
	mounted: function() {
		this.loadData();
	},
	methods: {
		loadData: function() {
			var self = this;
			if(this.id) {
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
		edit: function() {
			router.push(this.entityRootPath + '/' + this.id + '/edit');
		}
	}
});