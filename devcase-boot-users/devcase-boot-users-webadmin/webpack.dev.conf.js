const path = require('path');
const webpack = require('webpack');
const merge = require('webpack-merge')
const baseWebpackConfig = require('./webpack.base.conf')

const webpackConfig = merge(baseWebpackConfig, {
	plugins : [ new webpack.DefinePlugin({
		'process.env' : {
			NODE_ENV : JSON.stringify('development')
		}
	}) ]
})

module.exports = webpackConfig