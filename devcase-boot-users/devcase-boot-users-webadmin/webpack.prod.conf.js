const path = require('path');
const webpack = require('webpack');
const merge = require('webpack-merge')
const baseWebpackConfig = require('./webpack.base.conf')
const ExtractTextPlugin = require('extract-text-webpack-plugin')
const OptimizeCSSPlugin = require('optimize-css-assets-webpack-plugin')

const webpackConfig = merge(
		baseWebpackConfig,
		{
			plugins : [
					new webpack.DefinePlugin({
						'process.env' : {
							NODE_ENV : JSON.stringify('production')
						}
					}),
					// UglifyJs do not support ES6+, you can also use
					// babel-minify for better
					// treeshaking: https://github.com/babel/minify
					new webpack.optimize.UglifyJsPlugin({
						compress : {
							warnings : false
						},
						sourceMap : true
					}),
					// Compress extracted CSS. We are using this plugin so that
					// possible
					// duplicated CSS from different components can be deduped.
					new OptimizeCSSPlugin({
						cssProcessorOptions : {
							safe : true
						}
					}),
					// keep module.id stable when vender modules does not change
					new webpack.HashedModuleIdsPlugin()
					
					]
		});

module.exports = webpackConfig