import $ from 'jquery';
import withRender from './InputField.html';
import polyglot from "@/polyglot";


export default withRender({
	props: ['field', 'value' ],
	data: function() {
		return {
			polyglot: polyglot
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