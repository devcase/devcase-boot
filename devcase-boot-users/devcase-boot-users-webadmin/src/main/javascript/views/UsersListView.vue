<template>
	<div>
		<router-link to="/users/create" class="btn btn-primary">Criar Usuário</router-link>
		<data-grid :columns="['name']" :name="'users'" class="mt-4"/>
	</div>
</template>

<script>
import $ from 'jquery';
import router from '@/router';


export default {
	data: function() {
		return {
		};
	}
}
</script>