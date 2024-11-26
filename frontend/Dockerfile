# Use an official Node.js image to build the application
FROM node:14-alpine AS build
# Set the working directory inside the container
WORKDIR /app
# Copy package.json and package-lock.json to the working directory
COPY package*.json ./
# Install the dependencies
RUN npm install
# Copy the rest of the application files to the working directory
COPY . .
# Build the application for production
RUN npm run build
# List the contents of the /app directory to verify the build output
RUN ls -la /app

# Use an official Nginx image to serve the application
FROM nginx:alpine
# Copy the built files from the previous stage to the Nginx HTML directory
COPY --from=build /app/dist /usr/share/nginx/html
# Change the Nginx configuration to use port 8080 instead of the default port 80
RUN sed -i 's/80/8080/g' /etc/nginx/conf.d/default.conf
# Expose port 8080 to allow external access
EXPOSE 8080
# Start Nginx in the foreground
CMD ["nginx", "-g", "daemon off;"]
