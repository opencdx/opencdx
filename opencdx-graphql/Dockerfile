# Use an official Node runtime as a parent image
FROM node:20-alpine

# Set the working directory in the container
WORKDIR /usr/src/app

# Set port
ENV PORT=8632

# Copy package.json and package-lock.json to the working directory
COPY package*.json ./

# Install app dependencies
RUN npm install

# Copy the local source code to the container
COPY . .

# Expose port 3005 to the outside world
EXPOSE 8632

# Define environment variable
# ENV REACT_APP_API_URL=http://localhost:3000

# Command to run the application
CMD npm start