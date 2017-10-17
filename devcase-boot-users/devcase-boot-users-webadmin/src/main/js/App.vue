<template>
	<div id="app">
		<nav class="navbar navbar-dark bg-dark navbar-expand-lg mb-3 ">
			<router-link class="navbar-brand" :to="'/'">Users</router-link>
			
			<ul class="navbar-nav">
		      <li class="nav-item active">
		        <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
		      </li>
		     </ul>
		     
		     <form class="form-inline" action="/logout" method="POST">
		     	<input type="hidden" :name="$csrftoken.parameterName" :value="$csrftoken.token"/>
			    <button class="btn btn-default" type="submit" >{{$polyglot.t('action.logout')}}</button>
			 </form>
		</nav>
		<div class="container">
			<router-view></router-view>
		</div>
	</div>
</template>