const path = require('path');
var webpack = require('webpack');

module.exports = {
	entry : './src/main/javascript/main.js',
	output : {
		path : path.resolve(__dirname, 'src/main/webapp/js'),
		filename : 'webadmin.bundle.js'
	},
	module: {
	    rules: [
	        {
	          test: /\.vue$/,
	          loader: 'vue-loader',
	          options: {
	            loaders: {
	              // Since sass-loader (weirdly) has SCSS as its default parse mode, we map
	              // the "scss" and "sass" values for the lang attribute to the right configs here.
	              // other preprocessors should work out of the box, no loader config like this necessary.
	              'scss': 'vue-style-loader!css-loader!sass-loader',
	              'sass': 'vue-style-loader!css-loader!sass-loader?indentedSyntax'
	            }
	          }
	        },
            { 	
	        	test: /\.html$/, 
	        	loader: 'vue-template-loader'
	        },
	        {
	          test: /\.js$/,
	          loader: 'babel-loader',
	          exclude: /node_modules/
	        },
	        {
	          test: /\.(png|jpg|gif|svg)$/,
	          loader: 'file-loader',
	          options: {
	            name: '[name].[ext]?[hash]'
	          }
	        }
	      ]
	},
	resolve: {
	    extensions: ['.js', '.vue', '.json'],
	    alias: {
	      'vue$': 'vue/dist/vue.esm.js',
	      '@': path.resolve(__dirname, 'src/main/javascript'),
	    }
	  },
//	plugins : [ new webpack.DefinePlugin({
//		'process.env' : {
//			NODE_ENV : JSON.stringify('production')
//		}
//	})]
};