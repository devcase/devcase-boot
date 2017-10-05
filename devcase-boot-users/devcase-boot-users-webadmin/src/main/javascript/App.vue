<template>
	<div id="app">
		<nav class="navbar navbar-dark bg-dark navbar-expand-lg mb-3">
			<router-link class="navbar-brand" :to="'/'">Users</router-link>
		</nav>
		<div class="container">
			<router-view></router-view>
		</div>
	</div>
</template>