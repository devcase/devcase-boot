import $ from 'jquery';
import withRender from './InputField.html';



export default withRender({
	props: ['field', 'value'],
	data: function() {
		return {
		};
	},
	methods: {
		updateValue: function (value) {
			if(this.field.type == "boolean") {
				value = value == "true" || value == true;
			}
			this.$emit('input', value);
		}
	}
});