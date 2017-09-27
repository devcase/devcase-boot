import Vue from 'vue';
import Router from 'vue-router';
import ListUsersView from '@/views/ListUsersView';
import FormUserView from '@/views/FormUserView';
import DetailsUserView from '@/views/DetailsUserView';

Vue.use(Router);

export default new Router({
	routes : [
		{ path: '/', redirect : '/users/' },	
		{ path: '/users/', component: ListUsersView },
		{ path: '/users/create', component: FormUserView },
		{ path: '/users/:id', component: DetailsUserView },
		{ path: '/users/edit/:id', component: FormUserView }	
	]
});
