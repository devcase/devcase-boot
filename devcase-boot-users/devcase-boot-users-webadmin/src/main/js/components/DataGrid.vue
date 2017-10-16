<template>
		<div>
			<table class="table table-sm table-hover" v-if="contents">
			<thead>
				<th v-for="column in columns">
					{{column}}
				</th>
			</thead>
			<tbody>
				<tr v-for="entity in contents" >
					<td v-for="column in columns">
						<router-link :to="'/' + name + '/' + entity.id.toString()" >{{entity[column]}}</router-link>
					</td>
				</tr>
			</tbody>
			</table>
			<!-- PAGINATOR -->
			<nav aria-label="Paginador" class="mt-3" v-if="page.totalPages > 1">
				<ul class="pagination justify-content-center">
					<!-- previous page -->
					<li class="page-item" v-bind:class="{ active : page.current }" v-for="page in pages" >
						<a class="page-link" href="#" @click.prevent="changePage(page.number)">{{page.number + 1}}</a>
					</li>
				</ul>
			</nav>
			</div>
</template>

<script>

import $ from 'jquery';
import router from '@/router';


export default {
	props: ['name', 'columns'],
		data: function() {
			return {
				results: {},
				contents: [],
				page: {
					number: 0,
					size: 5,
					totalPages : 0,
					totalElements: 0
				}
			};
		},
		mounted: function() {
			this.loadData();
		},
		methods: {
			changePage: function(pageNum) {
				this.page.number = pageNum;
				this.loadData();
			},
			loadData: function() {
				var self = this;
				$.ajax({
					url: '/api/' + self.name + '?size=' + self.page.size + '&page=' + self.page.number,
					success: function(data) {
						self.results = data;
						self.contents = Object.values(data["_embedded"])[0];
						self.page = data['page'];
					},
					error: function() {
					}
				});, 
			}
		}, 
		computed: {
			pages: function() {
				var arr = [];
				if(this.results.page) {
					for(var i = 0; i < this.results.page.totalPages; i++) {
						arr.push({
							number: i,
							current: i == this.results.page.number
						})
					}
				}
				return arr;
			}	
		}
	}
</script>