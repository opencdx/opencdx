# Use an official Node runtime as a parent image
FROM node:20-alpine

# Set the working directory in the container
WORKDIR /usr/src/app

# Copy package.json and package-lock.json to the working directory
COPY package*.json ./

# Install app dependencies
RUN yarn install

# Copy the local source code to the container
COPY . .

# Expose port 3000 to the outside world
EXPOSE 3000

# Define environment variable
# ENV REACT_APP_API_URL=http://localhost:3000

# Command to run the application
CMD ["npm", "start"]