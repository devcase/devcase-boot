import Vue from 'vue';
import Router from 'vue-router';
import UsersListView from '@/views/UsersListView';
import UserFormView from '@/views/UserFormView';
import UserDetailsView from '@/views/UserDetailsView';
import OAuth2ClientListView from '@/views/OAuth2ClientListView';
import OAuth2ClientFormView from '@/views/OAuth2ClientFormView';
import OAuth2ClientDetailsView from '@/views/OAuth2ClientDetailsView';
import MenuView from '@/views/MenuView';

Vue.use(Router);

export default new Router({
	routes : [
		{ path: '/users/', component: UsersListView },
		{ path: '/users/create', component: UserFormView },
		{ path: '/users/:id', component: UserDetailsView , props: true },
		{ path: '/users/:id/edit', component: UserFormView , props: true },	
		{ path: '/oauth2clients/', component: OAuth2ClientListView },
		{ path: '/oauth2clients/create', component: OAuth2ClientFormView },
		{ path: '/oauth2clients/:id', component: OAuth2ClientDetailsView , props: true},
		{ path: '/oauth2clients/:id/edit', component: OAuth2ClientFormView, props: true },
		{ path: '/', component : MenuView }
	]
});
