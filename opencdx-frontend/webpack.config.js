const path = require('path');

module.exports = {
  entry: './src/index.js', // Adjust the entry file accordingly
  output: {
    filename: 'bundle.js',
    path: path.resolve(__dirname, 'dist'),
  },
  module: {
    rules: [
      {
        test: /\.html$/,
        use: 'html-loader',
      },
      // Add other loaders for JS, CSS, etc. if needed
    ],
  },
};
