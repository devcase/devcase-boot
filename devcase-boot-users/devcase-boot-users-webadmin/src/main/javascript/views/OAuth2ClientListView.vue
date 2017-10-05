<template>
	<div>
		<router-link to="/oauth2clients/create" class="btn btn-primary">Criar</router-link>
		<data-grid :columns="['name']" :name="'oauth2clients'" class="mt-4"/>
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